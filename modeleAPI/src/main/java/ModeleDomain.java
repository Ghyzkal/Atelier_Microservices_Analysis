import com.google.gson.Gson;
import model.Modele;
import org.bson.types.ObjectId;
import service.ModeleService;

import java.util.HashMap;

import static spark.Spark.*;
import static spark.SparkBase.setPort;

// http://localhost:4568/modele/
// https://stackoverflow.com/questions/1823264/quickest-way-to-convert-xml-to-json-in-java

/* Exemple de body JSON POST
{
    "ownerId"       : {
        "timestamp"         : 1526992875,
        "machineIdentifier" : 2020919,
        "processIdentifier" : 18540,
        "counter"           : 14055172
    },
    "fileName"      : "test2",
    "fileRawJson"   : "<?xml version=\"1.0\" ?><test attrib=\"moretest\">Turn this to JSON</test>",
    "author"        : "test2"
}
*/

public class ModeleDomain {
    private static ModeleService modeleService = new ModeleService();
    private static int PORT = 4569;
    private static HashMap<String, String> HTTP_PATH = ModeleDomain.initHashMap();

    public static void main(String[] args){
        Gson gson = new Gson();

        setPort(PORT);

        get(ModeleDomain.HTTP_PATH.get("display").split(";")[1], (request, response) -> {
            response.type("application/json");
            return modeleService.getAllModele();
        }, gson ::toJson);

        post(ModeleDomain.HTTP_PATH.get("add").split(";")[1], (request, response) -> {
            response.type("application/json");
            //Création de l'user en json dans la requete
            Modele modele = gson.fromJson(request.body(), Modele.class);
            //lancement du service add et récupère son retour
            return modeleService.addModele(modele);
        }, gson ::toJson);

        put("/", (request, response) -> {
            // Update something
            return null;
        });

        delete(ModeleDomain.HTTP_PATH.get("deleteAll").split(";")[1], (request, response) -> {
            response.type("application/json");
            return modeleService.deleteAllModele();
        }, gson ::toJson);

        delete(ModeleDomain.HTTP_PATH.get("delete").split(";")[1], (request, response) -> {
            response.type("application/json");
            ObjectId idModele = gson.fromJson(request.body(), ObjectId.class);
            return modeleService.deleteModele(idModele);
        }, gson ::toJson);

        options(ModeleDomain.HTTP_PATH.get("options").split(";")[1], (req, res) -> {
            res.type("application/json");
            //Conversion d'un hasmap<String, String> en JSON est possible
            return gson.toJson(ModeleDomain.HTTP_PATH);
        }, gson ::toJson);

    }

    private static HashMap<String, String> initHashMap(){
        //<HTTP Type#, Path>
        HashMap<String, String> myHashMap = new HashMap<String, String>();
        myHashMap.put("display",    "GET;/display");
        myHashMap.put("add",        "POST;/add");
        myHashMap.put("deleteAll",  "DELETE;/deleteAll");
        myHashMap.put("delete",     "DELETE;/delete");
        myHashMap.put("options",    "OPTIONS;/options");
        return myHashMap;
    }
}
