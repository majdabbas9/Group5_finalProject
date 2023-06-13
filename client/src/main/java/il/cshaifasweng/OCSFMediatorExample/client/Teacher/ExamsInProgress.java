/**
 * Sample Skeleton for 'teacherExamsInProgress.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExamsInProgress {

    @FXML
    private Text ErrTxt;

    @FXML // fx:id="addTimeButton"
    private Button addTimeButton; // Value injected by FXMLLoader

    @FXML // fx:id="buttonBack"
    private Button buttonBack; // Value injected by FXMLLoader

    @FXML // fx:id="compExamsTable"
    private TableView<ExamToExecute> examsInProgressTable; // Value injected by FXMLLoader

    @FXML // fx:id="examFinishTimeColumn"
    private TableColumn<ExamToExecute, String> examFinishTimeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="examIdColumn"
    private TableColumn<ExamToExecute, Integer> examIdColumn; // Value injected by FXMLLoader


    @FXML // fx:id="numberOfStudentDoingColumn"
    private TableColumn<ExamToExecute, Integer> numberOfStudentDoingColumn; // Value injected by FXMLLoader

    @FXML
    private Text warning;
    private ObservableList<ExamToExecute> examToExecutes;

    @FXML
    private TextField ExtraTimeTxt;
    @FXML
    void addTimeForTheExam(ActionEvent event) {
        if (ExtraTimeTxt.equals(""))
        {
            ErrTxt.setText("Illegal Input!");
            return;
        }
        int i;
        for (i = 0; i<ExtraTimeTxt.getText().length(); ++i)
        {
            if (ExtraTimeTxt.getText().charAt(i) > '9' || ExtraTimeTxt.getText().charAt(i) < '0')
            {
                ErrTxt.setText("Illegal Input!");
                return;
            }
        }
        try {
            List<Object> list = new ArrayList<>();
            list.add(examsInProgressTable.getSelectionModel().getSelectedItem().getId());
            list.add(Integer.parseInt(ExtraTimeTxt.getText()));
            Message message = new Message("AddExtraTime", list);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void backToBuildExam(ActionEvent event) throws IOException {
        App.setRoot("teacherHome");
    }
    @FXML
    void initialize()
    {
        examToExecutes = FXCollections.observableArrayList();
        examIdColumn.setCellValueFactory(new PropertyValueFactory<ExamToExecute,Integer>("code"));
        examFinishTimeColumn.setCellValueFactory(new PropertyValueFactory<ExamToExecute,String>("dateOfExam"));
        numberOfStudentDoingColumn.setCellValueFactory(new PropertyValueFactory<ExamToExecute,Integer>("numOfStudentDoing"));
        for(ExamToExecute examToExecute : GlobalDataSaved.getTeacherExamsToExecutes)
        {
            examToExecutes.add(examToExecute);
        }
        examsInProgressTable.setItems(examToExecutes);
    }


}
