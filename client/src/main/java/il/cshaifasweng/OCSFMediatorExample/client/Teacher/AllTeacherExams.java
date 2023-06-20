package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllTeacherExams {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonSubmit;

    @FXML
    private TableColumn<Exam, String> courseNameColumn;

    @FXML
    private TableColumn<Exam, String> examIdColumn;

    @FXML
    private TableView<Exam> examsTable;

    @FXML
    private TableColumn<Exam, String> notesColumn;

    @FXML
    private TableColumn<Exam, String> subjectNameColumn;

    @FXML
    private TableColumn<Exam, Integer> timeColumn;

    @FXML
    private Text warningTxt;

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        App.setRoot("teacherHome");
    }

    @FXML
    void showResult(ActionEvent event) throws IOException {
        warningTxt.setText("");
        if(examsTable.getSelectionModel().getSelectedItem()==null)
        {
            warningTxt.setText("no selected exam");
            return;
        }
        List<Object> dataToSever=new ArrayList<>();
        dataToSever.add(examsTable.getSelectionModel().getSelectedItem().getTeacherThatCreated().getId());
        dataToSever.add(examsTable.getSelectionModel().getSelectedItem().getId());
        Message msg3 = new Message("#getMyExamGrades",dataToSever); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg3); // sending the msg to the server
    }
    @FXML
    void initialize()
    {
        ObservableList<Exam> observableList= FXCollections.observableArrayList();
        examIdColumn.setCellValueFactory(new PropertyValueFactory<Exam,String>("exam_ID"));
        examIdColumn.setStyle("-fx-alignment: CENTER;");

        timeColumn.setCellValueFactory(new PropertyValueFactory<Exam,Integer>("time"));
        timeColumn.setStyle("-fx-alignment: CENTER;");

        notesColumn.setCellValueFactory(new PropertyValueFactory<Exam,String>("teacherNotes"));
        notesColumn.setStyle("-fx-alignment: CENTER;");
        courseNameColumn.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            String courseName=course.getCourseName();
            return new SimpleStringProperty(courseName);
        });
        courseNameColumn.setStyle("-fx-alignment: CENTER;");

        subjectNameColumn.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject=exam.getExamSubject();
            String subjectName=subject.getSubjectName();
            return new SimpleStringProperty(subjectName);
        });
        subjectNameColumn.setStyle("-fx-alignment: CENTER;");
        observableList.addAll(GlobalDataSaved.allTeacherExamsCreated);

        examsTable.setItems(observableList);
    }
}
