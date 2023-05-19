package il.cshaifasweng.OCSFMediatorExample.entities.educational;
import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "courses")
public class Course implements  Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String courseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject courseSubject;
    public Course() {

    }
    public Course(String courseName,Subject subject) {
        this.courseName = courseName;
        setCourseSubject(subject);
    }

    public int getId() {
        return id;
    }
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName,Subject courseSubject) {
        this.courseName = courseName;
        setCourseSubject(courseSubject);
    }
    public Subject getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(Subject courseSubject) {
        this.courseSubject = courseSubject;
    }
}
