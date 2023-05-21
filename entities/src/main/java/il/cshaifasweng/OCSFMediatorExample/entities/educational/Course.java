package il.cshaifasweng.OCSFMediatorExample.entities.educational;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course implements  Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String courseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject courseSubject;
    @ManyToMany(mappedBy = "questionCourses",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Question.class
    )
    private List<Question> courseQuestions;
    @ManyToMany(mappedBy = "studentCourses",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Student.class
    )
    private List<Student> courseStudents;
    @ManyToMany(mappedBy = "teacherCourses",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Teacher.class
    )
    private List<Teacher> courseTeachers;
    public Course() {

    }
    public Course(String courseName,Subject subject) {
        this.courseName = courseName;
        setCourseSubject(subject);
        this.courseQuestions=new ArrayList<>();
        this.courseStudents=new ArrayList<>();
        this.courseTeachers=new ArrayList<>();
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

    public List<Question> getCourseQuestions() {
        return courseQuestions;
    }

    public void setCourseQuestions(List<Question> courseQuestions) {
        this.courseQuestions = courseQuestions;
    }

    public List<Student> getCourseStudents() {
        return courseStudents;
    }

    public void setCourseStudents(List<Student> courseStudents) {
        this.courseStudents = courseStudents;
    }

    public List<Teacher> getCourseTeachers() {
        return courseTeachers;
    }

    public void setCourseTeachers(List<Teacher> courseTeachers) {
        this.courseTeachers = courseTeachers;
    }
    @Override
    public String toString() {
        return this.courseName;
    }
}
