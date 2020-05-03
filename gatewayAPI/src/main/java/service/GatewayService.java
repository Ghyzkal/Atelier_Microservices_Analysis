package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import dao.GatewayDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;


public class GatewayService {
    private static GatewayDAO gatewayDAO  = new GatewayDAO();
    private static String     annuaireURL = "http://localhost:4570";
    private static String     NL          = "\n";

    public Object test(){
        HashMap<String, String> annuaire = this.getAnnuaireHashMap();
        return annuaire;
    }

    public Object help() {
        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to Micoservices' API !")
                .append(NL)
                .append("How to use it ?")
                .append(NL)
                .append("Your request should be in the following format : localhost:4566/domain-name/action . Please note that some actions may require parameters (in JSON format or in the request using an additional '/parameter') Exemple : localhost:4567/domain/action/parameter .");
        return sb.toString();
    }

    public Object gateway(String domain, String action){
        //Format "DOMAIN_API;PORT"
        String apiAsked = this.getApiFromDomain(domain);
        if(apiAsked == null){
            return "Domain not valid !";
        }
        String pathActionAsked = this.getPathActionFromApi(apiAsked.split(";")[0], action);
        if(pathActionAsked == null){
            return "Action not valid !";
        }

        return this.reqApiRest(pathActionAsked.split(";")[0],
                            "http://localhost:" + apiAsked.split(";")[1] + pathActionAsked.split(";")[1]);
    }

    private JsonNode reqApiRest (String type, String url){
        HttpResponse<JsonNode> jsonResponse = null;

        switch (type){
            case "GET":
                try {
                    jsonResponse = Unirest.get(url)
                            .queryString("apiKey", "123")
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                break;

            case "POST":
                try {
                    jsonResponse = Unirest.post(url)
                            .queryString("apiKey", "123")
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                break;

            case "PUT":
                try {
                    jsonResponse = Unirest.put(url)
                            .queryString("apiKey", "123")
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                break;

            case "DELETE":
                try {
                    jsonResponse = Unirest.delete(url)
                            .queryString("apiKey", "123")
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                break;

            case "OPTIONS":
                try {
                    jsonResponse = Unirest.options(url)
                            .queryString("apiKey", "123")
                            .asJson();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
        return (jsonResponse != null) ? jsonResponse.getBody() : null;
    }

    private String getPathActionFromApi(String apiDomain, String action){
        //Chargement de la hashmap contenant les requête par API
        HashMap<String, String> hashMapAnnuaire = this.getAnnuaireHashMap();

        //Récupération de la hashmap contenant les path du domaine demandé
        String                  pathAction = hashMapAnnuaire.getOrDefault(apiDomain, null);
        if(pathAction == null){
            return "Erreur dans le chargement des actions de " + apiDomain;
        }

        HashMap<String, String> pathActionMap;
        ObjectMapper mapper = new ObjectMapper();
        try {
            //On enlève les guillemets en trop aux extremes
            pathAction = pathAction.substring(1, pathAction.length()-1);
            //Il faut virer les saut de lignes "\\\\" afin de convertir en hashmap
            pathAction = pathAction.replaceAll("\\\\","");
            pathActionMap = mapper.readValue(pathAction, new TypeReference<HashMap<String,String>>(){});
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Erreur dans le mapping des actions";
        }

        return pathActionMap.getOrDefault(action,null);
    }

    private String getApiFromDomain(String domain){
        String api = null;
        switch(domain){
            case "user":
                api = "USER_API;4567";
                break;
            case "session":
                api = "SESSION_API;4568";
                break;
            case "modele":
                api = "MODELE_API;4569";
                break;
            default:
                break;
        }
        return api;
    }

    private HashMap<String, String> getAnnuaireHashMap(){
        HttpResponse<JsonNode> jsonResponse;
        HashMap<String, String> result = null;
        String action = "/display";

        try {
            jsonResponse = Unirest.get(GatewayService.annuaireURL + action)
                    .queryString("apiKey", "123")
                    .asJson();

            //Conversion JSON String to Map
            Iterator it;
            JSONObject j;
            JSONObject objTmp;
            JSONArray jsonArray = jsonResponse.getBody().getArray();

            //Parcourt la liste d'annuaire
            for(int i=0; i < jsonArray.length(); ++i){
                j = jsonArray.optJSONObject(i);
                it = j.keys();
                //Parcourt du JSONObject avec iterateur
                while (it.hasNext()) {
                    //Récupération du JSONObject de la hashmap (on ne veut pas de l'entrée idAnnuaire)
                    objTmp = j.getJSONObject("hashMap");
                    try {
                        //Conversion du string JSON en Hashmap
                        result = new ObjectMapper().readValue(objTmp.toString(), HashMap.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    it.next();
                }
            }
        }
        catch (UnirestException e) {
            e.printStackTrace();
        }

        return result;
    }
}
