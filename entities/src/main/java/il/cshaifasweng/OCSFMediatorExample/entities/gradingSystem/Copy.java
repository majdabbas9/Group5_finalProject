package il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "copies")
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String answers;
    @OneToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compExam_ID")
    private ComputerizedExamToExecute compExamToExecute;

    public Copy(){}

    public Copy(String answers,ComputerizedExamToExecute compExamToExecute)
    {
        this.answers=answers;
        setCompExamToExecute(compExamToExecute);
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

    public ComputerizedExamToExecute getCompExamToExecute() {
        return compExamToExecute;
    }

    public void setCompExamToExecute(ComputerizedExamToExecute compExamToExecute) {
        this.compExamToExecute = compExamToExecute;
    }
}
