package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
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

    public static Copy getCopyById(Session session, int id)
    {
        String queryString=" FROM Copy WHERE id = : id";
        Query query = session.createQuery(queryString,Copy.class);
        query.setParameter("id",id);
        return (Copy) (query.getResultList().get(0));
    }

    public static List<Grade> getStudentGrades(Session session, int studentId) {
        String q="from Grade where student='"+ studentId +"' and teacherApprovement='"+ 1 +"'";
        Query query=session.createQuery(q);
        List<Grade> grades = (List<Grade>) (query.getResultList());
        return SpeedUpGradesData(grades);
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
    public static List<Grade> SpeedUpGradesData(List<Grade> grades){
        List<Grade> newGrades = new ArrayList<>();
        for (Grade grade : grades) {

            Grade newGrade = new Grade(grade);
            Copy copy=new Copy(grade.getExamCopy());
            Exam exam=new Exam(grade.getExamCopy().getCompExamToExecute().getExam());
            ExamToExecute examToExecute = new ExamToExecute(grade.getExamCopy().getCompExamToExecute());
            Subject subject=new Subject(grade.getExamCopy().getCompExamToExecute().getExam().getExamSubject());
            Course course=new Course(grade.getExamCopy().getCompExamToExecute().getExam().getExamCourse());
            Teacher teacher=new Teacher(grade.getExamCopy().getCompExamToExecute().getTeacherThatExecuted());
            Student student = new Student(grade.getStudent());

            exam.setExamSubject(subject);
            exam.setExamCourse(course);
            examToExecute.setExam(exam);
            examToExecute.setTeacherThatExecuted(teacher);
            copy.setCompExamToExecute(examToExecute);
            newGrade.setStudent(student);
            newGrade.setExamCopy(copy);
            newGrades.add(newGrade);
        }
        return newGrades;
    }
    public static List<Grade> getCopiesToApprove(Session session,int compExamId)
    {
        String queryString="FROM ExamToExecute WHERE id = : id";
        Query query = session.createQuery(queryString,ExamToExecute.class);
        query.setParameter("id",compExamId);

        List<ExamToExecute> exam=(List<ExamToExecute>)(query.getResultList());

        List<Grade> compExamGrades=new ArrayList<>();
        for(Copy copy : exam.get(0).getCopies())
        {
            if(!copy.getGrade().isTeacherApprovement())
            {
                Grade newGrade = new Grade(copy.getGrade());
                Copy newCopy=new Copy(copy.getGrade().getExamCopy());
                Exam newExam=new Exam(copy.getGrade().getExamCopy().getCompExamToExecute().getExam());
                ExamToExecute examToExecute = new ExamToExecute(copy.getGrade().getExamCopy().getCompExamToExecute());
                Subject subject=new Subject(copy.getGrade().getExamCopy().getCompExamToExecute().getExam().getExamSubject());
                Course course=new Course(copy.getGrade().getExamCopy().getCompExamToExecute().getExam().getExamCourse());
                Teacher teacher=new Teacher(copy.getGrade().getExamCopy().getCompExamToExecute().getTeacherThatExecuted());
                Student student = new Student(copy.getGrade().getStudent());

                newExam.setExamSubject(subject);
                newExam.setExamCourse(course);
                examToExecute.setExam(newExam);
                examToExecute.setTeacherThatExecuted(teacher);
                copy.setCompExamToExecute(examToExecute);
                newGrade.setStudent(student);
                newGrade.setExamCopy(copy);
                compExamGrades.add(newGrade);
            }

        }
        return compExamGrades;
    }
}
