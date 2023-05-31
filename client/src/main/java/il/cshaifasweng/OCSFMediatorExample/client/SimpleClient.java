package il.cshaifasweng.OCSFMediatorExample.client;

import aidClasses.Color;
import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.collections.FXCollections;
import javafx.print.Collation;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import aidClasses.Warning;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.List;

public class SimpleClient extends AbstractClient {

	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Warning.class)) {
			Warning warning=(Warning)msg;
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		if(msg.getClass().equals(Message.class)) {
			try {
				Message msgFromServer = (Message) msg;
				String contentOfMsg = msgFromServer.getMsg();
				if (contentOfMsg.equals("successful login")) {
					User LogedInUser = (User) msgFromServer.getObj();
					GlobalDataSaved.connectedUser = LogedInUser;
					if (LogedInUser.getClass().equals(Student.class)) {
						App.setRoot("studentHome");
					}
					if (LogedInUser.getClass().equals(Teacher.class)) {
						App.setRoot("teacherHome");
					}
					if (LogedInUser.getClass().equals(Principal.class)) {
						App.setRoot("principalHome");
					}
				}
					if (contentOfMsg.equals("sending teacher subjects")) {
						GlobalDataSaved.teacherSubjects = (List<Subject>) msgFromServer.getObj();
						return;
					}
					if (contentOfMsg.equals("sending teacher courses")) {
						GlobalDataSaved.teacherCourses = (List<Course>) msgFromServer.getObj();
						return;
					}
					if (contentOfMsg.equals("student grades")) {
						GlobalDataSaved.gradeList = (List<Grade>) msgFromServer.getObj();
						App.setRoot("studentGrades");
						return;
					}
					if (contentOfMsg.equals("exam copy")) {
						List<Object> dataFromServer = (List<Object>) msgFromServer.getObj();
						GlobalDataSaved.compExam = (ComputerizedExamToExecute) dataFromServer.get(0);
						GlobalDataSaved.studentAnswers = (List<String>) dataFromServer.get(1);
						return;
					}
					if (contentOfMsg.equals("write id to start")) {
						ComputerizedExamToExecute compExams = (ComputerizedExamToExecute) msgFromServer.getObj();
						GlobalDataSaved.compExam = compExams;
						App.setRoot("checkExamCode");
						return;
					}
					if (contentOfMsg.equals("do exam")) {
						App.setRoot("solve_Exam");
						return;
					}
					if (contentOfMsg.equals("Submitted successfully")) {
						GlobalDataSaved.studentAnswers = (List<String>) msgFromServer.getObj();
						EventBus.getDefault().post(new MessageEvent((Message) msg));
						App.setRoot("studentHome");
					}
					if (contentOfMsg.equals("All Subjects Given to principal")) {
						GlobalDataSaved.subjects = FXCollections.observableArrayList();
						GlobalDataSaved.subjects.addAll((List<Subject>) msgFromServer.getObj());
						App.setRoot("principalAddUsers");
						return;
					}
					if (contentOfMsg.equals("Teacher Added Successfully")) {
						GlobalDataSaved.AddFlag = true;
						return;
					}
					if (contentOfMsg.equals("Student Added Successfully")) {
						GlobalDataSaved.AddFlag = true;
							App.setRoot("principalHome");
					}
					if (contentOfMsg.equals("successful logout")) {
						App.setRoot("login");
					}
					if (contentOfMsg.equals("added the question successfully")) {
						GlobalDataSaved.connectedUser = (User) msgFromServer.getObj();
						EventBus.getDefault().post(new MessageEvent((Message) msg));
						System.out.println(Color.GREEN_BOLD + "teacher : " + GlobalDataSaved.connectedUser.getFirstName() + " " + GlobalDataSaved.connectedUser.getLastName() + " added question" + Color.ANSI_RESET);
						App.setRoot("buildExam");
					}
					if (contentOfMsg.equals("added the exam successfully")) {
						GlobalDataSaved.connectedUser = (User) msgFromServer.getObj();
						EventBus.getDefault().post(new MessageEvent((Message) msg));
						System.out.println(Color.GREEN_BOLD + "teacher : " + GlobalDataSaved.connectedUser.getFirstName() + " " + GlobalDataSaved.connectedUser.getLastName() + " added Exam" + Color.ANSI_RESET);
						App.setRoot("buildExam");
					}
					if (contentOfMsg.equals("added the CompExam successfully")) {
						GlobalDataSaved.connectedUser = (User) msgFromServer.getObj();
						EventBus.getDefault().post(new MessageEvent((Message) msg));
						System.out.println(Color.GREEN_BOLD + "teacher : " + GlobalDataSaved.connectedUser.getFirstName() + " " + GlobalDataSaved.connectedUser.getLastName() + " executed an Exam" + Color.ANSI_RESET);
						App.setRoot("teacherHome");
					}
					if (contentOfMsg.equals("sending teacher subjects")) {
						GlobalDataSaved.teacherSubjects = (List<Subject>) msgFromServer.getObj();
						return;
					}
					if (contentOfMsg.equals("sending teacher courses")) {
						GlobalDataSaved.teacherCourses = (List<Course>) msgFromServer.getObj();
						return;
					}
					if (contentOfMsg.equals("student grades")) {
						GlobalDataSaved.gradeList = (List<Grade>) msgFromServer.getObj();
						System.out.println("*****/////" + GlobalDataSaved.gradeList.get(0).getGrade());
						App.setRoot("studentGrades");
						return;
					}
					if (contentOfMsg.equals("do exam")) {
						ComputerizedExamToExecute compExams = (ComputerizedExamToExecute) msgFromServer.getObj();
						GlobalDataSaved.compExam = compExams;
						App.setRoot("solve_Exam");
						return;
					}
					if (contentOfMsg.equals("Teacher Added Successfully")) {
						GlobalDataSaved.AddFlag = true;
						EventBus.getDefault().post(new MessageEvent((Message) msg));
						return;
					}
					if (contentOfMsg.equals("Student Added Successfully")) {
						GlobalDataSaved.AddFlag = true;
						EventBus.getDefault().post(new MessageEvent((Message) msg));
						return;
					}//sending all exams for teacher
					if (contentOfMsg.equals("sending all exams for teacher")) {
						GlobalDataSaved.allExamsForTeacher = (List<Exam>) msgFromServer.getObj();
						App.setRoot("prepareExam");
					}
			}
            catch(Exception ex){
					System.out.println(ex.getMessage());
				}
			}


	}

	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3020);
		}
		return client;
	}

	protected void closeConnectionWithServer() {

	}

}
