package il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "students_subjects")
public class Student_Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    Student student;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    Subject subject;

    public Student_Subject(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
    }

    public Student_Subject() {

    }

    public int getId() {
        return id;
    }
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}