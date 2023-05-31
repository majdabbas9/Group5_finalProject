package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import javafx.event.ActionEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javafx.scene.input.MouseEvent;


public class PrincipalAddUsers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddBtn;

    @FXML
    private ListView<Course> CoursesList;

    @FXML
    private TextField IDText;

    @FXML
    private TextField PasswordText;
    @FXML
    private Button BackBtn;
    @FXML
    private ComboBox<String> StudnetOrTeacherBox;
    @FXML
    private Text errortxt;
    @FXML
    private TextField firstnametxt;
    @FXML
    private TextField lastnametxt;
    @FXML
    private TextField usernametxt;
    @FXML
    private ListView<Subject> SubjectsList;

    @FXML
    private ListView<Course> selectedCoursesList;

    @FXML
    private ListView<Subject> selectedSubjectsList;
    private List<Course> selectedCourses;
    private List<Subject> selectedSubjects;

    ObservableList<String> Roles = FXCollections.observableArrayList("Student", "Teacher");

    @FXML
    void initialize() throws IOException {
        SubjectsList.setItems(GlobalDataSaved.subjects);
        StudnetOrTeacherBox.setValue("Student");
        StudnetOrTeacherBox.setItems(Roles);
    }

    @FXML
    void BackToMenu() throws IOException {
        App.setRoot("principalHome");
    }

    @FXML
    void Add(ActionEvent event) {
        errortxt.setText("");
        if (firstnametxt.getText().equals("")) {
            errortxt.setText("Pleas enter First Name");
            return;
        }
        for (int i = 0; i < firstnametxt.getText().length(); i++) {
            char c = firstnametxt.getText().charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                errortxt.setText("First Name must only contain letters");
                return;

            }
        }
        if (lastnametxt.getText().equals("")) {
            errortxt.setText("Pleas enter First Name");
            return;
        }
        for (int i = 0; i < lastnametxt.getText().length(); i++) {
            char c = lastnametxt.getText().charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                errortxt.setText("Last Name must only contain letters");
                return;

            }

        }
        if (IDText.getText().equals("")) {
            errortxt.setText("Please enter ID");
            return;
        }
        for (int i = 0; i < IDText.getText().length(); i++) {
            if (IDText.getText().charAt(i) > '9' || IDText.getText().charAt(i) < '0') {
                errortxt.setText("ID must be only numbers");
                return;
            }
        }

        if (PasswordText.getText().equals("")) {
            errortxt.setText("Please enter Password");
            return;
        }

        Message msg = new Message("CheckID", IDText.getText());
        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (!GlobalDataSaved.AddFlag) {
            errortxt.setText("User already in the system");
            return;
        }

        String username = usernametxt.getText();
        String firstname = firstnametxt.getText();
        String lastname = lastnametxt.getText();
        String password = PasswordText.getText();
        String id = IDText.getText();

        if (StudnetOrTeacherBox.getValue().equals("Student")) {
            Student student = new Student(id, password, username, firstname, lastname);
            List<Object> dataToServer=new ArrayList<>();
            dataToServer.add(student);dataToServer.add(selectedSubjects);dataToServer.add(selectedCourses);
            Message msg2 = new Message("#add student", dataToServer);
            try {
                SimpleClient.getClient().sendToServer(msg2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Teacher teacher = new Teacher(id, password, username, firstname, lastname);
            List<Object> dataToServer=new ArrayList<>();
            dataToServer.add(teacher);dataToServer.add(selectedSubjects);dataToServer.add(selectedCourses);
            Message msg3 = new Message("#add teacher", dataToServer);
            try {
                SimpleClient.getClient().sendToServer(msg3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void selectCourse() {
        if (CoursesList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (selectedCourses == null) {
            selectedCourses = new ArrayList<>();
        }
        if (!selectedCourses.contains(CoursesList.getSelectionModel().getSelectedItem())) {
            selectedCourses.add(CoursesList.getSelectionModel().getSelectedItem());
            selectedCoursesList.getItems().add(CoursesList.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    void deleteCourse(MouseEvent event) {
        if (selectedCoursesList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (selectedCourses == null) {
            return;
        }
        if (selectedCourses.contains(selectedCoursesList.getSelectionModel().getSelectedItem())) {
            selectedCourses.remove(selectedCoursesList.getSelectionModel().getSelectedItem());
            selectedCoursesList.getItems().remove(selectedCoursesList.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void deleteSubject(MouseEvent event) {
        if (selectedSubjectsList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (selectedSubjects == null) {
            return;
        }
        if (selectedSubjects.contains(selectedSubjectsList.getSelectionModel().getSelectedItem())) {
            for (Course course : selectedSubjectsList.getSelectionModel().getSelectedItem().getSubjectCourses()) {
                CoursesList.getItems().remove(course);

                if (selectedCourses == null) {
                    continue;
                }
                if (selectedCourses.contains(course)) {
                    selectedCourses.remove(course);
                    selectedCoursesList.getItems().remove(course);
                }
            }
            selectedSubjects.remove(selectedSubjectsList.getSelectionModel().getSelectedItem());
            selectedSubjectsList.getItems().remove(selectedSubjectsList.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void selectSubject(MouseEvent event) {
        if (SubjectsList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (selectedSubjects == null) {
            selectedSubjects = new ArrayList<>();
        }
        if (!selectedSubjects.contains(SubjectsList.getSelectionModel().getSelectedItem())) {
            selectedSubjects.add(SubjectsList.getSelectionModel().getSelectedItem());
            selectedSubjectsList.getItems().add(SubjectsList.getSelectionModel().getSelectedItem());
            for (Course course : SubjectsList.getSelectionModel().getSelectedItem().getSubjectCourses()) {
                CoursesList.getItems().add(course);
            }
        }
    }

}