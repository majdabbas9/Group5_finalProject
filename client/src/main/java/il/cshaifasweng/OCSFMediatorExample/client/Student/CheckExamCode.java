package il.cshaifasweng.OCSFMediatorExample.client.Student;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.font.GlyphJustificationInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckExamCode {

    @FXML
    private Button checkValidationBtn;

    @FXML
    private TextField studentCode;

    @FXML
    private Button backToHome;

    private String examCode;
    private String userID;
    @FXML
    public void initialize() {
        GlobalDataSaved.changeTextCounter++;
        if (GlobalDataSaved.changeTextCounter % 2 == 0) {
            studentCode.setPromptText("Enter your ID");
            checkValidationBtn.setText("Start Exam");
        }
        else {
            studentCode.setPromptText("Enter the code");
            checkValidationBtn.setText("Check Validation");
        }
    }
    @FXML
    void checkValidation(ActionEvent event) throws IOException {
        Message msg;
        if (GlobalDataSaved.changeTextCounter % 2 == 0){
            userID = studentCode.getText();
            List<Object> objects = new ArrayList<>();
            objects.add(0,userID);
            objects.add(1,GlobalDataSaved.connectedUser.getUserID());
            objects.add(2,examCode);
            msg = new Message("#check id", objects);
        }
        else {
            examCode = studentCode.getText();
            msg = new Message("#check code validation", examCode);
        }
        SimpleClient.getClient().sendToServer(msg);
    }

    @FXML
    void backToHome(ActionEvent event) throws IOException {
        GlobalDataSaved.changeTextCounter = 0;
        App.setRoot("studentHome");
    }
}