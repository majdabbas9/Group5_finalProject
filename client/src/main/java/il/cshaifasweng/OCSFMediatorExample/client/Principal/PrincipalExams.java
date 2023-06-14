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
        ExamId.setStyle("-fx-alignment: CENTER;");

        ExamTeacherID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            int teacherId = teacher.getId();
            return new SimpleIntegerProperty(teacherId).asObject();
        });
        ExamTeacherID.setStyle("-fx-alignment: CENTER;");

        ExamTeacherFN.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            String teacherFirstName = teacher.getFirstName();
            return new SimpleStringProperty(teacherFirstName);
        });
        ExamTeacherFN.setStyle("-fx-alignment: CENTER;");

        ExamTeacherLN.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            String teacherLastName = teacher.getLastName();
            return new SimpleStringProperty(teacherLastName);
        });
        ExamTeacherLN.setStyle("-fx-alignment: CENTER;");

        SubjectName.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject = exam.getExamSubject();
            String subjectName = subject.getSubjectName();
            return new SimpleStringProperty(subjectName);
        });
        SubjectName.setStyle("-fx-alignment: CENTER;");

        SubjectID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject = exam.getExamSubject();
            int subjectId = subject.getId();
            return new SimpleIntegerProperty(subjectId).asObject();
        });
        SubjectID.setStyle("-fx-alignment: CENTER;");

        CourseID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            int courseId = course.getId();
            return new SimpleIntegerProperty(courseId).asObject();
        });
        CourseID.setStyle("-fx-alignment: CENTER;");

        CourseName.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            String courseName = course.getCourseName();
            return new SimpleStringProperty(courseName);
        });
        CourseName.setStyle("-fx-alignment: CENTER;");

        table.setItems(list);
    }

    private void UpdateShownExamTable(){
        ObservableList<Exam> list1 = FXCollections.observableArrayList();
        list1.add(examToShow);
        ExamShownId.setCellValueFactory(new PropertyValueFactory<Exam,String>("exam_ID"));
        ExamShownId.setStyle("-fx-alignment: CENTER;");

        ExamShownTeacherID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            int teacherId = teacher.getId();
            return new SimpleIntegerProperty(teacherId).asObject();
        });
        ExamShownTeacherID.setStyle("-fx-alignment: CENTER;");

        ExamShownTeacherFN.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            String teacherFirstName = teacher.getFirstName();
            return new SimpleStringProperty(teacherFirstName);
        });
        ExamShownTeacherFN.setStyle("-fx-alignment: CENTER;");

        ExamShownTeacherLN.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            String teacherLastName = teacher.getLastName();
            return new SimpleStringProperty(teacherLastName);
        });
        ExamShownTeacherLN.setStyle("-fx-alignment: CENTER;");

        ExamShownSubjectName.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject = exam.getExamSubject();
            String subjectName = subject.getSubjectName();
            return new SimpleStringProperty(subjectName);
        });
        ExamShownSubjectName.setStyle("-fx-alignment: CENTER;");

        ExamShownSubjectID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject = exam.getExamSubject();
            int subjectId = subject.getId();
            return new SimpleIntegerProperty(subjectId).asObject();
        });
        ExamShownSubjectID.setStyle("-fx-alignment: CENTER;");

        ExamShownCourseID.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            int courseId = course.getId();
            return new SimpleIntegerProperty(courseId).asObject();
        });
        ExamShownCourseID.setStyle("-fx-alignment: CENTER;");

        ExamShownCourseName.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            String courseName = course.getCourseName();
            return new SimpleStringProperty(courseName);
        });
        ExamShownCourseName.setStyle("-fx-alignment: CENTER;");

        ShownExamTable.getItems().clear();
        ShownExamTable.setItems(list1);
        ShownExamTable.refresh();
    }
}