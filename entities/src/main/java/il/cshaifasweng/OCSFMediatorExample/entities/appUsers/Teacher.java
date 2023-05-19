package il.cshaifasweng.OCSFMediatorExample.entities.appUsers;

import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Teacher extends User implements Serializable {
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Course.class
    )
    @JoinTable(
            name="teachers_courses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> teacherCourses;
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Subject.class
    )
    @JoinTable(
            name="teachers_subjects",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> teacherSubjects;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacherThatCreated")
    private List<Exam> exams;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacherThatExecuted")
    private List<ComputerizedExamToExecute> executedExams;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacherThatCreated")
    private List<Question> questionsCreated;
    public Teacher(String userID, String passWord, String userName, String firstName, String lastName) {
        super(userID, passWord, userName, firstName, lastName);
        this.teacherCourses=new ArrayList<>();
        this.teacherSubjects=new ArrayList<>();
        this.exams=new ArrayList<>();
        this.executedExams=new ArrayList<>();
        this.questionsCreated=new ArrayList<>();
    }
    public Teacher() {

    }
}
