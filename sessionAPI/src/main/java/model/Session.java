package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Session {
    @Id
    private ObjectId id;
    private ObjectId userId;


    public Session (){
    }

    public Session (ObjectId id, ObjectId UserId){
        this.id = id;
        this.userId = UserId;
    }

    public ObjectId getId() {return id;}

    public void setId(ObjectId id) { this.id = id;
    }

    public ObjectId getUserId() {return userId;}

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }


}
