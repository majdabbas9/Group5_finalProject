package il.cshaifasweng.OCSFMediatorExample.client.Student;


import aidClasses.GlobalDataSaved;
import aidClasses.GradesDetails;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.WordGeneratorFile;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ManualExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
public class StudentGrades {

    @FXML
    private Button backToHome;

    @FXML
    private Button getCopy;

    @FXML
    private Text warningtxt;

    @FXML
    private TableView<GradesDetails> studentGradesTableView;
    @FXML
    private TableColumn<GradesDetails, Integer> gradeNo;

    @FXML
    private TableColumn<GradesDetails, String> subject;
    @FXML
    private TableColumn<GradesDetails, String> course;
    @FXML
    private TableColumn<GradesDetails, String> teacherName;
    @FXML
    private TableColumn<GradesDetails,Integer> grade;

    private List<GradesDetails> gradesDetails = new ArrayList<>();

    @FXML
    void backToHome(ActionEvent event) throws IOException {
        App.setRoot("studentHome");
    }

    @FXML
    void getCopy(ActionEvent event) throws IOException {
        ExamToExecute examToExecute=studentGradesTableView.getSelectionModel().getSelectedItem().getGradeObject().getExamCopy().getCompExamToExecute();
        if(examToExecute.getClass().equals(ManualExamToExecute.class))
        {
            WordGeneratorFile.openWord(WordGeneratorFile.examsPath+((ManualExamToExecute)examToExecute).getFileName());
            return;
        }
        GlobalDataSaved.selectedGradeForExamCopy = studentGradesTableView.getSelectionModel().getSelectedIndex();
        List<Object> objects = new ArrayList<>();
        objects.add(0, GlobalDataSaved.selectedGradeForExamCopy);
        objects.add(1, GlobalDataSaved.connectedUser.getId());
        Message msg = new Message("#get exam copy",objects);
        SimpleClient.getClient().sendToServer(msg);
        App.setRoot("examStudentNotes");
    }

    @FXML
    public void initialize() {
        assert backToHome != null : "fx:id=\"backToHome\" was not injected: check your FXML file 'studentGrades.fxml'.";
        assert course != null : "fx:id=\"course\" was not injected: check your FXML file 'studentGrades.fxml'.";
        assert getCopy != null : "fx:id=\"getCopy\" was not injected: check your FXML file 'studentGrades.fxml'.";
        assert grade != null : "fx:id=\"grade\" was not injected: check your FXML file 'studentGrades.fxml'.";
        assert gradeNo != null : "fx:id=\"gradeNo\" was not injected: check your FXML file 'studentGrades.fxml'.";
        assert studentGradesTableView != null : "fx:id=\"studentGradesTableView\" was not injected: check your FXML file 'studentGrades.fxml'.";
        assert subject != null : "fx:id=\"subject\" was not injected: check your FXML file 'studentGrades.fxml'.";
        assert teacherName != null : "fx:id=\"teacherName\" was not injected: check your FXML file 'studentGrades.fxml'.";
        assert warningtxt != null : "fx:id=\"warningtxt\" was not injected: check your FXML file 'studentGrades.fxml'.";

        gradesDetails = buildGradeDetailsList(GlobalDataSaved.gradeList);

        ObservableList<GradesDetails> observableList = FXCollections.observableArrayList();
        for (GradesDetails g : gradesDetails) {
            observableList.add(g);
        }
        gradeNo.setCellValueFactory(new PropertyValueFactory<GradesDetails,Integer>("id"));
        subject.setCellValueFactory(new PropertyValueFactory<GradesDetails,String>("subjectName"));
        course.setCellValueFactory(new PropertyValueFactory<GradesDetails,String>("courseName"));
        teacherName.setCellValueFactory(new PropertyValueFactory<GradesDetails, String>("teacherName"));
        grade.setCellValueFactory(new PropertyValueFactory<GradesDetails,Integer>("grade"));

        studentGradesTableView.setItems(observableList);
    }

    public List<GradesDetails> buildGradeDetailsList(List<Grade> grades)
    {
        List<GradesDetails> gradesDetails = new ArrayList<>();
        String gradeSubject,gradeCourse,teacherFirstNameExecuted,teacherLastNameExecuted;
       int count = 0;
        for (Grade g : grades){
            count++;
            gradeSubject=g.getExamCopy().getCompExamToExecute().getExam().getExamSubject().getSubjectName();
            gradeCourse=g.getExamCopy().getCompExamToExecute().getExam().getExamCourse().getCourseName();
            teacherFirstNameExecuted=g.getExamCopy().getCompExamToExecute().getTeacherThatExecuted().getFirstName();
            teacherLastNameExecuted=g.getExamCopy().getCompExamToExecute().getTeacherThatExecuted().getLastName();
            gradesDetails.add(new GradesDetails(count,gradeSubject,gradeCourse,teacherFirstNameExecuted+" "+teacherLastNameExecuted,g.getGrade(),g));
        }
        return gradesDetails;
    }


}


