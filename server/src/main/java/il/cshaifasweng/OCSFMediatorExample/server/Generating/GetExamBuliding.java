package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GetExamBuliding {
    public static List<Exam> getAllExamsForCourses(Session session, List<Teacher_Course> courses)
    {
        List<Exam> allExams=new ArrayList<>();
        String queryString;
        for(Teacher_Course teacherCourse:courses)
        {
            queryString="";
           queryString = "FROM Exam  WHERE";
            queryString+=" ";
            queryString+="course_id";
            queryString+=" = ";
            queryString+=teacherCourse.getCourse().getId();
            Query query = session.createQuery(queryString, Exam.class);
            List<Exam> courseExamResult = query.getResultList();
            for(Exam exam : courseExamResult)
            {
                allExams.add(exam);
            }
        }
        return  allExams;
    }
}
