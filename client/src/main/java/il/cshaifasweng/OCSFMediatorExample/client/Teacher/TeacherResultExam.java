/**
 * Sample Skeleton for 'teacherResultExam.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ManualExamToExecute;
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
import javafx.scene.text.Text;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;

public class TeacherResultExam {

    @FXML // fx:id="buttonBack"
    private Button buttonBack; // Value injected by FXMLLoader

    @FXML // fx:id="dateColumn"
    private TableColumn<Grade, String> dateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="gradeColumn"
    private TableColumn<Grade, Integer> gradeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="manualColumn"
    private TableColumn<Grade, String> manualColumn; // Value injected by FXMLLoader

    @FXML // fx:id="studentColumn"
    private TableColumn<Grade, String> studentColumn; // Value injected by FXMLLoader

    @FXML // fx:id="studentIdColumn"
    private TableColumn<Grade, String> studentIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="tableGrades"
    private TableView<Grade> tableGrades; // Value injected by FXMLLoader

    @FXML // fx:id="teacherColumn"
    private TableColumn<Grade, String> teacherColumn; // Value injected by FXMLLoader

    @FXML // fx:id="timeToFinishColumn"
    private TableColumn<Grade, Integer> timeToFinishColumn; // Value injected by FXMLLoader

    @FXML // fx:id="warningTxt"
    private Text warningTxt; // Value injected by FXMLLoader

    @FXML
    void backToExams(ActionEvent event) throws IOException {
        App.setRoot("allTeacherExams");
    }
    @FXML
    void initialize()
    {
        ObservableList<Grade> observableList= FXCollections.observableArrayList();
        gradeColumn.setCellValueFactory(new PropertyValueFactory<Grade,Integer>("grade"));
        gradeColumn.setStyle("-fx-alignment: CENTER;");
        timeToFinishColumn.setCellValueFactory(new PropertyValueFactory<Grade,Integer>("timeToSolve"));
        teacherColumn.setStyle("-fx-alignment: CENTER;");
        dateColumn.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            String date=grade.getExamCopy().getCompExamToExecute().getDateOfExam();
            return new SimpleStringProperty(date);
        });
        dateColumn.setStyle("-fx-alignment: CENTER;");

        teacherColumn.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            String teacherName=grade.getExamCopy().getCompExamToExecute().getTeacherThatExecuted().getFirstName()+" "+
                    grade.getExamCopy().getCompExamToExecute().getTeacherThatExecuted().getLastName();
            if(grade.getExamCopy().getCompExamToExecute().getTeacherThatExecuted().getFirstName().equals(GlobalDataSaved
                    .connectedUser.getFirstName()))
            {
                teacherName="(me)";
            }
            return new SimpleStringProperty(teacherName);
        });
        teacherColumn.setStyle("-fx-alignment: CENTER;");

        manualColumn.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            Boolean isManual=false;
            if(grade.getExamCopy().getCompExamToExecute().getClass().equals(ManualExamToExecute.class))
                isManual=true;
            return new SimpleStringProperty(isManual.toString());
        });
        teacherColumn.setStyle("-fx-alignment: CENTER;");

        studentColumn.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            String studentName=grade.getStudent().getFirstName()+" "+grade.getStudent().getLastName();
            return new SimpleStringProperty(studentName);
        });
        studentColumn.setStyle("-fx-alignment: CENTER;");

        studentIdColumn.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            String studentId=grade.getStudent().getUserID();
            return new SimpleStringProperty(studentId);
        });
        studentIdColumn.setStyle("-fx-alignment: CENTER;");

        observableList.addAll(GlobalDataSaved.allTeacherExamGrades);

        tableGrades.setItems(observableList);

    }

}
