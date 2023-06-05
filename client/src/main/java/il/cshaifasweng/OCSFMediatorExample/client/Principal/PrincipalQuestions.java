package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class PrincipalQuestions {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackToMenuBtn;


    @FXML
    private TableColumn<Question, String> QuestionId;

    @FXML
    private TableColumn<Question, String> QuestionTeacherName;
    @FXML
    private TableColumn<Question, String> TheQuestion;
    @FXML
    private TableColumn<Question, String> QuestionTeacherID;


    @FXML
    private Button ShowQuestionBtn;

    @FXML
    private TableColumn<Question, Integer> SubjectID;

    @FXML
    private TableColumn<Question, String> SubjectName;

    @FXML
    private TableView<Question> table;
    private ObservableList<Question> list = FXCollections.observableArrayList(GlobalDataSaved.allQuestionsForPrincipal);
    private Question questionToShow = GlobalDataSaved.PrincipalQuestionToShow;
    private TableRow<Question> selected_row = new TableRow<>();
    private ScheduledExecutorService executor;


    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        App.setRoot("principalHome");
    }

    @FXML
    void ShowQuestion(ActionEvent event) throws IOException {
        if (questionToShow == null) {
            return;
        }
        GlobalDataSaved.PrincipalQuestionToShow = questionToShow;
        App.setRoot("principalShowQuestion");
    }

    @FXML
    void selectQuestion(MouseEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            selected_row.setStyle("");
            questionToShow = table.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    void initialize() {

        // to know that we are in showing Questions individually and not exam
        GlobalDataSaved.PrincipalExamToShow = null;

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

//        executor = Executors.newSingleThreadScheduledExecutor();
//        executor.scheduleAtFixedRate(() -> {
//            // Update the data in the background
//            List<Question> list = new ArrayList<>();
//            Message msg = new Message("UpdateAllQuestionsToPrincipal", list);
//            try {
//                SimpleClient.getClient().sendToServer(msg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // Update the UI on the JavaFX application thread
//            table.setItems(FXCollections.observableList(GlobalDataSaved.allQuestionsForPrincipal));
//            //Updating the color of the selected row
//            for (Node node : table.lookupAll(".table-row-cell")) {
//                if (node instanceof TableRow) {
//                    TableRow<Question> row = (TableRow<Question>) node;
//                    if (row.getItem() == questionToShow) {
//                        selected_row = row;
//                    }
//                }
//            }
//            selected_row.setStyle("-fx-background-color: #039ED3;");
//        }, 0, 2, TimeUnit.SECONDS);
    }

    public void UpdateTable() {
        //TODO:: FIX THIS - MOSTUFA - Cant Refresh the table.
        System.out.println(GlobalDataSaved.allQuestionsForPrincipal.size());
        list = FXCollections.observableArrayList(GlobalDataSaved.allQuestionsForPrincipal);
        table.refresh();

        table.getItems().removeAll(list);
        table.refresh();

        System.out.println(table.getItems().size());
        table.setItems(list);
        System.out.println(table.getItems().size());

        //Updating the color of the selected row
        for (Node node : table.lookupAll(".table-row-cell")) {
            if (node instanceof TableRow) {
                TableRow<Question> row = (TableRow<Question>) node;
                if (row.getItem() == questionToShow) {
                    selected_row = row;
                }
            }
        }
        selected_row.setStyle("-fx-background-color: #039ED3;");
    }
}


