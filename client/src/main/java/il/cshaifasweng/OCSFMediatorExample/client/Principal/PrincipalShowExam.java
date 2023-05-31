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
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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

import javax.security.auth.callback.Callback;

public class PrincipalShowExam {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackToMenuBtn;

    @FXML
    private Button ShowQuestionBtn;

    @FXML
    private Text course;

    @FXML
    private Text err_txt;

    @FXML
    private Text exam_id;

    @FXML
    private Text notes;

    @FXML
    private TableColumn<Exam, Integer> points;

    @FXML
    private TableView<Exam_Question> questions_table;

    @FXML
    private Text subject;

    @FXML
    private Text teacher;

    @FXML
    private TableColumn<Exam_Question, String> the_question;

    private Question question_to_show = GlobalDataSaved.PrincipalQuestionToShow;
    @FXML
    void BackToMenu(ActionEvent event){
        List<Exam> list = new ArrayList<>();
        Message msg = new Message("AllExamsToPrincipal", list);
        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ShowQuestion(ActionEvent event) throws IOException {
        if (question_to_show == null) {
            err_txt.setText("Please Select A Question!");
            return;
        }

        GlobalDataSaved.PrincipalQuestionToShow = question_to_show;
        App.setRoot("principalShowQuestion");
    }

    @FXML
    void selectquestion(MouseEvent event) {
        if (questions_table.getSelectionModel().getSelectedItem() != null){
            question_to_show = questions_table.getSelectionModel().getSelectedItem().getQuestion();
        }
    }

    @FXML
    void initialize() {

        exam_id.setText("Exam ID: " + GlobalDataSaved.PrincipalExamToShow.getExam_ID());
        teacher.setText("By Teacher: " + GlobalDataSaved.PrincipalExamToShow.getTeacherThatCreated().getFirstName() +
                " " + GlobalDataSaved.PrincipalExamToShow.getTeacherThatCreated().getLastName() + " (" +
                GlobalDataSaved.PrincipalExamToShow.getTeacherThatCreated().getUserID() +")");
        subject.setText("Subject: " + GlobalDataSaved.PrincipalExamToShow.getExamSubject());
        course.setText("Course: " + GlobalDataSaved.PrincipalExamToShow.getExamCourse());
        notes.setText("Notes: " + GlobalDataSaved.PrincipalExamToShow.getTeacherNotes());


        ObservableList<Exam_Question> exam_to_show =
                FXCollections.observableArrayList(GlobalDataSaved.PrincipalExamToShow.getExamQuestions());

        the_question.setCellValueFactory(cellData -> {
            Exam_Question examQuestion = cellData.getValue();
            String question = examQuestion.getQuestion().getStudentNotes();
            return new SimpleStringProperty(question);
        });

        //TODO:: Add points attribute to Exam_Questions in order to display also the points in the table.

        questions_table.setItems(exam_to_show);
    }

}
