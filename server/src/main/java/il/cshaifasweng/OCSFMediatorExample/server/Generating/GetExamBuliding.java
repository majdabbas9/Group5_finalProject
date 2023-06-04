package il.cshaifasweng.OCSFMediatorExample.server.Generating;

import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetExamBuliding {

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
                allExams.add(exam);
            }
        }
        return  allExams;
    }
    @Transactional
    public static List<ComputerizedExamToExecute> getAllCompExams(Session session, int teacherId)
    {
        String queryString=" FROM ComputerizedExamToExecute WHERE teacherThatExecuted.id = : id";
        Query query = session.createQuery(queryString,ComputerizedExamToExecute.class);
        query.setParameter("id",teacherId);
        List<ComputerizedExamToExecute> list=new ArrayList<>();
        boolean addCompExam=false;
        for(ComputerizedExamToExecute compExam:(List<ComputerizedExamToExecute>)(query.getResultList()))
        {
            addCompExam=false;
            for(Copy copy:compExam.getCopies())
            {
                if(!copy.getGrade().isTeacherApprovement())
                {
                    addCompExam=true;
                }
            }
            if(addCompExam)list.add(compExam);
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
    public static List<ComputerizedExamToExecute> getAllCompExamsNow(Session session, int teacherId, String date) throws ParseException {
        String queryString=" FROM ComputerizedExamToExecute WHERE teacherThatExecuted.id = : id";
        Query query = session.createQuery(queryString,ComputerizedExamToExecute.class);
        query.setParameter("id",teacherId);
        List<ComputerizedExamToExecute> list=new ArrayList<>();

        for(ComputerizedExamToExecute compExam:(List<ComputerizedExamToExecute>)(query.getResultList()))
        {
            String endDate=toNewDate(compExam.getDateOfExam(),compExam.getExam().getTime());
            if(compare2Dates(date,compExam.getDateOfExam())<=0 && compare2Dates(date,endDate)==1)
            {
                list.add(compExam);
            }
        }
        return list;
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

    public static int compare2Dates(String date1,String date2)
    {
        int year1,month1,day1,h1,m1,year2,month2,day2,h2,m2;
        day1=Integer.valueOf(date1.substring(0,2));day2=Integer.valueOf(date2.substring(8,10));
        month1=Integer.valueOf(date1.substring(3,5));month2=Integer.valueOf(date2.substring(5,7));
        year1=Integer.valueOf(date1.substring(6,10));year2=Integer.valueOf(date2.substring(0,4));
        h1=Integer.valueOf(date1.substring(11,13));h2=Integer.valueOf(date2.substring(11,13));
        m1=Integer.valueOf(date1.substring(14));m2=Integer.valueOf(date2.substring(14));
       if(year1>year2)return 0;
       if(year2>year1)return 1;
       if(month1>month2)return 0;
       if(month2>month1)return 1;
       if(day1>day2)return 0;
       if(day2>day1)return 1;
       if(h1>h2)return 0;
       if(h2>h1)return 1;
       if(m1>m2)return 0;
       if(m2>m1)return 1;
       return -1;
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

}
