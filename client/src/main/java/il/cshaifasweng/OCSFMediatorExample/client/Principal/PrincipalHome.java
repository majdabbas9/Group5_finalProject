package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PrincipalHome {

    @FXML
    private Button addUsersBtn;

    @FXML
    private Text TheDate;

    @FXML
    private Text TheTime;

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
    private Text PrincipalName;


    @FXML
    void initialize() throws IOException {
        PrincipalName.setText(GlobalDataSaved.connectedUser.getFirstName() +
                " " + GlobalDataSaved.connectedUser.getLastName());
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = datePicker.getValue().format(formatter);
        TheDate.setText(formattedDate);

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
            TheTime.setText(LocalDateTime.now().format(format));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    void ExamsNeedTimeApprovment(ActionEvent event) throws IOException {
        try {
            Message msg = new Message("ExtraTime");
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addUsers(ActionEvent event) throws IOException {
        Message msg = new Message("#AllSubjectsToPrincipal");
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
        Message msg = new Message("AllExamsToPrincipal");
        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showGrades(ActionEvent event) throws IOException {
        // to reset the exam to show
        GlobalDataSaved.PrincipalExamToShow = null;
        Message msg = new Message("AllGradesToPrincipal");
        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showQuestions(ActionEvent event) {
        //to know that we are in questions section
        GlobalDataSaved.PrincipalQuestionToShow = null;
        Message msg = new Message("AllQuestionsToPrincipal");
        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showStatisticalData(ActionEvent event) {
        try {
            Message message = new Message("StatisticalDataForPrincipal");
            SimpleClient.getClient().sendToServer(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}