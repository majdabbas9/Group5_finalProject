package il.cshaifasweng.OCSFMediatorExample.client.Principal;

import aidClasses.ExamStatistics;
import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalStatisticalReport {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="Eighties"
    private TableColumn<ExamStatistics, Integer> Eighties; // Value injected by FXMLLoader

    @FXML // fx:id="Fifties"
    private TableColumn<ExamStatistics, Integer> Fifties; // Value injected by FXMLLoader

    @FXML // fx:id="Forties"
    private TableColumn<ExamStatistics, Integer> Forties; // Value injected by FXMLLoader

    @FXML // fx:id="InfoTxt"
    private Text InfoTxt; // Value injected by FXMLLoader

    @FXML // fx:id="Nineties"
    private TableColumn<ExamStatistics, Integer> Nineties; // Value injected by FXMLLoader

    @FXML // fx:id="Seventies"
    private TableColumn<ExamStatistics, Integer> Seventies; // Value injected by FXMLLoader

    @FXML // fx:id="Sixties"
    private TableColumn<ExamStatistics, Integer> Sixties; // Value injected by FXMLLoader

    @FXML // fx:id="Thirties"
    private TableColumn<ExamStatistics, Integer> Thirties; // Value injected by FXMLLoader

    @FXML // fx:id="_Average"
    private TableColumn<ExamStatistics, Double> _Average; // Value injected by FXMLLoader

    @FXML // fx:id="_Course"
    private TableColumn<ExamStatistics, String> _Course; // Value injected by FXMLLoader

    @FXML // fx:id="_ExamID"
    private TableColumn<ExamStatistics, String> _ExamID; // Value injected by FXMLLoader

    @FXML // fx:id="_Grade"
    private TableColumn<ExamStatistics, Integer> _Grade; // Value injected by FXMLLoader

    @FXML // fx:id="_Histogram"
    private TableColumn<?, ?> _Histogram; // Value injected by FXMLLoader

    @FXML // fx:id="_Median"
    private TableColumn<ExamStatistics, Integer> _Median; // Value injected by FXMLLoader

    @FXML // fx:id="_Subject"
    private TableColumn<ExamStatistics, String> _Subject; // Value injected by FXMLLoader

    @FXML // fx:id="_Table"
    private TableView<ExamStatistics> _Table; // Value injected by FXMLLoader

    @FXML // fx:id="_TeacherName"
    private TableColumn<ExamStatistics, String> _TeacherName; // Value injected by FXMLLoader

    @FXML // fx:id="tens"
    private TableColumn<ExamStatistics, Integer> tens; // Value injected by FXMLLoader

    @FXML // fx:id="twenties"
    private TableColumn<ExamStatistics, Integer> twenties; // Value injected by FXMLLoader

    @FXML // fx:id="zero_nine"
    private TableColumn<ExamStatistics, Integer> zero_nine; // Value injected by FXMLLoader

    @FXML
    void BackToMenu(ActionEvent event) {
        try {
            Message message = new Message("StatisticalDataForPrincipal");
            SimpleClient.getClient().sendToServer(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        if (GlobalDataSaved.CurrentStatisticalCatagory.equals("Student")){
            InfoTxt.setText(InfoTxt.getText() + "The Student: "  +
                    GlobalDataSaved.StatisticalStudentForPrincipal.toString() +
                    " ( ID:" + GlobalDataSaved.StatisticalStudentForPrincipal.getUserID() +" )");
        } else if (GlobalDataSaved.CurrentStatisticalCatagory.equals("Teacher")) {
            InfoTxt.setText(InfoTxt.getText() + "The Teacher: " +
                    GlobalDataSaved.StatisticalTeacherForPrincipal.toString() +
                    " ( ID:" + GlobalDataSaved.StatisticalTeacherForPrincipal.getUserID() +" )");
        }else {
            InfoTxt.setText(InfoTxt.getText() + "The Course: " +
                    GlobalDataSaved.StatisticalCourseForPrincipal.getCourseName());
        }
        ObservableList<ExamStatistics> list = FXCollections.observableArrayList();
        for (Exam exam : GlobalDataSaved.allStatisticalDataExamsForPrincipal){
            list.add(new ExamStatistics(exam.getExam_ID(),exam.getExamSubject().getSubjectName(),
                    exam.getExamCourse().getCourseName(),exam.getTeacherThatCreated().toString()));
        }
        //adding all the grades for each exam (gathering the statistical data)
       for (Grade grade : GlobalDataSaved.allGradesForPrincipal){
           int  g = grade.getGrade();
            for (ExamStatistics examStatistics : list){
                if (grade.getExamCopy().getCompExamToExecute().getExam().getExam_ID().equals(examStatistics.getExam_Id())){
                    examStatistics.getAllGrades().add(grade.getGrade());
                    Integer[] histogram = examStatistics.getHistogram();
                   if (0 <= g && g <= 9){
                       histogram[0]++;
                       examStatistics.setHistogram(histogram);
                   }
                   if (10 <= g && g <= 19){
                       histogram[1]++;
                       examStatistics.setHistogram(histogram);
                   }
                   if (20 <= g && g <= 29){
                       histogram[2]++;
                       examStatistics.setHistogram(histogram);
                   }
                   if (30 <= g && g <= 39){
                       histogram[3]++;
                       examStatistics.setHistogram(histogram);
                   }
                   if (40 <= g && g <= 49){
                       histogram[4]++;
                       examStatistics.setHistogram(histogram);
                   }
                   if (50 <= g && g <= 59){
                       histogram[5]++;
                       examStatistics.setHistogram(histogram);
                   }
                   if (60 <= g && g <= 69){
                       histogram[6]++;
                       examStatistics.setHistogram(histogram);
                   }
                   if (70 <= g && g <= 79){
                       histogram[7]++;
                       examStatistics.setHistogram(histogram);
                   }
                   if (80 <= g && g <= 89){
                       histogram[8]++;
                       examStatistics.setHistogram(histogram);
                   }
                   if (90 <= g && g <= 100){
                       histogram[9]++;
                       examStatistics.setHistogram(histogram);
                   }
                   if (GlobalDataSaved.CurrentStatisticalCatagory.equals("Student") &&
                            grade.getStudent().getUserID().equals(
                                    GlobalDataSaved.StatisticalStudentForPrincipal.getUserID())){
                        examStatistics.setGrade(grade.getGrade());
                   }
                }
            }
       }

       //Calculating the median
       for (ExamStatistics examStatistics : list){
           examStatistics.setMedian();
           examStatistics.setAverage();
       }

       _Average.setCellValueFactory(new PropertyValueFactory<ExamStatistics,Double>("Average"));
       _ExamID.setCellValueFactory(new PropertyValueFactory<ExamStatistics,String>("Exam_Id"));
       _Subject.setCellValueFactory(new PropertyValueFactory<ExamStatistics,String>("Subject_Name"));
       _Course.setCellValueFactory(new PropertyValueFactory<ExamStatistics,String>("Course_Name"));
       _TeacherName.setCellValueFactory(new PropertyValueFactory<ExamStatistics,String>("Teacher_Name"));
       _Median.setCellValueFactory(new PropertyValueFactory<ExamStatistics,Integer>("Median"));

       if (GlobalDataSaved.CurrentStatisticalCatagory.equals("Student")){
           _Grade.setCellValueFactory(new PropertyValueFactory<ExamStatistics,Integer>("grade"));
       }else {
           _Grade.setVisible(false); //no grade to show.
       }

        zero_nine.setCellValueFactory(cellData -> {
            ExamStatistics examStatistics= cellData.getValue();
            Integer count = examStatistics.getHistogram()[0];
            return new SimpleIntegerProperty(count).asObject();
        });

        tens.setCellValueFactory(cellData -> {
            ExamStatistics examStatistics= cellData.getValue();
            Integer count = examStatistics.getHistogram()[1];
            return new SimpleIntegerProperty(count).asObject();
        });

        twenties.setCellValueFactory(cellData -> {
            ExamStatistics examStatistics= cellData.getValue();
            Integer count =  examStatistics.getHistogram()[2];
            return new SimpleIntegerProperty(count).asObject();
        });

        Thirties.setCellValueFactory(cellData -> {
            ExamStatistics examStatistics= cellData.getValue();
            Integer count = examStatistics.getHistogram()[3];
            return new SimpleIntegerProperty(count).asObject();
        });

        Forties.setCellValueFactory(cellData -> {
            ExamStatistics examStatistics= cellData.getValue();
            Integer count = examStatistics.getHistogram()[4];
            return new SimpleIntegerProperty(count).asObject();
        });

        Fifties.setCellValueFactory(cellData -> {
            ExamStatistics examStatistics= cellData.getValue();
            Integer count = examStatistics.getHistogram()[5];
            return new SimpleIntegerProperty(count).asObject();
        });

        Sixties.setCellValueFactory(cellData -> {
            ExamStatistics examStatistics = cellData.getValue();
            Integer count = examStatistics.getHistogram()[6];
            return new SimpleIntegerProperty(count).asObject();
        });

        Seventies.setCellValueFactory(cellData -> {
            ExamStatistics examStatistics= cellData.getValue();
            Integer count = examStatistics.getHistogram()[7];
            return new SimpleIntegerProperty(count).asObject();
        });

        Eighties.setCellValueFactory(cellData -> {
            ExamStatistics examStatistics= cellData.getValue();
            Integer count = examStatistics.getHistogram()[8];
            return new SimpleIntegerProperty(count).asObject();
        });

        Nineties.setCellValueFactory(cellData -> {
            ExamStatistics examStatistics= cellData.getValue();
            Integer count = examStatistics.getHistogram()[9];
            return new SimpleIntegerProperty(count).asObject();
        });

        _Table.setItems(list);
    }

}
