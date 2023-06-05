package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import org.hibernate.Session;

import javax.persistence.Query;

public class GetGrading {
    public static Grade getGradeById(Session session, int id)
    {
        String queryString=" FROM Grade WHERE id = : id";
        Query query = session.createQuery(queryString,Grade.class);
        query.setParameter("id",id);
        return (Grade) (query.getResultList().get(0));
    }
}
