package aidClasses;

import javafx.scene.control.TextField;

public class ExamQuestion {
    private String questionID;
    private String studentNotes;
    private TextField points;

    public ExamQuestion(String questionID, String studentNotes, TextField points) {
        this.questionID = questionID;
        this.studentNotes = studentNotes;
        this.points = points;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getStudentNotes() {
        return studentNotes;
    }

    public void setStudentNotes(String studentNotes) {
        this.studentNotes = studentNotes;
    }

    public TextField getPoints() {
        return points;
    }

    public void setPoints(TextField points) {
        this.points = points;
    }
}
