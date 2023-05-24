package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import org.hibernate.Session;
import org.hibernate.annotations.Where;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import java.util.ArrayList;
import java.util.List;

public class GetEducational {
    public static List<Subject> getAllSubjects(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);
        query.from(Subject.class);
        List<Subject> data = session.createQuery(query).getResultList();
        return data;
    }

    public static List<Course> getAllCourses(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        query.from(Course.class);
        List<Course> data = session.createQuery(query).getResultList();
        return data;
    }
    public static List<Exam> getAllExams(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Exam> query = builder.createQuery(Exam.class);
        query.from(Exam.class);
        List<Exam> data = session.createQuery(query).getResultList();
        return data;
    }


    public static void getCourse(Session session) {
        Query query = session.createQuery("from Course where courseName='linear algebra 3'");
        if (query.getResultList().size() == 0) {
            System.out.println("none");
            return;
        }
        List<Course> c = (List<Course>) query.getResultList();
        System.out.println("\n");
    }

    public static boolean checkID(Session session, String id) {
        Query query = session.createQuery("from User where userID=" + id + "");
        if (query.getResultList().size() == 0) {
            return true;
        }
        return false;
    }
}
