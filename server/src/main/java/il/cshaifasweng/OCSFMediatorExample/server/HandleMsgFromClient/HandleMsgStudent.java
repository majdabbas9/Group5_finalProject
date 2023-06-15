package il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import aidClasses.Warning;
import antlr.debug.MessageEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ManualExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetEducational;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetExamBuliding;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetGrading;
import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.greenrobot.eventbus.EventBus;
import org.hibernate.Session;

import javax.persistence.Query;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

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
            String now = LocalDateTime.now().toString();
            String year=now.substring(0,4);
            String month=now.substring(5,7);
            String day=now.substring(8,10);
            String hourMinute=now.substring(11,16);
            String date="";
            date+=year+"-"+month+"-"+day+" "+hourMinute;
            if(GetExamBuliding.compareDates(date,examToExecute.get(0).getDateOfExam(),GetExamBuliding.toNewDate(
                    examToExecute.get(0).getDateOfExam(),examToExecute.get(0).getExam().getTime()+examToExecute.get(0).getExtraTime())))
            {

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
            Message msgToClient = new Message("write id to start",GetExamBuliding.CopyExamToExe(examToExecute.get(0)));
            client.sendToClient(msgToClient);

            return true;
        }
        if (contentOfMsg.equals("#check id")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();;
            String userId = (String) dataFromClient.get(0);
            String connectUserId = (String) dataFromClient.get(1);
            String examCode = (String) dataFromClient.get(2);
            String q1 = "from Grade where student.userID = '"+connectUserId+"'";
            Query query1 = session.createQuery(q1);
            List<Grade> grades = query1.getResultList();
            for (Grade grade : grades) {
                if (grade.getExamCopy().getCompExamToExecute().getCode() == Integer.parseInt(examCode)) {
                    Warning warning = new Warning("You Already Did This Exam");
                    try {
                        client.sendToClient(warning);
                        System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
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
            int [] ides = SimpleServer.createGradeAndCopyToStudent(copy,grade,userId, examId);
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
