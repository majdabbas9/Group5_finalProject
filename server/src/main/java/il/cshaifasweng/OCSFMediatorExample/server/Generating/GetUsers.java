package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import aidClasses.GlobalDataSaved;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GetUsers {
    public static List<Subject> getTeacherSubjects(Session session, int id)
    {
        String queryString = "FROM Teacher_Subject  WHERE";
        queryString+=" ";
        queryString+="teacher_id";
        queryString+=" = ";
        queryString+=id;
        Query query = session.createQuery(queryString, Teacher_Subject.class);
        List<Teacher_Subject> subs = query.getResultList();
        List<Subject> subjectList=new ArrayList<>();
        for(Teacher_Subject ts:subs)
        {
            subjectList.add(new Subject(ts.getSubject()));
        }
       return  subjectList;
    }
    public static List<Course> getTeacherCourses(Session session, int id)
    {
        String queryString = "FROM Teacher_Course  WHERE";
        queryString+=" ";
        queryString+="teacher_id";
        queryString+=" = ";
        queryString+=id;
        Query query = session.createQuery(queryString, Teacher_Course.class);
        List<Teacher_Course> subs = query.getResultList();
        List<Course> coursesList=new ArrayList<>();
        for(Teacher_Course tc:subs)
        {
            Course course1=new Course(tc.getCourse());
            course1.setCourseSubject(new Subject(tc.getCourse().getCourseSubject()));
            coursesList.add(course1);
        }
        return coursesList;
    }
    public static Teacher getTeacherById(Session session,int id)
    {
        String queryString=" FROM Teacher WHERE id = : id";
        Query query = session.createQuery(queryString,Teacher.class);
        query.setParameter("id",id);
        return (Teacher) (query.getResultList().get(0));
    }
}
