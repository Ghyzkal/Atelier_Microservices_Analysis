import com.google.gson.Gson;
import model.Session;
import org.bson.types.ObjectId;
import service.SessionService;


import java.util.HashMap;

import static spark.SparkBase.setPort;
import static spark.Spark.*;
import static spark.Spark.post;

public class SessionDomain {
    private static SessionService sessionService = new SessionService();
    private static int PORT = 4568;
    private static HashMap<String, String> HTTP_PATH = SessionDomain.initHashMap();

    public static void main(String[] args) {
        Gson gson = new Gson();

        setPort(SessionDomain.PORT);

        get(SessionDomain.HTTP_PATH.get("display").split(";")[1], (req, res) -> {
            res.type("application/json");
            return sessionService.getSessions();
        }, gson ::toJson);

        post(SessionDomain.HTTP_PATH.get("add").split(";")[1], (req, res) -> {
            res.type("application/json");
            Session session = gson.fromJson(req.body(), Session.class);
            return sessionService.addSession(session);
        }, gson ::toJson);

        delete(SessionDomain.HTTP_PATH.get("delete").split(";")[1], (req, res) -> {
            res.type("application/json");
            Session session = gson.fromJson(req.body(), Session.class);
            ObjectId id = session.getId();
            return sessionService.deleteSession(id);
        }, gson ::toJson);

        options(SessionDomain.HTTP_PATH.get("options").split(";")[1], (req, res) -> {
            res.type("application/json");
            //Conversion d'un hasmap<String, String> en JSON est possible
            return gson.toJson(SessionDomain.HTTP_PATH);
        }, gson ::toJson);
    }

    private static HashMap<String, String> initHashMap(){
        //<HTTP Type#, Path>
        HashMap<String, String> myHashMap = new HashMap<String, String>();
        myHashMap.put("display",    "GET;/display");
        myHashMap.put("add",        "POST;/add");
        myHashMap.put("delete",     "POST;/delete");
        myHashMap.put("options",    "OPTIONS;/options");
        return myHashMap;
    }
}
