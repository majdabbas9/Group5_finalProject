/**
 * Sample Skeleton for 'examNeedApprovement.fxml' Controller Class
 */

package Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import aidClasses.aidClassesForTeacher.DisplayCompExamForApprove;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
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

    @FXML // fx:id="currentDate1"
    private Text currentDate1; // Value injected by FXMLLoader

    @FXML // fx:id="currentTime1"
    private Text currentTime1; // Value injected by FXMLLoader

    @FXML // fx:id="dataColumn"
    private TableColumn<DisplayCompExamForApprove, ComputerizedExamToExecute> dataColumn; // Value injected by FXMLLoader

    @FXML // fx:id="examIdColumn"
    private TableColumn<DisplayCompExamForApprove, String> examIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="examsTable"
    private TableView<DisplayCompExamForApprove> examsTable; // Value injected by FXMLLoader

    @FXML // fx:id="studentName"
    private Text studentName; // Value injected by FXMLLoader

    @FXML // fx:id="subjectColumn"
    private TableColumn<DisplayCompExamForApprove, String> subjectColumn; // Value injected by FXMLLoader

    @FXML
    void backClick(ActionEvent event) throws IOException {
        App.setRoot("teacherHome");
    }

    @FXML
    void nextClick(ActionEvent event) throws IOException {
        if(examsTable.getSelectionModel().getSelectedItem()==null)return;
        Message msg1 = new Message("#showAllCompExamGrades",examsTable.getSelectionModel().getSelectedItem().getDate().getId()); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server
    }
    @FXML
    void initialize()
    {
        ObservableList<DisplayCompExamForApprove> observableList = FXCollections.observableArrayList();
        dataColumn.setCellValueFactory(new PropertyValueFactory<DisplayCompExamForApprove,ComputerizedExamToExecute>("date"));
        dataColumn.setStyle("-fx-alignment: CENTER;");
        examIdColumn.setCellValueFactory(new PropertyValueFactory<DisplayCompExamForApprove,String>("examId"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<DisplayCompExamForApprove,String>("subjectName"));
        subjectColumn.setStyle("-fx-alignment: CENTER;");
        for(ComputerizedExamToExecute compExam: GlobalDataSaved.teacherCompExamsToApprove)
        {
            DisplayCompExamForApprove dcea=new DisplayCompExamForApprove(compExam,compExam.getExam().getExam_ID(),
                    compExam.getExam().getExamSubject().getSubjectName());
            observableList.add(dcea);
        }
        examsTable.setItems(observableList);
    }

}
