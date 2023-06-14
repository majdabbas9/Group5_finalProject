package il.cshaifasweng.OCSFMediatorExample.client.Student;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.WordGeneratorFile;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ManualExamToExecute;
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
    private static int hour=0, minute=0, second=0;
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

    private String  source, dist;

    @FXML
    void getWordFile(ActionEvent event) throws IOException {
        List<Object> objects = sendStudentManualAnswerToServer(false, false);
        Message msg = new Message("#create student copy and grade", objects);
        SimpleClient.getClient().sendToServer(msg);
        WordGeneratorFile.openWord(dist);
        examCountDownTimer();
    }
    private List<Object> sendStudentManualAnswerToServer(boolean onTime, boolean sendToServer) throws IOException {
        List<Object> objects = new ArrayList<>();
        objects.add(0,GlobalDataSaved.currentCopyId);
        objects.add(1,GlobalDataSaved.currentGradeId);
        objects.add(2,GlobalDataSaved.examToExecute.getId());
        objects.add(3, GlobalDataSaved.connectedUser.getId());
        objects.add(4,dist);
        objects.add(5,onTime);
        objects.add(6,-1);
        if(sendToServer)
        {
            Message msg = new Message("#update student answers", objects);
            SimpleClient.getClient().sendToServer(msg);
        }
        return objects;
    }
    @FXML
    void submitExam(ActionEvent event) throws IOException {
        timer.cancel();
        WordGeneratorFile.closeWordFile(dist);
        List<Object> objects = sendStudentManualAnswerToServer(true, true);


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
                }
                else {
                    examTimer.setFill(Paint.valueOf("#ffffff"));
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
        WordGeneratorFile.closeWordFile(dist);
        List<Object> objects = sendStudentManualAnswerToServer(false, true);
    }

    public static void addExtraTime(int time){
        while (time >= 60){
            hour++;
            time -= 60;
        }
        if (minute + time >= 60) {
            hour ++;
            minute = minute + time - 60;
        }else {
            minute += time;
        }
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
        }
        second = 0;
        ddHour = decimalFormat.format(hour);
        ddMinute = decimalFormat.format(minute);
        ddSecond = decimalFormat.format(second);
        examTimer.setText(ddHour+":"+ddMinute+":"+ddSecond);
        source=((ManualExamToExecute)(GlobalDataSaved.examToExecute)).getFileName();
        dist=source+GlobalDataSaved.connectedUser.getId();
        WordGeneratorFile.copyFileUsingApacheCommonsIO(source,dist);
    }

}
