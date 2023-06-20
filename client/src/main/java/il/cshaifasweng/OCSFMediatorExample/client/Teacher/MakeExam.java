/**
 * Sample Skeleton for 'makeExam.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
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

public class MakeExam {

    @FXML // fx:id="buttonBack"
    private Button buttonBack; // Value injected by FXMLLoader

    @FXML // fx:id="coursesTable"
    private TableView<Course> coursesTable; // Value injected by FXMLLoader

    @FXML // fx:id="examQuestionID"
    private TableColumn<Course, Integer> courseId; // Value injected by FXMLLoader

    @FXML // fx:id="examQuestionsTheQuestion"
    private TableColumn<Course, String> courseName; // Value injected by FXMLLoader

    @FXML // fx:id="subjectList"
    private ComboBox<Subject> subjectList; // Value injected by FXMLLoader
    @FXML // fx:id="warning"
    private Text warning; // Value injected by FXMLLoader
    private ObservableList<Course> observableList;
    public static Course selectedCourse;
    public static Subject selectedSubject;

    @FXML
    void addQuestionsButton(ActionEvent event) {
        warning.setText("");
        if (coursesTable.getSelectionModel().getSelectedItem() == null){
            warning.setText("you didnt select course!");
            return;
        }
        if(coursesTable.getSelectionModel().getSelectedItem().getId()==-1)
        {
            warning.setText("you didnt select course!");
            return;
        }
        selectedCourse=coursesTable.getSelectionModel().getSelectedItem();
        selectedSubject=subjectList.getSelectionModel().getSelectedItem();
        try {
            Message msg = new Message("#showAllCourseQuestion", coursesTable.getSelectionModel().getSelectedItem().getId()); // creating a msg to the server demanding the students
            SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void backToBuildExam(ActionEvent event) throws IOException {
        App.setRoot("buildExam");
    }

    @FXML
    void subjectSelected(ActionEvent event) {
        coursesTable.getItems().clear();
        observableList.clear();
        Subject selectedSubject=subjectList.getSelectionModel().getSelectedItem();
        for(Course course: GlobalDataSaved.teacherCourses)
        {
            if(course.getCourseSubject().getSubjectName().equals(selectedSubject.getSubjectName()))
            {
                observableList.add(course);
            }
        }
        coursesTable.setItems(observableList);

    }
    @FXML
    public void initialize()
    {
        warning.setText("");
        subjectList.getItems().setAll(GlobalDataSaved.teacherSubjects);
        observableList = FXCollections.observableArrayList();
        courseId.setCellValueFactory(new PropertyValueFactory<Course, Integer>("id"));
        courseName.setCellValueFactory(new PropertyValueFactory<Course,String>("courseName"));
        courseName.setStyle("-fx-alignment: CENTER;");
    }
}
