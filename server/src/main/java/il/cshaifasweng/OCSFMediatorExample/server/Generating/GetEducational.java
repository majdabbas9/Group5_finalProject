package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Principal;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import org.hibernate.Session;
import org.hibernate.annotations.Where;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import java.util.ArrayList;
import java.util.List;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;

public class GetEducational {
    public static List<Subject> getAllSubjects(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);
        query.from(Subject.class);
        List<Subject> data = session.createQuery(query).getResultList();
        return data;
    }

    public static List<ExamToExecute> getAllRequests(Session session) throws Exception {
        Query query = session.createQuery("from ExamToExecute where isExtraNeeded='"+1+"'");
        List<ExamToExecute> data =(List<ExamToExecute>) query.getResultList();
        return data;
    }

    public static List<Course> getAllCourses(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        query.from(Course.class);
        List<Course> data = session.createQuery(query).getResultList();
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
    public static List<Exam> getAllExams(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Exam> query = builder.createQuery(Exam.class);
        query.from(Exam.class);
        List<Exam> data = session.createQuery(query).getResultList();
        return data;
    }

    public static List<Question> getAllQuestions(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> query = builder.createQuery(Question.class);
        query.from(Question.class);
        List<Question> data = session.createQuery(query).getResultList();
        return data;
    }

    public static List<Grade> getAllGrades(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Grade> query = builder.createQuery(Grade.class);
        query.from(Grade.class);
        List<Grade> data = session.createQuery(query).getResultList();
        return data;
    }
    public static List<Student> getAllStudents(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = builder.createQuery(Student.class);
        query.from(Student.class);
        List<Student> data = session.createQuery(query).getResultList();
        return data;
    }
    public static List<Teacher> getAllTeachers(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = builder.createQuery(Teacher.class);
        query.from(Teacher.class);
        List<Teacher> data = session.createQuery(query).getResultList();
        return data;
    }
}
