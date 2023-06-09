package il.cshaifasweng.OCSFMediatorExample.entities.appUsers;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Student_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Student_Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends User implements  Serializable{
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private Set<Grade> grades=new HashSet<>();
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
    private Set<Student_Course> studentCourses=new HashSet<>();
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
    private Set<Student_Subject> studentSubjects=new HashSet<>();
    public Student(String userID, String passWord, String userName, String firstName, String lastName) {
        super(userID, passWord, userName, firstName, lastName);
    }
    public Student(Student student)
    {
        super(student);
    }
    public Student() {
        super();
    }


    public Set<Student_Course> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(Set<Student_Course> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public Set<Student_Subject> getStudentSubjects() {
        return studentSubjects;
    }

    public void setStudentSubjects(Set<Student_Subject> studentSubjects) {
        this.studentSubjects = studentSubjects;
    }
    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
    }
}
