package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.Color;
import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.Principal.PrincipalHome;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TeacherHome {

    @FXML
    private Button addQuestionBtn;

    @FXML
    private Button buildExamBtn;

    @FXML
    private Text currentDate;

    @FXML
    private Text currentTime;

    @FXML
    private Button examsApproveBtn;

    @FXML
    private Button examsInProgressBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button preparExamsBtn;

    @FXML
    private Text teacherName;

    @FXML
    void ExamsInProgress(ActionEvent event) throws IOException {
        String now = LocalDateTime.now().toString();
        String year=now.substring(0,4);
        String month=now.substring(5,7);
        String day=now.substring(8,10);
        String hourMinute=now.substring(11,16);
        String date="";
        date+=year+"-"+month+"-"+day+" "+hourMinute;
        List<Object> dataToSever=new ArrayList<>();
        dataToSever.add(GlobalDataSaved.connectedUser.getId());
        dataToSever.add(date);
        Message msg1 = new Message("#getTeacherCompExamsNow",dataToSever); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server
    }

    @FXML
    void ExamsNeedApprovment(ActionEvent event) throws IOException {
        Message msg1 = new Message("#showAllExamsForTeahcerToApprove", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server
    }
    @FXML
    void buildExam(ActionEvent event)  throws IOException{
       App.setRoot("buildExam");

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
    void prepareExamsForExecution(ActionEvent event) throws IOException {
        try {
            Message msg = new Message("#showAllExamsForTeacher",GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
            SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void showAllExams(ActionEvent event) throws IOException {
        Message msg1 = new Message("#allExamsForTeacherToCopy", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server;
    }

    @FXML
    void showAllQuestions(ActionEvent event) throws IOException {
        GlobalDataSaved.forQuestion=2;
        GlobalDataSaved.teacherCourses.clear();
        GlobalDataSaved.teacherSubjects.clear();
        Message msg1 = new Message("#teacherCouses", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server;
        Message msg = new Message("#teacherSubjects", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
        Message msg3 = new Message("#allQuestions"); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg3); // sending the msg to the server
    }
    @FXML
    void allExamsCreated(ActionEvent event) throws IOException {
        Message msg3 = new Message("#getAllExamsCreated",GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg3); // sending the msg to the server
    }
    @FXML
    public void initialize()
    {
        PrincipalHome.dataOnHome(teacherName,currentDate,currentTime);
    }


}