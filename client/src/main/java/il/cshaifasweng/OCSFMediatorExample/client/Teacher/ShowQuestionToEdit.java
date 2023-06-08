package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import aidClasses.aidClassesForTeacher.QuestionsExamsID;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowQuestionToEdit {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonSubmit;

    @FXML
    private TextField choice1;

    @FXML
    private TextField choice2;

    @FXML
    private TextField choice3;

    @FXML
    private TextField choice4;

    @FXML
    private ListView<Course> courseList;

    @FXML
    private RadioButton radioChoice1;

    @FXML
    private RadioButton radioChoice2;

    @FXML
    private RadioButton radioChoice3;

    @FXML
    private RadioButton radioChoice4;

    @FXML
    private ToggleGroup radioGroup;

    @FXML
    private ListView<Course> selectedCourseList;

    @FXML
    private TextField studentNotes;

    @FXML
    private TextField teacherNotes;

    @FXML
    private TextField theQuestion;

    @FXML
    private Text warningTxt;
    private List<Course> selectedCourses;

    @FXML
    void backToBuildExam(ActionEvent event) throws IOException {
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
    void selectCourse(MouseEvent event) {
        if(selectedCourses==null) {
            selectedCourses=new ArrayList<>();
        }
        if(courseList.getSelectionModel().getSelectedItem()==null)
        {
            return;
        }
        if(!selectedCourses.contains(courseList.getSelectionModel().getSelectedItem()))
        {
            selectedCourses.add(courseList.getSelectionModel().getSelectedItem());
            selectedCourseList.getItems().add(courseList.getSelectionModel().getSelectedItem());
        }
    }
    @FXML
    void removeCourse(MouseEvent event) {
        selectedCourses.remove(selectedCourseList.getSelectionModel().getSelectedItem());
        selectedCourseList.getItems().remove(selectedCourseList.getSelectionModel().getSelectedItem());
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
        if (selectedCourses==null || selectedCourses.size()==0)
        {
            warningTxt.setText("no courses selected");
            return;
        }
        List<String> choices=new ArrayList<>();
        String correctChoice="";
        if(choice1.getText().equals(choice2.getText()) || choice1.getText().equals(choice3.getText())
                || choice1.getText().equals(choice4.getText()) || choice2.getText().equals(choice3.getText())
                || choice2.getText().equals(choice4.getText()) || choice3.getText().equals(choice4.getText()))
        {
            warningTxt.setText("answers should be different");
            return;
        }
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
        if(!QuestionsExamsID.isInit)
        {
            QuestionsExamsID.init();
        }
        Question question=new Question(teacherNotes.getText(),theQuestion.getText(),correctChoice,choices);
        question.setQuestionID(QuestionsExamsID.questionID(GlobalDataSaved.selectedSubjectToCopy.getSubjectName(),
                GlobalDataSaved.selectedSubjectToCopy.getId()));
        question.setStudentNotesToShow(studentNotes.getText());
        //question.setQuestionCourses(selectedCourses);
        List<Object> dataToServer=new ArrayList<>();
        dataToServer.add(question);
        List<Integer> coursesIds=new ArrayList<>();
        for(Course course:selectedCourses)
        {
            coursesIds.add(course.getId());
        }
        dataToServer.add(coursesIds);
        dataToServer.add(GlobalDataSaved.selectedSubjectToCopy.getId());
        dataToServer.add(GlobalDataSaved.connectedUser.getId());
        Message msg = new Message("#addQuestionToCopy", dataToServer); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
    }
    @FXML
    void initialize()
    {
        theQuestion.setText(GlobalDataSaved.selectedQuestionsToCopy.getStudentNotes());
        choice1.setText(GlobalDataSaved.selectedQuestionsToCopy.getChoice1());
        choice2.setText(GlobalDataSaved.selectedQuestionsToCopy.getChoice2());
        choice3.setText(GlobalDataSaved.selectedQuestionsToCopy.getChoice3());
        choice4.setText(GlobalDataSaved.selectedQuestionsToCopy.getChoice4());
        if(GlobalDataSaved.selectedQuestionsToCopy.getCorrectChoice().equals(choice1))
        {
            radioChoice1.setSelected(true);
        }
        if(GlobalDataSaved.selectedQuestionsToCopy.getCorrectChoice().equals(choice2))
        {
            radioChoice2.setSelected(true);
        }
        if(GlobalDataSaved.selectedQuestionsToCopy.getCorrectChoice().equals(choice3))
        {
            radioChoice3.setSelected(true);
        }
        if(GlobalDataSaved.selectedQuestionsToCopy.getCorrectChoice().equals(choice4))
        {
            radioChoice4.setSelected(true);
        }
        for(Course course:GlobalDataSaved.teacherCourses)
        {
            if(course.getCourseSubject().getSubjectName().equals(GlobalDataSaved.selectedSubjectToCopy.getSubjectName()))
            {
                courseList.getItems().add(course);
            }
        }
    }

}
