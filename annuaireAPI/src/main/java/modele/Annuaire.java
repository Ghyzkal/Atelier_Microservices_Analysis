package modele;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.HashMap;

@Entity
public class Annuaire {
    @Id
    private ObjectId id;
    private HashMap<String, String> hashMap;

    public Annuaire() {
    }

    public Annuaire(ObjectId id, HashMap<String, String> hasMap) {
        this.id = id;
        this.hashMap = hasMap;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public HashMap<String, String> getHasMap() {
        return hashMap;
    }

    public void setHasMap(HashMap<String, String> hasMap) {
        this.hashMap = hasMap;
    }
}
