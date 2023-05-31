package il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "grades")
public class Grade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examCopy_id")
    private Copy examCopy;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;
    private int grade;
    private boolean isManuel;
    private int timeToSolve;
    private boolean doneOnTime;
    private String date;
    private String hour;

    private boolean teacherApprovement;
    public Grade(Student student, boolean isManuel, int timeToSolve, boolean doneOnTime, String date, String hour) {
        this.student = student;
        this.grade = 0;
        this.isManuel = isManuel;
        this.timeToSolve = timeToSolve;
        this.doneOnTime = doneOnTime;
        this.date = date;
        this.hour = hour;
    }

    public Grade( Student student, int grade, boolean isManuel, int timeToSolve, boolean doneOnTime, String date, String hour, boolean teacherApprovement) {
        this.student = student;
        this.grade = grade;
        this.isManuel = isManuel;
        this.timeToSolve = timeToSolve;
        this.doneOnTime = doneOnTime;
        this.date = date;
        this.hour = hour;
        this.teacherApprovement = teacherApprovement;
    }

    public Grade() {

    }

    public int getId() {
        return id;
    }

    public Copy getExamCopy() {
        return examCopy;
    }

    public void setExamCopy(Copy examCopy) {
        this.examCopy = examCopy;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isManuel() {
        return isManuel;
    }

    public void setManuel(boolean manuel) {
        isManuel = manuel;
    }

    public int getTimeToSolve() {
        return timeToSolve;
    }

    public void setTimeToSolve(int timeToSolve) {
        this.timeToSolve = timeToSolve;
    }

    public boolean isDoneOnTime() {
        return doneOnTime;
    }

    public void setDoneOnTime(boolean doneOnTime) {
        this.doneOnTime = doneOnTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public boolean isTeacherApprovement() {
        return teacherApprovement;
    }

    public void setTeacherApprovement(boolean teacherApprovement) {
        this.teacherApprovement = teacherApprovement;
    }
}
