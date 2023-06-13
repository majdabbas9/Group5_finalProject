/**
 * Sample Skeleton for 'approvement.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import aidClasses.aidClassesForTeacher.DisplayGrade;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.WordGeneratorFile;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Approvement {


    @FXML // fx:id="currentDate1"
    private Text currentDate1; // Value injected by FXMLLoader

    @FXML // fx:id="currentTime1"
    private Text currentTime1; // Value injected by FXMLLoader

    @FXML
    private TableColumn<DisplayGrade, Grade> gradeObjectColumn;

    @FXML // fx:id="gradeColumn"
    private TableColumn<DisplayGrade, TextField> gradeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="studentNameColumn"
    private TableColumn<DisplayGrade, String> studentNameColumn; // Value injected by FXMLLoader
    @FXML // fx:id="dateColumn"
    private TableColumn<DisplayGrade, String> dateColumn; // Value injected by FXMLLoader


    @FXML // fx:id="gradesTable"
    private TableView<DisplayGrade> gradesTable; // Value injected by FXMLLoader

    @FXML // fx:id="notesColumn"
    private TableColumn<DisplayGrade, TextField> notesColumn; // Value injected by FXMLLoader

    @FXML // fx:id="studentIdColumn"
    private TableColumn<DisplayGrade, String> studentIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="studentName"
    private Text studentName; // Value injected by FXMLLoader

    @FXML // fx:id="warning"
    private Text warning; // Value injected by FXMLLoader
    @FXML
    private TableColumn<DisplayGrade, Button> wordColumn;
    private Button[] buttons;

    private ObservableList<DisplayGrade> observableList = FXCollections.observableArrayList();

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        Message msg1 = new Message("#showAllExamsForTeahcerToApprove", GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server
    }

    @FXML
    public void updateGrade(MouseEvent mouseEvent) {
        warning.setText("");
        DisplayGrade dg = gradesTable.getSelectionModel().getSelectedItem();
        try {
            int newGrade = Integer.valueOf(gradesTable.getSelectionModel().getSelectedItem().getGrade().getText());
            if (newGrade > 100 || newGrade < 0) {
                warning.setText("the new grade is illegal");
                return;
            }
        } catch (Exception ex) {
            warning.setText("the new grade is not a number");
            return;
        }
        int newGrade = Integer.valueOf(gradesTable.getSelectionModel().getSelectedItem().getGrade().getText());
        String notes = gradesTable.getSelectionModel().getSelectedItem().getNotes().getText();
        List<Object> dataToServer = new ArrayList<>();
        dataToServer.add(dg.getGradeObject().getId());
        dataToServer.add(newGrade);
        dataToServer.add(notes);
        observableList.remove(dg);
        try {
            Message msg1 = new Message("#teacherApproveGrade", dataToServer); // creating a msg to the server demanding the students
            SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


    }

    private void handleButtonAction(ActionEvent event) throws IOException {
        for (int i = 0; i < buttons.length; i++) {
            if (event.getSource() == buttons[i]) {
                if (gradesTable.getItems().get(i).getGradeObject().isManuel()) {
                    WordGeneratorFile.openWord(gradesTable.getItems().get(i).getGradeObject().getExamCopy().getAnswers());
                    return;
                } else {
                    String answers = gradesTable.getItems().get(i).getGradeObject().getExamCopy().getAnswers();
                    if (answers != null){
                        List<String> answersList = new ArrayList<String>(Arrays.asList(answers.split(",")));
                        GlobalDataSaved.studentAnswers = answersList;
                    }
                    GlobalDataSaved.examToExecute = gradesTable.getItems().get(i).getGradeObject().getExamCopy().getCompExamToExecute();
                    GlobalDataSaved.copyToPrincipal = false;
                    GlobalDataSaved.copyToStudent = false;
                    GlobalDataSaved.copyToTeacher = true;
                    App.setRoot("examCopy");


                }

            }
        }
    }


    @FXML
    public void initialize() {
        int i = 0;
        buttons = new Button[GlobalDataSaved.compExamGrades.size()];
        for (int j = 0; j < buttons.length; j++) {
            buttons[i] = new Button("copy");
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        handleButtonAction(actionEvent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<DisplayGrade, String>("studentID"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<DisplayGrade, String>("studentName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<DisplayGrade, String>("date"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<DisplayGrade, TextField>("grade"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<DisplayGrade, TextField>("notes"));
        gradeObjectColumn.setCellValueFactory(new PropertyValueFactory<DisplayGrade, Grade>("gradeObject"));
        wordColumn.setCellValueFactory(new PropertyValueFactory<DisplayGrade, Button>("word"));
        for (Grade grade : GlobalDataSaved.compExamGrades) {
            Student student = grade.getStudent();
            TextField textFieldGrade = new TextField(Integer.toString(grade.getGrade()));
            textFieldGrade.setStyle("-fx-alignment: CENTER;");
            TextField textFieldNotes = new TextField();
            textFieldNotes.setPromptText("add notes");
            textFieldNotes.setStyle("-fx-alignment: CENTER;");
            DisplayGrade displayGrade = new DisplayGrade(student.getUserID(), student.getFirstName(), grade.getDate(), textFieldGrade, textFieldNotes, grade);
            observableList.add(displayGrade);
            displayGrade.setWord(buttons[i++]);
        }
        gradesTable.setItems(observableList);
    }


}
