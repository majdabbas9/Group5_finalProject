package aidClasses.aidClassesForTeacher;

import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;

public class DisplayCompExamForApprove {
    private ExamToExecute date;
    private String examId;
    private String subjectName;
    private String isManual;

    public DisplayCompExamForApprove(ExamToExecute date, String examId, String subjectName) {
        this.date = date;
        this.examId = examId;
        this.subjectName = subjectName;
        this.isManual="true";
    }

    public ExamToExecute getDate() {
        return date;
    }

    public void setDate(ExamToExecute date) {
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

    public boolean isManual() {
        return isManual.equals("true");
    }

    public void setManual(boolean manual) {
        isManual =String.valueOf(manual);
    }
    public String getIsManual()
    {
        return this.isManual;
    }

}
