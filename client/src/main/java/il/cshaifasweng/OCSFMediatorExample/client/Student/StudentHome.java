package il.cshaifasweng.OCSFMediatorExample.client.Student;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.Principal.PrincipalHome;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class StudentHome {

    @FXML
    private Text currentDate;

    @FXML
    private Text currentTime;

    @FXML
    private Button doExamBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button myGradeBtn;

    @FXML
    private Text studentName;

    @FXML
    void doExam(ActionEvent event) throws IOException {
        App.setRoot("checkExamCode");
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
    void showStudentGrade(ActionEvent event) throws IOException {
        Message msg = new Message("#show student grades", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg);
    }
    @FXML
    public void initialize() {
        PrincipalHome.dataOnHome(studentName,currentDate,currentTime);
    }
}

