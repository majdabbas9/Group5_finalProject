package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import aidClasses.GlobalDataSaved;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
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

public class PrincipalGrades {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackToMenuBtn;

    @FXML
    private TableColumn<Grade, String> CourseId;

    @FXML
    private TableColumn<Grade, String> CourseName;

    @FXML
    private Button CpyBtn;

    @FXML
    private TableColumn<Grade, String> ExamId;

    @FXML
    private Text NoCopyText;

    @FXML
    private TableColumn<Grade, String> StudentId;

    @FXML
    private TableColumn<Grade, String> StudentName;

    @FXML
    private TableColumn<Grade, String> SubjectId;

    @FXML
    private TableColumn<Grade, String> SubjectName;

    @FXML
    private TableColumn<Grade, Integer> TheGrade;

    @FXML
    private TableView<Grade> gradeTable;
    private ObservableList<Grade> list = FXCollections.observableArrayList(GlobalDataSaved.allGradesForPrincipal);
    private Grade gradeToShow;

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        App.setRoot("principalHome");
    }

    @FXML
    void ShowCopy(ActionEvent event) {
        if (gradeToShow.getExamCopy() == null){
            NoCopyText.setText("No Copy Exists To This Exam");
            return;
        }
        //TODO:: Show the copy;
    }

    @FXML
    void selectgrade(MouseEvent event) {
        NoCopyText.setText("");
        if(gradeTable.getSelectionModel().getSelectedItem() == null){
            return;
        }
        gradeToShow = gradeTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    void initialize() {
        TheGrade.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("grade"));

        StudentName.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            Student student = grade.getStudent();
            String studentFirstName = student.getFirstName();
            String studentLastName = student.getLastName();
            return new SimpleStringProperty(studentFirstName + " " + studentLastName);
        });

        StudentId.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            Student student = grade.getStudent();
            String studentId = student.getUserID();
            return new SimpleStringProperty(studentId);
        });

        //TODO:: Add ExamID ti know the exam of the grade so we can know which course and subject and which
        // questions this grade about, and add all of that to the table



        gradeTable.setItems(list);
    }

}
