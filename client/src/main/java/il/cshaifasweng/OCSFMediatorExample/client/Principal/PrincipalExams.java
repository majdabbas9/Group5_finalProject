package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import java.io.IOException;


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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PrincipalExams {
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
    private ObservableList<Exam> list = FXCollections.observableArrayList(GlobalDataSaved.allExamsForPrincipal);
    private Exam examToShow = GlobalDataSaved.PrincipalExamToShow;

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        //update that the principal are not in the exam section anymore
        GlobalDataSaved.ThePrincipalInExams = false;
        App.setRoot("principalHome");
    }

    @FXML
    void ShowExam(ActionEvent event) throws IOException{
        if (examToShow == null){
            ErrTxt.setText("Please Select Exam!");
            return;
        }
        ErrTxt.setText("");
        //update that the principal are not in the exam section anymore
        GlobalDataSaved.ThePrincipalInExams = false;
        Message msg = new Message("#showExamQuestionForPrincipal",examToShow.getId());
        try {
            SimpleClient.getClient().sendToServer(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void selectExam(MouseEvent event) {
        if(table.getSelectionModel().getSelectedItem() == null){
            return;
        }
        examToShow = table.getSelectionModel().getSelectedItem();
        GlobalDataSaved.PrincipalExamToShow = examToShow;
        UpdateShownExamTable();
    }

    @FXML
    void initialize() {
        //Update that we are in the Exam section
        GlobalDataSaved.ThePrincipalInExams =true;
        // to know that we are in exams section
        GlobalDataSaved.PrincipalQuestionToShow = null;

        //updating selected exam table
        if (examToShow != null){
            UpdateShownExamTable();
        }

        //initializing the table of all exams.
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

    private void UpdateShownExamTable(){
        ObservableList<Exam> list1 = FXCollections.observableArrayList();
        list1.add(examToShow);
        ExamShownId.setCellValueFactory(new PropertyValueFactory<Exam,String>("exam_ID"));

        ExamShownTeacherID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            int teacherId = teacher.getId();
            return new SimpleIntegerProperty(teacherId).asObject();
        });

        ExamShownTeacherFN.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            String teacherFirstName = teacher.getFirstName();
            return new SimpleStringProperty(teacherFirstName);
        });

        ExamShownTeacherLN.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            String teacherLastName = teacher.getLastName();
            return new SimpleStringProperty(teacherLastName);
        });

        ExamShownSubjectName.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject = exam.getExamSubject();
            String subjectName = subject.getSubjectName();
            return new SimpleStringProperty(subjectName);
        });

        ExamShownSubjectID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject = exam.getExamSubject();
            int subjectId = subject.getId();
            return new SimpleIntegerProperty(subjectId).asObject();
        });

        ExamShownCourseID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            int courseId = course.getId();
            return new SimpleIntegerProperty(courseId).asObject();
        });

        ExamShownCourseName.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            String courseName = course.getCourseName();
            return new SimpleStringProperty(courseName);
        });

        ShownExamTable.getItems().clear();
        ShownExamTable.setItems(list1);
        ShownExamTable.refresh();
    }
}