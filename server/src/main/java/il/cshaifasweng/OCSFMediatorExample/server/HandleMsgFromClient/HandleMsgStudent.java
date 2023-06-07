package il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import aidClasses.Warning;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetEducational;
import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;

import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HandleMsgStudent {
    public static boolean handleStudent(Session session, Message msgFromClient, String contentOfMsg, ConnectionToClient client) throws Exception {
        if (contentOfMsg.equals("#get exam copy")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();;
            int gradeId = (int) dataFromClient.get(0);
            int studentId = (int) dataFromClient.get(1);
            String q = "from Grade where student.id='"+studentId+"'";
            Query query=session.createQuery(q);
            List<Grade> grades = (List<Grade>) (query.getResultList());
            Copy copy = grades.get(gradeId).getExamCopy();
            List<Object> objects = new ArrayList<>();
            objects.add(0, copy.getCompExamToExecute());
            objects.add(1, copy.getAnswers());
            Message msgToClient = new Message("exam copy", objects);
            client.sendToClient(msgToClient);
            return true;
        }
        if (contentOfMsg.equals("#check code validation"))
        {
            String code = (String) msgFromClient.getObj().toString();
            String q = "from ExamToExecute where code = '"+ code +"'";
            Query query=session.createQuery(q);
            List<ExamToExecute> examToExecute=(List<ExamToExecute>) (query.getResultList());
            if (examToExecute.size() == 0){
                Warning warning = new Warning("code is not correct");
                try {
                    client.sendToClient(warning);
                    System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /////checking if the student solved this exam before
            String q1 = "from Grade where examCopy.id = '"+examToExecute.get(0).getId()+"'";
            Query query1 = session.createQuery(q1);
            List<Grade> grades = query1.getResultList();
            if (grades.size() != 0) {
                Warning warning = new Warning("You Already Did This Exam");
                try {
                    client.sendToClient(warning);
                    System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ////

            else {
                Message msgToClient = new Message("write id to start", examToExecute.get(0));
                client.sendToClient(msgToClient);
            }

            return true;
        }
        if (contentOfMsg.equals("#check id")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();;
            String userId = (String) dataFromClient.get(0);
            String connectUserId = (String) dataFromClient.get(1);
            if (!userId.equals(connectUserId)) {
                Warning warning = new Warning("Your ID is not correct");
                try {
                    client.sendToClient(warning);
                    System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Message msgToClient = new Message("do exam");
                client.sendToClient(msgToClient);
                return true ;
            }
        }
        if (contentOfMsg.equals("#submitted on the time")) {
            try {
                Message msgToClient = new Message("Submitted successfully", msgFromClient.getObj());
                client.sendToClient(msgToClient);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (contentOfMsg.equals("#create student copy and grade")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();
            String studentAnswers = (String) dataFromClient.get(0);
            Student user = (Student) dataFromClient.get(1);
            ComputerizedExamToExecute compExam = (ComputerizedExamToExecute) dataFromClient.get(2);
            int grade = (int) dataFromClient.get(3);
            //boolean submitOnTime = (boolean) dataFromClient.get(4);
            SimpleServer.createGradeAndCopyToStudent(studentAnswers, user, compExam, grade);
        }
        if (contentOfMsg.equals("#update student answers")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();
            String studentAnswers = (String) dataFromClient.get(0);
            Student user = (Student) dataFromClient.get(1);
            ComputerizedExamToExecute compExam = (ComputerizedExamToExecute) dataFromClient.get(2);
            int grade = (int) dataFromClient.get(3);
            boolean submitOnTime = (boolean) dataFromClient.get(4);
            SimpleServer.updateGradeAndCopyToStudent(studentAnswers, user, compExam, grade, submitOnTime);
        }
        if (contentOfMsg.equals("#time finished")) {
            Warning warning = new Warning("The Exam Time Ended");
            try {
                client.sendToClient(warning);
                System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                Message msgToClient = new Message("exam done", msgFromClient.getObj());
                client.sendToClient(msgToClient);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        if (contentOfMsg.equals("CheckID")) {
            String id = (String) msgFromClient.getObj();
            String answer;
            boolean check = GetEducational.checkID(session, id);
            if (check) {
                GlobalDataSaved.AddFlag = true;
                answer = "The User was Added to the System";
            } else {
                GlobalDataSaved.AddFlag = false;
                answer = "User Already in the System";
            }
            Warning warning = new Warning(answer);
            try {
                client.sendToClient(warning);
                System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }


            Message messageToClient = new Message(answer);

            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
