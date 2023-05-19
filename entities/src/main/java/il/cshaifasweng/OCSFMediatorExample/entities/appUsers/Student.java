package il.cshaifasweng.OCSFMediatorExample.entities.appUsers;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student extends User implements  Serializable{
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    private List<Grade> grades;
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Course.class
    )
    @JoinTable(
            name="students_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> studentCourses;
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Subject.class
    )
    @JoinTable(
            name="students_subjects",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> studentSubjects;
    public Student(String userID, String passWord, String userName, String firstName, String lastName) {
        super(userID, passWord, userName, firstName, lastName);
        this.grades=new ArrayList<>();
        this.studentCourses=new ArrayList<>();
        this.studentSubjects=new ArrayList<>();
    }
    public Student() {
        super();
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Course> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<Course> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public List<Subject> getStudentSubjects() {
        return studentSubjects;
    }

    public void setStudentSubjects(List<Subject> studentSubjects) {
        this.studentSubjects = studentSubjects;
    }
}
