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
        Teacher t2=new Teacher("33333","11","11","adam","agbaria");
        Teacher t3=new Teacher("31313","111","111","majd","hijazi");

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

        Student_Course sc3=new Student_Course(s3,discMath);
        session.save(sc3);
        session.flush();

        Student_Subject ss33=new Student_Subject(s3,cs);
        session.save(ss33);
        session.flush();

        Student_Course sc33=new Student_Course(s3,operatingSystem);
        session.save(sc22);
        session.flush();

        Student_Subject ss333=new Student_Subject(s3,physics);
        session.save(ss333);
        session.flush();

        Student_Course sc333=new Student_Course(s3,electromagnetism);
        session.save(sc333);
        session.flush();

        /*end of student add */


        /*adding question*/

        /*adding question for t1*/

        List<String> ch1=new ArrayList<>();
        ch1.add("1");ch1.add("2");ch1.add("3");ch1.add("4");
        Question q1=new Question("","2+2","00000",ch1,"4");
        List<Course> courses1=new ArrayList<>();
        courses1.add(algebra);
        Adding.addQuestion(SimpleServer.getSession(),q1,courses1,math,t1);

        List<String> ch2=new ArrayList<>();
        ch2.add("4");ch2.add("5");ch2.add("6");ch2.add("10");
        Question q2=new Question("","1*5","00001",ch2,"5");
        Adding.addQuestion(SimpleServer.getSession(),q2,courses1,math,t1);

        List<String> ch3=new ArrayList<>();
        ch3.add("1");ch3.add("5");ch3.add("3");ch3.add("15");
        Question q3=new Question("","3x+3=12","00002",ch3,"3");
        List<Course> courses2=new ArrayList<>();
        courses2.add(algebra2);
        Adding.addQuestion(SimpleServer.getSession(),q3,courses2,math,t1);

        List<String> ch4=new ArrayList<>();
        ch4.add("4");ch4.add("5");ch4.add("15");ch4.add("10");
        Question q4=new Question("","(2x-6)5=10","00003",ch4,"4");
        Adding.addQuestion(SimpleServer.getSession(),q4,courses2,math,t1);

        List<String> ch5=new ArrayList<>();
        ch5.add("x=1,y=2");ch5.add("x=2,y=3");ch5.add("x=2,y=1");ch5.add("x=1,y=1");
        Question q5=new Question("","3x+2y=8,2y+x=4","00004",ch5,"x=2,y=1");
        List<Course> courses3=new ArrayList<>();
        courses3.add(discMath);
        Adding.addQuestion(SimpleServer.getSession(),q5,courses3,math,t1);

        List<String> ch6=new ArrayList<>();
        ch6.add("x=8,y=-2");ch6.add("x=-2,y=8");ch6.add("x=8,y=2");ch6.add("x=-8,y=2");
        Question q6=new Question("","2x-2y=20,x+4y=0","00005",ch6,"x=8,y=-2");
        Adding.addQuestion(SimpleServer.getSession(),q6,courses3,math,t1);

        /*end of adding question for t1*/

        /*adding question for t2*/

        List<String> ch11=new ArrayList<>();
        ch11.add("1");ch11.add("2");ch11.add("3");ch11.add("4");
        Question q11=new Question("","int x=4; x=?","00010",ch11,"4");
        List<Course> courses11=new ArrayList<>();
        courses11.add(csIntro);
        Adding.addQuestion(SimpleServer.getSession(),q11,courses11,cs,t2);

        List<String> ch22=new ArrayList<>();
        ch22.add("4");ch22.add("5");ch22.add("6");ch22.add("10");
        Question q22=new Question("","int x=4; x++; x=?","00011",ch22,"5");
        Adding.addQuestion(SimpleServer.getSession(),q22,courses11,cs,t2);

        List<String> ch33=new ArrayList<>();
        ch33.add("exception");ch33.add("b");ch33.add("true");ch33.add("false");
        Question q33=new Question("","boolean b=true;if(b)return false; else return true; what is the output","00012",ch33,"false");
        List<Course> courses22=new ArrayList<>();
        courses22.add(softWare);
        Adding.addQuestion(SimpleServer.getSession(),q33,courses22,cs,t2);

        List<String> ch44=new ArrayList<>();
        ch44.add("exception");ch44.add("notGreater");ch44.add("x");ch44.add("greater");
        Question q44=new Question("","int x=5; if(x>5) return greater; else return notGreater","00013",ch44,"notGreater");
        Adding.addQuestion(SimpleServer.getSession(),q44,courses22,cs,t2);

        List<String> ch55=new ArrayList<>();
        ch55.add("exception");ch55.add("x");ch55.add("7");ch55.add("x=7");
        Question q55=new Question("","x=7; return x; what is the output","00014",ch55,"exception");
        List<Course> courses33=new ArrayList<>();
        courses33.add(operatingSystem);
        Adding.addQuestion(SimpleServer.getSession(),q55,courses33,cs,t2);

        List<String> ch66=new ArrayList<>();
        ch66.add("y");ch66.add("8");ch66.add("10");ch66.add("3");
        Question q66=new Question("","int x=5,y=3;while(x<10){x++;y++}return y;","00015",ch66,"8");
        Adding.addQuestion(SimpleServer.getSession(),q66,courses33,cs,t2);

        /*end of adding question for t2*/

        /*adding question for t3*/

        List<String> ch111=new ArrayList<>();
        ch111.add("100");ch111.add("20");ch111.add("70");ch111.add("50");
        Question q111=new Question("","what is the water boiling temperature","00020",ch111,"100");
        List<Course> courses111=new ArrayList<>();
        courses111.add(introPhysics);
        Adding.addQuestion(SimpleServer.getSession(),q111,courses111,physics,t3);

        List<String> ch222=new ArrayList<>();
        ch222.add("20");ch222.add("0");ch222.add("-10");ch222.add("10");
        Question q222=new Question("","what is the water freezing temperature","00021",ch222,"0");
        Adding.addQuestion(SimpleServer.getSession(),q222,courses11,physics,t3);

        List<String> ch333=new ArrayList<>();
        ch333.add("0");ch333.add("5");ch333.add("100");ch333.add("10");
        Question q333=new Question("","what is the gravity on earth","00022",ch333,"10");
        List<Course> courses222=new ArrayList<>();
        courses222.add(mechanic);
        Adding.addQuestion(SimpleServer.getSession(),q333,courses222,physics,t3);

        List<String> ch444=new ArrayList<>();
        ch444.add("5/3");ch444.add("1.62");ch444.add("10/6");ch444.add("allChoicesCorrect");
        Question q444=new Question("","what is the gravity on the moon","00023",ch444,"allChoicesCorrect");
        Adding.addQuestion(SimpleServer.getSession(),q444,courses222,physics,t3);

        List<String> ch555=new ArrayList<>();
        ch555.add("300,000km/h");ch555.add("300km/s");ch555.add("300,000km/s");ch555.add("300,000m/s");
        Question q555=new Question("","what is the light speed","00024",ch555,"300,000km/s");
        List<Course> courses333=new ArrayList<>();
        courses333.add(electromagnetism);
        Adding.addQuestion(SimpleServer.getSession(),q555,courses333,physics,t3);

        List<String> ch666=new ArrayList<>();
        ch666.add("1235km/h");ch666.add("1235km/s");ch666.add("1235m/h");ch666.add("1235m/s");
        Question q666=new Question("","What is the speed of sound","00025",ch666,"1235km/h");
        Adding.addQuestion(SimpleServer.getSession(),q666,courses333,physics,t3);

        /*end of adding question for t3*/


        /*end of adding question*/

        /*adding exam*/
        List<Integer> points1=new ArrayList<>();
        List<Question> questions1=new ArrayList<>();
        questions1.add(q1);questions1.add(q2);
        points1.add(50);points1.add(50);
        Exam exam=new Exam(30,"","","",points1);
        Adding.addExam(SimpleServer.getSession(),exam,t1,algebra,math,questions1);
        /*end of adding exam*/

        /*adding comp exam*/
        ComputerizedExamToExecute compExam=new ComputerizedExamToExecute("24/5/2023 23:19",1234);
        Adding.addCompExam(SimpleServer.getSession(),compExam,t1,exam);
        /*end of adding comp exam*/

        /*adding copy*/
        Copy copy=new Copy("4,5",compExam);
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
