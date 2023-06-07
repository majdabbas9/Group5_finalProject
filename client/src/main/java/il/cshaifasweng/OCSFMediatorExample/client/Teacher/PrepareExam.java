/**
 * Sample Skeleton for 'prepareExamForExecution.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.aidClassesForTeacher.DisplayQuestion;
import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.WordGeneratorFile;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ManualExamToExecute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrepareExam {

    @FXML
    private Text examQuestionsText;
    @FXML // fx:id="PointsColumn"
    private TableColumn<DisplayQuestion, Integer> PointsColumn; // Value injected by FXMLLoader

    @FXML // fx:id="buttonBack"
    private Button buttonBack; // Value injected by FXMLLoader
    @FXML
    private TextField codeLablel;

    @FXML // fx:id="date"
    private DatePicker date; // Value injected by FXMLLoader

    @FXML // fx:id="examIdColumn"
    private TableColumn<Exam, String> examIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="examQuestionTable"
    private TableView<DisplayQuestion> examQuestionTable; // Value injected by FXMLLoader

    @FXML // fx:id="examTable"
    private TableView<Exam> examTable; // Value injected by FXMLLoader

    @FXML // fx:id="examTimeColumn"
    private TableColumn<Exam, Integer> examTimeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="hourList"
    private ComboBox<String> hourList; // Value injected by FXMLLoader

    @FXML // fx:id="minuteList"
    private ComboBox<String> minuteList; // Value injected by FXMLLoader

    @FXML // fx:id="subjectList"
    private ComboBox<String> wayOfExecution;; // Value injected by FXMLLoader

    @FXML // fx:id="theQuestionColumn"
    private TableColumn<DisplayQuestion, String> theQuestionColumn; // Value injected by FXMLLoader
    @FXML // fx:id="pointsColumn"
    private TableColumn<DisplayQuestion, Integer> pointsColumn; // Value injected by FXMLLoader

    @FXML // fx:id="warningTxt"
    private Text warningTxt; // Value injected by FXMLLoader

    @FXML // fx:id="wordFileButton"
    private Button wordFileButton; // Value injected by FXMLLoader
    private Teacher theTeacher;
    private ObservableList<DisplayQuestion> observableListQuestions;

    @FXML
    void attachWordFile(ActionEvent event) {

    }

    @FXML
    void backToTeacherHome(ActionEvent event) throws IOException {
        App.setRoot("teacherHome");
    }

    @FXML
    void displayQuestions(MouseEvent event) {
        examQuestionsText.setVisible(true);
        examQuestionTable.setVisible(true);
        observableListQuestions.clear();
        int i=0;
        Exam exam=examTable.getSelectionModel().getSelectedItem();
        List<Exam_Question> examQuestion=new ArrayList<>(exam.getExamQuestions());
        List<Integer> examPoints=new ArrayList<>(exam.getPoints());
        for(Exam_Question exam_question:examQuestion)
        {
            observableListQuestions.add(new DisplayQuestion(exam_question.getQuestion().getStudentNotes(),examPoints.get(i++)));
        }
        examQuestionTable.setItems(observableListQuestions);
    }

    @FXML
    void submitExam(ActionEvent event) throws IOException {
        warningTxt.setText("");
        if(wayOfExecution.getSelectionModel().getSelectedItem()==null)
        {
            warningTxt.setText("please pick a way of exam execution");
            return;
        }
        if(hourList.getSelectionModel().getSelectedItem()==null)
        {
            warningTxt.setText("please pick an hour for the exam");
            return;
        }
        if(minuteList.getSelectionModel().getSelectedItem()==null)
        {
            warningTxt.setText("please pick an minute for the exam");
            return;
        }
        if(date.getValue()==null)
        {
            warningTxt.setText("please pick an date for  the exam");
            return;
        }
        if(codeLablel.getText().equals(""))
        {
            warningTxt.setText("please pick a code for the exam");
            return;
        }
        if(codeLablel.getText().length()!=4)
        {
            warningTxt.setText("illegal exam code");
            return;
        }
        int code=0;
        try {
            code=Integer.valueOf(codeLablel.getText());
        }
        catch (Exception ex)
        {
            warningTxt.setText("illegal exam code");
            return;
        }
        String dateOfExam=String.valueOf(date.getValue());
        dateOfExam+=" "+hourList.getSelectionModel().getSelectedItem()+":"+minuteList.getSelectionModel().getSelectedItem();
        if(wayOfExecution.getSelectionModel().getSelectedItem().equals("computerized"))
        {
            List<Object> dataToServer=new ArrayList<>();
               ComputerizedExamToExecute compExam=new ComputerizedExamToExecute(dateOfExam,code);
            dataToServer.add(compExam);dataToServer.add(GlobalDataSaved.connectedUser.getId());dataToServer.add(examTable.getSelectionModel().getSelectedItem().getId());
            Message msg = new Message("#addCompExam", dataToServer); // creating a msg to the server demanding the students
            try {
                SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        else
        {
            String fileName=examTable.getSelectionModel().getSelectedItem().getExam_ID();
            fileName+=GlobalDataSaved.connectedUser.getUserID();
            ManualExamToExecute manualExamToExecute=new ManualExamToExecute(dateOfExam,code,fileName);
            List<Object> dataToServer=new ArrayList<>();
            dataToServer.add(manualExamToExecute);dataToServer.add(GlobalDataSaved.connectedUser.getId());dataToServer.add(examTable.getSelectionModel().getSelectedItem().getId());
            Message msg = new Message("#addManualExam", dataToServer); // creating a msg to the server demanding the students
            try {
                SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            WordGeneratorFile.createWord(new ArrayList<>(examTable.getSelectionModel().getSelectedItem().getExamQuestions()),examTable.getSelectionModel().getSelectedItem().getPoints()
            ,examTable.getSelectionModel().getSelectedItem().getExamCourse().getCourseName(),GlobalDataSaved.connectedUser.getFirstName()+" "+
                            GlobalDataSaved.connectedUser.getLastName(),fileName);
        }
    }
    @FXML
    void onSortExam(ActionEvent event) {

    }
    @FXML
    public void initialize()
    {
        examQuestionsText.setVisible(false);
       examQuestionTable.setVisible(false);
        wayOfExecution.getItems().add("computerized");
        wayOfExecution.getItems().add("manual");
        theTeacher= (Teacher) GlobalDataSaved.connectedUser;
        hourList.getItems().clear();
        minuteList.getItems().clear();
        for(int i=0;i<=59;i++)
        {
            if(i<=23)
            {
                if(i<10)
                {
                    hourList.getItems().add("0"+String.valueOf(i));
                    minuteList.getItems().add("0"+String.valueOf(i));
                }
                else
                {
                    hourList.getItems().add(String.valueOf(i));
                    minuteList.getItems().add(String.valueOf(i));
                }
            }
            else
            {
                minuteList.getItems().add(String.valueOf(i));
            }

        }

        ObservableList<Exam> observableList = FXCollections.observableArrayList();
        examIdColumn.setCellValueFactory(new PropertyValueFactory<Exam,String>("exam_ID"));
        examTimeColumn.setCellValueFactory(new PropertyValueFactory<Exam,Integer>("time"));
        for(Exam exam:GlobalDataSaved.allExamsForTeacher)
        {
            observableList.add(exam);
        }
        examTable.setItems(observableList);
        observableListQuestions=FXCollections.observableArrayList();
        theQuestionColumn.setCellValueFactory(new PropertyValueFactory<DisplayQuestion,String>("theQuestion"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<DisplayQuestion,Integer>("points"));
    }

}
