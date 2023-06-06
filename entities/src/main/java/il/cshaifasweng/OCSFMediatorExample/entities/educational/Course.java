package il.cshaifasweng.OCSFMediatorExample.entities.educational;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Student_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course implements  Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String courseName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject courseSubject;

    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER)
    private Set<Teacher_Course> courseTeachers=new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "examCourse")
    private Set<Exam> courseExams=new HashSet<>();
    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER)
    private Set<Course_Question> courseQuestions=new HashSet<>();
    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER)
    private Set<Student_Course> courseStudents=new HashSet<>();


    public Course() {

    }
    public Course(String courseName,Subject subject) {
        this.courseName = courseName;
        setCourseSubject(subject);
    }

    public Course(int id, String courseName) {
        this.id = id;
        this.courseName = courseName;
        this.courseQuestions =new HashSet<>();
    }
    public Course(Course course,String str)
    {
        this.id=course.id;
        this.courseName=course.getCourseName();
    }

    public int getId() {
        return id;
    }
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName,Subject courseSubject) {
        this.courseName = courseName;
        setCourseSubject(courseSubject);
    }
    public Subject getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(Subject courseSubject) {
        this.courseSubject = courseSubject;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public Set<Student_Course> getCourseStudents() {
        return courseStudents;
    }

    public void setCourseStudents(Set<Student_Course> courseStudents) {
        this.courseStudents = courseStudents;
    }

    public Set<Teacher_Course> getCourseTeachers() {
        return courseTeachers;
    }

    public void setCourseTeachers(Set<Teacher_Course> courseTeachers) {
        this.courseTeachers = courseTeachers;
    }
    public Set<Exam> getCourseExams() {
        return courseExams;
    }

    public void setCourseExams(Set<Exam> courseExams) {
        this.courseExams = courseExams;
    }

    public Set<Course_Question> getCourseQuestions() {
        return courseQuestions;
    }

    public void setCourseQuestions(Set<Course_Question> courseQuestions) {
        this.courseQuestions = courseQuestions;
    }

    @Override
    public String toString() {
        return this.courseName;
    }

}
