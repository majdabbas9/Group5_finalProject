/**
 * Sample Skeleton for 'prepareExam.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
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

public class PrepareExam {

    @FXML // fx:id="buttonBack"
    private Button buttonBack; // Value injected by FXMLLoader

    @FXML // fx:id="courseColumn"
    private TableColumn<Exam, String> courseColumn; // Value injected by FXMLLoader

    @FXML // fx:id="examIdColumn"
    private TableColumn<Exam, String> examIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="examTable"
    private TableView<Exam> examTable; // Value injected by FXMLLoader

    @FXML // fx:id="examTimeColumn"
    private TableColumn<Exam, Integer> examTimeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="subjectColumn"
    private TableColumn<Exam, String> subjectColumn; // Value injected by FXMLLoader

    @FXML // fx:id="teacherNotesColumn"
    private TableColumn<Exam, String> teacherNotesColumn; // Value injected by FXMLLoader

    @FXML // fx:id="teacherColumn"
    private TableColumn<Exam, String> teacherColumn; // Value injected by FXMLLoader

    @FXML // fx:id="warningTxt"
    private Text warningTxt; // Value injected by FXMLLoader
    public static Exam selectedExam;

    @FXML
    void backToTeacherHome(ActionEvent event) throws IOException {
        App.setRoot("teacherHome");
    }

    @FXML
    void showQuestions(ActionEvent event) {
        warningTxt.setText("");
        if(examTable.getSelectionModel().getSelectedItem()==null)
        {
            warningTxt.setText("no selected exam");
            return;
        }
        PrepareExam.selectedExam=examTable.getSelectionModel().getSelectedItem();
        try {
            Message msg = new Message("#showExamQuestionsToPrepare",examTable.getSelectionModel().getSelectedItem().getId()); // creating a msg to the server demanding the students
            SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    void initialize()
    {
        ObservableList<Exam> observableList = FXCollections.observableArrayList();
        examIdColumn.setCellValueFactory(new PropertyValueFactory<Exam,String>("exam_ID"));
        examIdColumn.setStyle("-fx-alignment: CENTER;");

        teacherColumn.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            String teacherName=exam.getTeacherThatCreated().getFirstName()+" "+exam.getTeacherThatCreated().getLastName();
            if(exam.getTeacherThatCreated().getFirstName().equals(GlobalDataSaved.connectedUser.getFirstName()))
            {
                teacherName="(me)";
            }
            return new SimpleStringProperty(teacherName);
        });
        teacherColumn.setStyle("-fx-alignment: CENTER;");

        subjectColumn.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            return new SimpleStringProperty(exam.getExamSubject().getSubjectName());
        });
        subjectColumn.setStyle("-fx-alignment: CENTER;");

        courseColumn.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            return new SimpleStringProperty(exam.getExamCourse().getCourseName());
        });
        courseColumn.setStyle("-fx-alignment: CENTER;");

        examTimeColumn.setCellValueFactory(new PropertyValueFactory<Exam,Integer>("time"));
        examTimeColumn.setStyle("-fx-alignment: CENTER;");

        teacherNotesColumn.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            return new SimpleStringProperty(exam.getTeacherNotes());
        });
        teacherNotesColumn.setStyle("-fx-alignment: CENTER;");

        for(Exam exam:GlobalDataSaved.allExamsForTeacher)
        {
            observableList.add(exam);
        }
        examTable.setItems(observableList);
    }

}
