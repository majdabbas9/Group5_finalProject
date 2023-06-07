package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import aidClasses.aidClassesForTeacher.QuestionsExamsID;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Student_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Student_Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Principal;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import org.hibernate.Session;

public class GenerateAll {

    public static void generateEducational(Session session)
    {
        /*adding the principal*/
        Principal principal=new Principal("1111","0","0","moustfa","jbraeen");
        session.save(principal);
        session.flush();
        /*end of adding principal*/

        /*adding subjects*/
        Subject math=new Subject("math");
        session.save(math);
        session.flush();

        Subject cs=new Subject("computer science");
        session.save(cs);
        session.flush();

        Subject physics=new Subject("physics");
        session.save(physics);
        session.flush();
        /*end of adding subjects*/


        /*adding courses*/

        /*math*/
        Course algebra=new Course("algebra",math);
        Course algebra2=new Course("algebra 2",math);
        Course discMath=new Course("discrete mathematics",math);

        session.save(algebra);
        session.save(algebra2);
        session.save(discMath);
        session.flush();

        math.getSubjectCourses().add(algebra);
        math.getSubjectCourses().add(algebra2);
        math.getSubjectCourses().add(discMath);
        session.update(math);
        session.flush();

        /*end of math*/

        /*cs*/
        Course csIntro=new Course("introduction to  computer science",cs);
        Course softWare=new Course("software engineering",cs);
        Course operatingSystem=new Course("operating system",cs);

        session.save(csIntro);
        session.save(softWare);
        session.save(operatingSystem);
        session.flush();

        cs.getSubjectCourses().add(csIntro);
        cs.getSubjectCourses().add(softWare);
        cs.getSubjectCourses().add(operatingSystem);
        session.update(cs);
        session.flush();
        /*end of cs*/
        /*physics*/
        Course introPhysics=new Course("Introductory Physics",physics);
        Course mechanic=new Course("Classical Mechanics",physics);
        Course electromagnetism=new Course("Electromagnetism",physics);

        session.save(introPhysics);
        session.save(mechanic);
        session.save(electromagnetism);
        session.flush();

        physics.getSubjectCourses().add(introPhysics);
        physics.getSubjectCourses().add(mechanic);
        physics.getSubjectCourses().add(electromagnetism);
        session.update(physics);
        session.flush();
        /*end of physics*/

        /*end of adding courses*/

        Teacher t1=new Teacher("21212","1","1","mohamed","abbas");
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

    }
    public static void generateUsers(Session session)
    {


    }


}
