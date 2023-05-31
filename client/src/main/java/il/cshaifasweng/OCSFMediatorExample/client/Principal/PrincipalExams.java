package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import aidClasses.GlobalDataSaved;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
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

public class PrincipalExams {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackToMenuBtn;

    @FXML
    private TableColumn<Exam, Integer> CourseID;

    @FXML
    private TableColumn<Exam, String> CourseName;

    @FXML
    private TableColumn<Exam, String> ExamId;

    @FXML
    private TableColumn<Exam, String> ExamTeacherFN;

    @FXML
    private TableColumn<Exam, Integer> ExamTeacherID;

    @FXML
    private TableColumn<Exam, String> ExamTeacherLN;

    @FXML
    private Button ShowExamBtn;

    @FXML
    private TableColumn<Exam, Integer> SubjectID;

    @FXML
    private TableColumn<Exam, String> SubjectName;

    @FXML
    private TableView<Exam> table;
    private ObservableList<Exam> list = FXCollections.observableArrayList(GlobalDataSaved.allExamsForPrincipal);
    private Exam examToShow = GlobalDataSaved.PrincipalExamToShow;

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        App.setRoot("principalHome");
    }

    @FXML
    void ShowExam(ActionEvent event) throws IOException{
        if (examToShow == null){
            return;
        }
        GlobalDataSaved.PrincipalExamToShow = examToShow;
        App.setRoot("principalShowExam");
    }

    @FXML
    void selectExam(MouseEvent event) {
        examToShow = table.getSelectionModel().getSelectedItem();
    }

    @FXML
    void initialize() {
        // to know that we are in exams section
        GlobalDataSaved.PrincipalQuestionToShow = null;

        ExamId.setCellValueFactory(new PropertyValueFactory<Exam,String>("exam_ID"));

        ExamTeacherID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            int teacherId = teacher.getId();
            return new SimpleIntegerProperty(teacherId).asObject();
        });

        ExamTeacherFN.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            String teacherFirstName = teacher.getFirstName();
            return new SimpleStringProperty(teacherFirstName);
        });

        ExamTeacherLN.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            String teacherLastName = teacher.getLastName();
            return new SimpleStringProperty(teacherLastName);
        });

        SubjectName.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject = exam.getExamSubject();
            String subjectName = subject.getSubjectName();
            return new SimpleStringProperty(subjectName);
        });

        SubjectID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject = exam.getExamSubject();
            int subjectId = subject.getId();
            return new SimpleIntegerProperty(subjectId).asObject();
        });

        CourseID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            int courseId = course.getId();
            return new SimpleIntegerProperty(courseId).asObject();
        });

        CourseName.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            String courseName = course.getCourseName();
            return new SimpleStringProperty(courseName);
        });

        table.setItems(list);
    }

}