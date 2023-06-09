package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GetGrading {
    public static Grade getGradeById(Session session, int id)
    {
        String queryString=" FROM Grade WHERE id = : id";
        Query query = session.createQuery(queryString,Grade.class);
        query.setParameter("id",id);
        return (Grade) (query.getResultList().get(0));
    }
    public static List<Grade> getAllTeacherExamsGrade(Session session,int teacherId,int exaId)
    {
        Teacher teacher=GetUsers.getTeacherById(session,teacherId);
        Exam exam=GetExamBuliding.getExamById(session,exaId);
        List<Grade> grades=new ArrayList<>();
        for(ExamToExecute examToExecute:exam.getCompExamsToExecute())
        {
            for(Copy copy:examToExecute.getCopies())
            {
                if(copy.getGrade().isTeacherApprovement()) {
                        /*creating the new objects*/
                        Student newStudent = new Student(copy.getGrade().getStudent());
                        Grade newGrade = new Grade(copy.getGrade());
                        Copy newCopy = new Copy(copy);
                        ExamToExecute newExamToExecute = new ExamToExecute(examToExecute);
                        Exam newExam = new Exam(exam);
                        Teacher teacher1 = new Teacher(examToExecute.getTeacherThatExecuted());
                        /*end of creating the new objects*/

                        /*updating the exam*/
                        newExamToExecute.setExam(newExam);
                        newExamToExecute.setTeacherThatExecuted(teacher1);
                        /*end of updating the exam*/

                        /*updating the copy*/
                        newCopy.setCompExamToExecute(newExamToExecute);
                        /*end of updating the copy*/

                        /*updating the grade*/
                        newGrade.setStudent(newStudent);
                        newGrade.setExamCopy(newCopy);
                        /*end of updating the grade*/

                        grades.add(newGrade);
                    }
                }
            }
        return grades;
    }
}
