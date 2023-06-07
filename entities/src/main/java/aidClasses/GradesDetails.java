package aidClasses;

import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;

public class GradesDetails {

    private int id;
    private String subjectName;
    private String courseName;
    private String teacherName;
    private int grade;
    private Grade gradeObject;

    public GradesDetails(int id, String subjectName, String courseName, String teacherName, int grade, Grade gradeObject) {
        this.id = id;
        this.subjectName = subjectName;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.grade = grade;
        this.gradeObject=gradeObject;
    }

    public GradesDetails() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Grade getGradeObject() {
        return gradeObject;
    }

    public void setGradeObject(Grade gradeObject) {
        this.gradeObject = gradeObject;
    }
}