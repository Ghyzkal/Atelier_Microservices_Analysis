import com.google.gson.Gson;
import model.User;
import service.UserService;

import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.SparkBase.setPort;

public class UserDomain {
    private static UserService userService = new UserService();
    private static int PORT = 4567;
    private static HashMap<String, String> HTTP_PATH = UserDomain.initHashMap();

    public static void main(String[] args) {
        Gson gson = new Gson();

        setPort(UserDomain.PORT);

        get(UserDomain.HTTP_PATH.get("display").split(";")[1], (req, res) -> {
            res.type("application/json");
            return userService.getUsers();
        }, gson ::toJson);

        get(UserDomain.HTTP_PATH.get("search").split(";")[1], (req, res) -> {
            String firstname = req.params(":firstname");
            return userService.getUser(firstname);
        },gson ::toJson);

        post(UserDomain.HTTP_PATH.get("add").split(";")[1], (req, res) -> {
            res.type("application/json");
            User user = gson.fromJson(req.body(), User.class);
            return userService.addUser(user);
        }, gson ::toJson);

        options(UserDomain.HTTP_PATH.get("options").split(";")[1], (req, res) -> {
            res.type("application/json");
            //Conversion d'un hasmap<String, String> en JSON est possible
            return gson.toJson(UserDomain.HTTP_PATH);
        }, gson ::toJson);
    }

    private static HashMap<String, String> initHashMap(){
        //<HTTP Type#, Path>
        HashMap<String, String> myHashMap = new HashMap<String, String>();
        myHashMap.put("display",    "GET;/display");
        myHashMap.put("search",     "GET;/search/:firstname");
        myHashMap.put("add",        "POST;/add");
        myHashMap.put("options",    "OPTIONS;/options");
        return myHashMap;
    }
}
