package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class GenerateAll {

    public static void generateEducational(Session session)
    {
        Subject math=new Subject("math");
        session.save(math);
        session.flush();

        Course algebra=new Course("algebra",math);
        session.save(algebra);
        session.flush();

        math.getSubjectCourses().add(algebra);
        session.save(math);
        session.flush();

        Teacher t1=new Teacher("33333","teacher","teacher","mohamed","abbas");
        Teacher t2=new Teacher("33333","3","3","aa","aa");

        session.save(t1);
        session.save(t2);
        session.flush();

        /*updating t1*/
        t1.getTeacherSubjects().add(math);
        session.save(t1);
        session.flush();

        math.getSubjectTeachers().add(t1);
        session.save(math);
        session.flush();

        t1.getTeacherCourses().add(algebra);
        session.save(t1);
        session.flush();

        algebra.getCourseTeachers().add(t1);
        session.save(algebra);
        session.flush();
        /*end of updating t1*/

        /*updating t2*/
        t2.getTeacherSubjects().add(math);
        session.save(t2);
        session.flush();

        math.getSubjectTeachers().add(t2);
        session.save(math);
        session.flush();

        t2.getTeacherCourses().add(algebra);
        session.save(t2);
        session.flush();

        algebra.getCourseTeachers().add(t2);
        session.save(algebra);
        session.flush();
        /*end of updating t2*/





    }
    public static void generateUsers(Session session)
    {


    }


}
