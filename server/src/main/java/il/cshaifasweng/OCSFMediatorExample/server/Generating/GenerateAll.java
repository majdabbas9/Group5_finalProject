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
        List<Course> global_courses=new ArrayList<>();
        List<Subject> global_subjects=new ArrayList<>();
        List<Course> mathCourses=new ArrayList<>();
        List<Course> csCourses=new ArrayList<>();
        Subject math=new Subject("math");
        Subject cs=new Subject("computer science");

        session.save(math);
        session.save(cs);
        session.flush();

        Course math_c1=new Course("linear algebra 1",math);
        Course math_c2=new Course("linear algebra 2",math);
        Course math_c3=new Course("discrete math",math);
        session.save(math_c1);
        session.save(math_c2);
        session.save(math_c3);
        session.flush();

        mathCourses.add(math_c1);
        mathCourses.add(math_c2);
        mathCourses.add(math_c3);
        math.setSubjectCourses(mathCourses);
        session.save(math);
        session.flush();

        Course cs_c1=new Course("introduction to cs",cs);
        Course cs_c2=new Course("data structure",cs);
        session.save(cs_c1);
        session.save(cs_c2);
        session.flush();

        csCourses.add(cs_c1);csCourses.add(cs_c2);
        cs.setSubjectCourses(csCourses);
        session.save(cs);
        session.flush();

        global_courses.add(math_c1);global_courses.add(math_c2);global_courses.add(math_c3);
        global_courses.add(cs_c1);global_courses.add(cs_c2);

        global_subjects.add(math);global_subjects.add(cs);

        Student student=new Student("33333","1","1","majd","abbas");
        Student student1=new Student("111","2","2","hdfhfh","jfjtj");
        session.save(student);
        session.save(student1);
        session.flush();
        Teacher t1=new Teacher("33333","teacher","teacher","mohamed","abbas");
        session.save(t1);
        session.flush();

        student.setStudentCourses(global_courses);
        student.setStudentSubjects(global_subjects);
        student1.setStudentCourses(global_courses);
        student1.setStudentSubjects(global_subjects);
        session.save(student);
        session.save(student1);
        session.flush();

        t1.setTeacherCourses(global_courses);
        t1.setTeacherSubjects(global_subjects);
        session.save(t1);
        session.flush();

        for(Course course:global_courses)
        {
            course.getCourseStudents().add(student);
            course.getCourseStudents().add(student1);
            course.getCourseTeachers().add(t1);
            session.save(course);
            session.flush();
        }
        for(Subject subject:global_subjects)
        {
            subject.getSubjectStudents().add(student);
            subject.getSubjectStudents().add(student1);
            subject.getSubjectTeachers().add(t1);
            session.save(subject);
            session.flush();
        }
        List<String> ch=new ArrayList<>();
        ch.add("1");ch.add("2");ch.add("3");ch.add("4");
        Question q1=new Question("hi","what is 2+2","5757",ch,"3",math,t1);
        t1.getQuestionsCreated().add(q1);
        q1.setQuestionCourses(mathCourses);
        session.save(q1);
        session.save(t1);
        session.flush();

        List<String> ch1=new ArrayList<>();
        ch1.add("5");ch1.add("6");ch1.add("7");ch1.add("8");
        Question q2=new Question("hi","what is 2+3","1234",ch1,"0",math,t1);
        t1.getQuestionsCreated().add(q2);
        q2.setQuestionCourses(mathCourses);
        session.save(q2);
        session.save(t1);
        session.flush();

        List<Integer> points=new ArrayList<>();
        points.add(50);
        points.add(50);
        Exam exam=new Exam(t1,90,"6256","hi","hi",points);
        exam.getExamQuestions().add(q1);
        exam.getExamQuestions().add(q2);
        session.save(exam);
        session.flush();

        q1.getQuestionExams().add(exam);
        q2.getQuestionExams().add(exam);
        session.save(q1);
        session.save(q2);
        session.flush();

        ComputerizedExamToExecute ct=new ComputerizedExamToExecute(exam,t1);
        session.save(exam);
        session.save(ct);
        session.flush();

        exam.getCompExamsToExecute().add(ct);
        session.save(ct);
        session.flush();

        Copy cop=new Copy("01",ct);
        Grade grade=new Grade(cop,student,false,50,true,"19/5/2023","23:33");
        cop.setGrade(grade);
        session.save(cop);
        session.save(grade);
        session.flush();

        Copy cop1=new Copy("12",ct);
        Grade grade1=new Grade(cop1,student1,false,60,true,"19/5/2023","23:36");
        cop1.setGrade(grade1);
        session.save(cop1);
        session.save(grade1);
        session.flush();

    }
    public static void generateUsers(Session session)
    {


    }


}
