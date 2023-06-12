/**
 * Sample Skeleton for 'examNeedApprovement.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import aidClasses.aidClassesForTeacher.DisplayCompExamForApprove;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.Principal.PrincipalHome;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
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

public class ExamsNeedApprovement {

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="backButton1"
    private Button backButton1; // Value injected by FXMLLoader

    @FXML // fx:id="dataColumn"
    private TableColumn<DisplayCompExamForApprove, ComputerizedExamToExecute> dataColumn; // Value injected by FXMLLoader

    @FXML // fx:id="examIdColumn"
    private TableColumn<DisplayCompExamForApprove, String> examIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="examsTable"
    private TableView<DisplayCompExamForApprove> examsTable; // Value injected by FXMLLoader

    @FXML
    private Text currentDate;

    @FXML
    private Text currentTime;
    @FXML
    private Text teacherName;

    @FXML // fx:id="subjectColumn"
    private TableColumn<DisplayCompExamForApprove, String> subjectColumn; // Value injected by FXMLLoader
    @FXML
    private TableColumn<DisplayCompExamForApprove, String> manualColumn;
    @FXML
    void backClick(ActionEvent event) throws IOException {
        App.setRoot("teacherHome");
    }

    @FXML
    void nextClick(ActionEvent event) throws IOException { // TODO add manual exam approve
        if(examsTable.getSelectionModel().getSelectedItem()==null)return;
        if(examsTable.getSelectionModel().getSelectedItem().isManual())GlobalDataSaved.isManualToApprove=true;
        else GlobalDataSaved.isManualToApprove=false;
        Message msg1 = new Message("#showAllExamGrades",examsTable.getSelectionModel().getSelectedItem().getDate().getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server
    }
    @FXML
    void initialize()
    {
        PrincipalHome.dataOnHome(teacherName,currentDate,currentTime);
        ObservableList<DisplayCompExamForApprove> observableList = FXCollections.observableArrayList();
        dataColumn.setCellValueFactory(new PropertyValueFactory<DisplayCompExamForApprove,ComputerizedExamToExecute>("date"));
        dataColumn.setStyle("-fx-alignment: CENTER;");
        examIdColumn.setCellValueFactory(new PropertyValueFactory<DisplayCompExamForApprove,String>("examId"));
        examIdColumn.setStyle("-fx-alignment: CENTER;");
        subjectColumn.setCellValueFactory(new PropertyValueFactory<DisplayCompExamForApprove,String>("subjectName"));
        subjectColumn.setStyle("-fx-alignment: CENTER;");
        manualColumn.setCellValueFactory(new PropertyValueFactory<DisplayCompExamForApprove,String>("isManual"));
        manualColumn.setStyle("-fx-alignment: CENTER;");
        for(ExamToExecute exam: GlobalDataSaved.teacherExamsToApprove)
        {
            DisplayCompExamForApprove dcea=new DisplayCompExamForApprove(exam,exam.getExam().getExam_ID(),
                    exam.getExam().getExamSubject().getSubjectName());
            if(exam.getClass().equals(ComputerizedExamToExecute.class))dcea.setManual(false);
            observableList.add(dcea);
        }
        examsTable.setItems(observableList);
    }

}
