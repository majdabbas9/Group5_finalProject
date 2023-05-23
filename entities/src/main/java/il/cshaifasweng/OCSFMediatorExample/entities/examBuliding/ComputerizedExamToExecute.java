package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compExams")
public class ComputerizedExamToExecute implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String dateOfExam;
    private int code;
    private int examAverage;
    private int numOfStudentDoing;
    @ElementCollection
    private List<Integer> histogram;
    @Column(name = "inTime")
    private int numberOfStudentDoneInTime;
    @Column(name = "notInTime")
    private int numberOfStudentNotDoneInTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compExamToExecute")
    private List<Copy> copies;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherThatExecuted;

    public ComputerizedExamToExecute(String dateOfExam,int code,Exam exam, Teacher teacherThatExecuted) {
        this.examAverage = 0;
        this.histogram = new ArrayList<>();
        this.numberOfStudentDoneInTime = 0;
        this.numberOfStudentNotDoneInTime = 0;
        setExam(exam);
        this.copies =new ArrayList<>();
        setTeacherThatExecuted(teacherThatExecuted);
        this.code=code;
        this.dateOfExam=dateOfExam;
        this.numOfStudentDoing=0;
    }

    public ComputerizedExamToExecute() {

    }

    public int getId() {
        return id;
    }


    public int getExamAverage() {
        return examAverage;
    }

    public void setExamAverage(int average) {
        this.examAverage = average;
    }

    public List<Integer> getHistogram() {
        return histogram;
    }

    public void setHistogram(List<Integer> histogram) {
        this.histogram = histogram;
    }

    public int getNumberOfStudentDoneInTime() {
        return numberOfStudentDoneInTime;
    }

    public void setNumberOfStudentDoneInTime(int numberOfStudentDoneInTime) {
        this.numberOfStudentDoneInTime = numberOfStudentDoneInTime;
    }

    public int getNumberOfStudentNotDoneInTime() {
        return numberOfStudentNotDoneInTime;
    }

    public void setNumberOfStudentNotDoneInTime(int numberOfStudentNotDoneInTime) {
        this.numberOfStudentNotDoneInTime = numberOfStudentNotDoneInTime;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<Copy> getCopies() {
        return copies;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }

    public Teacher getTeacherThatExecuted() {
        return teacherThatExecuted;
    }

    public void setTeacherThatExecuted(Teacher teacherThatExecuted) {
        this.teacherThatExecuted = teacherThatExecuted;
    }
    public String getDateOfExam() {
        return dateOfExam;
    }

    public void setDateOfExam(String dateOfExam) {
        this.dateOfExam = dateOfExam;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getNumOfStudentDoing() {
        return numOfStudentDoing;
    }

    public void setNumOfStudentDoing(int numOfStudentDoing) {
        this.numOfStudentDoing = numOfStudentDoing;
    }
}
