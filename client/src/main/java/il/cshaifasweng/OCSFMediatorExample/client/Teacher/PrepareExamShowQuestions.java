package il.cshaifasweng.OCSFMediatorExample.client.Teacher;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import aidClasses.aidClassesForTeacher.DisplayQuestion;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.WordGeneratorFile;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ManualExamToExecute;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrepareExamShowQuestions {

    @FXML
    private Button buttonBack;

    @FXML
    private TextField codeLablel;

    @FXML
    private DatePicker date;

    @FXML
    private TableView<Exam_Question> examQuestionTable;

    @FXML
    private Text examQuestionsText;

    @FXML
    private ComboBox<String> hourList;

    @FXML
    private ComboBox<String> minuteList;

    @FXML
    private TableColumn<Exam_Question, Integer> pointsColumn;

    @FXML
    private TableColumn<Exam_Question, String> theQuestionColumn;
    @FXML
    private TableColumn<Exam_Question, String> correctAnswerColumn;
    @FXML
    private TableColumn<Exam_Question, String> quesionIdColumn;

    @FXML
    private Text warningTxt;

    @FXML
    private ComboBox<String> wayOfExecution;

    @FXML
    void backToExams(ActionEvent event) {
        try {
            Message msg = new Message("#showAllExamsForTeacher",GlobalDataSaved.connectedUser.getId()); // creating a msg to the server demanding the students
            SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
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
        try
        {
            String compDate=dateOfExam+=" "+hourList.getSelectionModel().getSelectedItem()+":"+minuteList.getSelectionModel().getSelectedItem();
            if(!CompareDates.occurBeforeNow(compDate))
            {
                warningTxt.setText("illegal date of exam");
                return;
            }
        }
        catch (ParseException parseException)
        {
            System.out.println(parseException.getMessage());
        }

        dateOfExam+=" "+hourList.getSelectionModel().getSelectedItem()+":"+minuteList.getSelectionModel().getSelectedItem();
        if(wayOfExecution.getSelectionModel().getSelectedItem().equals("computerized"))
        {
            List<Object> dataToServer=new ArrayList<>();
            ComputerizedExamToExecute compExam=new ComputerizedExamToExecute(dateOfExam,code);
            dataToServer.add(compExam);dataToServer.add(GlobalDataSaved.connectedUser.getId());dataToServer.add(PrepareExam.selectedExam.getId());
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
            String fileName=PrepareExam.selectedExam.getExam_ID();
            fileName+=GlobalDataSaved.connectedUser.getUserID()+code;
            ManualExamToExecute manualExamToExecute=new ManualExamToExecute(dateOfExam,code,fileName);
            List<Object> dataToServer=new ArrayList<>();
            dataToServer.add(manualExamToExecute);dataToServer.add(GlobalDataSaved.connectedUser.getId());dataToServer.add(PrepareExam.selectedExam.getId());
            Message msg = new Message("#addManualExam", dataToServer); // creating a msg to the server demanding the students
            try {
                SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            WordGeneratorFile.createWord(new ArrayList<>(GlobalDataSaved.examQuestionsToShowPrepare),PrepareExam.selectedExam.getPoints()
                    ,PrepareExam.selectedExam.getExamCourse().getCourseName(),GlobalDataSaved.connectedUser.getFirstName()+" "+
                            GlobalDataSaved.connectedUser.getLastName(),fileName);
        }
    }
    @FXML
    void initialize()
    {
        date.setValue(LocalDate.now());
        wayOfExecution.getItems().add("computerized");
        wayOfExecution.getItems().add("manual");
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
       hourList.getSelectionModel().select(CompareDates.getCurrentTimeHour());
        minuteList.getSelectionModel().select(CompareDates.getCurrentTimeMinute());

        ObservableList<Exam_Question> observableList = FXCollections.observableArrayList();

        quesionIdColumn.setCellValueFactory(cellData -> {
            Exam_Question exam_question = cellData.getValue();
            return new SimpleStringProperty(exam_question.getQuestion().getQuestionID());
        });
        quesionIdColumn.setStyle("-fx-alignment: CENTER;");

        theQuestionColumn.setCellValueFactory(cellData -> {
            Exam_Question exam_question = cellData.getValue();
            return new SimpleStringProperty(exam_question.getQuestion().getStudentNotes());
        });
        theQuestionColumn.setStyle("-fx-alignment: CENTER;");

        correctAnswerColumn.setCellValueFactory(cellData -> {
            Exam_Question exam_question = cellData.getValue();
            return new SimpleStringProperty(exam_question.getQuestion().getCorrectChoice());
        });
        correctAnswerColumn.setStyle("-fx-alignment: CENTER;");

        pointsColumn.setCellValueFactory(new PropertyValueFactory<Exam_Question,Integer>("points"));
        pointsColumn.setStyle("-fx-alignment: CENTER;");

       observableList.setAll(GlobalDataSaved.examQuestionsToShowPrepare);
       examQuestionTable.setItems(observableList);
    }

}