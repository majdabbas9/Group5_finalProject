package aidClasses.aidClassesForTeacher;

public class DisplayCompExamForApprove {
    private String date;
    private String examId;
    private String subjectName;

    public DisplayCompExamForApprove(String date, String examId,String subjectName) {
        this.date = date;
        this.examId = examId;
        this.subjectName = subjectName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
