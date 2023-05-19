package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String teacherNotes;
    private String studentNotes;
    private String ID;
    @ElementCollection
    private List<String> choices;
    private String correctChoice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject questionSubject;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Question.class
    )
    @JoinTable(
            name="questions_courses",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> questionCourses;

    public Question() {

    }
    public Question(String teacherNotes, String studentNotes, String ID,List<String> choices,String correctChoice,Subject questionSubject) {
        this.teacherNotes = teacherNotes;
        this.studentNotes = studentNotes;
        this.ID = ID;
        this.choices=choices;
        this.correctChoice = correctChoice;
        this.setQuestionSubject(questionSubject);
        this.questionCourses=new ArrayList<>();
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
}
