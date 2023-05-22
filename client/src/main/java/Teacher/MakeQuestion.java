/**
 * Sample Skeleton for 'makeQuestion.fxml' Controller Class
 */

package Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MakeQuestion {

    @FXML // fx:id="buttonBack"
    private Button buttonBack; // Value injected by FXMLLoader

    @FXML // fx:id="buttonSubmit"
    private Button buttonSubmit; // Value injected by FXMLLoader

    @FXML // fx:id="choice1"
    private TextField choice1; // Value injected by FXMLLoader

    @FXML // fx:id="choice2"
    private TextField choice2; // Value injected by FXMLLoader

    @FXML // fx:id="choice3"
    private TextField choice3; // Value injected by FXMLLoader

    @FXML // fx:id="choice4"
    private TextField choice4; // Value injected by FXMLLoader

    @FXML // fx:id="courseList"
    private ListView<Course> courseList; // Value injected by FXMLLoader
    @FXML
    private ListView<Course> selectedCourseList;

    @FXML
    private TextField teacherNotes;

    @FXML // fx:id="radioChoice1"
    private RadioButton radioChoice1; // Value injected by FXMLLoader

    @FXML // fx:id="radioChoice2"
    private RadioButton radioChoice2; // Value injected by FXMLLoader

    @FXML // fx:id="radioChoice3"
    private RadioButton radioChoice3; // Value injected by FXMLLoader

    @FXML // fx:id="radioChoice4"
    private RadioButton radioChoice4; // Value injected by FXMLLoader

    @FXML // fx:id="radioGroup"
    private ToggleGroup radioGroup; // Value injected by FXMLLoader

    @FXML // fx:id="subjectList"
    private ComboBox<Subject> subjectList; // Value injected by FXMLLoader
    @FXML // fx:id="theQuestion"
    private TextField theQuestion; // Value injected by FXMLLoader

    @FXML // fx:id="warningTxt"
    private Text warningTxt; // Value injected by FXMLLoader
    private Teacher theTeacher;
    private List<Course> selectedCourses;

    @FXML
    void backToBuildExam(ActionEvent event) throws IOException {
        App.setRoot("buildExam");
    }

    @FXML
    void subjectSelected(ActionEvent event) {
        if(selectedCourses!=null) {
            selectedCourses.clear();
        }
        else
        {
            selectedCourses=new ArrayList<>();
        }
        selectedCourses.clear();
        courseList.getItems().clear();
        selectedCourseList.getItems().clear();
        Subject selectedSubject=subjectList.getSelectionModel().getSelectedItem();
        for(Course course: theTeacher.getTeacherCourses())
        {
            if(course.getCourseSubject().getSubjectName().equals(selectedSubject.getSubjectName()))
            {
                courseList.getItems().add(course);
            }
        }
    }

    @FXML
    void submitQuestion(ActionEvent event) throws IOException {
        warningTxt.setText("");
        if(theQuestion.getText().equals(""))
        {
            warningTxt.setText("no question text");
            return;
        }
        if(choice1.getText().equals(""))
        {
            warningTxt.setText("choice 1 missing ");
            return;
        }
        if(choice2.getText().equals(""))
        {
            warningTxt.setText("choice 1 missing ");
            return;
        }
        if(choice3.getText().equals(""))
        {
            warningTxt.setText("choice 1 missing ");
            return;
        }
        if(choice4.getText().equals(""))
        {
            warningTxt.setText("choice 1 missing ");
            return;
        }
        if (radioGroup.getSelectedToggle()==null)
        {
            warningTxt.setText("no choice selected ");
            return;
        }
        if(subjectList.getSelectionModel().getSelectedItem()==null)
        {
            warningTxt.setText("no subject selected");
            return;
        }
        if (selectedCourses.size()==0)
        {
            warningTxt.setText("no courses selected");
            return;
        }
        List<String> choices=new ArrayList<>();
        String correctChoice="";
        choices.add(choice1.getText());choices.add(choice2.getText());choices.add(choice3.getText());
        choices.add(choice4.getText());
        if(radioChoice1.isSelected())
        {
            correctChoice=choice1.getText();
        }
        if(radioChoice2.isSelected())
        {
            correctChoice=choice2.getText();
        }
        if(radioChoice3.isSelected())
        {
            correctChoice=choice3.getText();
        }
        if(radioChoice4.isSelected())
        {
            correctChoice=choice4.getText();
        }
        String questionID="";
        int subjectId=subjectList.getSelectionModel().getSelectedItem().getId()-1;
        int questionNum=subjectList.getSelectionModel().getSelectedItem().getSubjectQuestions().size();
        if(subjectId<10)
        {
            questionID="0"+subjectId;
        }
        else
        {
            questionID= String.valueOf(subjectId);
        }
        if(questionNum<10)
        {
            questionID+="00"+questionNum;
        }
        if(questionNum <100 && questionNum>9)
        {
            questionID+="0"+questionNum;
        }
        if(questionNum>99)
        {
            questionID+=questionNum;
        }
        Question question=new Question(teacherNotes.getText(),theQuestion.getText(),questionID,choices,correctChoice,subjectList.getSelectionModel().getSelectedItem(),theTeacher);
        question.setQuestionCourses(selectedCourses);
        Message msg = new Message("#addQuestion", question); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
    }
    @FXML
    void selectCourse() {
        if(selectedCourses==null) {
            selectedCourses=new ArrayList<>();
        }
        if(!selectedCourses.contains(courseList.getSelectionModel().getSelectedItem()))
        {
            selectedCourses.add(courseList.getSelectionModel().getSelectedItem());
            selectedCourseList.getItems().add(courseList.getSelectionModel().getSelectedItem());
        }

    }
    @FXML
    public void initialize()
    {
        if(subjectList.getItems()!=null) {
            theTeacher = (Teacher) GlobalDataSaved.connectedUser;
            subjectList.getItems().setAll(theTeacher.getTeacherSubjects());
        }
    }



}
