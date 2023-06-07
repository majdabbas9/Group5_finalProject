package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "compExams")
public class ComputerizedExamToExecute extends ExamToExecute implements Serializable {
    public ComputerizedExamToExecute(String dateOfExam,int code)
    {
        super(dateOfExam,code);
    }

    public ComputerizedExamToExecute() {

    }
}
