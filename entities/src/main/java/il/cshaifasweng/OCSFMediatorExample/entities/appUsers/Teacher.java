package il.cshaifasweng.OCSFMediatorExample.entities.appUsers;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends User implements Serializable {

    @OneToMany(mappedBy = "teacher",fetch = FetchType.EAGER)
    Set<Teacher_Subject> teacherSubjects;
    @OneToMany(mappedBy = "teacher",fetch = FetchType.EAGER)
    Set<Teacher_Course> teacherCourses;

    @OneToMany( mappedBy = "teacherThatCreated",fetch = FetchType.EAGER)
    private Set<Exam> examsCreated=new HashSet<>();
    @OneToMany( mappedBy = "teacherThatExecuted",fetch = FetchType.EAGER)
    private Set<ExamToExecute> executedExams=new HashSet<>();
    @OneToMany(mappedBy = "teacherThatCreated",fetch = FetchType.EAGER)
    private Set<Question> questionsCreated;
    public Teacher(String userID, String passWord, String userName, String firstName, String lastName) {
        super(userID, passWord, userName, firstName, lastName);
        this.teacherCourses=new HashSet<>();
        this.teacherSubjects=new HashSet<>();
        this.questionsCreated=new HashSet<>();
    }
    public Teacher() {

    }
    public Teacher(Teacher teacher)
    {
        super(teacher);
    }

    public Set<Teacher_Course> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(Set<Teacher_Course> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }

    public Set<Teacher_Subject> getTeacherSubjects() {
        return teacherSubjects;
    }

    public void setTeacherSubjects(Set<Teacher_Subject> teacherSubjects) {
        this.teacherSubjects = teacherSubjects;
    }

    public Set<Exam> getExamsCreated() {
        return examsCreated;
    }

    public void setExamsCreated(Set<Exam> examsCreated) {
        this.examsCreated = examsCreated;
    }

    public Set<ExamToExecute> getExecutedExams() {
        return executedExams;
    }

    public void setExecutedExams(Set<ExamToExecute> executedExams) {
        this.executedExams = executedExams;
    }

    public Set<Question> getQuestionsCreated() {
        return questionsCreated;
    }

    public void setQuestionsCreated(Set<Question> questionsCreated) {
        this.questionsCreated = questionsCreated;
    }
    @Override
    public String toString() {
        return this.getFirstName() + " " +  this.getLastName();
    }

}
