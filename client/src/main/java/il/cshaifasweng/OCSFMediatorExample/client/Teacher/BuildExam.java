/**
 * Sample Skeleton for 'buildExam.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
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
        GlobalDataSaved.forQuestion=false;
        Message msg1 = new Message("#teacherCouses", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server;
        Message msg = new Message("#teacherSubjects", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
    }

    @FXML
    void makeQuestion(ActionEvent event) throws IOException {
        GlobalDataSaved.forQuestion=true;
        Message msg1 = new Message("#teacherCouses", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server;
        Message msg = new Message("#teacherSubjects", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
    }

}
