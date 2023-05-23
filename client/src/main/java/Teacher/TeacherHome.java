package Teacher;

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
        App.setRoot("teacherExamsInProgress");
    }

    @FXML
    void ExamsNeedApprovment(ActionEvent event) {

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
        App.setRoot("prepareExam");
    }
    @FXML
    public void initialize()
    {
        if(GlobalDataSaved.connectedUser.getUserName().equals("3"))
        {
            try {
                Teacher teacher=(Teacher) GlobalDataSaved.connectedUser;
                Subject subject=teacher.getTeacherSubjects().get(0);
                Course course=teacher.getTeacherCourses().get(0);
            }
            catch (Exception exception)
            {
                System.out.println(exception.getMessage());
            }

        }
    }

}