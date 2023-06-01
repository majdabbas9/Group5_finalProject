package il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient;

import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetEducational;
import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

public class HandleMsgPrincipal {
    public static boolean handlePrincipal(Session session, Message msgFromClient, String contentOfMsg, ConnectionToClient client) throws Exception {
        if (contentOfMsg.equals("#add teacher")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();
            SimpleServer.addTeacher((Teacher) dataFromClient.get(0),(List<Subject>) dataFromClient.get(1),(List<Course>) dataFromClient.get(2));
            Message messageToClient = new Message("Teacher Added Successfully");
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#add student")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();
            SimpleServer.addStudent((Student) dataFromClient.get(0),(List<Subject>) dataFromClient.get(1),(List<Course>) dataFromClient.get(2));
            Message messageToClient = new Message("Student Added Successfully");
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("AllExamsToPrincipal")) {
            List<Exam> list = (List<Exam>) msgFromClient.getObj();
            list = GetEducational.getAllExams(session);
            Message messageToClient = new Message("AllExamsToPrincipal",list);
            try {
                client.sendToClient(messageToClient);
            }catch (IOException e ){
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("AllQuestionsToPrincipal")) {
        List<Question> list = (List<Question>) msgFromClient.getObj();
        list = GetEducational.getAllQuestions(session);
        Message messageToClient = new Message("AllQuestionsToPrincipal",list);
        try {
            client.sendToClient(messageToClient);
        }catch (IOException e ){
            e.printStackTrace();
        }
            return true;
    }
        if (contentOfMsg.equals("UpdateAllQuestionsToPrincipal")) {
            List<Question> list = (List<Question>) msgFromClient.getObj();
            list = GetEducational.getAllQuestions(session);
            Message messageToClient = new Message("UpdateAllQuestionsToPrincipal",list);
            try {
                client.sendToClient(messageToClient);
            }catch (IOException e ){
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("AllGradesToPrincipal")) {
            List<Grade> list = (List<Grade>) msgFromClient.getObj();
            list = GetEducational.getAllGrades(session);
            Message messageToClient = new Message("AllGradesToPrincipal",list);
            try {
                client.sendToClient(messageToClient);
            }catch (IOException e ){
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#AllSubjectsToPrincipal")) {

            List<Subject> list = (List<Subject>) msgFromClient.getObj();
            list.addAll(GetEducational.getAllSubjects(session));
            Message messageToClient = new Message("All Subjects Given to principal");
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
