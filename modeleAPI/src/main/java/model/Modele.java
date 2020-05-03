package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Modele {
    @Id
    private ObjectId id;
    private ObjectId ownerId;
    private String fileName;
    private String fileRawJson;
    private String author;

    public Modele() {
    }

    public Modele(String fileName, String fileRawJson, ObjectId owner) {
        this.fileName = fileName;
        this.fileRawJson = fileRawJson;
        this.ownerId = owner;
    }

    public Modele(String fileName, String fileRawJson, ObjectId owner, String author) {
        this.fileName = fileName;
        this.fileRawJson = fileRawJson;
        this.ownerId = owner;
        this.author = author;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileRawJson() {
        return fileRawJson;
    }

    public void setFileRawJson(String fileRawJson) {
        this.fileRawJson = fileRawJson;
    }

    public ObjectId getOwner() {
        return ownerId;
    }

    public void setOwner(ObjectId owner) {
        this.ownerId = owner;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
