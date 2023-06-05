/**
 * Sample Skeleton for 'teacherExamsInProgress.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.aidClassesForTeacher.ExamQuestion;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;

public class ExamsInProgress {

    @FXML // fx:id="addTimeButton"
    private Button addTimeButton; // Value injected by FXMLLoader

    @FXML // fx:id="buttonBack"
    private Button buttonBack; // Value injected by FXMLLoader

    @FXML // fx:id="compExamsTable"
    private TableView<ComputerizedExamToExecute> compExamsTable; // Value injected by FXMLLoader

    @FXML // fx:id="examFinishTimeColumn"
    private TableColumn<ComputerizedExamToExecute, String> examFinishTimeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="examIdColumn"
    private TableColumn<ComputerizedExamToExecute, Integer> examIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="hourList"
    private ComboBox<String> hourList; // Value injected by FXMLLoader

    @FXML // fx:id="minuteList"
    private ComboBox<String> minuteList; // Value injected by FXMLLoader

    @FXML // fx:id="numberOfStudentDoingColumn"
    private TableColumn<ComputerizedExamToExecute, Integer> numberOfStudentDoingColumn; // Value injected by FXMLLoader

    @FXML // fx:id="warningTxt"
    private Text warningTxt; // Value injected by FXMLLoader
    private Teacher theTeacher;
    private ObservableList<ComputerizedExamToExecute> compExamObservableList;

    @FXML
    void addTimeForTheExam(ActionEvent event) {

    }
    @FXML
    void backToBuildExam(ActionEvent event) throws IOException {
        App.setRoot("teacherHome");
    }
    @FXML
    void initialize()
    {
        hourList.getItems().clear();
        minuteList.getItems().clear();
        for(int i=0;i<=59;i++)
        {
            if(i<=24)
            {
                if(i<10)
                {
                    hourList.getItems().add("0"+String.valueOf(i));
                    minuteList.getItems().add("0"+String.valueOf(i));
                }
                else
                {
                    hourList.getItems().add(String.valueOf(i));
                    minuteList.getItems().add(String.valueOf(i));
                }
            }
            else
            {
                minuteList.getItems().add(String.valueOf(i));
            }

        }
        theTeacher=(Teacher) GlobalDataSaved.connectedUser;
        compExamObservableList = FXCollections.observableArrayList();
        examIdColumn.setCellValueFactory(new PropertyValueFactory<ComputerizedExamToExecute,Integer>("code"));
        examFinishTimeColumn.setCellValueFactory(new PropertyValueFactory<ComputerizedExamToExecute,String>("dateOfExam"));
        numberOfStudentDoingColumn.setCellValueFactory(new PropertyValueFactory<ComputerizedExamToExecute,Integer>("numOfStudentDoing"));
        for(ComputerizedExamToExecute compExam : theTeacher.getExecutedExams())
        {
            compExamObservableList.add(compExam);
        }
        compExamsTable.setItems(compExamObservableList);
    }


}
