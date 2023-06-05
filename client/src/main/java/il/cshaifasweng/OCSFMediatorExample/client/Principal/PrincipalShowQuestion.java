package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PrincipalShowQuestion {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackBtn;

    @FXML
    private Text Courses;

    @FXML
    private RadioButton FirstChoice;

    @FXML
    private Text Firsttxt;

    @FXML
    private RadioButton FourthChoice;

    @FXML
    private Text Fourthtxt;

    @FXML
    private Text QuestionId;

    @FXML
    private Text QuestionTeacher;

    @FXML
    private RadioButton SecondChoice;

    @FXML
    private Text Secondtxt;

    @FXML
    private Text Subject;

    @FXML
    private Text TheQuestion;

    @FXML
    private RadioButton ThirdChoice;

    @FXML
    private Text Thirdtxt;

    Question questionTosShow = GlobalDataSaved.PrincipalQuestionToShow;
    @FXML
    void BackToTable(ActionEvent event) throws IOException{
        if (GlobalDataSaved.PrincipalExamToShow ==null){
            Message msg = new Message("AllQuestionsToPrincipal");
            try {
                SimpleClient.getClient().sendToServer(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            App.setRoot("principalShowExam");
        }
    }


    @FXML

    void initialize() {
        TheQuestion.setText(questionTosShow.getStudentNotes());
        if(!questionTosShow.getTeacherNotes().equals("")) {
            TheQuestion.setText(questionTosShow.getStudentNotes() +
                    "\nNotes: " + questionTosShow.getTeacherNotes());
        }
        Firsttxt.setText(questionTosShow.getChoices().get(0));
        Secondtxt.setText(questionTosShow.getChoices().get(1));
        Thirdtxt.setText(questionTosShow.getChoices().get(2));
        Fourthtxt.setText(questionTosShow.getChoices().get(3));

        if (Firsttxt.getText().equals(
                questionTosShow.getCorrectChoice())) {
            Firsttxt.setFill(Color.GREEN);
            FirstChoice.setSelected(true);
        } else {
            Firsttxt.setFill(Color.RED);
        }

        if (Secondtxt.getText().equals(
                questionTosShow.getCorrectChoice())) {
            Secondtxt.setFill(Color.GREEN);
            SecondChoice.setSelected(true);
        } else {
            Secondtxt.setFill(Color.RED);
        }

        if (Thirdtxt.getText().equals(
                questionTosShow.getCorrectChoice())) {
            Thirdtxt.setFill(Color.GREEN);
            ThirdChoice.setSelected(true);
        } else {
            Thirdtxt.setFill(Color.RED);
        }

        if (Fourthtxt.getText().equals(
                questionTosShow.getCorrectChoice())) {
            Fourthtxt.setFill(Color.GREEN);
            FourthChoice.setSelected(true);
        } else {
            Fourthtxt.setFill(Color.RED);
        }

        FirstChoice.setDisable(true);
        SecondChoice.setDisable(true);
        ThirdChoice.setDisable(true);
        FourthChoice.setDisable(true);

        QuestionId.setText("Question ID: " + questionTosShow.getQuestionID());
        QuestionTeacher.setText("By Teacher: " + questionTosShow.getTeacherThatCreated().getFirstName()
                + " " + questionTosShow.getTeacherThatCreated().getLastName()
                + " (ID: " + questionTosShow.getTeacherThatCreated().getUserID() + ")");
        Subject.setText("Subject: " + questionTosShow.getQuestionSubject().getSubjectName());
        String courses = "Courses:";
        for (Course_Question course : questionTosShow.getQuestionCourses())
        {
            courses += " - " +course.getCourse().getCourseName() + " -";
        }
        Courses.setText(courses);
    }

}
