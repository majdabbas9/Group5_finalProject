package il.cshaifasweng.OCSFMediatorExample.client.Student;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;


import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class SolveExam {

    @FXML
    private Button nextQuestion;
    @FXML
    private Text examTimer;

    @FXML
    private Button prevQuestion;
    @FXML
    private Button submitBtn;
    @FXML
    private Text questionContent;

    @FXML
    private Text questionNo;

    @FXML
    private ToggleGroup questionChoices;
    @FXML
    private RadioButton firstChoice;
    @FXML
    private RadioButton secondChoice;
    @FXML
    private RadioButton thirdChoice;
    @FXML
    private RadioButton fourthChoice;


    public Set<Exam_Question> examQuestions = new HashSet<>();
    public List<Question> questionList;

    public List<String> choicesList;
    private int questionCounter = 0;

    private int hour=0, minute=0, second=0;
    private String ddHour, ddMinute, ddSecond;

    private DecimalFormat decimalFormat = new DecimalFormat("00");
    private Timer timer;
    private List<String> answers = new ArrayList<>();

    @FXML
    void goToNextQuestion(ActionEvent event) {
        if(questionChoices.getSelectedToggle() != null){
            RadioButton selected = (RadioButton) questionChoices.getSelectedToggle();
            answers.set(questionCounter,selected.getText());
        }
        questionCounter++;
        question(); 
    }
    @FXML
    void goToPreviousQuestion(ActionEvent event) {
        if(questionChoices.getSelectedToggle() != null){
            RadioButton selected = (RadioButton) questionChoices.getSelectedToggle();
            answers.set(questionCounter,selected.getText());
        }
        questionCounter--;
        question();
    }

    @FXML
    void submitExam(ActionEvent event) throws IOException {
        timer.cancel();
        if(questionChoices.getSelectedToggle() != null){
            RadioButton selected = (RadioButton) questionChoices.getSelectedToggle();
            answers.set(questionCounter,selected.getText());
        }
        for (int i=0; i<answers.size(); i++) {
            System.out.println("**** the saved answer is: "+ answers.get(i));
        }


        System.out.println(hour+":" +minute +":"+ second);
        if (hour != 0 || minute != 0 || second != 0) {
            Message msg = new Message("#submitted on the time", answers);
            SimpleClient.getClient().sendToServer(msg);
        }
        sendStudentAnswersToServer(true, calculateStudentExamGrade());

    }

    private int calculateStudentExamGrade() {
        Exam exam = GlobalDataSaved.examToExecute.getExam();
        List<Integer> points = new ArrayList<>(exam.getPoints());

        examQuestions = GlobalDataSaved.examToExecute.getExam().getExamQuestions();
        List<Question> questionList = sortedQuestionsList(examQuestions);
        Question q;
        int grade = 0;
        for (int i=0; i<answers.size(); i++) {
            q = questionList.get(i);
            if (q.getCorrectChoice().equals(answers.get(i))) {
                grade += points.get(i);
            }
        }
        System.out.println("the exam grade is : "+ grade+ "/30");

        return grade;
    }

    private void sendStudentAnswersToServer(boolean onTime , int grade) throws IOException {
        String answerSt = String.join(",", answers);
        List<Object> objects = new ArrayList<>();
        objects.add(0,answerSt);
        objects.add(1,GlobalDataSaved.connectedUser);
        objects.add(2,GlobalDataSaved.examToExecute);
        objects.add(3,grade);
        objects.add(4, onTime);
        Message msg = new Message("#update student answers", objects);
        SimpleClient.getClient().sendToServer(msg);
    }

    @FXML
    public void initialize() throws IOException {
        // TODO late
        List<Object> objects = new ArrayList<>();
        objects.add(0,null);
        objects.add(1,GlobalDataSaved.connectedUser);
        objects.add(2,GlobalDataSaved.examToExecute);
        objects.add(3,0);
        Message msg = new Message("#create student copy and grade", objects);
        SimpleClient.getClient().sendToServer(msg);

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
        examCountDownTimer();

        examQuestions = GlobalDataSaved.examToExecute.getExam().getExamQuestions();
        questionList =sortedQuestionsList(examQuestions);

        for (int i=0; i<questionList.size(); i++) {
            answers.add(i,null);
        }
        question();



    }

    void question() {
        Question q = questionList.get(questionCounter);
        //answers.add(questionCounter,null);
        getCurrentQuestion(q);
    }

    public List<Question> sortedQuestionsList(Set<Exam_Question> list)
    {

       examQuestions = GlobalDataSaved.examToExecute.getExam().getExamQuestions();

        List<Object> examQ=Arrays.asList(examQuestions.toArray());
        Question q;
        List<Question> questionList = new ArrayList<>();

        for(Object exam_question:examQ) {
            q = ((Exam_Question) exam_question).getQuestion();
            questionList.add(q);
        }

        Collections.sort(questionList, new sortById());

        return questionList;
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
    public  void addTime(int time) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date d = df.parse(hour+":"+minute+":"+second);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, time);
        String newDate = df.format(cal.getTime());
        hour=Integer.valueOf(newDate.substring(0,2));
        minute=Integer.valueOf(newDate.substring(3,5));
        second=Integer.valueOf(newDate.substring(6,7));
    }

    private void examFinishedTime() throws IOException {
        System.out.println("the end ..... no more time ....");
        Message msg = new Message("#time finished", answers);
        SimpleClient.getClient().sendToServer(msg);
        if(questionChoices.getSelectedToggle() != null){
            RadioButton selected = (RadioButton) questionChoices.getSelectedToggle();
            answers.set(questionCounter,selected.getText());
        }
        sendStudentAnswersToServer(false, calculateStudentExamGrade());
    }

    void getCurrentQuestion(Question question)
    {
        firstChoice.setFocusTraversable(false);
        secondChoice.setFocusTraversable(false);
        thirdChoice.setFocusTraversable(false);
        fourthChoice.setFocusTraversable(false);

        if (answers.get(questionCounter) != null) {
            if (answers.get(questionCounter).equals(firstChoice.getText())) {
                firstChoice.setSelected(true);
            } else if (answers.get(questionCounter).equals(secondChoice.getText())) {
                secondChoice.setSelected(true);
            } else if (answers.get(questionCounter).equals(thirdChoice.getText())) {
                thirdChoice.setSelected(true);
            } else if (answers.get(questionCounter).equals(fourthChoice.getText())) {
                fourthChoice.setSelected(true);
            }
        } else if (answers.get(questionCounter) == null && questionCounter > 0 && questionChoices.getSelectedToggle() != null) {
            questionChoices.getSelectedToggle().setSelected(false);
        }

        if (questionCounter == 0){
            prevQuestion.setDisable(true);
            nextQuestion.setDisable(false);
        }
        else {
            prevQuestion.setDisable(false);
            nextQuestion.setDisable(false);
        }
        if (questionCounter-1 < examQuestions.size()){
            submitBtn.setDisable(true);
        }
        if (questionCounter + 1 == examQuestions.size()){
            nextQuestion.setDisable(true);
            submitBtn.setDisable(false);
        }
        choicesList = question.getChoices();
        questionContent.setText(question.getStudentNotes());
        questionNo.setText("Question "+ (questionCounter+1) + ":");
        firstChoice.setText(choicesList.get(0));
        secondChoice.setText(choicesList.get(1));
        thirdChoice.setText(choicesList.get(2));
        fourthChoice.setText(choicesList.get(3));


    }
}

class sortById implements Comparator<Question> {

    @Override
    public int compare(Question o1, Question o2) {
        return o1.getId() - o2.getId();
    }
}