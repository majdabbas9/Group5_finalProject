package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Student_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Student_Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Principal;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;
import il.cshaifasweng.OCSFMediatorExample.server.adding.Adding;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class GenerateAll {

    public static void generateEducational(Session session)
    {

        /*adding the principal*/
        Principal principal=new Principal("1111","0","0","moustfa","jbraeen");
        session.save(principal);
        session.flush();
        /*end of adding principal*/
        Subject math=new Subject("math");
        session.save(math);
        session.flush();

        Course algebra=new Course("algebra",math);
        session.save(algebra);
        session.flush();

        math.getSubjectCourses().add(algebra);
        session.update(math);
        session.flush();

        Teacher t1=new Teacher("33333","1","1","mohamed","abbas");
        Teacher t2=new Teacher("33333","3","3","aa","aa");

        session.save(t1);
        session.save(t2);
        session.flush();

        Teacher_Subject ts1=new Teacher_Subject(t1,math);
        session.save(ts1);
        session.flush();

        Teacher_Subject ts2=new Teacher_Subject(t2,math);
        session.save(ts2);
        session.flush();

        Teacher_Course tc1=new Teacher_Course(t1,algebra);
        session.save(tc1);
        session.flush();

        Teacher_Course tc2=new Teacher_Course(t2,algebra);
        session.save(tc2);
        session.flush();

        /*updating t1*/
        t1.getTeacherSubjects().add(ts1);
        session.update(t1);
        session.flush();

        math.getSubjectTeachers().add(ts1);
        session.update(math);
        session.flush();

        t1.getTeacherCourses().add(tc1);
        session.update(t1);
        session.flush();

        algebra.getCourseTeachers().add(tc1);
        session.update(algebra);
        session.flush();
        /*end of updating t1*/

        /*updating t2*/
        t2.getTeacherSubjects().add(ts2);
        session.update(t2);
        session.flush();

        math.getSubjectTeachers().add(ts2);
        session.update(math);
        session.flush();

        t2.getTeacherCourses().add(tc2);
        session.update(t2);
        session.flush();

        algebra.getCourseTeachers().add(tc2);
        session.update(algebra);
        session.flush();
        /*end of updating t2*/

        /*student Add*/

        Student s1=new Student("111","2","2","abdo","abbas");
        session.save(s1);
        session.flush();

        Student_Subject ss1=new Student_Subject(s1,math);
        session.save(ss1);
        session.flush();

        Student_Course sc1=new Student_Course(s1,algebra);
        session.save(sc1);
        session.flush();

        /*end of student add */

        /*adding question*/
        List<String> cho=new ArrayList<>();
        cho.add("1");cho.add("2");cho.add("3");cho.add("4");
        Question question=new Question("","2+2","00000",cho,"4");
        List<Course> courses=new ArrayList<>();
        courses.add(algebra);
        Adding.addQuestion(SimpleServer.getSession(),question,courses,math,t1);
        /*end of adding question*/

        /*adding exam*/
        List<Integer> points=new ArrayList<>();
        List<Question> questions=new ArrayList<>();
        questions.add(question);
        points.add(100);
        Exam exam=new Exam(10,"","","",points);
        Adding.addExam(SimpleServer.getSession(),exam,t1,algebra,math,questions);
        /*end of adding exam*/

        /*adding comp exam*/
        ComputerizedExamToExecute compExam=new ComputerizedExamToExecute("24/5/2023 23:19",1234);
        Adding.addCompExam(SimpleServer.getSession(),compExam,t1,exam);
        /*end of adding comp exam*/

        /*adding copy*/
        Copy copy=new Copy("4",compExam);
        session.save(copy);session.flush();
        /*end of adding copy*/

        /*adding grade*/
        Grade grade=new Grade(s1,false,1,true,"24/5/2023","23:26");
        session.save(grade);session.flush();

        copy.setGrade(grade);session.update(copy);session.flush();

        grade.setExamCopy(copy);session.update(grade);session.flush();
        /*end of adding grade*/

    }
    public static void generateUsers(Session session)
    {


    }


}
