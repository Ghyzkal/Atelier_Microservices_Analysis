import com.google.gson.Gson;
import service.AnnuaireService;

import java.util.HashMap;

import static spark.Spark.*;
import static spark.SparkBase.setPort;

// http://localhost:4569/user/display
public class AnnuaireDomain {
    private static AnnuaireService annuaireService = new AnnuaireService();
    private static int PORT = 4570;
    private static HashMap<String, String> HTTP_PATH = AnnuaireDomain.initHashMap();

    public static void main(String[] args){
        Gson gson = new Gson();

        setPort(AnnuaireDomain.PORT);

        get(AnnuaireDomain.HTTP_PATH.get("display").split(";")[1], (req, res) -> {
            res.type("application/json");
            return annuaireService.getAnnuaire();
        }, gson ::toJson);

        put(AnnuaireDomain.HTTP_PATH.get("refresh").split(";")[1], (req, res) -> {
            res.type("application/json");
            return annuaireService.refresh();
        },gson ::toJson);

        delete(AnnuaireDomain.HTTP_PATH.get("deleteAll").split(";")[1], (request, response) -> {
            response.type("application/json");
            return annuaireService.deleteAllModele();
        }, gson ::toJson);

        options(AnnuaireDomain.HTTP_PATH.get("options").split(";")[1], (req, res) -> {
            res.type("application/json");
            //Conversion d'un hasmap<String, String> en JSON est possible
            return gson.toJson(AnnuaireDomain.HTTP_PATH);
        }, gson ::toJson);

    }

    private static HashMap<String, String> initHashMap(){
        //<HTTP Type#, Path>
        HashMap<String, String> myHashMap = new HashMap<String, String>();
        myHashMap.put("display",    "GET;/display");
        myHashMap.put("refresh",    "PUT;/refresh");
        myHashMap.put("deleteAll",  "DELETE;/deleteAll");
        myHashMap.put("options",    "OPTIONS;/options");
        return myHashMap;
    }
}
