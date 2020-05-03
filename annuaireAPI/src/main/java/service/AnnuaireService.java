package service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import dao.AnnuaireDAO;
import modele.Annuaire;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;

public class AnnuaireService {
    private static AnnuaireDAO annuaireDAO = new AnnuaireDAO();

    public List<Annuaire> getAnnuaire() {
        return AnnuaireService.annuaireDAO.getAnnuaire();
    }

    public String refresh(){
        //Création de la nouvelle hashmap avec toutes les entrées
        HashMap<String, String> myHashMap = new HashMap<String, String>();

        myHashMap.put("USER_API",       getHttpOptionsJSON("4567"));
        myHashMap.put("SESSION_API",    getHttpOptionsJSON("4568"));
        myHashMap.put("MODELE_API",     getHttpOptionsJSON("4569"));
        //myHashMap.put("ANALYSE_API",    getHttpOptionsJSON("4571"));

        return AnnuaireService.annuaireDAO.refresh(myHashMap);
    }

    public String deleteAllModele() {
        return AnnuaireService.annuaireDAO.deleteAllModele();
    }

    private String getHttpOptionsJSON(String port){
        HttpResponse<String> jsonResponse;
        String url;

        try {
            url = "http://localhost:"+ port +"/options";
            jsonResponse = Unirest.options(url)
                                  .asString();
            return jsonResponse.getBody();
        }
        catch (UnirestException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Problème dans le getHttpOptionsJSON pour le port ");
        sb.append(port);

        return  sb.toString();
    }
}
