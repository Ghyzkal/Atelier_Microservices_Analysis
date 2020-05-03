package service;

import dao.ModeleDAO;
import model.Modele;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class ModeleService {
    private static ModeleDAO modeleDAO = new ModeleDAO();

    public Object getAllModele() {
        return ModeleService.modeleDAO.getAllModele();
    }

    public Object getModeleById(ObjectId idUser) {
        try {
            return ModeleService.modeleDAO.getModeleById(idUser);
        }
        catch (Exception e){
            return e.toString();
        }
    }

    public String addModele(Modele modele){
        try {
            String modeleRawXML  = modele.getFileRawJson();
            String modeleRawJson = null;
            if(modeleRawXML != null){
                modeleRawJson = this.xmlToJson(modeleRawXML);
            }
            modele.setFileRawJson(modeleRawJson);
            return ModeleService.modeleDAO.addModele(modele);
        }
        catch (JSONException je) {
            return je.toString();
        }
    }

    public String deleteAllModele(){
        try {
            return ModeleService.modeleDAO.deleteAllModele();
        }
        catch (Exception e){
            return e.toString();
        }
    }

    public String deleteModele(ObjectId idModele) {
        try {
            return ModeleService.modeleDAO.deleteModele(idModele);
        }
        catch (Exception e){
            return e.toString();
        }
    }

    private String xmlToJson(String xmlRaw){
        JSONObject xmlJSONObj = XML.toJSONObject(xmlRaw);
        return xmlJSONObj.toString();
    }
}
