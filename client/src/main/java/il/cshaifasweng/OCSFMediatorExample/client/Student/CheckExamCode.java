package il.cshaifasweng.OCSFMediatorExample.client.Student;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    private static String examCode;
    private static String userID;
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
        if (studentCode.equals("") || studentCode.getText() == null){
            return;
        }
        if (GlobalDataSaved.changeTextCounter % 2 == 0){
            userID = studentCode.getText();
            List<Object> objects = new ArrayList<>();
            objects.add(0,userID);
            objects.add(1,GlobalDataSaved.connectedUser.getUserID());
            objects.add(2,examCode);
            System.out.println("the user id is: "+ examCode);
            msg = new Message("#check id", objects);
        }
        else {
            examCode = studentCode.getText();
            List<Object> dataToServer=new ArrayList<>();
            dataToServer.add(examCode);dataToServer.add(GlobalDataSaved.connectedUser.getId());
            msg = new Message("#check code validation", dataToServer);
        }
        SimpleClient.getClient().sendToServer(msg);
    }

    @FXML
    void backToHome(ActionEvent event) throws IOException {
        GlobalDataSaved.changeTextCounter = 0;
        App.setRoot("studentHome");
    }
}