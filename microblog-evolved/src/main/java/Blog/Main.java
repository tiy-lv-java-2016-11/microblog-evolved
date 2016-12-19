package Blog;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

/**
 * Created by sparatan117 on 12/14/16.
 */
public class Main {
    public static final String LOGIN_NAME = "loginName";
    static HashMap<String, User> users = new HashMap<>();


    public static void main(String[] args) {
        Spark.init();
        Spark.get("/", ((request, response) -> {
            Session session = request.session();
            String loginName = session.attribute(LOGIN_NAME);
            User user = users.get(loginName);
            HashMap users = new HashMap();
            users.put("user", user);

            if (user == null){
                return new ModelAndView(users, "login.html");
            }
            else {
                return new ModelAndView(users, "home.html");
            }
        }), new MustacheTemplateEngine());



        Spark.get("/home", ((request, response)->{
            Session session = request.session();
            String loginName = session.attribute(LOGIN_NAME);
            User user = users.get(loginName);
            if(user == null){
                throw new Exception("not logged in");
            }

            int id = Integer.parseInt(request.queryParams("id"));
            Post message = user.getMessage(id);

            HashMap users = new HashMap();
            users.put("user", user);
            users.put("message", message);

            return new ModelAndView(message, "/message_detail.html");
        }), new MustacheTemplateEngine());

        Spark.post("/login", ((request, response)->{
            String name = request.queryParams(LOGIN_NAME);
            String pass = request.queryParams("password");

            Session session = request.session();
            session.attribute(LOGIN_NAME, name);

            User user = users.get(name);

            if (user != null && user.getLoginName().equals(name)){
                if(!user.getPassword().equals(pass)){
                    throw new Exception("that's the wrong answer");
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

        Spark.post("/add-message", (((request, response) -> {
            Session session = request.session();
            String loginName = session.attribute(LOGIN_NAME);
            User user = users.get(loginName);

            if (user == null){
                throw new Exception("not logged in");
            }

            String message = request.queryParams("message");
            int id = Post.addId(user.getMessages());

            user.addMessage(new Post(id, message));
            response.redirect("/");
            return "";
        }
        )));

        Spark.post("/update-message", (request, response) -> {
            Session session = request.session();
            String loginName = session.attribute(LOGIN_NAME);
            User user = users.get(loginName);
            if(user == null){
                throw new Exception("not logged in");
            }

            int id = Integer.parseInt(request.queryParams("id"));
            String message = request.queryParams("message");
            user. updateMessage(id, message);
            response.redirect("/");
            return "";
        });

        Spark.post("/logout", ((request, response) -> {
            Session session = request.session();
            session.invalidate();
            response.redirect("/");
            return "";
        }));
    }
}
