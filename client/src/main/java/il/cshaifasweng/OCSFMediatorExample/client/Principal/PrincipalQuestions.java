package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import java.io.IOException;

import aidClasses.GlobalDataSaved;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PrincipalQuestions {
    @FXML
    private Text ErrTxt;

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
    private ObservableList<Question> list = FXCollections.observableArrayList(GlobalDataSaved.allQuestionsForPrincipal);
    private Question questionToShow = GlobalDataSaved.PrincipalQuestionToShow;
    @FXML
    private TableView<Question> _QuestionToShowTabel;

    @FXML
    private TableColumn<Question, String> _TheQuestion;

    @FXML
    private TableColumn<Question, String> _TheQuestionId;

    @FXML
    private TableColumn<Question, String> _TheQuestionTeacherID;

    @FXML
    private TableColumn<Question, String> _TheQuestionTeacherName;

    @FXML
    private TableColumn<Question, String> _TheQuestipnSubjectName;

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        GlobalDataSaved.ThePrincipalInQuestions = false;
        App.setRoot("principalHome");
    }

    @FXML
    void ShowQuestion(ActionEvent event) throws IOException {
        if (questionToShow == null) {
            ErrTxt.setText("Please Select Question!");
            return;
        }
        ErrTxt.setText("");
        GlobalDataSaved.ThePrincipalInQuestions = false;
        GlobalDataSaved.PrincipalQuestionToShow = questionToShow;
        App.setRoot("principalShowQuestion");
    }

    @FXML
    void selectQuestion(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            questionToShow = table.getSelectionModel().getSelectedItem();
            GlobalDataSaved.PrincipalQuestionToShow = questionToShow;
            UpdateQuestionToShowTable();
        }

    }
    private void UpdateQuestionToShowTable()
    {
        ObservableList<Question> list1 = FXCollections.observableArrayList();
        list1.add(questionToShow);

        _TheQuestion.setCellValueFactory(new PropertyValueFactory<Question, String>("studentNotes"));
        _TheQuestionId.setCellValueFactory(new PropertyValueFactory<Question, String>("questionID"));

        _TheQuestionTeacherID.setCellValueFactory(cellData -> {
            Question question = cellData.getValue();
            Teacher teacher = question.getTeacherThatCreated();
            String teacherId = teacher.getUserID();
            return new SimpleStringProperty(teacherId);
        });

        _TheQuestionTeacherName.setCellValueFactory(cellData -> {
            Question question = cellData.getValue();
            Teacher teacher = question.getTeacherThatCreated();
            String teacherFirstName = teacher.getFirstName();
            String teacherLastName = teacher.getLastName();
            return new SimpleStringProperty(teacherFirstName + " " + teacherLastName);
        });

        _TheQuestipnSubjectName.setCellValueFactory(cellData -> {
            Question question = cellData.getValue();
            Subject subject = question.getQuestionSubject();
            String subjectName = subject.getSubjectName();
            return new SimpleStringProperty(subjectName);
        });

        _QuestionToShowTabel.getItems().clear();
        _QuestionToShowTabel.setItems(list1);
        _QuestionToShowTabel.refresh();
    }

    @FXML
    void initialize() {
        GlobalDataSaved.ThePrincipalInQuestions = true;
        // to know that we are in showing Questions individually and not exam
        GlobalDataSaved.PrincipalExamToShow = null;
        if (questionToShow!= null){
            UpdateQuestionToShowTable();
        }
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
        table.setItems(list);
    }

}
