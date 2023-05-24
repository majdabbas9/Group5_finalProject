/**
 * Sample Skeleton for 'makeExam.fxml' Controller Class
 */

package Teacher;

import aidClasses.aidClassesForTeacher.ExamQuestion;
import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MakeExam {

    @FXML // fx:id="buttonBack"
    private Button buttonBack; // Value injected by FXMLLoader

    @FXML // fx:id="buttonSubmit"
    private Button buttonSubmit; // Value injected by FXMLLoader

    @FXML // fx:id="courseList"
    private ListView<Course> courseList; // Value injected by FXMLLoader

    @FXML // fx:id="courseQuestions"
    private ListView<Question> courseQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="subjectList"
    private ComboBox<Subject> subjectList; // Value injected by FXMLLoader

    @FXML // fx:id="time"
    private TextField timeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="warningTxt"
    private Text warningTxt; // Value injected by FXMLLoader
    @FXML // fx:id="examQuestionID"
    private TableColumn<ExamQuestion, String> examQuestionID; // Value injected by FXMLLoader

    @FXML // fx:id="examQuestionPoints"
    private TableColumn<ExamQuestion, TextField> examQuestionPoints; // Value injected by FXMLLoader

    @FXML // fx:id="examQuestionTable"
    private TableView<ExamQuestion> examQuestionTable; // Value injected by FXMLLoader

    @FXML // fx:id="examQuestionsTheQuestion"
    private TableColumn<ExamQuestion, String> examQuestionsTheQuestion; // Value injected by FXMLLoader
    private  ObservableList<ExamQuestion> observableList;

    private Teacher theTeacher;
    private List<Question> selectedQuestions;
    private List<String> selectedQuestionsID;

    @FXML
    void backToBuildExam(ActionEvent event) throws IOException {
        App.setRoot("buildExam");
    }

    @FXML
    void selectCourse(MouseEvent event) {
       selectedQuestions.clear();
        observableList.clear();
        if(courseQuestions!=null)
        {
            courseQuestions.getItems().clear();
        }
            List<Question> questions=new ArrayList<>();
            Course course=courseList.getSelectionModel().getSelectedItem();
            for(Course_Question cq:course.getCourseQuestions())
            {
                questions.add(cq.getQuestion());
            }
            if(course.getCourseQuestions().size()!=0)
            {
                courseQuestions.getItems().setAll(questions);
            }
        }

    @FXML
    void subjectSelected(ActionEvent event) {
        courseList.getItems().clear();
        selectedQuestions.clear();
        observableList.clear();
        Subject selectedSubject=subjectList.getSelectionModel().getSelectedItem();
        for(Course course: GlobalDataSaved.teacherCourses)
        {
            if(course.getCourseSubject().getSubjectName().equals(selectedSubject.getSubjectName()))
            {
                courseList.getItems().add(course);
            }
        }

    }
    @FXML
    void questionSelected(MouseEvent event) {
        Question selectedQuestion=courseQuestions.getSelectionModel().getSelectedItem();
        if(!selectedQuestions.contains(selectedQuestion))
        {
            selectedQuestions.add(selectedQuestion);
            observableList.add(new ExamQuestion(selectedQuestion.getQuestionID(),selectedQuestion.getStudentNotes(),new TextField("0")));
            examQuestionTable.setItems(observableList);
        }
    }
    @FXML
    void submitExam(ActionEvent event) {
        warningTxt.setText("");
        int points=0,sum=0,time=0;
        String pointsString;
        List<Integer> examPoints=new ArrayList<>();
        if(timeLabel.getText().equals(""))
        {
            warningTxt.setText("the exam have no time");
            return;
        }
        try {
            time=Integer.valueOf(timeLabel.getText());
        }
        catch (Exception ex)
        {
            warningTxt.setText("the exam have illegal time!");
            return;
        }
        if(time<=0)
        {
            warningTxt.setText("the exam have none positive number time");
            return;
        }
        for(ExamQuestion examQuestion:examQuestionTable.getItems())
        {
            pointsString=examQuestion.getPoints().getText();
            if(pointsString.equals(""))
            {
                warningTxt.setText("questionID =  "+examQuestion.getQuestionID()+" "+"have no points");
                return;
            }
            try {
                points=Integer.valueOf(pointsString);
            }
            catch (Exception ex)
            {
                warningTxt.setText("questionID =  "+examQuestion.getQuestionID()+" "+"have illegal number of points!");
                return;
            }
            if(points<=0)
            {
                warningTxt.setText("questionID =  "+examQuestion.getQuestionID()+" "+"have none positive number of points!");
                return;
            }
            if(points>100)
            {
                warningTxt.setText("questionID =  "+examQuestion.getQuestionID()+" "+"have number of points above 100!");
                return;
            }
            sum+=points;
            examPoints.add(points);
        }
        if(sum!=100)
        {
            warningTxt.setText("the exam have number of points above 100!");
            return;
        }
        Exam exam=new Exam(time,"","","",examPoints);
        List<Object>objects=new ArrayList<>();
        objects.add(exam);
        objects.add(GlobalDataSaved.connectedUser);
        objects.add(courseList.getSelectionModel().getSelectedItem());
        objects.add(subjectList.getSelectionModel().getSelectedItem());
        objects.add(selectedQuestions);
        Message msg = new Message("#addExam", objects); // creating a msg to the server demanding the students
        try {
            SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
        }
        catch (IOException exception)
        {
            System.out.println(exception.getMessage());
        }

    }

    @FXML
    public void initialize()
    {
       subjectList.getItems().setAll(GlobalDataSaved.teacherSubjects);
        observableList = FXCollections.observableArrayList();
        selectedQuestions=new ArrayList<>();
        selectedQuestionsID=new ArrayList<>();
        examQuestionID.setCellValueFactory(new PropertyValueFactory<ExamQuestion,String>("questionID"));
        examQuestionsTheQuestion.setCellValueFactory(new PropertyValueFactory<ExamQuestion,String>("studentNotes"));
        examQuestionPoints.setCellValueFactory(new PropertyValueFactory<ExamQuestion,TextField>("points"));
    }

}
