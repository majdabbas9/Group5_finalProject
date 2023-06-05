package aidClasses.aidClassesForTeacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;

import java.util.ArrayList;
import java.util.List;

public class QuestionsExamsID {
    public static List<String> subjectListNames=new ArrayList<>();
    public static List<String> coursesListNames=new ArrayList<>();

    public static List<Integer> numOfQuestionsOfSubjects=new ArrayList<>();

    public static List<Integer> numOfExamsOfCourses=new ArrayList<>();
    public static boolean isInit=false;

    public static void init()
    {
        isInit=true;
        subjectListNames.add("math");
        coursesListNames.add("algebra");coursesListNames.add("algebra 2");coursesListNames.add("discrete mathematics");

        subjectListNames.add("computer science");
        coursesListNames.add("introduction to  computer science");coursesListNames.add("software engineering");coursesListNames.add("operating system");


        subjectListNames.add("physics");
        coursesListNames.add("Introductory Physics");coursesListNames.add("Classical Mechanics");coursesListNames.add("Electromagnetism");


        for(int i=1;i<=9;i++)
        {
            if(i<=3)
            {
                numOfExamsOfCourses.add(0);
                numOfQuestionsOfSubjects.add(0);
            }
            else
            {
                numOfExamsOfCourses.add(0);
            }
        }
    }
    public static String questionID(String subjectName,int subjectId){
        subjectId=subjectId-1;
        String questionID="";
        int i=0,place=0;
        for(String subject: QuestionsExamsID.subjectListNames)
        {
            if(subject.equals(subjectName))
            {
                place=i;
            }
            i++;
        }
        int questionNum=QuestionsExamsID.numOfQuestionsOfSubjects.get(place);
        QuestionsExamsID.numOfQuestionsOfSubjects.set(place,1+questionNum);
        if(subjectId<10)
        {
            questionID="0"+subjectId;
        }
        else
        {
            questionID= String.valueOf(subjectId);
        }
        if(questionNum<10)
        {
            questionID+="00"+questionNum;
        }
        if(questionNum <100 && questionNum>9)
        {
            questionID+="0"+questionNum;
        }
        if(questionNum>99)
        {
            questionID+=questionNum;
        }
        return questionID;
    }
    public static String examID(String courseName,int subjectId,int courseId)
    {
        String exam_ID="";
        subjectId=subjectId-1;courseId=courseId-1;
        int i=0,place=0;
        for(String course: QuestionsExamsID.coursesListNames)
        {
            if(course.equals(courseName))
            {
                place=i;
            }
            i++;
        }
        int examNum=QuestionsExamsID.numOfExamsOfCourses.get(place);
        QuestionsExamsID.numOfExamsOfCourses.set(place,1+examNum);
        if(subjectId<10)
        {
            exam_ID+="0"+subjectId;
        }
        else
        {
            exam_ID+=subjectId;
        }
        if(courseId<10)
        {
            exam_ID+="0"+courseId;
        }
        else
        {
            exam_ID+=courseId;
        }
        if(examNum<10)
        {
            exam_ID+="0"+examNum;
        }
        else
        {
            exam_ID+=examNum;
        }
        return exam_ID;
    }
}
