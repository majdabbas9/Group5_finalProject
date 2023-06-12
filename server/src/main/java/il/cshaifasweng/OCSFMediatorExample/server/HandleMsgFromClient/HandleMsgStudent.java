package il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import aidClasses.Warning;
import antlr.debug.MessageEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ManualExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetEducational;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetExamBuliding;
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

public class HandleMsgStudent {
    public static boolean handleStudent(Session session, Message msgFromClient, String contentOfMsg, ConnectionToClient client) throws Exception {
        if (contentOfMsg.equals("#get exam copy")) {
            Grade grade = (Grade) msgFromClient.getObj();
            Message msgToClient = new Message("exam copy", grade);
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
            /*String examDate[];
            String date, time;
            examDate = examToExecute.get(0).getDateOfExam().split(" ");
            date = examDate[0];
            time = examDate[1];
            int examTime = examToExecute.get(0).getExam().getTime();
            int examHour=0, examMinutes=0;
            while (examTime > 60) {
                examHour++;
                examTime -= 60;
            }
            examMinutes = examTime;
            String now = LocalDateTime.now().toString();
            String year=now.substring(0,4);
            String month=now.substring(5,7);
            String day=now.substring(8,10);
            String hour=now.substring(11,13);
            String minutes = now.substring(14,16);
            String dateElements[] = date.split("-");
            String timeElements[] = time.split(":");
            int t1 = Integer.parseInt(timeElements[0]);
            int t2 = Integer.parseInt(timeElements[1]);
            int ExtraTime = examToExecute.get(0).getExtraTime();
            if (examToExecute.get(0).getIsExtraNeeded() == 2){
                while (ExtraTime >= 60){
                    examHour++;
                    ExtraTime -= 60;
                }
            }else {
                ExtraTime = 0;
            }
            if (dateElements[0].equals(year) && dateElements[1].equals(month) && dateElements[2].equals(day)
                    && t1 + examHour >= Integer.parseInt(hour) && t2 + examMinutes + ExtraTime >= Integer.parseInt(minutes)
                    && t1 <= Integer.parseInt(hour) && t2 <= Integer.parseInt(minutes)){

            }*/
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
            Message msgToClient = new Message("write id to start", examToExecute.get(0));
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
            String studentAnswers = (String) dataFromClient.get(0);
            Student user = (Student) dataFromClient.get(1);
            int grade = (int) dataFromClient.get(3);
            if (dataFromClient.get(2) instanceof ComputerizedExamToExecute){
                ComputerizedExamToExecute compExam = (ComputerizedExamToExecute) dataFromClient.get(2);
                SimpleServer.createGradeAndCopyToStudent(studentAnswers, user, compExam, grade,compExam.getNumOfStudentDoing()+1);
            }
            if (dataFromClient.get(2) instanceof ManualExamToExecute) {
                ManualExamToExecute manualExamToExecute = (ManualExamToExecute) dataFromClient.get(2);
                SimpleServer.createGradeAndCopyToManualExam(studentAnswers, user, manualExamToExecute, grade, manualExamToExecute.getNumOfStudentDoing()+1);
            }
            return true;
        }
        if (contentOfMsg.equals("#update student answers")) {
            List<Object> dataFromClient = (List<Object>) msgFromClient.getObj();
            String studentAnswers = (String) dataFromClient.get(0);
            Student user = (Student) dataFromClient.get(1);
            int grade = (int) dataFromClient.get(3);
            boolean submitOnTime = (boolean) dataFromClient.get(4);
            if (dataFromClient.get(2) instanceof ComputerizedExamToExecute){
                ComputerizedExamToExecute compExam = (ComputerizedExamToExecute) dataFromClient.get(2);
                SimpleServer.updateGradeAndCopyToStudent(studentAnswers, user, compExam, grade, submitOnTime);
            }
            if (dataFromClient.get(2) instanceof ManualExamToExecute) {
                ManualExamToExecute manualExamToExecute = (ManualExamToExecute) dataFromClient.get(2);
                SimpleServer.updateGradeAndCopyToManualExam(studentAnswers, user, manualExamToExecute, grade, submitOnTime);
            }
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
            User user = (User) msgFromClient.getObj();
            String q="from Grade where student='"+ user.getId() +"' and teacherApprovement='"+ 1 +"'";
            Query query=session.createQuery(q);
            List<Grade> grades = (List<Grade>) (query.getResultList());
            Message msgToClient = new Message("student grades",grades);
            client.sendToClient(msgToClient);
            return true;
        }
        return false;
    }

}
