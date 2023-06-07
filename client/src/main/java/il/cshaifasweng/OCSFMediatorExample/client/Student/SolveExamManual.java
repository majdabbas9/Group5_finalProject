package il.cshaifasweng.OCSFMediatorExample.client.Student;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.WordGeneratorFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SolveExamManual {
    private int hour=0, minute=0, second=0;
    private String ddHour, ddMinute, ddSecond;

    private DecimalFormat decimalFormat = new DecimalFormat("00");
    private Timer timer;

    @FXML
    private Text examTimer;

    @FXML
    private Text questionContent;

    @FXML
    private Button submitBtn;

    @FXML
    private Button wordFile;

    @FXML
    void getWordFile(ActionEvent event) throws IOException {
        WordGeneratorFile.openWord(GlobalDataSaved.examToExecute.getExam().getExam_ID()+
                GlobalDataSaved.examToExecute.getExam().getTeacherThatCreated().getUserID()+GlobalDataSaved.connectedUser.getId());
        examCountDownTimer();
    }
    private void sendStudentManualAnswerToServer(boolean onTime , int grade) throws IOException {
        List<Object> objects = new ArrayList<>();
        objects.add(0);
        objects.add(1,GlobalDataSaved.connectedUser.getId());
        objects.add(2,GlobalDataSaved.examToExecute.getId());
        objects.add(3,grade);
        objects.add(4, onTime);
        Message msg = new Message("#update student manual answer", objects);
        SimpleClient.getClient().sendToServer(msg);
    }
    @FXML
    void submitExam(ActionEvent event) {
        timer.cancel();

    }
    void examCountDownTimer()
    {
        ddHour = decimalFormat.format(hour);
        ddMinute = decimalFormat.format(minute);
        ddSecond = decimalFormat.format(second);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                second--;

                if (second == -1) {
                    second = 59;
                    minute--;
                }
                if (minute == 0 && hour == 0) {

                }
                else if (minute == 0) {
                    hour--;
                    minute = 59;
                }
                if (hour == 0 && minute < 10) {
                    examTimer.setFill(Paint.valueOf("#ff0000"));
                    //examTimer.setStyle("-fx-text-fill: red;");
                }
                ddHour = decimalFormat.format(hour);
                ddMinute = decimalFormat.format(minute);
                ddSecond = decimalFormat.format(second);
                examTimer.setText(ddHour+":"+ddMinute+":"+ddSecond);

                if (hour == 0 && minute == 0 && second == 0) {
                    try {
                        examFinishedTime();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    timer.cancel();
                }
            }
        },1000,1000);
    }
    private void examFinishedTime() throws IOException {
        System.out.println("the end ..... no more time ....");
        Message msg = new Message("#time finished");
        SimpleClient.getClient().sendToServer(msg);
    }
    @FXML
    void initialize() throws IOException {
        int examTime = GlobalDataSaved.examToExecute.getExam().getTime();
        while (examTime > 60) {
            hour++;
            examTime -= 60;
        }
        minute = examTime;
        if (hour == 0 && minute < 10) {
            examTimer.setFill(Paint.valueOf("#ff0000"));
            //examTimer.setStyle("-fx-text-fill: red;");
        }
        ddHour = decimalFormat.format(hour);
        ddMinute = decimalFormat.format(minute);
        ddSecond = decimalFormat.format(second);
        examTimer.setText(ddHour+":"+ddMinute+":"+ddSecond);
        String source="",dist="";
        String sourceFileName=GlobalDataSaved.examToExecute.getExam().getExam_ID()+
                GlobalDataSaved.examToExecute.getExam().getTeacherThatCreated().getUserID();
        source=WordGeneratorFile.examsPath+sourceFileName+".docx";
        dist=sourceFileName+GlobalDataSaved.connectedUser.getId();
        WordGeneratorFile.copyFileUsingApacheCommonsIO(source,dist);
    }

}