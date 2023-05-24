package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject questionSubject;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherThatCreated;

    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER)
    private Set<Course_Question> questionCourses=new HashSet<>();

    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER)
    private Set<Exam_Question> questionExams=new HashSet<>();


    public Question() {

    }
    public Question(String teacherNotes, String studentNotes, String questionID, List<String> choices, String correctChoice) {
        this.teacherNotes = teacherNotes;
        this.studentNotes = studentNotes;
        this.questionID = questionID;
        this.choices=choices;
        this.correctChoice = correctChoice;
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


    public Set<Course_Question> getQuestionCourses() {
        return questionCourses;
    }

    public void setQuestionCourses(Set<Course_Question> questionCourses) {
        this.questionCourses = questionCourses;
    }

    public Set<Exam_Question> getQuestionExams() {
        return questionExams;
    }

    @Override
    public String toString() {
        return this.studentNotes;
    }
}
