package il.cshaifasweng.OCSFMediatorExample.client.Student;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

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
    private TextArea questionContent;

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
    @FXML
    private HBox questionsNumbersButtons;

    public Set<Exam_Question> examQuestions = new HashSet<>();
    public List<Question> questionList;

    public List<String> choicesList;
    private int questionCounter = 0;

    private static int hour=0, minute=0, second=0;
    private String ddHour, ddMinute, ddSecond;

    private DecimalFormat decimalFormat = new DecimalFormat("00");
    private Timer timer;
    private List<String> answers = new ArrayList<>();

    @FXML
    void goToNextQuestion(ActionEvent event) {
        checkCurrentQuestion();
        questionCounter++;
        question(); 
    }
    @FXML
    void goToPreviousQuestion(ActionEvent event) {
        checkCurrentQuestion();
        questionCounter--;
        question();
    }
    void checkCurrentQuestion() {
        if(questionChoices.getSelectedToggle() != null){
            RadioButton selected = (RadioButton) questionChoices.getSelectedToggle();
            answers.set(questionCounter,selected.getText());
            Button button = (Button) questionsNumbersButtons.getChildren().get(questionCounter);
            button.setStyle("-fx-background-color: #0A8A0A");
            button.setTextFill(Paint.valueOf("#ffffff"));
        }
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
        sendStudentAnswersToServer(true, calculateStudentExamGrade());

    }

    private int calculateStudentExamGrade() {
        Exam exam = GlobalDataSaved.examToExecute.getExam();

        examQuestions = GlobalDataSaved.examToExecute.getExam().getExamQuestions();
        List<Exam_Question> examQuestionsToGetPoints=new ArrayList<>(GlobalDataSaved.examToExecute.getExam().getExamQuestions());
        List<Question> questionList = sortedQuestionsList(examQuestions);
        Question q;
        int grade = 0;
        for (int i=0; i<answers.size(); i++) {
            q = questionList.get(i);
            if (q.getCorrectChoice().equals(answers.get(i))) {
                grade += examQuestionsToGetPoints.get(i).getPoints();
            }
        }
        System.out.println("the exam grade is : "+ grade+ "/100");

        return grade;
    }

    private void sendStudentAnswersToServer(boolean onTime , int grade) throws IOException {
        String answerSt = String.join(",", answers);
        List<Object> objects = new ArrayList<>();
        objects.add(0,GlobalDataSaved.currentCopyId);
        objects.add(1,GlobalDataSaved.currentGradeId);
        objects.add(2,GlobalDataSaved.examToExecute.getId());
        objects.add(3, GlobalDataSaved.connectedUser.getId());
        objects.add(4,answerSt);
        objects.add(5,onTime);
        objects.add(6,grade);
        Message msg = new Message("#update student answers", objects);
        SimpleClient.getClient().sendToServer(msg);
    }

    @FXML
    public void initialize() throws IOException {
        // TODO late
        hour=0;minute=0;second=0;
        String date = GlobalDataSaved.examToExecute.getDateOfExam();
        String[] dateAndHour = date.split(" ");
        Grade grade1 = new Grade(-1,false,GlobalDataSaved.examToExecute.getExam().getTime(),
                false,dateAndHour[0], dateAndHour[1], false);
        Copy copy = new Copy();
        copy.setAnswers(null);
        List<Object> objects = new ArrayList<>();
        objects.add(0,copy);
        objects.add(1,grade1);
        objects.add(2,GlobalDataSaved.examToExecute.getId());
        objects.add(3, GlobalDataSaved.connectedUser.getId());
        GlobalDataSaved.currentCopyId = copy.getId();
        GlobalDataSaved.currentGradeId = grade1.getId();
        Message msg = new Message("#create student copy and grade", objects);
        SimpleClient.getClient().sendToServer(msg);

        int examTime = GlobalDataSaved.examToExecute.getExam().getTime();
        if(examTime==15)
        {
            System.out.println("hi");
        }
        if (GlobalDataSaved.examToExecute.getIsExtraNeeded() == 2){
            examTime += GlobalDataSaved.examToExecute.getExtraTime();
        }
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
        examCountDownTimer();

        examQuestions = GlobalDataSaved.examToExecute.getExam().getExamQuestions();
        questionList =sortedQuestionsList(examQuestions);

        for (int i=0; i<questionList.size(); i++) {
            answers.add(i,null);
        }
        questionsNumbersButtons.setSpacing(5);
        for (int i=0; i<questionList.size(); i++){
            Button button = new Button(String.valueOf(i+1));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    checkCurrentQuestion();
                    questionCounter = Integer.parseInt(button.getText()) - 1;
                    question();
                }
            });
            questionsNumbersButtons.getChildren().add(button);
        }
        question();



    }

    void question() {
        Question q = questionList.get(questionCounter);
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
    private void examFinishedTime() throws IOException {
        System.out.println("the end ..... no more time ....");
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
        if (question.getStudentNotesToShow() != null){
            questionContent.setText(question.getStudentNotes() + "\nNotes: "+ question.getStudentNotesToShow());
        }
        else {
            questionContent.setText(question.getStudentNotes());
        }
        questionNo.setText("Question "+ (questionCounter+1) + ":");
        firstChoice.setText(choicesList.get(0));
        secondChoice.setText(choicesList.get(1));
        thirdChoice.setText(choicesList.get(2));
        fourthChoice.setText(choicesList.get(3));


        if (answers.get(questionCounter) != null) {
            ExamCopy.setButtonSelectedBefore(answers,questionCounter,firstChoice,secondChoice,thirdChoice,fourthChoice);
        } else if (answers.get(questionCounter) == null && questionChoices.getSelectedToggle() != null) {
            questionChoices.getSelectedToggle().setSelected(false);
        }
    }
}

class sortById implements Comparator<Question> {

    @Override
    public int compare(Question o1, Question o2) {
        return o1.getId() - o2.getId();
    }
}