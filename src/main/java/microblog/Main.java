package microblog;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by melmo on 12/14/16.
 */
public class Main {
    public static final String SESSION_USER_NAME = "username";
    static Map<String, User> users = new HashMap<>();


    public static void main(String[] args) {
        Spark.init();

        /* *
         * Start/Home page
         * Gets the current user from the session,
         * If no user is logged in, goes to login page,
         * If a user is logged in, goes to messages page
         * */
        Spark.get("/", ((request, response) -> {
            Session session = request.session();
            String username = session.attribute(SESSION_USER_NAME);
            User user = users.get(username);

            HashMap m = new HashMap();
            m.put("user", user);

            if (user == null){
                return new ModelAndView(m, "index.html");
            }
            else {
                return new ModelAndView(m, "messages.html");
            }
        }), new MustacheTemplateEngine());

        /* *
         * Receives info from form on login page
         * Assigns user to session
         * Gets user from 'users'
         * If user exists, verifies password
         * If user doesn't exist, creates user and adds to 'users'
         * Then redirects to Start/Home page
         * */
        Spark.post("/create-user", ((request, response) -> {
            String name = request.queryParams("username");
            String pass = request.queryParams("password");

            Session session = request.session();
            session.attribute(SESSION_USER_NAME, name);

            User user = users.get(name);

            if (user != null && user.getUsername().equals(name)){
                if (!user.getPassword().equals(pass)){
                    throw new Exception("You entered the wrong password");
                }
                else {
                    response.redirect("/");
                }
            }
            else {
                user = new User(name, pass);
                users.put(name, user);
                response.redirect("/");
            }
            return "";
        }));

        /* *
         * Receives input from Submit on messages page
         * Verifies a user is assigned to the session, and gets user form 'users'
         * Creates 'Message' from input and adds to user's List of 'messages'
         * Redirects to Start/Home page
         * */
        Spark.post("/create-message", ((request, response) -> {
            Session session = request.session();
            String username = session.attribute(SESSION_USER_NAME);
            User user = users.get(username);
            if (user == null){
                throw new Exception("User is not logged in.");
            }

            String content = request.queryParams("content");
            int id = Message.getNextId(user.getMessages());

            user.addMessage(new Message(id, content));
            response.redirect("/");
            return "";
        }));

        /* *
         * Logs out the current user,
         * Invalidate the current session info
         * Redirects to Start/Home
         * */
        Spark.post("/logout", ((request, response) -> {
            Session session = request.session();
            session.invalidate();
            response.redirect("/");
            return "";
        }));

        /* *
         * Receives input from link to single message on messages page
         * Verifies a user is assigned to the session, and gets user form 'users'
         * Gets message 'id' from input, gets message from user's messages
         * Assigns 'user' and 'message' to model and redirects to message detail page
         * */
        Spark.get("/message", ((request, response) -> {
            Session session = request.session();
            String username = session.attribute(SESSION_USER_NAME);
            User user = users.get(username);
            if (user == null){
                throw new Exception("User is not logged in.");
            }

            int id = Integer.parseInt(request.queryParams("id"));
            Message message = user.getMessage(id);

            HashMap m = new HashMap();
            m.put("user", user);
            m.put("message", message);

            return new ModelAndView(m, "/message_detail.html");
        }), new MustacheTemplateEngine());

        /* *
         * Receives input from 'Delete' on message detail
         * Gets message 'id' from input and removes message from user's messages
         * Redirects to Start/Home page
         * */
        Spark.post("/delete", ((request, response) -> {
            Session session = request.session();
            String username = session.attribute(SESSION_USER_NAME);
            User user = users.get(username);
            if (user == null){
                throw new Exception("User is not logged in.");
            }
            int id = Integer.parseInt(request.queryParams("id"));
            user.removeMessage(id);
            response.redirect("/");
            return "";
        }));

        /* *
         * Receives input from 'Update' on message detail page
         * Gets message 'id' and 'content'from input
         * Overwrites the message at the 'id' in the user's 'messages'
         * Redirects to Start/Home page
         * */
        Spark.post("/update_message", ((request, response) -> {
            Session session = request.session();
            String username = session.attribute(SESSION_USER_NAME);
            User user = users.get(username);
            if (user == null){
                throw new Exception("User is not logged in.");
            }
            int id = Integer.parseInt(request.queryParams("id"));
            String content = request.queryParams("content");
            user.updateMessage(id, content);
            response.redirect("/");
            return "";
        }));
    }
}
