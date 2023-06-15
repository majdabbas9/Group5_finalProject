package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Principal;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import org.hibernate.Session;
import org.hibernate.annotations.Where;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;

public class GetEducational {
    public static List<Subject> getAllSubjects(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);
        query.from(Subject.class);
        List<Subject> data = session.createQuery(query).getResultList();
        return data;
    }

    public static List<Subject> getAllSubjectsForPrincipal(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);
        query.from(Subject.class);
        List<Subject> data = session.createQuery(query).getResultList();
        List<Subject> subjects = new ArrayList<>();
        for (Subject subject : data){
            Subject newSubject = new Subject();
            newSubject.setSubjectCourses(subject.getSubjectCourses());
            newSubject.setSubjectName(subject.getSubjectName());
            subjects.add(newSubject);
        }
        return subjects;
    }


    public static List<ExamToExecute> getAllRequests(Session session) throws Exception {
        Query query = session.createQuery("from ExamToExecute where isExtraNeeded='"+1+"'");
        List<ExamToExecute> data =(List<ExamToExecute>) query.getResultList();
        List<ExamToExecute> newExams=new ArrayList<>();
        for(ExamToExecute examToExecute : data)
        {
            ExamToExecute newExam=new ExamToExecute(examToExecute);
            Exam exam=new Exam(examToExecute.getExam());
            Subject subject=new Subject(examToExecute.getExam().getExamSubject());
            Course course=new Course(examToExecute.getExam().getExamCourse());
            Teacher teacher=new Teacher(examToExecute.getTeacherThatExecuted());
            exam.setExamCourse(course);
            exam.setExamSubject(subject);
            newExam.setExam(exam);
            newExam.setTeacherThatExecuted(teacher);
            newExams.add(newExam);
        }
        return data;
    }

    public static List<Course> getAllCourses(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        query.from(Course.class);
        List<Course> data = session.createQuery(query).getResultList();
        List<Course> courses = new ArrayList<>();
        for (Course course : data){
            Course  newCourse = new Course();
            newCourse.setCourseName(course.getCourseName());
            courses.add(newCourse);
        }
        return courses;
    }

    public static void getCourse(Session session) {
        Query query = session.createQuery("from Course where courseName='linear algebra 3'");
        if (query.getResultList().size() == 0) {
            System.out.println("none");
            return;
        }
        List<Course> c = (List<Course>) query.getResultList();
        System.out.println("\n");
    }

    public static boolean checkID(Session session, String id) {
        Query query = session.createQuery("from User where userID=" + id + "");
        if (query.getResultList().size() == 0) {
            return true;
        }
        return false;
    }
    public  static List<Question> getCourseQuestions(Session session,int course_id)
    {
        String queryString = "FROM Course_Question  WHERE";
        queryString+=" ";
        queryString+="course_id";
        queryString+=" = ";
        queryString+=course_id;
        Query query = session.createQuery(queryString, Course_Question.class);
        List<Course_Question> subs = query.getResultList();
        List<Question> questions=new ArrayList<>();
        for(Course_Question tc:subs)
        {
            questions.add(tc.getQuestion());
        }
        return questions;
    }
    public static List<Exam> getAllExams(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Exam> query = builder.createQuery(Exam.class);
        query.from(Exam.class);
        List<Exam> data = session.createQuery(query).getResultList();
        return data;
    }
    public static List<Exam> getAllExamsAllNeeded(Session session)
    {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Exam> query = builder.createQuery(Exam.class);
        query.from(Exam.class);
        List<Exam> data = session.createQuery(query).getResultList();
        List<Exam> exams=new ArrayList<>();
        for(Exam exam:data)
        {
            Exam newExam=new Exam(exam);
            newExam.setExamSubject(new Subject(exam.getExamSubject()));
            newExam.setExamCourse(new Course(exam.getExamCourse()));
            newExam.setTeacherThatCreated(new Teacher(exam.getTeacherThatCreated()));
            exams.add(newExam);
        }
        return exams;
    }
    public static Exam getExamQuestionsByExamId(Session session,int examId)
    {
        Exam exam=GetExamBuliding.getExamById(session,examId);
        List<Exam_Question> examQuestions=new ArrayList<>();
        for(Exam_Question exam_question:exam.getExamQuestions())
        {
           examQuestions.add(new Exam_Question(exam_question));
        }
        Exam newExam=new Exam(exam);
        newExam.setTeacherThatCreated(new Teacher(exam.getTeacherThatCreated()));
        newExam.setExamSubject(new Subject(exam.getExamSubject()));
        newExam.setExamCourse(new Course(exam.getExamCourse()));
        newExam.getExamQuestions() .addAll(examQuestions);
        return newExam;
    }

    public static List<Question> getAllQuestions(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> query = builder.createQuery(Question.class);
        query.from(Question.class);
        List<Question> data = session.createQuery(query).getResultList();
        List<Question> questions=new ArrayList<>();
        for(Question question:data)
        {
            Question question1=new Question(question);
            question1.setQuestionSubject(new Subject(question.getQuestionSubject()));
            List<Course_Question> courses=new ArrayList<>();
            for(Course_Question course:question.getQuestionCourses())
            {
              courses.add(new Course_Question(course.getCourse()));
            }
            question1.getQuestionCourses().addAll(courses);
            question1.setTeacherThatCreated(new Teacher(question.getTeacherThatCreated()));
            questions.add(question1);
        }
        return questions;
    }

    public static List<Grade> getAllGrades(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Grade> query = builder.createQuery(Grade.class);
        query.from(Grade.class);
        List<Grade> data = session.createQuery(query).getResultList();
        return GetGrading.SpeedUpGradesData(data);
    }
    public static List<Student> getAllStudents(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = builder.createQuery(Student.class);
        query.from(Student.class);
        List<Student> data = session.createQuery(query).getResultList();
        List<Student> students = new ArrayList<>();
        for (Student student : data){
            Student newStudent = new Student();
            newStudent.setFirstName(student.getFirstName());
            newStudent.setLastName(student.getLastName());
            newStudent.setUserID(student.getUserID());
            Set<Grade> newGrades = new HashSet<>();
            for (Grade grade : student.getGrades()){
              newGrades.add(grade);
            }
            newStudent.setGrades(newGrades);
            students.add(newStudent);
        }
        return students;
    }
    public static List<Teacher> getAllTeachers(Session session) throws Exception {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = builder.createQuery(Teacher.class);
        query.from(Teacher.class);
        List<Teacher> data = session.createQuery(query).getResultList();
        List<Teacher> teachers = new ArrayList<>();
        for (Teacher teacher : data){
            Teacher  newTeacher = new Teacher();
            newTeacher.setFirstName(teacher.getFirstName());
            newTeacher.setLastName(teacher.getLastName());
            newTeacher.setUserID(teacher.getUserID());
            teachers.add(newTeacher);
        }
        return teachers;
    }
}
