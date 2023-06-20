package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.*;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Principal;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenerateAll {

    public static void generateEducational(Session session)
    {
        /*adding the principal*/
        Principal principal=new Principal("1111","0","0","moustfa","jbraeen");
        principal.setKind("principal");
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

        Teacher t1=new Teacher("21212","1","1","adam","rayan");
        t1.setKind("teacher");
        Teacher t2=new Teacher("33333","3","3","majd","abbas");
        t2.setKind("teacher");
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

        Student s1=new Student("111","2","2","mohamed","hijazi");
        s1.setKind("student");
        session.save(s1);
        session.flush();

        Student_Subject ss1=new Student_Subject(s1,math);

        session.save(ss1);
        session.flush();

        Student_Course sc1=new Student_Course(s1,algebra);
        session.save(sc1);
        session.flush();

        Student s2=new Student("222","22","22","mohamad","jbaren");
        s2.setKind("student");
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
        choices.add("<1;2;2>");
        choices.add("<1;1;2>");
        choices.add("<1;0;2>");
        choices.add("<2;1;2>");
        Question question8 = new Question("", "<1;1;0> + <0;1;2> =", "<1;2;2>", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question8,coursesIds, math.getId(),t1.getId());

        choices.clear();
        choices.add("<1;2;2>");
        choices.add("<1;1;2>");
        choices.add("<1;0;2>");
        choices.add("<2;1;2>");
        Question question9 = new Question("", "<6;10;13> - <5;10;11> =", "<1;0;2>", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question9,coursesIds, math.getId(),t1.getId());

        choices.clear();
        choices.add("<24;4;7>");
        choices.add("<22;5;7>");
        choices.add("<23;4;6>");
        choices.add("<23;4;7>");
        Question question10 = new Question("", "<1;1;0> + <3;1;2> + <19;2;5> = ", "<23;4;7>", choices);
        coursesIds.clear();
        coursesIds.add(algebra.getId());
        buildQuestions(session,question10,coursesIds, math.getId(),t1.getId());

        Exam exam=new Exam(10,"","","");
        List<Question> examQuestions=new ArrayList<>();

        examQuestions.add(question1);examQuestions.add(question2);examQuestions.add(question3);examQuestions.add(question4);
        buildExam(session,exam,examQuestions,1,math,t1);

        examQuestions.clear();

        examQuestions.add(question5);examQuestions.add(question6); examQuestions.add(question7);examQuestions.add(question8);
        examQuestions.add(question9); examQuestions.add(question10);

        Exam exam1=new Exam(20,"","","");
        buildExam(session,exam1,examQuestions,1,math,t2);

        examQuestions.clear();

        examQuestions.add(question1);examQuestions.add(question2);examQuestions.add(question3);examQuestions.add(question4);
        examQuestions.add(question5);examQuestions.add(question6); examQuestions.add(question7);examQuestions.add(question8);
        examQuestions.add(question9);examQuestions.add(question10);

        Exam exam2=new Exam(15,"","","");
        buildExam(session,exam2,examQuestions,1,math,t2);

        /*creating exams to execute*/

        /*first exam*/
        ExamToExecute examToExecute=new ComputerizedExamToExecute("2023-06-18 20:48","1234");
        examToExecute.setExam(exam);
        examToExecute.setTeacherThatExecuted(t2);
        session.save(examToExecute);
        session.flush();

        exam.getCompExamsToExecute().add(examToExecute);
        session.update(exam);
        /*end of first exam*/

        /*second exam*/
        ExamToExecute examToExecute1=new ComputerizedExamToExecute("2023-06-18 21:05","1235");
        examToExecute1.setExam(exam1);
        examToExecute1.setTeacherThatExecuted(t1);
        session.save(examToExecute1);
        session.flush();

        exam1.getCompExamsToExecute().add(examToExecute1);
        session.update(exam1);
        /*end of second exam*/

        /*third exam*/
        ExamToExecute examToExecute2=new ComputerizedExamToExecute("2023-06-18 21:05","1236");
        examToExecute2.setExam(exam2);
        examToExecute2.setTeacherThatExecuted(t1);
        session.save(examToExecute2);
        session.flush();

        exam2.getCompExamsToExecute().add(examToExecute2);
        session.update(exam2);
        /*end of third exam*/
        /*end of adding examToExe*/


        /*adding copies to the first exam*/
        Copy copy=new Copy("6,25,2,-10");
        Grade grade=new Grade(50,false,5,true,"2023-06-18","20:52",true);

        addCopy(session,examToExecute,grade,copy,s1);

        Copy copy1=new Copy("6,31,3,-10");
        Grade grade1=new Grade(25,false,7,true,"2023-06-18","20:55",true);

        addCopy(session,examToExecute,grade1,copy1,s2);
        /*end of adding copies to frsit exam*/

        /*adding copies to the seond exam*/
        Copy copy11=new Copy("x,101,34,<1,2,2>,<1,2,2>,<24,4,7>");
        Grade grade11=new Grade(20,false,10,true,"2023-06-18","21:10",true);

        addCopy(session,examToExecute1,grade11,copy11,s1);

        Copy copy12=new Copy("1,101,34,<1,2,2>,<1,2,2>,<23,4,7>");
        Grade grade12=new Grade(25,false,7,true,"2023-06-18","20:55",true);

        addCopy(session,examToExecute1,grade12,copy12,s2);
        /*end of adding copies to frsit exam*/

        /*adding copies to the third exam*/
        Copy copy21=new Copy("6,31,3,-10,x,101,34,<1,2,2>,<1,2,2>,<24,4,7>");
        Grade grade21=new Grade(100,false,15,true,"2023-06-18","21:10",true);

        addCopy(session,examToExecute2,grade21,copy21,s1);

        Copy copy22=new Copy("6,31,3,-10,1,101,34,<1,2,2>,<1,2,2>,<23,4,7>");
        Grade grade22=new Grade(60,false,10,true,"2023-06-18","21:10",true);

        addCopy(session,examToExecute2,grade22,copy22,s2);
        /*end of adding copies to third exam*/

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
    public static void buildExam(Session session, Exam exam, List<Question> questions, int courseId, Subject subject, Teacher teacher)
    {
        Course course=GetExamBuliding.getCourseById(session,courseId);
        int size= 100/questions.size();
        exam.setExam_ID(GetExamBuliding.examID(course.getCourseExams().size(),subject.getId(),courseId));
        session.save(exam);
        session.flush();

        Set<Exam_Question> examQuestions=new HashSet<>();
        for(Question question:questions)
        {
            Exam_Question exam_question=new Exam_Question(exam,question,size);
            session.save(exam_question);
            session.flush();

            exam.getExamQuestions().add(exam_question);
            question.getQuestionExams().add(exam_question);
            session.update(exam);
            session.update(question);
            session.flush();
        }

        course.getCourseExams().add(exam);
        exam.setExamCourse(course);
        session.update(exam);
        session.update(course);
        session.flush();

        subject.getSubjectExams().add(exam);
        exam.setExamSubject(subject);
        session.update(exam);
        session.update(subject);

        teacher.getExamsCreated().add(exam);
        exam.setTeacherThatCreated(teacher);
        session.update(exam);
        session.update(teacher);
        session.flush();
    }
    public static void addCopy(Session session,ExamToExecute examToExecute,Grade grade,Copy copy ,Student student)
    {
        grade.setStudent(student);
        session.save(grade);

        student.getGrades().add(grade);
        session.update(student);session.flush();

        copy.setCompExamToExecute(examToExecute);
        session.save(copy);session.flush();

        examToExecute.getCopies().add(copy);
        session.update(examToExecute);session.flush();

        session.save(grade);

        grade.setExamCopy(copy);
        copy.setGrade(grade);
        session.update(grade);
        session.update(copy);session.flush();
    }
}
