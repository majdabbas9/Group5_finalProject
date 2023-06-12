package il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany;

import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "exams_questions")
public class Exam_Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    Exam exam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    Question question;

    int points;

    public Exam_Question(Exam exam, Question question,int points) {
        this.exam = exam;
        this.question = question;
        this.points=points;
    }

    public Exam_Question(Question question) {
        this.question = question;
    }

    public Exam_Question() {

    }

    public int getId() {
        return id;
    }
    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
