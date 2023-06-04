package aidClasses.aidClassesForTeacher;

import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DisplayGrade {
   private String studentID;
   private String studentName;
   private String date;
   private TextField grade;
   private Grade gradeObject;

   private TextField notes;


    public DisplayGrade(String studentID, String studentName, String date, TextField grade, TextField notes,Grade gradeObject) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.date = date;
        this.grade = grade;
        this.notes = notes;
        this.gradeObject=gradeObject;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TextField getGrade() {
        return grade;
    }

    public void setGrade(TextField grade) {
        this.grade = grade;
    }

    public TextField getNotes() {
        return notes;
    }

    public void setNotes(TextField notes) {
        this.notes = notes;
    }

    public Grade getGradeObject() {
        return gradeObject;
    }

    public void setGradeObject(Grade gradeObject) {
        this.gradeObject = gradeObject;
    }
}
