package dao;

import com.mongodb.MongoClient;
import model.Modele;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.json.XML;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.Iterator;
import java.util.List;

public class ModeleDAO {
    private static String   HOST    = "localhost";
    private static int      PORT    = 27017;
    private static String   DB_NAME = "modele";

    private static MongoClient client    = new MongoClient(HOST, PORT);
    private static Datastore   datastore = new Morphia().createDatastore(client, DB_NAME);

    public Object getAllModele() {
        List<Modele> list = ModeleDAO.datastore.find(Modele.class).asList();
        if(list != null){
            //Parcourt de la liste pour reconvertir le JSON du modèle en XML
            for (Modele element : list) {
                String modeleRawJson = element.getFileRawJson();
                String modeleRawXml = null;
                if(modeleRawJson != null){
                    modeleRawXml = this.jsonToXml(modeleRawJson);
                }
                element.setFileRawJson(modeleRawXml);
            }
            return list;
        }
        return null;
    }

    public Object getModeleById(ObjectId idUser) {
        Query<Modele> query = ModeleDAO.datastore.createQuery(Modele.class);
        query.field("ownerId").equal(idUser);
        Modele modeleFound = query.get();

        if(modeleFound != null){
            return modeleFound;
        }
        return "Pas de modèle associé à cet id !";
    }

    public String addModele(Modele modele){
        ModeleDAO.datastore.save(modele);

        StringBuilder sb = new StringBuilder();
        sb.append("Modele ");
        sb.append(modele.getFileName());
        sb.append(" de ");
        sb.append(" ajouté");

        return  sb.toString();
    }

    public String deleteAllModele(){
        List<Modele> list = ModeleDAO.datastore.find(Modele.class).asList();
        if(list != null){
            for(Modele element : list){
                ModeleDAO.datastore.delete(element);
            }
            return "Modèles bien tous supprimmés !";
        }
        return "Pas de modèle à supprimer !";
    }

    public String deleteModele(ObjectId idModele) {
        Query<Modele> query = ModeleDAO.datastore.createQuery(Modele.class);
        query.field("id").equal(idModele);
        Modele modeleFound = query.get();

        if(modeleFound != null){
            String nomModeleFound = modeleFound.getFileName();
            ModeleDAO.datastore.delete(modeleFound);
            return "Modele " + nomModeleFound + " bien supprimé";
        }
        return "Pas de modèle associé à cet id !";
    }

    private String jsonToXml(String jsonRaw){
        JSONObject json = new JSONObject(jsonRaw);
        return XML.toString(json);
    }
}
