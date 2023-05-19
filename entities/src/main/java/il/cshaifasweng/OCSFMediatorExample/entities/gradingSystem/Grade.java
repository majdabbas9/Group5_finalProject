package il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "grades")
public class Grade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examCopy_id")
    private Copy examCopy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
    private int grade;
    private boolean isManuel;
    private int timeToSolve;
    private boolean doneOnTime;
    private String date;
    private String hour;


}
