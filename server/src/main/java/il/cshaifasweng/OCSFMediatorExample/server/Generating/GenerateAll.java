package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import aidClasses.aidClassesForTeacher.QuestionsExamsID;
import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.*;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Principal;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Set;

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

        Student s2=new Student("222","22","22","mohamad","abbas");
        session.save(s2);
        session.flush();

        Student_Subject ss2=new Student_Subject(s2,math);
        session.save(ss2);
        session.flush();

        Student_Course sc2=new Student_Course(s2,algebra);
        session.save(sc2);
        session.flush();

        List<String> choices = new ArrayList<>();
        choices.add("6");
        choices.add("7");
        choices.add("8");
        choices.add("9");
        Question question1 = new Question("", "5 + 3 = ", "8", choices);
        List<Integer> coursesIds = new ArrayList<>();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question1,coursesIds, math.getId(),t1.getId());

        choices.clear();
        choices.add("25");
        choices.add("31");
        choices.add("28");
        choices.add("29");
        Question question2 = new Question("", "7 * 4 = ", "28", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question2,coursesIds, math.getId(),t1.getId());

        choices.clear();
        choices.add("1");
        choices.add("2");
        choices.add("3");
        choices.add("4");
        Question question3 = new Question("", "x - 5 + 4 * x = 10", "3", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        coursesIds.add(algebra2.getId());
        buildQuestions(session,question3,coursesIds, math.getId(),t1.getId());

        choices.clear();
        choices.add("-10");
        choices.add("-15");
        choices.add("10");
        choices.add("15");
        Question question4 = new Question("", "5 * (-5) - 9 * 4 / 6 + 16 =", "-15", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question4,coursesIds, math.getId(),t1.getId());

        choices.clear();
        choices.add("x");
        choices.add("1");
        choices.add("0");
        choices.add("x-1");
        Question question5 = new Question("", "2 * x - 1 = x ", "1", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question5,coursesIds, math.getId(),t1.getId());

        choices.clear();
        choices.add("101");
        choices.add("102");
        choices.add("103");
        choices.add("104");
        Question question6 = new Question("", "1000 / 8 * 2 - 12 * 13 + 9 = ", "103", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question6,coursesIds, math.getId(),t1.getId());


        choices.clear();
        choices.add("34");
        choices.add("31");
        choices.add("28");
        choices.add("29");
        Question question7 = new Question("", "6 * 7 - 24 / 3 = ", "34", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question7,coursesIds, math.getId(),t1.getId());

        choices.clear();
        choices.add("<1,2,2>");
        choices.add("<1,1,2>");
        choices.add("<1,0,2>");
        choices.add("<2,1,2>");
        Question question8 = new Question("", "<1,1,0> + <0,1,2> =", "<1,2,2>", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question8,coursesIds, math.getId(),t1.getId());

        choices.clear();
        choices.add("<1,2,2>");
        choices.add("<1,1,2>");
        choices.add("<1,0,2>");
        choices.add("<2,1,2>");
        Question question9 = new Question("", "<6,10,13> - <5,10,11> =", "<1,0,2>", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question9,coursesIds, math.getId(),t1.getId());

        choices.clear();
        choices.add("<24,4,7>");
        choices.add("<22,5,7>");
        choices.add("<23,4,6>");
        choices.add("<23,4,7>");
        Question question10 = new Question("", "<1,1,0> + <3,1,2> + <19,2,5> = ", "<23,4,7>", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question10,coursesIds, math.getId(),t1.getId());
    }

    public static void buildQuestions(Session session, Question question, List<Integer> CoursesIds,int subjectId,int teacherId) {
        List<Course>questionCourses=GetExamBuliding.getCoursesById(session,CoursesIds);
        Subject questionSubject=GetExamBuliding.getSubjectById(session,subjectId);
        Teacher teacher=GetUsers.getTeacherById(session,teacherId);
        session.save(question);
        session.flush();
        question.setQuestionID(GetExamBuliding.questionID(questionSubject.getSubjectQuestions().size(),subjectId));
        question.setTeacherThatCreated(teacher);
        question.setQuestionSubject(questionSubject);

        session.update(question);
        session.flush();

        teacher.getQuestionsCreated().add(question);
        session.update(teacher);
        session.flush();

        questionSubject.getSubjectQuestions().add(question);
        session.update(questionSubject);
        session.flush();

        Course_Question courseQuestion;
        for(Course course:questionCourses) {
            courseQuestion=new Course_Question(course,question);
            session.save(courseQuestion);
            //session.flush();

            course.getCourseQuestions().add(courseQuestion);
            session.update(course);
            //session.flush();

            question.getQuestionCourses().add(courseQuestion);
            session.update(question);
            session.flush();
        }

    }


}
