package dao;

import com.mongodb.MongoClient;
import model.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class UserDAO {
    private static String   HOST    = "localhost";
    private static int      PORT    = 27017;
    private static String   DB_NAME = "user";


    //On se connecte à MongoDB
    private static MongoClient client = new MongoClient(HOST, PORT);
    //Créer une base de données
    private static Datastore datastore = new Morphia().createDatastore(client, DB_NAME);

    //Récupère la liste des utilisateurs présents dans la base de données
    public List<User> getUsers(){
        List<User> users = datastore.find(User.class).asList();
        if(users != null){
            //Si on trouve des utilisateurs, on retourne la liste des utilisateurs
            return users;
        }else{
            return null;
        }
    }

    //Récupère l'utilisateur renseigné par l'utilisateur
    public User getUser(String firstname){
        Query<User> query = datastore.createQuery(User.class);
        query.field("firstName").equal(firstname);
        User userFound = query.get();
        if(userFound!=null){
            return userFound;
        }else{
            return null;
        }
    }

    //Méthodes
    //Ajout de l'utilisateur dans la base de données
    public String addUser(User user){
        UserDAO.datastore.save(user);
        return "model.User has been added successfully :)";
    }

    //Delete tous les utilisateurs dans la bdd
    public String deleteUsers(){
        return null;
    }
}
