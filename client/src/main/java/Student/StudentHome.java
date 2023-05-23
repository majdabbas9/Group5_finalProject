package Student;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
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
    @FXML
    public void initialize() {
        if (GlobalDataSaved.connectedUser.getUserName().equals("1")) {
            try {
                Student student = (Student) GlobalDataSaved.connectedUser;
                Subject subject = student.getStudentSubjects().get(0);
                Course course = student.getStudentCourses().get(0);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }

        }

    }
}

