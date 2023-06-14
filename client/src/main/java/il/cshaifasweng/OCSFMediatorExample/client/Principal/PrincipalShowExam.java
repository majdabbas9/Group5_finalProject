package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import aidClasses.ExamQuestionDetails;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.LightBase;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
    private TableColumn<ExamQuestionDetails, Integer> points;

    @FXML
    private TableView<ExamQuestionDetails> questions_table;

    @FXML
    private Text subject;

    @FXML
    private Text teacher;
    private ObservableList<Exam_Question> exam_to_show =
            FXCollections.observableArrayList(GlobalDataSaved.PrincipalExamToShow.getExamQuestions());

    @FXML
    private TableColumn<ExamQuestionDetails, String> the_question;

    private Question question_to_show = GlobalDataSaved.PrincipalQuestionToShow;
    @FXML
    void BackToMenu(ActionEvent event){
        Message msg = new Message("AllExamsToPrincipal");
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
            for (Exam_Question examQuestion : exam_to_show ){
                if (examQuestion.getQuestion().getStudentNotes().equals(
                        questions_table.getSelectionModel().getSelectedItem().get_The_Question())){
                    question_to_show = examQuestion.getQuestion();
                }
            }
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

        ObservableList<ExamQuestionDetails> list = FXCollections.observableArrayList();
        List<Exam_Question> examQuestionList=new ArrayList<>(GlobalDataSaved.PrincipalExamToShow.getExamQuestions());
        for (int i = 0 ; i < exam_to_show.size() ; ++i){
            list.add(new ExamQuestionDetails(exam_to_show.get(i).getQuestion().getStudentNotes(),
                    examQuestionList.get(i).getPoints()));
        }
        the_question.setCellValueFactory(cellData -> {
            ExamQuestionDetails examQuestion = cellData.getValue();
            String question = examQuestion.get_The_Question();
            return new SimpleStringProperty(question);
        });
        the_question.setStyle("-fx-alignment: CENTER;");

        points.setCellValueFactory(cellData -> {
            ExamQuestionDetails examQuestion = cellData.getValue();
            Integer points = examQuestion.get_Points();
            return new SimpleIntegerProperty(points).asObject();
        });
        points.setStyle("-fx-alignment: CENTER;");

        questions_table.setItems(list);
    }

}
