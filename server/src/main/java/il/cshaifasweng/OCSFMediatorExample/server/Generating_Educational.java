package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import jdk.dynalink.beans.StaticClass;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generating_Educational {
    public static void genareteSubjectCourses(Session session)
    {
         List<Course> mathCourses=new ArrayList<>();
         Subject math=new Subject("math");

         session.save(math);
         session.flush();

        Course math_c1=new Course("linear algebra 1",math);
        Course math_c2=new Course("linear algebra 2",math);
        session.save(math_c1);
        session.save(math_c2);
        session.flush();

        mathCourses.add(math_c1);
        mathCourses.add(math_c2);
        math.setSubjectCourses(mathCourses);
        session.save(math);
        session.flush();
    }

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
    public  static void getCourse(Session session)
    {
     Query query=session.createQuery("from Course where courseName='linear algebra 3'");
     if(query.getResultList().size()==0)
     {
         System.out.println("none");
         return;
     }
     List<Course> c=(List<Course>) query.getResultList();
     System.out.println("\n");
    }
}
