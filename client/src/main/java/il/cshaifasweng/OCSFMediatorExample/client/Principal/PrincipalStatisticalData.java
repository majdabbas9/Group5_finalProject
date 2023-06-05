/**
 * Sample Skeleton for 'principalStatisticalData.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import java.io.IOException;
import java.lang.reflect.Member;
import java.net.URL;
import java.util.ResourceBundle;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PrincipalStatisticalData {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="ErrTxt"
    private Text ErrTxt; // Value injected by FXMLLoader

    @FXML // fx:id="ExamsCourse"
    private TableColumn<Exam, String> ExamsCourse; // Value injected by FXMLLoader

    @FXML // fx:id="ExamsId"
    private TableColumn<Exam, String> ExamsId; // Value injected by FXMLLoader

    @FXML // fx:id="ExamsSubject"
    private TableColumn<Exam, String> ExamsSubject; // Value injected by FXMLLoader

    @FXML // fx:id="ExamsTeacher"
    private TableColumn<Exam, String> ExamsTeacher; // Value injected by FXMLLoader

    @FXML // fx:id="SelectItem"
    private Text SelectItem; // Value injected by FXMLLoader

    @FXML // fx:id="Exams"
    private TableView<Exam> ExamsTable; // Value injected by FXMLLoader

    @FXML // fx:id="SelectedExams"
    private TableView<Exam> SelectedExamsTable; // Value injected by FXMLLoader

    @FXML // fx:id="SelectedExamsCourse"
    private TableColumn<Exam, String> SelectedExamsCourse; // Value injected by FXMLLoader

    @FXML // fx:id="SelectedExamsId"
    private TableColumn<Exam, String> SelectedExamsId; // Value injected by FXMLLoader

    @FXML // fx:id="SelectedExamsSubject"
    private TableColumn<Exam, String> SelectedExamsSubject; // Value injected by FXMLLoader

    @FXML // fx:id="SelectedExamsTeacher"
    private TableColumn<Exam, String> SelectedExamsTeacher; // Value injected by FXMLLoader

    @FXML // fx:id="_ComboBox"
    private ComboBox<String> _ComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="_CoursesListView"
    private ListView<Course> _CoursesListView; // Value injected by FXMLLoader

    @FXML // fx:id="_StudentsListView"
    private ListView<Student> _StudentsListView; // Value injected by FXMLLoader

    @FXML // fx:id="_TeachersListView"
    private ListView<Teacher> _TeachersListView; // Value injected by FXMLLoader

    private final ObservableList<String> _ComboBoxList = FXCollections.observableArrayList("Student","Teacher","Course");
    private final ObservableList<Course> _CoursesList = FXCollections.observableList(GlobalDataSaved.allCoursesForPrincipal);
    private final ObservableList<Teacher> _TeachersList = FXCollections.observableList(GlobalDataSaved.allTeachersForPrincipal);
    private final ObservableList<Student> _StudentsList = FXCollections.observableList(GlobalDataSaved.allStudentsForPrincipal);
    private final ObservableList<Exam> _ExamsList = FXCollections.observableList(GlobalDataSaved.allExamsForPrincipal);
    private ObservableList<Exam> SelectedExams = FXCollections.observableArrayList();
    private ObservableList<Exam> Exams = FXCollections.observableArrayList();
    private String CurrentCatagory = "Course";

    @FXML
    void BackToMenu(ActionEvent event) throws IOException {
        App.setRoot("principalHome");
    }

    @FXML
    void SelectExam(MouseEvent event) {
        if (ExamsTable.getSelectionModel().getSelectedItem() != null){
            if (!SelectedExamsTable.getItems().contains(ExamsTable.getSelectionModel().getSelectedItem())) {
                SelectedExamsTable.getItems().add(ExamsTable.getSelectionModel().getSelectedItem());
                SelectedExamsTable.refresh();
            }
        }
    }

    @FXML
    void SelectTeacher(MouseEvent event) {
        if (_TeachersListView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        ExamsTable.getItems().clear();
        SelectedExamsTable.getItems().clear();
        for (Exam exam : _ExamsList){
            if (exam.getTeacherThatCreated().getUserID().equals(
                    _TeachersListView.getSelectionModel().getSelectedItem().getUserID())){
                ExamsTable.getItems().add(exam);
            }
        }
    }
    @FXML
    void SelectCourse(MouseEvent event) {
        if (_CoursesListView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        ExamsTable.getItems().clear();
        SelectedExamsTable.getItems().clear();
        for (Exam exam : _ExamsList){
            if (exam.getExamCourse().getCourseName().equals(
                    _CoursesListView.getSelectionModel().getSelectedItem().getCourseName())){
                ExamsTable.getItems().add(exam);
            }
        }
    }
    @FXML
    void SelectStudent(MouseEvent event) {
        if (_StudentsListView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        ExamsTable.getItems().clear();
        SelectedExamsTable.getItems().clear();
        for (Exam exam : _ExamsList){
            for(Grade grade : _StudentsListView.getSelectionModel().getSelectedItem().getGrades()){
                if (grade.getExamCopy().getCompExamToExecute().getExam().getExam_ID().equals(exam.getExam_ID())){
                    ExamsTable.getItems().add(exam);
                }
            }
        }
    }

    @FXML
    void Show(ActionEvent event) throws IOException {
        if (SelectedExams.size() == 0){
            ErrTxt.setText("Please Select Exams!");
            return;
        }
        if (CurrentCatagory.equals("Student")){
            GlobalDataSaved.StatisticalStudentForPrincipal = _StudentsListView.getSelectionModel().getSelectedItem();
        } else if (CurrentCatagory.equals("Teacher")) {
            GlobalDataSaved.StatisticalTeacherForPrincipal = _TeachersListView.getSelectionModel().getSelectedItem();
        }else {
            GlobalDataSaved.StatisticalCourseForPrincipal = _CoursesListView.getSelectionModel().getSelectedItem();
        }
        GlobalDataSaved.CurrentStatisticalCatagory = CurrentCatagory;
        GlobalDataSaved.allStatisticalDataExamsForPrincipal = SelectedExams;
        try {
            Message message = new Message("GetGradesForStatisticalData");
            SimpleClient.getClient().sendToServer(message);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    void UnselectExam(MouseEvent event) {
        if (SelectedExamsTable.getSelectionModel().getSelectedItem() != null){
            SelectedExamsTable.getItems().remove(SelectedExamsTable.getSelectionModel().getSelectedItem());
            SelectedExamsTable.refresh();
        }
    }

    private void Clear(){
        SelectedExamsTable.getItems().clear();
        SelectedExamsTable.refresh();
        ExamsTable.getItems().clear();
        ExamsTable.refresh();
        _CoursesListView.setVisible(false);
        _StudentsListView.setVisible(false);
        _TeachersListView.setVisible(false);
    }
    @FXML
    void SelectCatagory(ActionEvent event) {
        if(_ComboBox.getValue().equals("Student") && !_ComboBox.getValue().equals(CurrentCatagory)){
            SelectItem.setText("Select Student:");
            CurrentCatagory = "Student";
            Clear();
            _StudentsListView.setVisible(true);
        } else if (_ComboBox.getValue().equals("Course") && !_ComboBox.getValue().equals(CurrentCatagory)) {
            SelectItem.setText("Select Course:");
            CurrentCatagory = "Course";
            Clear();
            _CoursesListView.setVisible(true);
        } else if (_ComboBox.getValue().equals("Teacher") && !_ComboBox.getValue().equals(CurrentCatagory)) {
            SelectItem.setText("Select Teacher:");
            CurrentCatagory = "Teacher";
            Clear();
            _TeachersListView.setVisible(true);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        _CoursesListView.setItems(_CoursesList);
        _TeachersListView.setItems(_TeachersList);
        _StudentsListView.setItems(_StudentsList);

        ErrTxt.setText("");
        _ComboBox.setItems(_ComboBoxList);
        _ComboBox.setValue("Course");

        _TeachersListView.setVisible(false);
        _StudentsListView.setVisible(false);

        ExamsId.setCellValueFactory(new PropertyValueFactory<Exam,String>("exam_ID"));
        SelectedExamsId.setCellValueFactory(new PropertyValueFactory<Exam,String>("exam_ID"));

        ExamsTeacher.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            String teacherName = teacher.getFirstName() + teacher.getLastName();
            return new SimpleStringProperty(teacherName);
        });

        SelectedExamsTeacher.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Teacher teacher = exam.getTeacherThatCreated();
            String teacherName = teacher.getFirstName() + teacher.getLastName();
            return new SimpleStringProperty(teacherName);
        });

        ExamsSubject.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject = exam.getExamSubject();
            String subjectName = subject.getSubjectName();
            return new SimpleStringProperty(subjectName);
        });

        SelectedExamsSubject.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Subject subject = exam.getExamSubject();
            String subjectName = subject.getSubjectName();
            return new SimpleStringProperty(subjectName);
        });


        ExamsCourse.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            String courseName = course.getCourseName();
            return new SimpleStringProperty(courseName);
        });

        SelectedExamsCourse.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Course course = exam.getExamCourse();
            String courseName = course.getCourseName();
            return new SimpleStringProperty(courseName);
        });

        SelectedExamsTable.setItems(SelectedExams);
        ExamsTable.setItems(Exams);

    }

}
