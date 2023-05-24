package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import aidClasses.GlobalDataSaved;
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
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class GenerateAll {

    public static void generateEducational(Session session) {

        List<Course> global_courses = new ArrayList<>();
        List<Subject> global_subjects = new ArrayList<>();
        List<Course> mathCourses = new ArrayList<>();
        List<Course> csCourses = new ArrayList<>();

        /*creating subjects*/
        Subject math = new Subject("math");
        Subject cs = new Subject("computer science");

        session.save(math);
        session.save(cs);
        session.flush();
        /*end of creating subjects*/

        /*creating courses*/
        Course math_c1 = new Course("linear algebra 1", math);
        Course math_c2 = new Course("linear algebra 2", math);
        Course math_c3 = new Course("discrete math", math);

        session.save(math_c1);
        session.save(math_c2);
        session.save(math_c3);
        session.flush();

        Course cs_c1 = new Course("introduction to cs", cs);
        Course cs_c2 = new Course("data structure", cs);

        session.save(cs_c1);
        session.save(cs_c2);
        session.flush();

        mathCourses.add(math_c1);
        mathCourses.add(math_c2);
        mathCourses.add(math_c3);
        csCourses.add(cs_c1);
        csCourses.add(cs_c2);
        /*end of creating courses*/

        /*adding  courses to subject*/
        math.setSubjectCourses(mathCourses);

        session.save(math);
        session.flush();

        cs.setSubjectCourses(csCourses);

        session.save(cs);
        session.flush();
        /*end of adding  courses to subject*/

        /*editing the global*/
        global_courses.add(math_c1);
        global_courses.add(math_c2);
        global_courses.add(math_c3);
        global_courses.add(cs_c1);
        global_courses.add(cs_c2);
        global_subjects.add(math);
        global_subjects.add(cs);
        /*end of editing the global*/

        /*creating students*/
        Student student = new Student("33333", "1", "1", "majd", "abbas");
        Student student1 = new Student("111", "2", "2", "hdfhfh", "jfjtj");

        session.save(student);
        session.save(student1);
        session.flush();
        /*creating students*/


        /*creating principal*/
        Principal p1 = new Principal("123", "123", "mostufa", "mostufa", "mostufa");

        session.save(p1);
        session.flush();
        /*creating principal*/


        /*creating teacher*/
        Teacher t1 = new Teacher("33333", "teacher", "teacher", "mohamed", "abbas");

        session.save(t1);
        session.flush();
        /*creating teacher*/

        /*adding courses and subjects to students*/
        student.setStudentCourses(global_courses);
        student.setStudentSubjects(global_subjects);

        session.save(student);
        session.flush();

        student1.setStudentCourses(global_courses);
        student1.setStudentSubjects(global_subjects);

        session.save(student1);
        session.flush();
        /*end of adding courses and subjects to students*/

        /*adding courses and subjects to teachers*/
        t1.setTeacherCourses(global_courses);
        t1.setTeacherSubjects(global_subjects);
        session.save(t1);
        session.flush();
        /*end of adding courses and subjects to teachers*/

        /*adding teachers and students to courses and subjects*/
        for (Course course : global_courses) {
            course.getCourseStudents().add(student);
            course.getCourseStudents().add(student1);
            course.getCourseTeachers().add(t1);
            session.save(course);
            session.flush();
        }
        for (Subject subject : global_subjects) {
            subject.getSubjectStudents().add(student);
            subject.getSubjectStudents().add(student1);
            subject.getSubjectTeachers().add(t1);
            session.save(subject);
            session.flush();
        }

    }

    public static void generateUsers(Session session) {


    }


}
