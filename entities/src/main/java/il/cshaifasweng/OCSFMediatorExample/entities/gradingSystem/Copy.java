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
    @JoinColumn(name = "compexamToExcute_id")
    private ComputerizedExamToExecute compExamToExecute;

    public Copy() {
    }
}
