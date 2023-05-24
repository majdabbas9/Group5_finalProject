package il.cshaifasweng.OCSFMediatorExample.server.adding;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import org.hibernate.Session;

import java.util.List;

public class Adding {
    public static void addQuestion(Session session, Question question, List<Course> questionCourses, Subject questionSubject, Teacher theTeacher) {
        session.save(question);
        session.flush();

        question.setQuestionSubject(questionSubject);
        session.update(question);
        session.flush();

        question.setTeacherThatCreated(theTeacher);
        session.update(question);
        session.flush();

        theTeacher.getQuestionsCreated().add(question);
        session.update(theTeacher);
        session.flush();

        Subject subject = question.getQuestionSubject();
        subject.getSubjectQuestions().add(question);
        session.update(subject);
        session.flush();

        Course_Question cq;
        for(Course course:questionCourses)
        {
            cq=new Course_Question(course,question);
            session.save(cq);
            session.flush();

            course.getCourseQuestions().add(cq);
            session.update(course);
            session.flush();

            question.getQuestionCourses().add(cq);
            session.update(question);
            session.flush();
        }

    }

    public static void addExam(Session session,Exam exam, Teacher teacher, Course course, Subject subject, List<Question> questions) {
        /*saving exam*/
        session.save(exam);
        session.flush();
        /*end of saving exam*/

        /*updating exam*/
        exam.setTeacherThatCreated(teacher);
        session.update(exam);
        session.flush();

        exam.setExamSubject(subject);
        session.update(exam);
        session.flush();

        exam.setExamCourse(course);
        session.update(exam);
        session.flush();
        /*end of updating exam*/

        /*updating teacher*/
        teacher.getExamsCreated().add(exam);
        session.update(teacher);
        session.flush();
        /*end of updating teacher*/

        /*updating course*/
        course.getCourseExams().add(exam);
        session.update(course);
        session.flush();
        /*end of updating course*/

        /*updating subject*/
        subject.getSubjectExams().add(exam);
        session.update(subject);
        session.flush();
        /*end of updating subject*/

        Exam_Question eq;
        for(Question question:questions)
        {
            eq=new Exam_Question(exam,question);
            session.save(eq);
            session.flush();

            question.getQuestionExams().add(eq);
            session.update(question);
            session.flush();

            exam.getExamQuestions().add(eq);
            session.update(exam);
            session.flush();


        }
        int subjectId=subject.getId()-1;
        int courseId=course.getId()-1;
        int examId=exam.getId()-1;
        String exam_ID="";
        if(subjectId<10)
        {
            exam_ID+="0"+subjectId;
        }
        else
        {
            exam_ID+=subjectId;
        }
        if(courseId<10)
        {
            exam_ID+="0"+courseId;
        }
        else
        {
            exam_ID+=courseId;
        }
        if(examId<10)
        {
            exam_ID+="0"+examId;
        }
        else
        {
            exam_ID+=examId;
        }
        exam.setExam_ID(exam_ID);
        session.update(exam);
        session.flush();
    }
    public static void addCompExam(Session session,ComputerizedExamToExecute compExam, Teacher teacher, Exam exam)
    {
        session.save(compExam);
        session.flush();

        compExam.setExam(exam);
        session.update(compExam);
        session.flush();

        compExam.setTeacherThatExecuted(teacher);
        session.update(compExam);
        session.flush();

        teacher=compExam.getTeacherThatExecuted();
        teacher.getExecutedExams().add(compExam);
        session.update(teacher);
        session.flush();


        exam.getCompExamsToExecute().add(compExam);
        session.update(exam);
        session.flush();

    }
}
