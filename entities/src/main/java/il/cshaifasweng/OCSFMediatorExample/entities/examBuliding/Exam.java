package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "exams")
public class Exam implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exam")
    private Set<ComputerizedExamToExecute> compExamsToExecute=new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherThatCreated;
    private int time;
    private String exam_ID;
    private String teacherNotes;
    private String notes;
    @ElementCollection
    private List<Integer> points;
    @OneToMany(mappedBy = "exam",fetch = FetchType.EAGER)
    private Set<Exam_Question> examQuestions=new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject examSubject;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course examCourse;

    public Exam(int time, String exam_ID, String teacherNotes, String notes, List<Integer> points) {
        this.time = time;
        this.exam_ID = exam_ID;
        this.teacherNotes = teacherNotes;
        this.notes = notes;
        this.points = points;
    }

    public Exam() {

    }

    public int getId() {
        return id;
    }

    public Set<ComputerizedExamToExecute> getCompExamsToExecute() {
        return compExamsToExecute;
    }

    public void setCompExamsToExecute(Set<ComputerizedExamToExecute> compExamsToExecute) {
        this.compExamsToExecute = compExamsToExecute;
    }

    public Teacher getTeacherThatCreated() {
        return teacherThatCreated;
    }

    public void setTeacherThatCreated(Teacher teacherThatCreated) {
        this.teacherThatCreated = teacherThatCreated;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getExam_ID() {
        return exam_ID;
    }

    public void setExam_ID(String ID) {
        this.exam_ID = ID;
    }

    public String getTeacherNotes() {
        return teacherNotes;
    }

    public void setTeacherNotes(String teacherNotes) {
        this.teacherNotes = teacherNotes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public void setPoints(List<Integer> points) {
        this.points = points;
    }

    public Set<Exam_Question> getExamQuestions() {
        return examQuestions;
    }

    public void setExamQuestions(Set<Exam_Question> examQuestions) {
        this.examQuestions = examQuestions;
    }

    public Subject getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(Subject examSubject) {
        this.examSubject = examSubject;
    }

    public Course getExamCourse() {
        return examCourse;
    }

    public void setExamCourse(Course examCourse) {
        this.examCourse = examCourse;
    }

}
