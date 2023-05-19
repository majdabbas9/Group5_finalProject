package il.cshaifasweng.OCSFMediatorExample.entities.examBuliding;

import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "exams")
public class ComputerizedExamToExecute implements Serializable {
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compExamToExecute")
    private List<Copy> copies;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacherThatExecuted;
}
