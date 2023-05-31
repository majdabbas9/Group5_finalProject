package Student;

import aidClasses.GlobalDataSaved;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ExamStudentNotes {

    @FXML
    private Button backToGradesTable;

    @FXML
    private Button goToQuestions;

    @FXML
    private TextArea teacherNotes;

    private Grade grade;

    @FXML
    public void initialize() throws IOException {
        grade = GlobalDataSaved.currentGrade;
        //teacherNotes.setText(grade);
    }
    @FXML
    void backToGradesTable(ActionEvent event) throws IOException {
        App.setRoot("studentGrades");
    }

    @FXML
    void goToQuestions(ActionEvent event) throws IOException {
        App.setRoot("examCopy");
    }

}
