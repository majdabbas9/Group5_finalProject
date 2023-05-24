package Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.client.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class principalExams {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackToMenuBtn;

    @FXML
    private TableColumn<?, ?> ExamId;

    @FXML
    private TableColumn<?, ?> ExamTeacher;

    @FXML
    private Button ShowExamBtn;

    @FXML
    private TableView<?> table;

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        App.setRoot("principalHome");
    }

    @FXML
    void ShowExam(ActionEvent event) {

    }

    @FXML
    void selectExam(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert BackToMenuBtn != null : "fx:id=\"BackToMenuBtn\" was not injected: check your FXML file 'principalExams.fxml'.";
        assert ExamId != null : "fx:id=\"ExamId\" was not injected: check your FXML file 'principalExams.fxml'.";
        assert ExamTeacher != null : "fx:id=\"ExamTeacher\" was not injected: check your FXML file 'principalExams.fxml'.";
        assert ShowExamBtn != null : "fx:id=\"ShowExamBtn\" was not injected: check your FXML file 'principalExams.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'principalExams.fxml'.";

    }

}
