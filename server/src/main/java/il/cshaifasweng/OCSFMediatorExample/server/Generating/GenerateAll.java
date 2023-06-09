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

        /*adding teachers*/

        Teacher t1=new Teacher("21212","1","1","mohamed","abbas");
        Teacher t2=new Teacher("33333","11","11","aa","aa");
        Teacher t3=new Teacher("33333","111","111","aaa","aaa");

        session.save(t1);
        session.save(t2);
        session.flush();

        Teacher_Subject ts1=new Teacher_Subject(t1,math);
        session.save(ts1);
        session.flush();

        Teacher_Subject ts2=new Teacher_Subject(t2,cs);
        session.save(ts2);
        session.flush();

        Teacher_Subject ts3=new Teacher_Subject(t3,physics);
        session.save(ts3);
        session.flush();

        Teacher_Course tc1=new Teacher_Course(t1,algebra);
        session.save(tc1);
        session.flush();

        Teacher_Course tc11=new Teacher_Course(t1,algebra2);
        session.save(tc11);
        session.flush();

        Teacher_Course tc111=new Teacher_Course(t1,discMath);
        session.save(tc111);
        session.flush();

        Teacher_Course tc2=new Teacher_Course(t2,csIntro);
        session.save(tc2);
        session.flush();

        Teacher_Course tc22=new Teacher_Course(t2,softWare);
        session.save(tc22);
        session.flush();

        Teacher_Course tc222=new Teacher_Course(t2,operatingSystem);
        session.save(tc222);
        session.flush();

        Teacher_Course tc3=new Teacher_Course(t3,introPhysics);
        session.save(tc3);
        session.flush();

        Teacher_Course tc33=new Teacher_Course(t3,mechanic);
        session.save(tc33);
        session.flush();

        Teacher_Course tc333=new Teacher_Course(t3,electromagnetism);
        session.save(tc333);
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

        t1.getTeacherCourses().add(tc11);
        session.update(t1);
        session.flush();

        algebra.getCourseTeachers().add(tc11);
        session.update(algebra2);
        session.flush();

        t1.getTeacherCourses().add(tc111);
        session.update(t1);
        session.flush();

        algebra.getCourseTeachers().add(tc111);
        session.update(discMath);
        session.flush();

        /*end of updating t1*/

        /*updating t2*/
        t2.getTeacherSubjects().add(ts2);
        session.update(t2);
        session.flush();

        math.getSubjectTeachers().add(ts2);
        session.update(cs);
        session.flush();

        t2.getTeacherCourses().add(tc2);
        session.update(t2);
        session.flush();

        algebra.getCourseTeachers().add(tc2);
        session.update(csIntro);
        session.flush();

        t2.getTeacherCourses().add(tc22);
        session.update(t2);
        session.flush();

        algebra.getCourseTeachers().add(tc22);
        session.update(softWare);
        session.flush();

        t2.getTeacherCourses().add(tc222);
        session.update(t2);
        session.flush();

        algebra.getCourseTeachers().add(tc222);
        session.update(operatingSystem);
        session.flush();
        /*end of updating t2*/

        /*updating t3*/
        t2.getTeacherSubjects().add(ts3);
        session.update(t3);
        session.flush();

        math.getSubjectTeachers().add(ts3);
        session.update(physics);
        session.flush();

        t2.getTeacherCourses().add(tc3);
        session.update(t3);
        session.flush();

        algebra.getCourseTeachers().add(tc3);
        session.update(introPhysics);
        session.flush();

        t2.getTeacherCourses().add(tc33);
        session.update(t3);
        session.flush();

        algebra.getCourseTeachers().add(tc33);
        session.update(mechanic);
        session.flush();

        t2.getTeacherCourses().add(tc333);
        session.update(t3);
        session.flush();

        algebra.getCourseTeachers().add(tc333);
        session.update(electromagnetism);
        session.flush();
        /*end of updating t3*/

        /*end of adding teachers */

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

        Student_Subject ss11=new Student_Subject(s1,cs);
        session.save(ss11);
        session.flush();

        Student_Course sc11=new Student_Course(s1,csIntro);
        session.save(sc11);
        session.flush();

        Student_Subject ss111=new Student_Subject(s1,physics);
        session.save(ss111);
        session.flush();

        Student_Course sc111=new Student_Course(s1,introPhysics);
        session.save(sc111);
        session.flush();

        Student s2=new Student("166","22","22","mohamad","abbas");
        session.save(s2);
        session.flush();

        Student_Subject ss2=new Student_Subject(s2,math);
        session.save(ss2);
        session.flush();

        Student_Course sc2=new Student_Course(s2,algebra2);
        session.save(sc2);
        session.flush();

        Student_Subject ss22=new Student_Subject(s2,cs);
        session.save(ss22);
        session.flush();

        Student_Course sc22=new Student_Course(s2,softWare);
        session.save(sc22);
        session.flush();

        Student_Subject ss222=new Student_Subject(s2,physics);
        session.save(ss222);
        session.flush();

        Student_Course sc222=new Student_Course(s2,mechanic);
        session.save(sc222);
        session.flush();

        Student s3=new Student("555","222","222","mohamad","mostafa");
        session.save(s3);
        session.flush();

        Student_Subject ss3=new Student_Subject(s3,math);
        session.save(ss3);
        session.flush();

        Student_Course sc3=new Student_Course(s3,algebra2);
        session.save(sc3);
        session.flush();

        Student_Subject ss33=new Student_Subject(s3,cs);
        session.save(ss33);
        session.flush();

        Student_Course sc33=new Student_Course(s3,softWare);
        session.save(sc22);
        session.flush();

        Student_Subject ss333=new Student_Subject(s3,physics);
        session.save(ss333);
        session.flush();

        Student_Course sc333=new Student_Course(s3,mechanic);
        session.save(sc333);
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
