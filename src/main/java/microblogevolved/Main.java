package microblogevolved;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by darionmoore on 12/17/16.
 */
public class Main {
    static User user;
    static String password;
    static ArrayList<String> messages = new ArrayList();
    static HashMap<String, User> userList = new HashMap<>();


    public static void main(String[] args) {
        Spark.init();

//        Create a GET route for "/" and a POST route for "/create-user" and "/create-message".
        Spark.get("/", ((request, response) -> {
            HashMap model = new HashMap();
            if(userList.containsValue(user)){
                userList.containsKey(password);
                return new ModelAndView(model, "Index.html");
            }
            else if(user == null) {
                return new ModelAndView(model, "Index.html");
            } else {
                model.put("user", user);
                model.put("messages", messages);
                return new ModelAndView(model, "messages.html");
            }
        }), new MustacheTemplateEngine());

        Spark.post("/Index", ((request, response) -> {
            String createUser = request.queryParams("user");
            String createPassword = request.queryParams("password");
            user = new User(createUser);
            password = new String(createPassword);
            response.redirect("/");
            return "";
        }));

        Spark.post("/messages", ((request, response) -> {
            String createPost = request.queryParams("messages");
            messages.add(createPost);
            response.redirect("/");
            return "";
        }));

    }
}
