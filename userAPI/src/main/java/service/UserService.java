package service;

import dao.UserDAO;
import model.User;

import java.util.List;

public class UserService {
    // Création de la couche DAO
    private static UserDAO userDAO = new UserDAO();

    //Méthodes
    //Ajout de l'utilisateur dans la base de données
    public String addUser(User user){
        return UserService.userDAO.addUser(user);
    }
    //Récupère la liste des utilisateurs présents dans la base de données
    public List<User> getUsers(){
        return UserService.userDAO.getUsers();
    }

    //Récupère l'utilisateur renseigné par l'utilisateur
    public User getUser(String firstname){
        return UserService.userDAO.getUser(firstname);
    }


}
