package service;

import dao.SessionDAO;
import model.Session;
import org.bson.types.ObjectId;

import java.util.List;

public class SessionService {

    private static SessionDAO sessionDAO = new SessionDAO();

    public List<Session> getSessions(){
        return SessionService.sessionDAO.getSessions();
    }

    public String addSession(Session session){
        return SessionService.sessionDAO.addSession(session);
    }

    public String deleteSession (ObjectId sessionId){ return SessionService.sessionDAO.deleteSession(sessionId);}
}
