package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class GetExamBuliding {
    public static  int counter=0;

    @Transactional
    public static List<Exam> getAllExamsForCourses(Session session, int teacherId)
    {
        List<Exam> allExams=new ArrayList<>();
        String queryString;
        List<Teacher_Course> teacherCourses=teacherCourses(session,teacherId);
        for(Teacher_Course teacherCourse:teacherCourses)
        {
            queryString="";
           queryString = "FROM Exam  WHERE";
            queryString+=" ";
            queryString+="course_id";
            queryString+=" = ";
            queryString+=teacherCourse.getCourse().getId();
            Query query = session.createQuery(queryString, Exam.class);
            List<Exam> courseExamResult = query.getResultList();
            for(Exam exam : courseExamResult)
            {
                Exam exam1=new Exam(exam,"all needed");
                Set<Exam_Question> examQuestions=exam.getExamQuestions();
                for(Exam_Question exam_question:examQuestions)
                {
                    Exam_Question eq=new Exam_Question(new Question(exam_question.getQuestion(),"all needed"),"all needed");
                    exam1.getExamQuestions().add(eq);
                }
                exam1.setExamCourse(new Course(exam.getExamCourse(),"hit"));
                allExams.add(exam1);
            }
        }
        return  allExams;
    }
    @Transactional
    public static List<ExamToExecute> getAllExamsForTeacher(Session session, int teacherId)
    {
        String queryString=" FROM ExamToExecute WHERE teacherThatExecuted.id = : id";
        Query query = session.createQuery(queryString,ExamToExecute.class);
        query.setParameter("id",teacherId);
        List<ExamToExecute> list=new ArrayList<>();
        boolean addExam=false;
        for(ExamToExecute compExam:(List<ExamToExecute>)(query.getResultList()))
        {
            addExam=false;
            for(Copy copy:compExam.getCopies())
            {
                if(!copy.getGrade().isTeacherApprovement())
                {
                    addExam=true;
                }
            }
            if(addExam)list.add(compExam);
        }
        return list;
    }
    public static List<Teacher_Course> teacherCourses(Session session,int teacherId)
    {
        String queryString=" FROM Teacher_Course WHERE teacher.id = : id";
        Query query = session.createQuery(queryString,Teacher_Course.class);
        query.setParameter("id",teacherId);
        return  (List<Teacher_Course>)query.getResultList();
    }
    public static List<ExamToExecute> getAllExamsToExecute(Session session, int teacherId, String date) throws ParseException {
        counter++;
        if(counter==2)
            System.out.println("hi");
        String queryString=" FROM ExamToExecute WHERE teacherThatExecuted.id = : id";
        Query query = session.createQuery(queryString,ExamToExecute.class);
        query.setParameter("id",teacherId);
        List<ExamToExecute> list=(List<ExamToExecute>)(query.getResultList());
        List<ExamToExecute> list1=new ArrayList<>();
        for(ExamToExecute compExam:list)
        {
            String endDate=toNewDate(compExam.getDateOfExam(),compExam.getExam().getTime());
            if(compareDates(date,compExam.getDateOfExam(),endDate))
            {
                ExamToExecute exam=new ExamToExecute(compExam);
                exam.setExam(new Exam(compExam.getExam(),"all needed"));
                list1.add(exam);
            }
        }
        return list1;
    }
    public static String toNewDate(String date,int time) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = df.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, time);
        String newDate = df.format(cal.getTime());
        return newDate;
    }

    public static Boolean compareDates(String date1,String date2,String date3) throws ParseException {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1 = sdFormat.parse(date1);
        Date d2 = sdFormat.parse(date2);
        Date d3=sdFormat.parse(date3);
        if(d1.compareTo(d2)<0)return false; // if d1 occur before d2 return false
        if(d3.compareTo(d1)>0)return true;  // if d3 occur after d1 return true
        return false;
    }
    public static Exam getExamById(Session session,int id)
    {
        String queryString=" FROM Exam WHERE id = : id";
        Query query = session.createQuery(queryString,Exam.class);
        query.setParameter("id",id);
       return  (Exam) (query.getResultList().get(0));
    }
    public static List<Course> getCoursesById(Session session,List<Integer> courseIndexes)
    {
        List<Course> courses=new ArrayList<>();
        int size=courseIndexes.size();
        for(int i=0;i<size;i++)
        {
            String queryString=" FROM Course WHERE id = : id";
            Query query = session.createQuery(queryString,Course.class);
            query.setParameter("id",courseIndexes.get(i));
            courses.add((Course) (query.getResultList().get(0)));
        }
        return courses;
    }
    public static Subject getSubjectById(Session session,int subjectId)
    {
        String queryString=" FROM Subject WHERE id = : id";
        Query query = session.createQuery(queryString,Subject.class);
        query.setParameter("id",subjectId);
        return  (Subject) (query.getResultList().get(0));
    }
    public static Course getCourseById(Session session,int subjectId)
    {
        String queryString=" FROM Course WHERE id = : id";
        Query query = session.createQuery(queryString,Course.class);
        query.setParameter("id",subjectId);
        return  (Course) (query.getResultList().get(0));
    }
    public static List<Question> getQuestionsById(Session session, List<Integer> questionsIds)
    {
        List<Question> questions=new ArrayList<>();
        int size=questionsIds.size();
        for(int i=0;i<size;i++)
        {
            String queryString=" FROM Question WHERE id = : id";
            Query query = session.createQuery(queryString,Question.class);
            query.setParameter("id",questionsIds.get(i));
            questions.add((Question) (query.getResultList().get(0)));
        }
        return questions;
    }
    public static List<Subject> getSubjectsById(Session session, List<Integer> subjectIds)
    {
        List<Subject> subjects=new ArrayList<>();
        int size=subjectIds.size();
        for(int i=0;i<size;i++)
        {
            String queryString=" FROM Subject WHERE id = : id";
            Query query = session.createQuery(queryString,Subject.class);
            query.setParameter("id",subjectIds.get(i));
            subjects.add((Subject) (query.getResultList().get(0)));
        }
        return subjects;
    }
    public static ExamToExecute getExamToExeById(Session session,int id)
    {
        String queryString=" FROM ExamToExecute WHERE id = : id";
        Query query = session.createQuery(queryString,ExamToExecute.class);
        query.setParameter("id",id);
        return  (ExamToExecute) (query.getResultList().get(0));
    }

}
