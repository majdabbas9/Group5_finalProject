package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
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
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllExams {

    @FXML
    private TableColumn<Exam, Integer> ExamShownCourseID;
    @FXML
    private TableColumn<Exam, String> ExamShownCourseName;
    @FXML
    private TableColumn<Exam, String> ExamShownId;
    @FXML
    private TableColumn<Exam, String> ExamShownSubjectName;
    @FXML
    private TableColumn<Exam, String> ExamShownTeacherFN;
    @FXML
    private TableColumn<Exam, Integer> ExamShownTeacherID;
    @FXML
    private TableColumn<Exam, String> ExamShownTeacherLN;
    @FXML
    private TableColumn<Exam, Integer> ExamShownSubjectID;
    @FXML
    private TableView<Exam> ShownExamTable;
    @FXML
    private Text ErrTxt;

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
    private TableColumn<Exam, Integer> SubjectID;

    @FXML
    private TableColumn<Exam, String> SubjectName;

    @FXML
    private TableView<Exam> table;
    private ObservableList<Exam> list = FXCollections.observableArrayList();

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        App.setRoot("teacherHome");
    }

    @FXML
    void ShowExam(ActionEvent event) throws IOException {
        ErrTxt.setText("");
        if(table.getSelectionModel().getSelectedItem()==null)
        {
            ErrTxt.setText("please selected exam");
            return;
        }
        GlobalDataSaved.selectedExamToCopy=table.getSelectionModel().getSelectedItem();
        List<Object> dataToServer=new ArrayList<>();
        dataToServer.add(table.getSelectionModel().getSelectedItem().getExamSubject().getId());
        dataToServer.add(table.getSelectionModel().getSelectedItem().getExamCourse().getId());
        Message msg1 = new Message("#allQuestionForExamsForTeacherToCopy",dataToServer); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg1); // sending the msg to the server;
    }
    @FXML
    void initialize()
    {
        list.addAll(GlobalDataSaved.allExamsForTeacherToCopy);
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
