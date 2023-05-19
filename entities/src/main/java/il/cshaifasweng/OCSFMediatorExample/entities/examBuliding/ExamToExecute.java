package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "exams")
public class ExamToExecute{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int average;
    @ElementCollection
    private List<Integer> histogram;
    @Column(name = "inTime")
    private int numberOfStudentDoneInTime;
    @Column(name = "notInTime")
    private int numberOfStudentNotDoneInTime;

}
