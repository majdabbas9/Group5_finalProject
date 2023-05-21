/**
 * Sample Skeleton for 'buildExam.fxml' Controller Class
 */

package Teacher;

import il.cshaifasweng.OCSFMediatorExample.client.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class BuildExam {

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="currentDate"
    private Text currentDate; // Value injected by FXMLLoader

    @FXML // fx:id="currentTime"
    private Text currentTime; // Value injected by FXMLLoader

    @FXML // fx:id="makeExamButton"
    private Button makeExamButton; // Value injected by FXMLLoader

    @FXML // fx:id="makeQuestionButton"
    private Button makeQuestionButton; // Value injected by FXMLLoader

    @FXML // fx:id="studentName"
    private Text studentName; // Value injected by FXMLLoader

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        App.setRoot("teacherHome");
    }

    @FXML
    void makeExam(ActionEvent event)throws IOException {
        App.setRoot("makeExam");

    }

    @FXML
    void makeQuestion(ActionEvent event) throws IOException {
        App.setRoot("makeQuestion");

    }

}
