package il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient;

import aidClasses.GlobalDataSaved;
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
import java.util.ArrayList;
import java.util.List;

public class HandleMsgPrincipal {
    public static boolean handlePrincipal(Session session, Message msgFromClient, String contentOfMsg, ConnectionToClient client) throws Exception {
        if (contentOfMsg.equals("#add teacher")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();
            SimpleServer.addTeacher((Teacher) dataFromClient.get(0),(List<Integer>) dataFromClient.get(1),(List<Integer>) dataFromClient.get(2));
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
            SimpleServer.addStudent((Student) dataFromClient.get(0),(List<Integer>) dataFromClient.get(1),(List<Integer>) dataFromClient.get(2));
            Message messageToClient = new Message("Student Added Successfully");
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("AllExamsToPrincipal")) {
            List<Exam> list = GetEducational.getAllExams(session);
            Message messageToClient = new Message("AllExamsToPrincipal",list);
            try {
                client.sendToClient(messageToClient);
            }catch (IOException e ){
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("UpdateAllExamsToPrincipal")) {
            List<Exam> list = GetEducational.getAllExams(session);
            Message messageToClient = new Message("UpdateAllExamsToPrincipal",list);
            try {
                client.sendToClient(messageToClient);
            }catch (IOException e ){
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("AllQuestionsToPrincipal")) {
        List<Question> list = GetEducational.getAllQuestions(session);
        Message messageToClient = new Message("AllQuestionsToPrincipal",list);
        try {
            client.sendToClient(messageToClient);
        }catch (IOException e ){
            e.printStackTrace();
        }
            return true;
    }
        if (contentOfMsg.equals("UpdateAllQuestionsToPrincipal")) {
            List<Question> list = GetEducational.getAllQuestions(session);
            Message messageToClient = new Message("UpdateAllQuestionsToPrincipal",list);
            try {
                client.sendToClient(messageToClient);
            }catch (IOException e ){
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("AllGradesToPrincipal")) {
            List<Grade> list = GetEducational.getAllGrades(session);
            Message messageToClient = new Message("AllGradesToPrincipal",list);
            try {
                client.sendToClient(messageToClient);
            }catch (IOException e ){
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("UpdateAllGradesToPrincipal")) {
            List<Grade> list = GetEducational.getAllGrades(session);
            Message messageToClient = new Message("UpdateAllGradesToPrincipal",list);
            try {
                client.sendToClient(messageToClient);
            }catch (IOException e ){
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#AllSubjectsToPrincipal")) {

            List<Subject> list = GetEducational.getAllSubjects(session);
            Message messageToClient = new Message("All Subjects Given to principal");
            messageToClient.setObj(list);
            try {
                client.sendToClient(messageToClient);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("StatisticalDataForPrincipal")) {
            //creating the object which I send to the client
            List<Object> list = new ArrayList<>();
            list.add(GetEducational.getAllExams(session));
            list.add(GetEducational.getAllStudents(session));
            list.add(GetEducational.getAllCourses(session));
            list.add(GetEducational.getAllTeachers(session));

            try {
                Message messageToClient = new Message("StatisticalDataForPrincipal");
                messageToClient.setObj(list);
                client.sendToClient(messageToClient);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        if (contentOfMsg.equals("GetGradesForStatisticalData")) {
            try {
                Message messageToClient = new Message("GetGradesForStatisticalData");
                messageToClient.setObj(GetEducational.getAllGrades(session));
                client.sendToClient(messageToClient);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
            return false;
    }
}
