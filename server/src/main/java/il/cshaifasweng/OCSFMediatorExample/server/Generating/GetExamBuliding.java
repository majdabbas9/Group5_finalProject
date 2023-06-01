package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GetExamBuliding {
    public static int counter=0;
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
    public static List<ComputerizedExamToExecute> getAllCompExams(Session session, int teacherId)
    {
        counter++;
        if(counter==2)
        {
            System.out.println("hi");
        }
        String queryString="FROM ComputerizedExamToExecute WHERE teacherThatExecuted.id = : id";
        Query query = session.createQuery(queryString,ComputerizedExamToExecute.class);
        query.setParameter("id",teacherId);
        List<ComputerizedExamToExecute> list=new ArrayList<>();
        boolean addCompExam=false;
        for(ComputerizedExamToExecute compExam:(List<ComputerizedExamToExecute>)(query.getResultList()))
        {
            addCompExam=false;
            for(Copy copy:compExam.getCopies())
            {
                if(!copy.getGrade().isTeacherApprovement())
                {
                    addCompExam=true;
                }
            }
            if(addCompExam)list.add(compExam);
        }
        return list;
    }
}
