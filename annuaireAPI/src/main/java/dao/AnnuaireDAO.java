package dao;

import com.mongodb.MongoClient;
import modele.Annuaire;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.HashMap;
import java.util.List;

public class AnnuaireDAO {
    private static String                   HOST    = "localhost";
    private static int                      PORT    = 27017;
    private static String                   DB_NAME = "annuaire";

    private static MongoClient client    = new MongoClient(HOST, PORT);
    private static Datastore datastore   = new Morphia().createDatastore(client, DB_NAME);

    public List<Annuaire> getAnnuaire() {
        List<Annuaire> list = AnnuaireDAO.datastore.find(Annuaire.class).asList();
        if(list != null){
            return list;
        }
        return null;
    }

    public String refresh(HashMap<String, String> myHash){
        Annuaire annuaire = new Annuaire();
        annuaire.setHasMap(myHash);

        AnnuaireDAO.datastore.save(annuaire);
        return "Annuaire ajouté et rafraichis !";
    }


    public String deleteAllModele() {
        List<Annuaire> list = AnnuaireDAO.datastore.find(Annuaire.class).asList();
        if(list != null){
            for(Annuaire element : list){
                AnnuaireDAO.datastore.delete(element);
            }
            return "Annuaires bien tous supprimmés !";
        }
        return "Pas d'annuaires à supprimer !";
    }
}
