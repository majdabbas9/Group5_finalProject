package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ManualExamToExecute;
import javafx.beans.property.SimpleIntegerProperty;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalExtraTimeApprovment {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="ApproveBtn"
    private Button ApproveBtn; // Value injected by FXMLLoader

    @FXML // fx:id="BackToMenuBtn"
    private Button BackToMenuBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DeclineBtn"
    private Button DeclineBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Errortxt"
    private Text Errortxt; // Value injected by FXMLLoader

    @FXML // fx:id="RequestsTable"
    private TableView<ExamToExecute> RequestsTable; // Value injected by FXMLLoader

    @FXML // fx:id="_Course"
    private TableColumn<ExamToExecute, String> _Course; // Value injected by FXMLLoader

    @FXML // fx:id="_ExamID"
    private TableColumn<ExamToExecute, String> _ExamID; // Value injected by FXMLLoader

    @FXML // fx:id="_ExtraTime"
    private TableColumn<ExamToExecute, Integer> _ExtraTime; // Value injected by FXMLLoader

    @FXML // fx:id="_OriginalTime"
    private TableColumn<ExamToExecute, Integer> _OriginalTime; // Value injected by FXMLLoader

    @FXML // fx:id="_Subject"
    private TableColumn<ExamToExecute, String> _Subject; // Value injected by FXMLLoader

    @FXML // fx:id="_Teacher"
    private TableColumn<ExamToExecute, String> _Teacher; // Value injected by FXMLLoader
    private ExamToExecute SelectedExam;
    private ObservableList<ExamToExecute> list = FXCollections.observableArrayList();
    @FXML
    void Approve(ActionEvent event) throws IOException {
        if (SelectedExam == null){
            Errortxt.setText("Please Select Exam!");
        }
        try {
            Message message = null;
            if (SelectedExam instanceof ComputerizedExamToExecute){
                message = new Message("Add Extra Time For CompExam",SelectedExam.getId());
            }
            else if (SelectedExam instanceof ManualExamToExecute) {
                message = new Message("Add Extra Time For ManualExam",SelectedExam.getId());
            }
            SimpleClient.getClient().sendToServer(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        App.setRoot("principalHome");
    }

    @FXML
    void Decline(ActionEvent event) {
        if (SelectedExam == null){
            Errortxt.setText("Please Select Exam!");
        }
        try {
            Message message = new Message("Do Not Add Extra Time",SelectedExam.getId());
            SimpleClient.getClient().sendToServer(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void selectRequest(MouseEvent event) {
        if (RequestsTable.getSelectionModel().getSelectedItem() == null){
            return;
        }
        SelectedExam = RequestsTable.getSelectionModel().getSelectedItem();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        for (ExamToExecute examToExecute : GlobalDataSaved.allExamsToExecuteForPrincipal) {
            list.add(examToExecute);
        }

        _ExtraTime.setCellValueFactory(new PropertyValueFactory<ExamToExecute, Integer>("extraTime"));

        _ExamID.setCellValueFactory(cellData -> {
            ExamToExecute examToExecute = cellData.getValue();
            String examId = examToExecute.getExam().getExam_ID();
            return new SimpleStringProperty(examId);
        });

        _Course.setCellValueFactory(cellData -> {
            ExamToExecute examToExecute = cellData.getValue();
            String courseName = examToExecute.getExam().getExamCourse().getCourseName();
            return new SimpleStringProperty(courseName);
        });

        _Subject.setCellValueFactory(cellData -> {
            ExamToExecute examToExecute = cellData.getValue();
            String subjectName = examToExecute.getExam().getExamSubject().getSubjectName();
            return new SimpleStringProperty(subjectName);
        });

        _Teacher.setCellValueFactory(cellData -> {
            ExamToExecute examToExecute = cellData.getValue();
            String teacher = examToExecute.getTeacherThatExecuted().toString();
            return new SimpleStringProperty(teacher);
        });

        _OriginalTime.setCellValueFactory(cellData -> {
            ExamToExecute examToExecute = cellData.getValue();
            int time = examToExecute.getExam().getTime();
            return new SimpleIntegerProperty(time).asObject();
        });

        RequestsTable.setItems(list);

    }

}
