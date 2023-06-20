package il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient;

import aidClasses.Message;
import aidClasses.Warning;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetExamBuliding;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetGrading;
import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HandleMsgStudent {
    public static boolean handleStudent(Session session, Message msgFromClient, String contentOfMsg, ConnectionToClient client) throws Exception {
        if (contentOfMsg.equals("#get exam copy")) {
            int gradeId = (int) msgFromClient.getObj();
            Grade grade = GetGrading.getGradeById(session,gradeId);
            List<Object> objects = new ArrayList<>();
            List<Question> questionList = GetExamBuliding.copyQuestions(GetExamBuliding.getExamById(session,grade.getExamCopy().getCompExamToExecute().getExam().getId()).getExamQuestions());
            objects.add(0, questionList);
            objects.add(1, grade);
            Message msgToClient = new Message("exam copy", objects);
            client.sendToClient(msgToClient);
            return true;
        }
        if (contentOfMsg.equals("#check code validation"))
        {
            List<Object> dataFromClient=(List<Object>) msgFromClient.getObj();
            int userId=(int)dataFromClient.get(1);
            String code = (String) dataFromClient.get(0);
            String queryString = "SELECT code FROM ExamToExecute WHERE code="+"'"+code+"'";
            org.hibernate.Query<Object[]> query = session.createQuery(queryString, Object[].class);
            List<Object[]> results = query.getResultList();
            if (results.size() == 0){
                Warning warning = new Warning("code is not correct");
                try {
                    client.sendToClient(warning);
                    System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String now = LocalDateTime.now().toString();
            String year=now.substring(0,4);
            String month=now.substring(5,7);
            String day=now.substring(8,10);
            String hourMinute=now.substring(11,16);
            String date="";
            date+=year+"-"+month+"-"+day+" "+hourMinute;
            ExamToExecute examToExecute=GetExamBuliding.CopyExamToExe(session.getSession(),code,date);
            if(examToExecute!=null)
            {
                String queryString1 = "select g.id FROM Grade g join  g.examCopy e join  e.compExamToExecute comp WHERE comp.code = :code and g.student.id= :id";
                org.hibernate.Query<Object[]> query1 = session.createQuery(queryString1, Object[].class);
                query1.setParameter("code", code);
                query1.setParameter("id", userId);
                List<Object[]> results1 = query1.getResultList();
                if(results1.size()>0)
                {
                    Warning warning = new Warning("You Already Did This Exam");
                    try {
                        client.sendToClient(warning);
                        System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Message msgToClient = new Message("write id to start",examToExecute);
                client.sendToClient(msgToClient);

                return true;
            }
            else {
                Warning warning = new Warning("You Can't Do This Exam");
                try {
                    client.sendToClient(warning);
                    System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (contentOfMsg.equals("#check id")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();;
            String userId = (String) dataFromClient.get(0);
            String connectUserId = (String) dataFromClient.get(1);
            //String examCode = (String) dataFromClient.get(2);
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
        if (contentOfMsg.equals("#create student copy and grade")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();
            Copy copy = (Copy) dataFromClient.get(0);
            int userId = (int) dataFromClient.get(3);
            Grade grade = (Grade) dataFromClient.get(1);
            int examId = (int) dataFromClient.get(2);
            int [] ides;
            if(dataFromClient.size()>4)
            {
                 ides = SimpleServer.createGradeAndCopyToStudent(copy,grade,userId, examId,true);
            }
            else
            {
                ides = SimpleServer.createGradeAndCopyToStudent(copy,grade,userId, examId,false);
            }
            Message msgToClient = new Message("save grade id and copy id", ides);
            client.sendToClient(msgToClient);
            return true;
        }
        if (contentOfMsg.equals("#update student answers")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();
            String studentAnswers = (String) dataFromClient.get(4);
            int userId = (int) dataFromClient.get(3);
            int gradeId = (int) dataFromClient.get(1);
            int copyId = (int) dataFromClient.get(0);
            boolean submitOnTime = (boolean) dataFromClient.get(5);
            int grade = (int) dataFromClient.get(6);
            int examId = (int) dataFromClient.get(2);
            SimpleServer.updateGradeAndCopyToStudent(copyId, gradeId,studentAnswers, userId, examId, grade, submitOnTime);
            if (submitOnTime) {
                Message msgToClient = new Message("exam done on time", studentAnswers);
                client.sendToClient(msgToClient);
                return true;
            }
            else {
                Warning warning = new Warning("The Exam Time Ended");
                try {
                    client.sendToClient(warning);
                    System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                    Message msgToClient = new Message("exam finished time", studentAnswers);
                    client.sendToClient(msgToClient);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (contentOfMsg.equals("#show student grades"))
        {
            Message msgToClient = new Message("student grades", GetGrading.getStudentGrades(session,(int) msgFromClient.getObj()));
            client.sendToClient(msgToClient);
            return true;
        }
        return false;
    }

}
