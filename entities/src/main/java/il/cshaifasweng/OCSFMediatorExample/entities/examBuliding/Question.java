package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String teacherNotes;
    private String studentNotes; // this the content of the question
    private String studentNotesToShow; //is this the notes for the student
    private String questionID;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String correctChoice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject questionSubject;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherThatCreated;

   // @OneToMany(mappedBy = "question"/*,fetch = FetchType.EAGER*/)
   // private Set<Course_Question> questionCourses=new HashSet<>();
    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER)
    private Set<Course_Question> questionCourses=new HashSet<>();


    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER)
    private Set<Exam_Question> questionExams=new HashSet<>();


    public Question() {

    }
    public Question(String teacherNotes, String studentNotes,String correctChoice,List<String> choices) {
        this.teacherNotes = teacherNotes;
        this.studentNotes = studentNotes;
        this.correctChoice = correctChoice;
        this.choice1=choices.get(0);this.choice2=choices.get(1);this.choice3=choices.get(2);this.choice4=choices.get(3);
    }

    public Question(Question question) {
        this.id = question.id;
        this.teacherNotes = question.teacherNotes;
        this.studentNotes = question.studentNotes;
        this.studentNotesToShow =question.studentNotesToShow;
        this.questionID = question.questionID;
        this.choice1 = question.choice1;
        this.choice2 = question.choice2;
        this.choice3 = question.choice3;
        this.choice4 = question.choice4;
        this.correctChoice = question.correctChoice;
    }

    public int getId() {
        return id;
    }
    public String getTeacherNotes() {
        return teacherNotes;
    }

    public void setTeacherNotes(String teacherNotes) {
        this.teacherNotes = teacherNotes;
    }

    public String getStudentNotes() {
        return studentNotes;
    }

    public void setStudentNotes(String studentNotes) {
        this.studentNotes = studentNotes;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String ID) {
        this.questionID = ID;
    }


    public String getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }

    public Subject getQuestionSubject() {
        return questionSubject;
    }

    public void setQuestionSubject(Subject questionSubject) {
        this.questionSubject = questionSubject;
    }

    public Teacher getTeacherThatCreated() {
        return teacherThatCreated;
    }

    public void setTeacherThatCreated(Teacher teacherThatCreated) {
        this.teacherThatCreated = teacherThatCreated;
    }


    public Set<Course_Question> getQuestionCourses() {
        return questionCourses;
    }

    public void setQuestionCourses(Set<Course_Question> questionCourses) {
        this.questionCourses = questionCourses;
    }

    public Set<Exam_Question> getQuestionExams() {
        return questionExams;
    }
    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public void setQuestionExams(Set<Exam_Question> questionExams) {
        this.questionExams = questionExams;
    }

    public List<String> getChoices()
    {
        List<String> choices=new ArrayList<>();
        choices.add(choice1);choices.add(choice2);choices.add(choice3);choices.add(choice4);
        return  choices;
    }
    @Override
    public String toString() {
        return this.studentNotes;
    }

    public String questionsString()
    {
        String res="";
        res+="a) "+choice1+"          "+"b) "+choice2+"          ";
        res+="c) "+choice3+"          "+"d) "+choice4+"\n";
        return res;
    }
    public String getQuestionTitle(int points,int numberOfQuestion)
    {
        String res=numberOfQuestion+" ) ";
        res+=studentNotes+" "+"("+" "+points+" points )"+"\n";
        return res;
    }

    public String getStudentNotesToShow() {
        return studentNotesToShow;
    }

    public void setStudentNotesToShow(String studentNotesToShow) {
        this.studentNotesToShow = studentNotesToShow;
    }
}
