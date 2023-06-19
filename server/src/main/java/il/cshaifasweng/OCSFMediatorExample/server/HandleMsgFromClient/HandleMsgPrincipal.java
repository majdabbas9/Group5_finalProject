package il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import aidClasses.Warning;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetEducational;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetExamBuliding;
import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.LoggedInClient;
import org.hibernate.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HandleMsgPrincipal {
    public static boolean handlePrincipal(Session session, Message msgFromClient, String contentOfMsg, ConnectionToClient client) throws Exception {
        if (contentOfMsg.equals("#add teacher")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();
            String id = ((Teacher) dataFromClient.get(0)).getUserID();
            boolean check = GetEducational.checkID(session, id);
            if (!check) {
                GlobalDataSaved.AddFlag = false;
                Warning warning = new Warning("User Already in the System");
                try {
                    client.sendToClient(warning);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                SimpleServer.addTeacher((Teacher) dataFromClient.get(0), (List<Integer>) dataFromClient.get(1), (List<Integer>) dataFromClient.get(2));
                Message messageToClient = new Message("Teacher Added Successfully");
                try {
                    client.sendToClient(messageToClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        if (contentOfMsg.equals("#add student")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();
            String id = ((Student) dataFromClient.get(0)).getUserID();
            boolean check = GetEducational.checkID(session, id);
            if (!check) {
                GlobalDataSaved.AddFlag = false;
                Warning warning = new Warning("User Already in the System");
                try {
                    client.sendToClient(warning);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                SimpleServer.addStudent((Student) dataFromClient.get(0), (List<Integer>) dataFromClient.get(1), (List<Integer>) dataFromClient.get(2));
                Message messageToClient = new Message("Student Added Successfully");
                try {
                    client.sendToClient(messageToClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        if(contentOfMsg.equals("#showExamQuestionForPrincipal"))
        {
            Message msgToClient=new Message("exam questions for principal",GetEducational.getExamQuestionsByExamId(session,(int) msgFromClient.getObj()));
            try {
                client.sendToClient(msgToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("AllExamsToPrincipal")) {
            List<Exam> list = GetEducational.getAllExamsAllNeeded(session);
            Message messageToClient = new Message("AllExamsToPrincipal",list);
            try {
                client.sendToClient(messageToClient);
            }catch (IOException e ){
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("UpdateAllExamsToPrincipal")) {
            List<Exam> list = GetEducational.getAllExamsAllNeeded(session);
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
            list.add(GetEducational.getAllExamsAllNeeded(session));
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
        if (contentOfMsg.equals("ExtraTime")) {
            try {
                Message message = new Message("ExtraTime", GetEducational.getAllRequests(session));
                client.sendToClient(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if (contentOfMsg.equals("Do Not Add Extra Time")) {
            session.beginTransaction();
            session.clear();
            ExamToExecute examToExecute = GetExamBuliding.getExamToExeById(session,(int) msgFromClient.getObj());
            examToExecute.setExtraTime(0);
            examToExecute.setIsExtraNeeded(0);
            session.update(examToExecute);
            session.flush();
            session.getTransaction().commit();
            try {
                Message message = new Message("Do Not Add Extra Time",GetEducational.getAllRequests(session));
                client.sendToClient(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if (contentOfMsg.equals("Add Extra Time For CompExam")) {
            ExamToExecute examToExecute = GetExamBuliding.getExamToExeById(session,(int) msgFromClient.getObj());
            List<ConnectionToClient> list = updateForExtraTime(session,examToExecute);
            try {
                Message messageToClient = new Message("ExtraTime", GetEducational.getAllRequests(session));
                client.sendToClient(messageToClient);
                Message message = new Message("AddTimeToStudentForCompExam",examToExecute.getExtraTime());
                for (ConnectionToClient connectionToClient : list){
                    connectionToClient.sendToClient(message);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if (contentOfMsg.equals("Add Extra Time For ManualExam")) {
            ExamToExecute examToExecute = GetExamBuliding.getExamToExeById(session,(int) msgFromClient.getObj());
            List<ConnectionToClient> list = updateForExtraTime(session,examToExecute);
            try {
                Message message = new Message("AddTimeToStudentForManualExam",examToExecute.getExtraTime());
                for (ConnectionToClient connectionToClient : list){
                    connectionToClient.sendToClient(message);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
            return false;
    }

    static List<ConnectionToClient> updateForExtraTime(Session session, ExamToExecute examToExecute) throws Exception {
        session.beginTransaction();
        session.clear();
        examToExecute.setIsExtraNeeded(2);
        session.update(examToExecute);
        session.flush();
        session.getTransaction().commit();

        List<ConnectionToClient> list = new ArrayList<>();
        for (Grade grade : GetEducational.getAllGrades(session)){
            if (grade.getExamCopy().getCompExamToExecute().getId() ==
                    examToExecute.getId() && grade.getGrade() == -1){
                for (LoggedInClient loggedInClient : SimpleServer._LoggedInList){
                    if (loggedInClient.get_id().equals(grade.getStudent().getUserID())){
                        list.add(loggedInClient.getClient());
                    }
                }
            }
        }
        return list;
    }
}
