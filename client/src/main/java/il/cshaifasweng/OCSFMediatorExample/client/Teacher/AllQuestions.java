/**
 * Sample Skeleton for 'allQuestions.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class AllQuestions {

    @FXML // fx:id="BackToMenuBtn"
    private Button BackToMenuBtn; // Value injected by FXMLLoader

    @FXML
    private TableColumn<Question, String> QuestionId;

    @FXML
    private TableColumn<Question, String> QuestionTeacherName;
    @FXML
    private TableColumn<Question, String> TheQuestion;
    @FXML
    private TableColumn<Question, String> QuestionTeacherID;
    @FXML
    private TableColumn<Question, String> SubjectName;

    @FXML
    private TableView<Question> table;

    @FXML // fx:id="ShowQuestionBtn"
    private Button ShowQuestionBtn; // Value injected by FXMLLoader

    @FXML // fx:id="warning"
    private Text warning; // Value injected by FXMLLoader

    @FXML
    void BackToMenu(ActionEvent event) {

    }

    @FXML
    void ShowQuestion(ActionEvent event) {

    }

    @FXML
    void selectQuestion(MouseEvent event) {

    }
    @FXML
   void initialize()
    {
        TheQuestion.setCellValueFactory(new PropertyValueFactory<Question, String>("studentNotes"));
        QuestionId.setCellValueFactory(new PropertyValueFactory<Question, String>("questionID"));

        QuestionTeacherID.setCellValueFactory(cellData -> {
            Question question = cellData.getValue();
            Teacher teacher = question.getTeacherThatCreated();
            String teacherId = teacher.getUserID();
            return new SimpleStringProperty(teacherId);
        });

        QuestionTeacherName.setCellValueFactory(cellData -> {
            Question question = cellData.getValue();
            Teacher teacher = question.getTeacherThatCreated();
            String teacherFirstName = teacher.getFirstName();
            String teacherLastName = teacher.getLastName();
            return new SimpleStringProperty(teacherFirstName + " " + teacherLastName);
        });

        SubjectName.setCellValueFactory(cellData -> {
            Question question = cellData.getValue();
            Subject subject = question.getQuestionSubject();
            String subjectName = subject.getSubjectName();
            return new SimpleStringProperty(subjectName);
        });
        ObservableList<Question> observableList = FXCollections.observableArrayList();
        observableList.addAll(GlobalDataSaved.allQuestionsForTeacher);
        table.setItems(observableList);
    }



}
