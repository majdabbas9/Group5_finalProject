package aidClasses.aidClassesForTeacher;

import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DisplayGrade {
   private String studentID;
   private String studentName;
   private Grade date;
   private TextField grade;

   private TextField notes;


    public DisplayGrade(String studentID, String studentName, Grade date, TextField grade, TextField notes) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.date = date;
        this.grade = grade;
        this.notes = notes;
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

    public Grade getDate() {
        return date;
    }

    public void setDate(Grade date) {
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
}
