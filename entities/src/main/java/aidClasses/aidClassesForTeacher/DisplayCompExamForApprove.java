package aidClasses.aidClassesForTeacher;

import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;

public class DisplayCompExamForApprove {
    private ComputerizedExamToExecute date;
    private String examId;
    private String subjectName;

    public DisplayCompExamForApprove(ComputerizedExamToExecute date, String examId,String subjectName) {
        this.date = date;
        this.examId = examId;
        this.subjectName = subjectName;
    }

    public ComputerizedExamToExecute getDate() {
        return date;
    }

    public void setDate(ComputerizedExamToExecute date) {
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
