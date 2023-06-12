package il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient;

import aidClasses.Message;
import aidClasses.Warning;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Course_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Teacher_Course;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.*;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetEducational;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetExamBuliding;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetGrading;
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
        if (contentOfMsg.equals("#addQuestionToCopy")) {
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
            Message messageToClient = new Message("copied the question successfully");
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }//
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
        if (contentOfMsg.equals("#addExamToCopy")) {
            List<Object> dataFromClient=(List<Object>) msgFromClient.getObj();
            SimpleServer.addExam((Exam) dataFromClient.get(0),(int) dataFromClient.get(1), (int) dataFromClient.get(2),(int)dataFromClient.get(3),(List<Integer>) dataFromClient.get(4),(List<Integer>)dataFromClient.get(5));
            Message messageToClient = new Message("copied the exam successfully");
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#allExamsForTeacherToCopy")) {
            Message messageToClient = new Message("all the exams for teacher copy",GetExamBuliding.getAllExamsForTeacher(session));
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#allQuestionForExamsForTeacherToCopy")) {
            List<Object> dataFromClient=(List<Object>) msgFromClient.getObj();
            Message messageToClient = new Message("all the questions for teacher exam to copy",GetExamBuliding.getAllQuestionsForExamToCopy(session,
                    (int)(dataFromClient.get(0)),(int)(dataFromClient.get(1))));
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
                questionsList.add(new Question(cq.getQuestion()));
            }
            try {
                Message msgToClient=new Message("course questions",questionsList);
                client.sendToClient(msgToClient);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//showExamQuestionsToPrepare
        if (contentOfMsg.equals("#showExamQuestionsToPrepare"))
        {
            try {
                Message msgToClient=new Message("exam questions for prepare",
                        GetExamBuliding.getExamQuestionsById(session,(int) msgFromClient.getObj()));
                client.sendToClient(msgToClient);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (contentOfMsg.equals("#addCompExam")) {
            List<Object> dataFromClient=(List<Object>) msgFromClient.getObj();
            String queryString="FROM ExamToExecute WHERE code = : code";
            Query query = session.createQuery(queryString, ExamToExecute.class);
            query.setParameter("code",((ExamToExecute)dataFromClient.get(0)).getCode());
            List<ExamToExecute> res=query.getResultList();
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
        if (contentOfMsg.equals("#addManualExam")) {
            List<Object> dataFromClient=(List<Object>) msgFromClient.getObj();
            String queryString="FROM ExamToExecute WHERE code = : code";
            Query query = session.createQuery(queryString, ExamToExecute.class);
            query.setParameter("code",((ExamToExecute)dataFromClient.get(0)).getCode());
            List<ExamToExecute> res=query.getResultList();
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

            SimpleServer.addManualExam((ManualExamToExecute) dataFromClient.get(0),(int)dataFromClient.get(1),(int) dataFromClient.get(2));
            Message messageToClient = new Message("added the manualExam successfully");
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
        if (contentOfMsg.equals("#showAllExamsForTeahcerToApprove"))
        {
            int teacherId=(int)msgFromClient.getObj();
            List<ExamToExecute> exams= GetExamBuliding.getAllExamsForTeacher(session,teacherId);
            Message messageToClient = new Message("sending all compExams for teacher",exams);
            try {
                client.sendToClient(messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (contentOfMsg.equals("#getAllExamsForTeacher"))
        {
            List<Question> questions= GetExamBuliding.getAllQuestionsForTeacher(session);
            Message messageToClient = new Message("sending all compExams for teacher");
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
            List<Subject> teacherSubjects=GetUsers.getTeacherSubjects(session,id);
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
        if(contentOfMsg.equals("#showAllExamGrades"))
        {
            int compExamId=(int)msgFromClient.getObj();
            String queryString="FROM ExamToExecute WHERE id = : id";
            Query query = session.createQuery(queryString,ExamToExecute.class);
            query.setParameter("id",compExamId);

            List<ExamToExecute> exam=(List<ExamToExecute>)(query.getResultList());

            List<Grade> compExamGrades=new ArrayList<>();
            for(Copy copy : exam.get(0).getCopies())
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
            Message messageToClient = new Message("teacher compExams now",GetExamBuliding.getAllExamsToExecute(session,
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
        if(contentOfMsg.equals("AddExtraTime")) {
            int id = (int) ((List<Object>) msgFromClient.getObj()).get(0);
            int ExtraTime = (int) ((List<Object>) msgFromClient.getObj()).get(1);
            ExamToExecute exam=GetExamBuliding.getExamToExeById(session,id);
            SimpleServer.AddExtraTime(exam, ExtraTime);
            try {
                Message message = new Message("AddExtraTime");
                client.sendToClient(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(contentOfMsg.equals("#allQuestions")) {
            try {
                Message message = new Message("all questions",GetExamBuliding.getAllQuestionsForTeacher(session));
                client.sendToClient(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }if(contentOfMsg.equals("#getAllExamsCreated")) {
            try {
                List<Exam>exams=GetExamBuliding.getAllExamsCreatedForTeacher(session,(int)msgFromClient.getObj());
                Message message = new Message("all exams",exams);
                client.sendToClient(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(contentOfMsg.equals("#getMyExamGrades")) {
            try {
                int teacherId = (int) ((List<Object>) msgFromClient.getObj()).get(0);
                int examId = (int) ((List<Object>) msgFromClient.getObj()).get(1);
                Message message = new Message("all grades for teacher exam", GetGrading.getAllTeacherExamsGrade(session,teacherId,examId));
                client.sendToClient(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
