package aidClasses.aidClassesForTeacher;
import javafx.scene.control.TextField;

public class DisplayCompExam {
    private String examID;
    private String finishTime;

    private int numberOfStudentsDoing;
    private TextField hourList;
    private TextField minuteList;

    public DisplayCompExam(String examID, String finishTime, TextField hourList, TextField minuteList) {
        this.examID = examID;
        this.finishTime = finishTime;
        this.numberOfStudentsDoing = numberOfStudentsDoing;
        this.hourList = hourList;
        this.minuteList = minuteList;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getNumberOfStudentsDoing() {
        return numberOfStudentsDoing;
    }

    public void setNumberOfStudentsDoing(int numberOfStudentsDoing) {
        this.numberOfStudentsDoing = numberOfStudentsDoing;
    }

    public TextField getHour() {
        return hourList;
    }

    public void setHour(TextField hourList) {
        this.hourList = hourList;
    }

    public TextField getMinute() {
        return minuteList;
    }

    public void setMinute(TextField minuteList) {
        this.minuteList = minuteList;
    }

}
