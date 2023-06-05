package aidClasses.aidClassesForTeacher;

import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.scene.control.TextField;

public class ExamQuestion {
    private String questionID;
    private Question question;
    private TextField points;

    public ExamQuestion(String questionID, Question question, TextField points) {
        this.questionID = questionID;
        this.question = question;
        this.points = points;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public TextField getPoints() {
        return points;
    }

    public void setPoints(TextField points) {
        this.points = points;
    }
}
