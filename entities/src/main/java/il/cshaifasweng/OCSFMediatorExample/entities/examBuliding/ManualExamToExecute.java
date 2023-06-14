package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "manualExams")
public class ManualExamToExecute extends ExamToExecute implements Serializable{
    String fileName;
    public ManualExamToExecute(String dateOfExam, int code)
    {
        super(dateOfExam,code);
    }

    public ManualExamToExecute() {

    }

    public ManualExamToExecute(String dateOfExam, int code, String fileName) {
        super(dateOfExam, code);
        this.fileName = fileName;
    }
    public ManualExamToExecute(ExamToExecute examToExecute) {
        super(examToExecute);
    }

    public ManualExamToExecute(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
