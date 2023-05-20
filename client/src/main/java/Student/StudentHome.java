package Student;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class StudentHome {

    @FXML
    private Text currentDate;

    @FXML
    private Text currentTime;

    @FXML
    private Button doExamBtn;

    @FXML
    private Button examsCopyBtn1;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button nyGradeBtn;

    @FXML
    private Text studentName;

    @FXML
    void doExam(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {
        try {
            Message msg = new Message("#logout", GlobalDataSaved.connectedUser); // creating a msg to the server demanding the students
            SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    void showExamsCopy(ActionEvent event) {

    }

    @FXML
    void showStudentGrade(ActionEvent event) {

    }

}

