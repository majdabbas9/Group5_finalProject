package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "exams")
public class Exam implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exam")
    private List<ComputerizedExamToExecute> compExamsToExecute;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherThatCreated;
    private int time;
    private String exam_ID;
    private String teacherNotes;
    private String notes;
    @ElementCollection
    private List<Integer> points;
    @ManyToMany(mappedBy = "questionExams",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Question.class
    )
    private List<Question> examQuestions;

    public Exam(Teacher teacherThatCreated, int time, String exam_ID, String teacherNotes, String notes, List<Integer> points) {
        this.compExamsToExecute =new ArrayList<>();
        setTeacherThatCreated(teacherThatCreated);
        this.time = time;
        this.exam_ID = exam_ID;
        this.teacherNotes = teacherNotes;
        this.notes = notes;
        this.points = points;
        this.examQuestions=new ArrayList<>();
    }

    public Exam() {

    }

    public int getId() {
        return id;
    }

    public List<ComputerizedExamToExecute> getCompExamsToExecute() {
        return compExamsToExecute;
    }

    public void setCompExamsToExecute(List<ComputerizedExamToExecute> compExamsToExecute) {
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

    public List<Question> getExamQuestions() {
        return examQuestions;
    }

    public void setExamQuestions(List<Question> examQuestions) {
        this.examQuestions = examQuestions;
    }
}
