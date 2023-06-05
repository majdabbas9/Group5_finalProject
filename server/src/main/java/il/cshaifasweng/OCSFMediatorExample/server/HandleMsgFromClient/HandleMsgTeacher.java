package il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient;

import aidClasses.Message;
import aidClasses.Warning;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetEducational;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetExamBuliding;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetUsers;
import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;

import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HandleMsgTeacher {
    public static boolean handleTeacher(Session session, Message msgFromClient, String contentOfMsg, ConnectionToClient client) throws Exception {
        if (contentOfMsg.equals("#addQuestion")) {
            List<Object> dataFromClient=(List<Object>) msgFromClient.getObj();
            Question question = (Question) (dataFromClient.get(0));

            String queryString="FROM Question WHERE studentNotes = : studentNotes";
            Query query = session.createQuery(queryString,Question.class);
            query.setParameter("studentNotes",(question.getStudentNotes()));
            List<Question> res=query.getResultList();
            session.clear();

            if(res.size()!=0)
            {
                Warning warning = new Warning("the question already existed");
                try {
                    client.sendToClient(warning);
                    System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            SimpleServer.addQuestion(question,(List<Integer>)dataFromClient.get(1),(int)dataFromClient.get(2),(int)dataFromClient.get(3));
            Message messageToClient = new Message("added the question successfully");
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#addExam")) {
            List<Object> dataFromClient=(List<Object>) msgFromClient.getObj();
            SimpleServer.addExam((Exam) dataFromClient.get(0),(int) dataFromClient.get(1), (int) dataFromClient.get(2),(int)dataFromClient.get(3),(List<Integer>) dataFromClient.get(4),(List<Integer>)dataFromClient.get(5));
            Message messageToClient = new Message("added the exam successfully");
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#showAllCourseQuestion"))
        {
            int courseId=(int) msgFromClient.getObj();
            String queryString="FROM Course_Question WHERE course.id = : courseId";
            Query query = session.createQuery(queryString, Course_Question.class);
            query.setParameter("courseId",courseId);
            List<Course_Question> res=query.getResultList();
            List<Question> questionsList=new ArrayList<>();
            for(Course_Question cq:res)
            {
                questionsList.add(cq.getQuestion());
            }
            try {
                Message msgToClient=new Message("course questions",questionsList);
                client.sendToClient(msgToClient);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (contentOfMsg.equals("#addCompExam")) {
            List<Object> dataFromClient=(List<Object>) msgFromClient.getObj();

            String queryString="FROM ComputerizedExamToExecute WHERE code = : code";
            Query query = session.createQuery(queryString, ComputerizedExamToExecute.class);
            query.setParameter("code",((ComputerizedExamToExecute)dataFromClient.get(0)).getCode());
            List<ComputerizedExamToExecute> res=query.getResultList();
            if(res.size()!=0)
            {
                Warning warning = new Warning("code already used!");
                try {
                    client.sendToClient(warning);
                    System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SimpleServer.addCompExam((ComputerizedExamToExecute)dataFromClient.get(0),(int)dataFromClient.get(1),(int) dataFromClient.get(2));
            Message messageToClient = new Message("added the CompExam successfully");
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#showAllExamsForTeacher"))
        {
            int teacherId=(int)msgFromClient.getObj();
            List<Teacher_Course> teacherCourses=new ArrayList<>();
            List<Exam> ExamsToClient= GetExamBuliding.getAllExamsForCourses(session,teacherId);
            Message messageToClient = new Message("sending all exams for teacher",ExamsToClient);
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#showAllCompExamsForTeahcerToApprove"))
        {
            int teacherId=(int)msgFromClient.getObj();
            List<ComputerizedExamToExecute> compExams= GetExamBuliding.getAllCompExams(session,teacherId);
            Message messageToClient = new Message("sending all compExams for teacher",compExams);
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if(contentOfMsg.equals("#teacherSubjects"))
        {
            int id=(int)msgFromClient.getObj();
            List<Subject> teacherSubjects= GetUsers.getTeacherSubjects(session,id);
            Message messageToClient = new Message("sending teacher subjects",teacherSubjects);
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if(contentOfMsg.equals("#teacherCouses"))
        {
            int id=(int)msgFromClient.getObj();
            List<Course> teacherCourses=GetUsers.getTeacherCourses(session,id);
            Message messageToClient = new Message("sending teacher courses",teacherCourses);
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if(contentOfMsg.equals("#showAllCompExamGrades"))
        {
            int compExamId=(int)msgFromClient.getObj();
            String queryString="FROM ComputerizedExamToExecute WHERE id = : id";
            Query query = session.createQuery(queryString,ComputerizedExamToExecute.class);
            query.setParameter("id",compExamId);

            List<ComputerizedExamToExecute> compExam=(List<ComputerizedExamToExecute>)(query.getResultList());

            List<Grade> compExamGrades=new ArrayList<>();
            for(Copy copy : compExam.get(0).getCopies())
            {
                if(!copy.getGrade().isTeacherApprovement())compExamGrades.add(copy.getGrade());
            }
            Message messageToClient = new Message("show all grades",compExamGrades);
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if(contentOfMsg.equals("#teacherApproveGrade"))
        {
            List<Object> dataFromClient=(List<Object>) msgFromClient.getObj();
            int gradeId=(int) dataFromClient.get(0);
            int newGrade=(int) dataFromClient.get(1);
            String notes=(String)dataFromClient.get(2);
            SimpleServer.teacherApproveStudentGrade(gradeId,newGrade,notes);
            Message messageToClient = new Message("the grade updated",dataFromClient);
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#show student grades"))
        {
            User user = (User) msgFromClient.getObj();
            String q="from Grade where student='"+ user.getId() +"' and teacherApprovement='"+ 1 +"'";
            Query query=session.createQuery(q);
            List<Grade> grades = (List<Grade>) (query.getResultList());
            Message msgToClient = new Message("student grades",grades);
            client.sendToClient(msgToClient);
            return true;
        }
        if(contentOfMsg.equals("#getTeacherCompExams"))
        {
            Teacher teacher=(Teacher) msgFromClient.getObj();
            String queryString="FROM ComputerizedExamToExecute WHERE teacherThatExecuted.id = "+teacher.getId();
            Query query = session.createQuery(queryString,ComputerizedExamToExecute.class);
            List<ComputerizedExamToExecute> compExams=(List<ComputerizedExamToExecute>) (query.getResultList());
            Message messageToClient = new Message("teacher compExams",compExams);
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if(contentOfMsg.equals("#getTeacherCompExamsNow"))
        {
            List<Object> dataFromClient=(List<Object>) msgFromClient.getObj();
            Message messageToClient = new Message("teacher compExams now",GetExamBuliding.getAllCompExamsNow(session,
                    (int)dataFromClient.get(0),(String) dataFromClient.get(1)));
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if(contentOfMsg.equals("#allSubjects"))
        {
            List<Subject> list = new ArrayList<>();
            list.addAll(GetEducational.getAllSubjects(session));
            Message messageToClient = new Message("all subjects for init",list);
            messageToClient.setObj(list);
            try {
                client.sendToClient(messageToClient);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
