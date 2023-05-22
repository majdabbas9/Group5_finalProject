package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String teacherNotes;
    private String studentNotes; // this the content of the question
    private String questionID;
    @ElementCollection
    private List<String> choices;
    private String correctChoice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject questionSubject;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherThatCreated;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Course.class
    )
    @JoinTable(
            name="questions_courses",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> questionCourses;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Exam.class
    )
    @JoinTable(
            name="questions_exams",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id")
    )
    private List<Exam> questionExams;


    public Question() {

    }
    public Question(String teacherNotes, String studentNotes, String questionID, List<String> choices, String correctChoice, Subject questionSubject, Teacher teacherThatCreated) {
        this.teacherNotes = teacherNotes;
        this.studentNotes = studentNotes;
        this.questionID = questionID;
        this.choices=choices;
        this.correctChoice = correctChoice;
        this.setQuestionSubject(questionSubject);
        this.setTeacherThatCreated(teacherThatCreated);
        this.questionCourses=new ArrayList<>();
        this.questionExams=new ArrayList<>();
    }
    public int getId() {
        return id;
    }
    public String getTeacherNotes() {
        return teacherNotes;
    }

    public void setTeacherNotes(String teacherNotes) {
        this.teacherNotes = teacherNotes;
    }

    public String getStudentNotes() {
        return studentNotes;
    }

    public void setStudentNotes(String studentNotes) {
        this.studentNotes = studentNotes;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String ID) {
        this.questionID = ID;
    }


    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
    public String getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }

    public List<Course> getQuestionCourses() {
        return questionCourses;
    }

    public void setQuestionCourses(List<Course> questionCourses) {
        this.questionCourses = questionCourses;
    }
    public Subject getQuestionSubject() {
        return questionSubject;
    }

    public void setQuestionSubject(Subject questionSubject) {
        this.questionSubject = questionSubject;
    }

    public Teacher getTeacherThatCreated() {
        return teacherThatCreated;
    }

    public void setTeacherThatCreated(Teacher teacherThatCreated) {
        this.teacherThatCreated = teacherThatCreated;
    }

    public List<Exam> getQuestionExams() {
        return questionExams;
    }

    public void setQuestionExams(List<Exam> questionExams) {
        this.questionExams = questionExams;
    }

    @Override
    public String toString() {
        return this.studentNotes;
    }
}
