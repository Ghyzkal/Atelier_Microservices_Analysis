package dao;

import com.mongodb.MongoClient;
import model.Session;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class SessionDAO {
    private static String HOST = "localhost";
    private static int PORT = 27017;
    private static String DB_NAME = "session";

    private static MongoClient client = new MongoClient(HOST, PORT);
    private static Datastore datastore = new Morphia().createDatastore(client, DB_NAME);

    //ajout de la sessions dans la BD
    public String addSession(Session session) {
        SessionDAO.datastore.save(session);
        return "model.session has been added successfully :)";
    }


    //Récupère la liste des sessions présentes dans la base de données
    public List<Session> getSessions(){
        List<Session> sessions = datastore.find(Session.class).asList();

        if(sessions != null){
            //Si on trouve des sessions, on retourne la liste des sessions
            return sessions;
        }else{
            return null;
        }
    }

    //supprime une session à partir de son id
    public String deleteSession(ObjectId sessionID) {
        Query<Session> query = datastore.createQuery(Session.class);
        query.field("id").equal(sessionID);
        Session SessionFound = query.get();
        if (SessionFound != null) {
            SessionDAO.datastore.delete(SessionFound);
            return "session delete successfully";
        }
        return "session not delete";


    }
}
