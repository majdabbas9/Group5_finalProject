package il.cshaifasweng.OCSFMediatorExample.entities.educational;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject implements  Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subjectName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courseSubject")
    private List<Course> subjectCourses;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionSubject")
    private List<Question> subjectQuestions;
    public Subject() {

    }
    public Subject(String subjectName) {
        this.subjectName = subjectName;
        this.subjectCourses=new ArrayList<>();
        this.subjectQuestions=new ArrayList<>();
    }
    public int getId() {
        return id;
    }
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Course> getSubjectCourses() {
        return subjectCourses;
    }

    public void setSubjectCourses(List<Course> subjectCourses) {
        this.subjectCourses = subjectCourses;
    }
    public void  addCourse(Course course)
    {
        this.subjectCourses.add(course);
    }

    public List<Question> getSubjectQuestions() {
        return subjectQuestions;
    }

    public void setSubjectQuestions(List<Question> subjectQuestions) {
        this.subjectQuestions = subjectQuestions;
    }
}
