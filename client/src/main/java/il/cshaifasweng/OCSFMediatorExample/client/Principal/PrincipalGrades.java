package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.WordGeneratorFile;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
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

public class PrincipalGrades {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackToMenuBtn;

    @FXML
    private TableColumn<Grade, Integer> CourseId;

    @FXML
    private TableColumn<Grade, String> CourseName;

    @FXML
    private Button CpyBtn;

    @FXML
    private TableColumn<Grade, String> ExamId;

    @FXML
    private Text NoCopyText;

    @FXML
    private TableColumn<Grade, String> StudentId;

    @FXML
    private TableColumn<Grade, String> StudentName;

    @FXML
    private TableColumn<Grade, Integer> SubjectId;

    @FXML
    private TableColumn<Grade, String> SubjectName;

    @FXML
    private TableColumn<Grade, Integer> TheGrade;

    @FXML
    private TableView<Grade> gradeTable;
    private ObservableList<Grade> list = FXCollections.observableArrayList(GlobalDataSaved.allGradesForPrincipal);
    private Grade gradeToShow;

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        //updating the global variable that the pricnipal are no longer in Grades section
        GlobalDataSaved.ThePrincipalInGrades = false;

        App.setRoot("principalHome");
    }

    @FXML
    void ShowCopy(ActionEvent event) throws IOException {
        if (gradeToShow == null){
            NoCopyText.setText("Please Select Grade!");
            return;
        }
        //updating the global variable that the pricnipal are no longer in Grades section
        GlobalDataSaved.ThePrincipalInGrades = false;

        GlobalDataSaved.currentGrade = gradeToShow;
        if (gradeToShow.getExamCopy() == null) {
            NoCopyText.setText("No Copy Exists To This Exam");
            return;
        }
        if (gradeToShow.isManuel()) {
            WordGeneratorFile.openWord(gradeToShow.getExamCopy().getAnswers());
        } else {
            String answers = gradeToShow.getExamCopy().getAnswers();
            if (answers != null) {
                List<String> answersList = new ArrayList<String>(Arrays.asList(answers.split(",")));
                GlobalDataSaved.studentAnswers = answersList;
            }
            GlobalDataSaved.examToExecute = gradeToShow.getExamCopy().getCompExamToExecute();
            Message msg = new Message("#get exam copy", gradeToShow.getId());
            SimpleClient.getClient().sendToServer(msg);        }
    }

    @FXML
    void selectgrade(MouseEvent event) {
        NoCopyText.setText("");
        if(gradeTable.getSelectionModel().getSelectedItem() == null){
            return;
        }
        gradeToShow = gradeTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    void initialize() {
        //updating the global variable that the pricnipal in Grades section
        GlobalDataSaved.ThePrincipalInGrades = true;
        GlobalDataSaved.copyToPrincipal = true;
        GlobalDataSaved.copyToTeacher = false;
        GlobalDataSaved.copyToStudent = false;
        TheGrade.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("grade"));

        StudentName.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            Student student = grade.getStudent();
            String studentFirstName = student.getFirstName();
            String studentLastName = student.getLastName();
            return new SimpleStringProperty(studentFirstName + " " + studentLastName);
        });

        StudentId.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            Student student = grade.getStudent();
            String studentId = student.getUserID();
            return new SimpleStringProperty(studentId);
        });

        SubjectName.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            Subject subject = grade.getExamCopy().getCompExamToExecute().getExam().getExamSubject();
            String subjectName = subject.getSubjectName();
            return new SimpleStringProperty(subjectName);
        });

        SubjectId.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            Subject subject = grade.getExamCopy().getCompExamToExecute().getExam().getExamSubject();
            int subjectId = subject.getId();
            return new SimpleIntegerProperty(subjectId).asObject();
        });

        CourseName.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            Course course = grade.getExamCopy().getCompExamToExecute().getExam().getExamCourse();
            String courseName = course.getCourseName();
            return new SimpleStringProperty(courseName);
        });

        CourseId.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            Course course = grade.getExamCopy().getCompExamToExecute().getExam().getExamCourse();
            int courseId = course.getId();
            return new SimpleIntegerProperty(courseId).asObject();
        });

        ExamId.setCellValueFactory(cellData -> {
            Grade grade = cellData.getValue();
            String examId = grade.getExamCopy().getCompExamToExecute().getExam().getExam_ID();
            return new SimpleStringProperty(examId);
        });

        gradeTable.setItems(list);
    }

}
