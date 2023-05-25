package Student;

import aidClasses.GlobalDataSaved;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;


import javax.transaction.Transactional;
import java.net.URL;
import java.util.*;

public class SolveExam {

    @FXML
    private Button nextQuestion;
    @FXML
    private Text examTimer;

    @FXML
    private Button prevQuestion;

    @FXML
    private Text questionContent;

    @FXML
    private Text questionNo;

    @FXML
    private RadioButton firstChoice;
    @FXML
    private RadioButton secondChoice;
    @FXML
    private RadioButton thirdChoice;
    @FXML
    private RadioButton fourthChoice;


    public Set<Exam_Question> examQuestions =new HashSet<>();
    private int questionCounter = 0;

    @FXML
    void goToNextQuestion(ActionEvent event) {

    }
    @FXML
    void goToPreviousQuestion(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        // TODO late

       examQuestions = GlobalDataSaved.compExam.getExam().getExamQuestions();
        List<Object> examQ=Arrays.asList(examQuestions.toArray());
        Question q;
        for(Object exam_question:examQ)
        {
            q=((Exam_Question)exam_question).getQuestion();
            questionNo.setText(Integer.toString(questionCounter));
            questionContent.setText(q.getStudentNotes());
            firstChoice.setText(q.getChoices().get(0));
            secondChoice.setText(q.getChoices().get(1));
            thirdChoice.setText(q.getChoices().get(2));
            fourthChoice.setText(q.getChoices().get(3));
        }

    }

}