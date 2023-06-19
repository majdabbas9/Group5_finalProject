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
        String q="from Grade where student='"+ studentId +"' and teacherApprovement=true ";
        Query query=session.createQuery(q);
        List<Grade> grades = (List<Grade>) (query.getResultList());
        return SpeedUpGradesData(grades);
    }
    public static List<Grade> getAllTeacherExamsGrade(Session session,int teacherId,int exaId)
    {
        String queryString="select g FROM Grade g join g.examCopy copy JOIN copy.compExamToExecute exe " +
                "join  exe.exam exam join exam.teacherThatCreated teacher  WHERE teacher.id = : id and exam.id = :examId and g.teacherApprovement=true ";
        Query query = session.createQuery(queryString,Grade.class);
        query.setParameter("id",teacherId);
        query.setParameter("examId",exaId);
        List<Grade> grades=new ArrayList<>();

        for(Grade grade:(List<Grade>)query.getResultList())
        {
            /*creating the new objects*/
            Student newStudent = new Student(grade.getStudent());
                        Grade newGrade = new Grade(grade);
                        Copy newCopy = new Copy(grade.getExamCopy());
                        ExamToExecute newExamToExecute = new ExamToExecute(grade.getExamCopy().getCompExamToExecute());
                        Exam newExam = new Exam(grade.getExamCopy().getCompExamToExecute().getExam());
                        Teacher teacher1 = new Teacher(grade.getExamCopy().getCompExamToExecute().getTeacherThatExecuted());
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
        String queryString="select g FROM Grade g join g.examCopy copy join copy.compExamToExecute comp  where comp.id= :id AND g.teacherApprovement=false ";
        Query query = session.createQuery(queryString,Grade.class);
        query.setParameter("id",compExamId);
        List<Grade> grades=new ArrayList<>();
        for(Grade grade:(List<Grade>)query.getResultList())
        {
                Grade newGrade = new Grade(grade);
                Copy copy=new Copy(grade.getExamCopy());
                //Copy newCopy=new Copy(copy.getGrade().getExamCopy());
                Exam newExam=new Exam(grade.getExamCopy().getCompExamToExecute().getExam());
                ExamToExecute examToExecute = new ExamToExecute(grade.getExamCopy().getCompExamToExecute());
                Subject subject=new Subject(grade.getExamCopy().getCompExamToExecute().getExam().getExamSubject());
                Course course=new Course(grade.getExamCopy().getCompExamToExecute().getExam().getExamCourse());
                Teacher teacher=new Teacher(grade.getExamCopy().getCompExamToExecute().getTeacherThatExecuted());
                Student student = new Student(grade.getStudent());

                newExam.setExamSubject(subject);
                newExam.setExamCourse(course);
                examToExecute.setExam(newExam);
                examToExecute.setTeacherThatExecuted(teacher);
                copy.setCompExamToExecute(examToExecute);
                newGrade.setStudent(student);
                newGrade.setExamCopy(copy);
                grades.add(newGrade);
        }
        return grades;
    }
}
