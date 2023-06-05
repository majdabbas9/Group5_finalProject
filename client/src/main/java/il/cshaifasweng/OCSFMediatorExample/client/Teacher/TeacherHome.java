package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.Color;
import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private Text studentName;

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
        Message msg1 = new Message("#showAllCompExamsForTeahcerToApprove", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server
    }

    @FXML
    void addQuestions(ActionEvent event) {

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
    public void initialize()
    {
    }

}