package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class GetUsers
{
    public static List<Subject> getTeacherSubjects(Session session,int id)
    {
        String queryString = "FROM Teacher  WHERE id = :id";
        Query query = session.createQuery(queryString, Teacher.class);
        query.setParameter("id",id);
        List<Subject> subs = query.getResultList();
        return subs;
    }
    public static List<Course> getTeacherCourses(Session session, int id)
    {
        String queryString = "FROM Teacher  WHERE id = :id";
        Query query = session.createQuery(queryString, Teacher.class);
        query.setParameter("id",id);
        List<Course> list = query.getResultList();
        return list;
    }
}