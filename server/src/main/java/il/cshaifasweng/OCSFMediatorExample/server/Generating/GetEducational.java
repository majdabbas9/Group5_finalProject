package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    public  static List<Question> getCourseQuestions(Session session,int course_id)
    {
        String queryString = "FROM Course_Question  WHERE";
        queryString+=" ";
        queryString+="course_id";
        queryString+=" = ";
        queryString+=course_id;
        Query query = session.createQuery(queryString, Course_Question.class);
        List<Course_Question> subs = query.getResultList();
        List<Question> questions=new ArrayList<>();
        for(Course_Question tc:subs)
        {
            questions.add(tc.getQuestion());
        }
        return questions;
    }
}
