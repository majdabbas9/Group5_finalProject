package il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "copies")
public class Copy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String answers;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    private Grade grade;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_toExecuteId")
    private ExamToExecute compExamToExecute;

    public Copy(){}

    public Copy(String answers,ComputerizedExamToExecute compExamToExecute)
    {
        this.answers=answers;
        setCompExamToExecute(compExamToExecute);
    }
    public Copy(String answers)
    {
        this.answers=answers;
    }
    public Copy(Copy copy)
    {
        this.id=copy.id;
        this.answers=copy.answers;
    }

    public int getId() {
        return id;
    }
    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public ExamToExecute getCompExamToExecute() {
        return compExamToExecute;
    }

    public void setCompExamToExecute(ExamToExecute compExamToExecute) {
        this.compExamToExecute = compExamToExecute;
    }
}
