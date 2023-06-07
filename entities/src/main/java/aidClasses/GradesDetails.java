package aidClasses;

public class GradesDetails {

    private int idOnGradeTable;
    private int id;
    private String subjectName;
    private String courseName;
    private String teacherName;
    private int grade;

    public GradesDetails(int idOnGradeTable, int id, String subjectName, String courseName, String teacherName, int grade) {
        this.idOnGradeTable = idOnGradeTable;
        this.id = id;
        this.subjectName = subjectName;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.grade = grade;
    }

    public GradesDetails() {
    }

    public int getIdOnGradeTable() {
        return idOnGradeTable;
    }

    public void setIdOnGradeTable(int idOnGradeTable) {
        this.idOnGradeTable = idOnGradeTable;
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
}