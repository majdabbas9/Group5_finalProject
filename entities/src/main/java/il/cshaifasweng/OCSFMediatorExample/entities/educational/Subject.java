package il.cshaifasweng.OCSFMediatorExample.entities.educational;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Student_Subject;
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
@Table(name = "subjects")
public class Subject implements  Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subjectName;
    @OneToMany(mappedBy = "subject",fetch = FetchType.EAGER)
    Set<Teacher_Subject> subjectTeachers;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "courseSubject")
    private Set<Course> subjectCourses=new HashSet<>();
    @OneToMany(mappedBy = "questionSubject",fetch = FetchType.EAGER)
    private Set<Question> subjectQuestions=new HashSet<>();
    @OneToMany(mappedBy = "subject",fetch = FetchType.EAGER)
    private Set<Student_Subject> subjectStudents=new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "examSubject")
    private Set<Exam> subjectExams=new HashSet<>();
    public Subject() {

    }
    public Subject(String subjectName) {
        this.subjectName = subjectName;
        this.subjectTeachers=new HashSet<>();
    }
    public int getId() {
        return id;
    }
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Set<Course> getSubjectCourses() {
        return subjectCourses;
    }

    public void setSubjectCourses(Set<Course> subjectCourses) {
        this.subjectCourses = subjectCourses;
    }

    public Set<Question> getSubjectQuestions() {
        return subjectQuestions;
    }

    public void setSubjectQuestions(Set<Question> subjectQuestions) {
        this.subjectQuestions = subjectQuestions;
    }

    public Set<Student_Subject> getSubjectStudents() {
        return subjectStudents;
    }

    public Set<Teacher_Subject> getSubjectTeachers() {
        return  subjectTeachers;
    }

    public void setSubjectTeachers(Set<Teacher_Subject> subjectTeachers) {
        this.subjectTeachers = subjectTeachers;
    }

    public void setSubjectExams(Set<Exam> subjectExams) {
        this.subjectExams = subjectExams;
    }

    public Set<Exam> getSubjectExams() {
        return subjectExams;
    }

    @Override
    public String toString() {
        return this.subjectName;
    }


}
