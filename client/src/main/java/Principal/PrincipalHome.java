package Principal;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrincipalHome {

    @FXML
    private Button addUsersBtn;

    @FXML
    private Text currentDate;

    @FXML
    private Text currentTime;

    @FXML
    private Button examsBtn;

    @FXML
    private Button examsTimeApproveBtn;

    @FXML
    private Button gradesBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button questionsBtn;

    @FXML
    private Button statisticalDataBtn;

    @FXML
    private Text studentName;


    @FXML
    void initialize() throws IOException {
        assert addUsersBtn != null : "fx:id=\"addUsersBtn\" was not injected: check your FXML file 'principalHome.fxml'.";
        assert currentDate != null : "fx:id=\"currentDate\" was not injected: check your FXML file 'principalHome.fxml'.";
        assert currentTime != null : "fx:id=\"currentTime\" was not injected: check your FXML file 'principalHome.fxml'.";
        assert examsBtn != null : "fx:id=\"examsBtn\" was not injected: check your FXML file 'principalHome.fxml'.";
        assert examsTimeApproveBtn != null : "fx:id=\"examsTimeApproveBtn\" was not injected: check your FXML file 'principalHome.fxml'.";
        assert gradesBtn != null : "fx:id=\"gradesBtn\" was not injected: check your FXML file 'principalHome.fxml'.";
        assert logOutBtn != null : "fx:id=\"logOutBtn\" was not injected: check your FXML file 'principalHome.fxml'.";
        assert questionsBtn != null : "fx:id=\"questionsBtn\" was not injected: check your FXML file 'principalHome.fxml'.";
        assert statisticalDataBtn != null : "fx:id=\"statisticalDataBtn\" was not injected: check your FXML file 'principalHome.fxml'.";
        assert studentName != null : "fx:id=\"studentName\" was not injected: check your FXML file 'principalHome.fxml'.";

    }

    @FXML
    void ExamsNeedTimeApprovment(ActionEvent event) throws IOException {
        App.setRoot("principalExtraTimeApprovment");
    }

    @FXML
    void addUsers(ActionEvent event) throws IOException {
        List<Subject> list = new ArrayList<>();
        Message msg = new Message("#AllSubjectsToPrincipal", list);
        try {
            SimpleClient.getClient().sendToServer(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        try {
            Message msg = new Message("#logout", GlobalDataSaved.connectedUser);
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void showExams(ActionEvent event) throws IOException {
        //TODO:: getallExams() then update the exams list
        App.setRoot("principalExams");
    }

    @FXML
    void showGrades(ActionEvent event) throws IOException {
        App.setRoot("principalGrades");
    }

    @FXML
    void showQuestions(ActionEvent event) throws IOException {
        App.setRoot("principalQuestions");
    }

    @FXML
    void showStatisticalData(ActionEvent event) throws IOException {
        App.setRoot("principalStatisticalData");
    }

}