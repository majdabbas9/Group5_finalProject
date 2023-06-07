package il.cshaifasweng.OCSFMediatorExample.client.Student;


import aidClasses.GlobalDataSaved;
import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;

public class ExamCopy {

    @FXML
    private Button backToGradesTable;

    @FXML
    private RadioButton firstChoice;

    @FXML
    private RadioButton fourthChoice;

    @FXML
    private Button nextQuestion;

    @FXML
    private Button prevQuestion;

    @FXML
    private ToggleGroup questionChoices;

    @FXML
    private Text questionContent;

    @FXML
    private Text questionNo;

    @FXML
    private Text questionStatus;

    @FXML
    private Text correctAnswer;

    @FXML
    private RadioButton secondChoice;

    @FXML
    private RadioButton thirdChoice;

    public Set<Exam_Question> examQuestions = new HashSet<>();
    private int questionCounter = 0;
    public List<String> choicesList;
    public List<Question> questionList;
    @FXML
    public void initialize() throws IOException {
        examQuestions = GlobalDataSaved.examToExecute.getExam().getExamQuestions();
        questionList = sortedQuestionsList(examQuestions);
        question();
    }

    void question() throws IOException {
        Question q = questionList.get(questionCounter);
        getCurrentQuestion(q);
    }

    public List<Question> sortedQuestionsList(Set<Exam_Question> list)
    {
        List<Object> examQ= Arrays.asList(examQuestions.toArray());
        Question q;
        List<Question> questionList = new ArrayList<>();

        for(Object exam_question:examQ) {
            q = ((Exam_Question) exam_question).getQuestion();
            questionList.add(q);
        }

        Collections.sort(questionList, new sortById());

        return questionList;
    }

    void getCurrentQuestion(Question question)
    {
        firstChoice.setDisable(true);
        secondChoice.setDisable(true);
        thirdChoice.setDisable(true);
        fourthChoice.setDisable(true);

        if (questionCounter == 0){
            prevQuestion.setDisable(true);
            nextQuestion.setDisable(false);
        }
        else {
            prevQuestion.setDisable(false);
            nextQuestion.setDisable(false);
        }
        if (questionCounter + 1 == examQuestions.size()){
            nextQuestion.setDisable(true);
        }
        choicesList = question.getChoices();
        questionContent.setText(question.getStudentNotes());
        questionNo.setText("Question "+ (questionCounter+1) + ":");
        firstChoice.setText(choicesList.get(0));
        secondChoice.setText(choicesList.get(1));
        thirdChoice.setText(choicesList.get(2));
        fourthChoice.setText(choicesList.get(3));
        switch (GlobalDataSaved.studentAnswers.get(questionCounter)) {
            case "1":
                firstChoice.setSelected(true);
                break;
            case "2":
                secondChoice.setSelected(true);
                break;
            case "3":
                thirdChoice.setSelected(true);
                break;
            case "4":
                fourthChoice.setSelected(true);
                break;
        }
        Question q;
        q = questionList.get(questionCounter);
        if (q.getCorrectChoice().equals(GlobalDataSaved.studentAnswers.get(questionCounter))) {
            questionStatus.setText("Correct");
            questionStatus.setFill(Paint.valueOf("#04a90c"));
        }
        else {
            questionStatus.setText("Incorrect");
            questionStatus.setFill(Paint.valueOf("#ff0000"));
        }
        correctAnswer.setText(q.getCorrectChoice());


    }
    @FXML
    void backToGradesTable(ActionEvent event) throws IOException {
        App.setRoot("studentGrades");
    }

    @FXML
    void goToNextQuestion(ActionEvent event) throws IOException {
        questionCounter++;
        question();
    }

    @FXML
    void goToPreviousQuestion(ActionEvent event) throws IOException {
        questionCounter--;
        question();
    }


}