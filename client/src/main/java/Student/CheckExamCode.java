package Student;

import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CheckExamCode {

    @FXML
    private Button checkValidationBtn;

    @FXML
    private TextField studentCode;

    @FXML
    private Button backToHome;
    @FXML
    void checkValidation(ActionEvent event) throws IOException {
        Message msg = new Message("#check code validation", studentCode.getText());
        SimpleClient.getClient().sendToServer(msg);
    }

    @FXML
    void backToHome(ActionEvent event) throws IOException {
        Message msg = new Message("#back to student home");
        SimpleClient.getClient().sendToServer(msg);
    }
}