package il.cshaifasweng.OCSFMediatorExample.client;
import aidClasses.*;
import aidClasses.aidClassesForTeacher.QuestionsExamsID;
import il.cshaifasweng.OCSFMediatorExample.client.Principal.PrincipalQuestions;
import il.cshaifasweng.OCSFMediatorExample.client.Student.SolveExam;
import il.cshaifasweng.OCSFMediatorExample.client.Student.SolveExamManual;
import il.cshaifasweng.OCSFMediatorExample.client.Teacher.BuildExam;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.Exam_Question;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.greenrobot.eventbus.EventBus;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.*;

import java.util.ArrayList;
import java.util.Arrays;
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
						return;
					}
					if (LogedInUser.getClass().equals(Teacher.class)) {
						App.setContentTeacher("teacherHome");
						return;
					}
					if (LogedInUser.getClass().equals(Principal.class)) {
						App.setRoot("principalHome");
						return;
					}
				}
				if (contentOfMsg.equals("sending teacher subjects")) {
					GlobalDataSaved.teacherSubjects = (List<Subject>) msgFromServer.getObj();
					if(GlobalDataSaved.forQuestion==0)App.setRoot("makeQuestion");
					if(GlobalDataSaved.forQuestion==1) App.setRoot("makeExam");
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
					List<Object> objects = (List<Object>) msgFromServer.getObj();
					Grade grade = (Grade) objects.get(1);
					GlobalDataSaved.currentGrade = grade;
					GlobalDataSaved.questionList = (List<Question>) objects.get(0);
					//GlobalDataSaved.examToExecute = (ExamToExecute) grade.getExamCopy().getCompExamToExecute();
					String answers = grade.getExamCopy().getAnswers();
					List<String> answersList = new ArrayList<String>(Arrays.asList(answers.split(",")));
					GlobalDataSaved.studentAnswers = answersList;
					App.setRoot("examStudentNotes");
					return;
				}
				if (contentOfMsg.equals("write id to start")) {
					ExamToExecute examToExecute = (ExamToExecute) msgFromServer.getObj();
					GlobalDataSaved.examToExecute = examToExecute;
					App.setRoot("checkExamCode");
					return;
				}
				if (contentOfMsg.equals("do exam")) {
					if(GlobalDataSaved.examToExecute.getClass().equals(ComputerizedExamToExecute.class))
					{
						App.setRoot("solve_Exam");
						return;
					}

					App.setRoot("solveExamManual");
					return;
				}
				if (contentOfMsg.equals("exam done on time")) {
					examFinished(msgFromServer);
					Message newMessage=new Message("Submitted Successfully");
					EventBus.getDefault().post(new MessageEvent(newMessage));
					App.setRoot("studentHome");
					return;
				}
				if (contentOfMsg.equals("exam finished time")) {
					examFinished(msgFromServer);
					App.setRoot("studentHome");
					return;
				}
				if (contentOfMsg.equals("All Subjects Given to principal")) {
					GlobalDataSaved.subjects = FXCollections.observableArrayList();
					GlobalDataSaved.subjects.addAll((List<Subject>) msgFromServer.getObj());
					App.setRoot("principalAddUsers");
					return;
				}
				if (contentOfMsg.equals("User Already in the System")){
					GlobalDataSaved.AddFlag = false;
					EventBus.getDefault().post(new WarningEvent(new Warning(contentOfMsg)));
					App.setRoot("principalHome");
					return;
				}
				if (contentOfMsg.equals("Teacher Added Successfully")) {
					GlobalDataSaved.AddFlag = true;
					EventBus.getDefault().post(new MessageEvent((Message) msgFromServer));
					App.setRoot("principalHome");
					return;
				}
				if (contentOfMsg.equals("Student Added Successfully")) {
					GlobalDataSaved.AddFlag = true;
					EventBus.getDefault().post(new MessageEvent((Message) msgFromServer));
					App.setRoot("principalHome");
					return;
				}
				if (contentOfMsg.equals("successful logout")) {
					App.backLogin("login");
					return;
				}
				if (contentOfMsg.equals("course questions")) {
					GlobalDataSaved.courseQuestionsForMakeExam = (List<Question>) msgFromServer.getObj();
					App.setRoot("examChooseQuestions");
					return;
				}
				if (contentOfMsg.equals("exam questions for prepare")) {
					GlobalDataSaved.examQuestionsToShowPrepare = (List<Exam_Question>) msgFromServer.getObj();
					App.setRoot("prepareExamShowQuestions");
					return;
				}
				if (contentOfMsg.equals("added the question successfully")) {
					EventBus.getDefault().post(new MessageEvent((Message) msg));
					System.out.println(Color.GREEN_BOLD + "teacher : " + GlobalDataSaved.connectedUser.getFirstName() + " " + GlobalDataSaved.connectedUser.getLastName() + " added question" + Color.ANSI_RESET);
					App.setRoot("makeQuestion");
					return;
				}//
				if (contentOfMsg.equals("copied the question successfully")) {
					EventBus.getDefault().post(new MessageEvent((Message) msg));
					System.out.println(Color.GREEN_BOLD + "teacher : " + GlobalDataSaved.connectedUser.getFirstName() + " " + GlobalDataSaved.connectedUser.getLastName() + " copied question" + Color.ANSI_RESET);
					App.setRoot("teacherHome");
					return;
				}
				if (contentOfMsg.equals("added the exam successfully")) {
					EventBus.getDefault().post(new MessageEvent((Message) msg));
					System.out.println(Color.GREEN_BOLD + "teacher : " + GlobalDataSaved.connectedUser.getFirstName() + " " + GlobalDataSaved.connectedUser.getLastName() + " added Exam" + Color.ANSI_RESET);
					App.setRoot("makeExam");
					return;
				}
				if (contentOfMsg.equals("copied the exam successfully")) {
					EventBus.getDefault().post(new MessageEvent((Message) msg));
					System.out.println(Color.GREEN_BOLD + "teacher : " + GlobalDataSaved.connectedUser.getFirstName() + " " + GlobalDataSaved.connectedUser.getLastName() + " copied Exam" + Color.ANSI_RESET);
					App.setRoot("teacherHome");
					return;
				}
				if (contentOfMsg.equals("added the CompExam successfully")) {
					EventBus.getDefault().post(new MessageEvent((Message) msg));
					System.out.println(Color.GREEN_BOLD + "teacher : " + GlobalDataSaved.connectedUser.getFirstName() + " " + GlobalDataSaved.connectedUser.getLastName() + " executed an comp Exam" + Color.ANSI_RESET);
					App.setRoot("teacherHome");
					return;
				}
				if (contentOfMsg.equals("added the manualExam successfully")) {
					EventBus.getDefault().post(new MessageEvent((Message) msg));
					System.out.println(Color.GREEN_BOLD + "teacher : " + GlobalDataSaved.connectedUser.getFirstName() + " " + GlobalDataSaved.connectedUser.getLastName() + " executed an manual Exam" + Color.ANSI_RESET);
					App.setRoot("teacherHome");
					return;
				}
				if (contentOfMsg.equals("sending teacher subjects")) {
					GlobalDataSaved.teacherSubjects = (List<Subject>) msgFromServer.getObj();
					return;
				}
				if (contentOfMsg.equals("sending teacher courses")) {
					GlobalDataSaved.teacherCourses = (List<Course>) msgFromServer.getObj();
					return;
				}
				if (contentOfMsg.equals("save grade id and copy id")) {
					int[] ides = (int[]) msgFromServer.getObj();
					GlobalDataSaved.currentGradeId = ides[0];
					GlobalDataSaved.currentCopyId = ides[1];
				}
				if (contentOfMsg.equals("the grade updated")) {
					List<Object> dataFromServer=(List<Object>)msgFromServer.getObj();
					int newGrade=(int)dataFromServer.get(1);
					Message newMessage=new Message(	" grade in this exam has been updated to "+newGrade);
					EventBus.getDefault().post(new MessageEvent((Message) newMessage));
					return;
				}
				if (contentOfMsg.equals("student grades")) {
					GlobalDataSaved.gradeList = (List<Grade>) msgFromServer.getObj();
					App.setRoot("studentGrades");
					return;
				}
				if (contentOfMsg.equals("do exam")) {
					ExamToExecute examToExecute = (ExamToExecute) msgFromServer.getObj();
					GlobalDataSaved.examToExecute = examToExecute;
					if(examToExecute.getClass().equals(ComputerizedExamToExecute.class))App.setRoot("solve_Exam");
					else App.setRoot("solveExamManual");
					return;
				}
				if(contentOfMsg.equals("sending all compExams for teacher"))
				{
					GlobalDataSaved.teacherExamsToApprove=(List<ExamToExecute>) msgFromServer.getObj();
					App.setRoot("examNeedApprovement");
				}
				//sending all exams for teacher
				if (contentOfMsg.equals("sending all exams for teacher")) {
					GlobalDataSaved.allExamsForTeacher = (List<Exam>) msgFromServer.getObj();
					App.setRoot("prepareExam");
					return;
				}
				if (contentOfMsg.equals("teacher compExams")) {
					GlobalDataSaved.teacherExamsToApprove = (List<ExamToExecute>) msgFromServer.getObj();
					App.setRoot("examNeedApprovement");
				}
				if (contentOfMsg.equals("show all grades")) {
					GlobalDataSaved.compExamGrades = (List<Grade>) msgFromServer.getObj();
					App.setRoot("approvement");
				}
				if(contentOfMsg.equals("AllExamsToPrincipal")){
					GlobalDataSaved.allExamsForPrincipal = (List<Exam>) msgFromServer.getObj();
					App.setRoot("principalExams");
					return;
				}
				if(contentOfMsg.equals("UpdateAllExamsToPrincipal")){
					GlobalDataSaved.allExamsForPrincipal = (List<Exam>) msgFromServer.getObj();
					if (GlobalDataSaved.ThePrincipalInExams) {
						App.setRoot("principalExams");
					}
					return;
				}
				if(contentOfMsg.equals("AllQuestionsToPrincipal")){
					GlobalDataSaved.allQuestionsForPrincipal = (List<Question>) msgFromServer.getObj();
					App.setRoot("principalQuestions");
					return;
				}
				if(contentOfMsg.equals("UpdateAllQuestionsToPrincipal")){
					GlobalDataSaved.allQuestionsForPrincipal = (List<Question>) msgFromServer.getObj();
					if (GlobalDataSaved.ThePrincipalInQuestions) {
						App.setRoot("principalQuestions");
					}
					return;
				}
				if(contentOfMsg.equals("AllGradesToPrincipal")){
					GlobalDataSaved.allGradesForPrincipal = (List<Grade>) msgFromServer.getObj();
					App.setRoot("principalGrades");
					return;
				}
				if(contentOfMsg.equals("teacher compExams now")){
					GlobalDataSaved.getTeacherExamsToExecutes=(List<ExamToExecute>) msgFromServer.getObj() ;
					App.setRoot("teacherExamsInProgress");
					return;
				}
				if(contentOfMsg.equals("UpdateAllGradesToPrincipal")){
					GlobalDataSaved.allGradesForPrincipal = (List<Grade>) msgFromServer.getObj();
					if (GlobalDataSaved.ThePrincipalInGrades) {
						App.setRoot("principalGrades");
					}
					return;
				}
				if(contentOfMsg.equals("StatisticalDataForPrincipal")){
					List<Object> list = (List<Object>) msgFromServer.getObj();
					GlobalDataSaved.allCoursesForPrincipal = (List<Course>) list.get(2);
					GlobalDataSaved.allExamsForPrincipal = (List<Exam>) list.get(0);
					GlobalDataSaved.allTeachersForPrincipal = (List<Teacher>) list.get(3);
					GlobalDataSaved.allStudentsForPrincipal =(List<Student>) list.get(1);

					App.setRoot("principalStatisticalData");
				}
				if (contentOfMsg.equals("GetGradesForStatisticalData")) {
					GlobalDataSaved.allGradesForPrincipal = (List<Grade>) msgFromServer.getObj();
					App.setRoot("principalStatisticalReport");
				}
				if(contentOfMsg.equals("AddExtraTime")) {
					EventBus.getDefault().post(new MessageEvent((Message)msg));
					App.setRoot("teacherExamsInProgress");
				}
				if(contentOfMsg.equals("ExtraTime")) {
					GlobalDataSaved.allExamsToExecuteForPrincipal = (List<ExamToExecute>) msgFromServer.getObj();
					App.setRoot("principalExtraTimeApprovment");
				}
				if(contentOfMsg.equals("Do Not Add Extra Time")) {
					GlobalDataSaved.allExamsToExecuteForPrincipal = (List<ExamToExecute>) msgFromServer.getObj();
					App.setRoot("principalExtraTimeApprovment");
				}
				if(contentOfMsg.equals("AddTimeToStudentForCompExam")) {
					SolveExam.addExtraTime((int) msgFromServer.getObj());
					EventBus.getDefault().post(new MessageEvent(new Message("Added Extra Time")));
				}
				if(contentOfMsg.equals("AddTimeToStudentForManualExam")) {
					SolveExamManual.addExtraTime((int) msgFromServer.getObj());
					EventBus.getDefault().post(new MessageEvent(new Message("Added Extra Time")));
				}
				if(contentOfMsg.equals("all questions")) {
					GlobalDataSaved.allQuestionsForTeacher=(List<Question>) msgFromServer.getObj();
					App.setRoot("allQuestions");
				}
				if(contentOfMsg.equals("all the exams for teacher copy")) {
					GlobalDataSaved.allExamsForTeacherToCopy=(List<Exam>) msgFromServer.getObj();
					App.setRoot("allExams");
				}
				if(contentOfMsg.equals("all the questions for teacher exam to copy"))
				{
					GlobalDataSaved.allQuestionsForExamToCopy=(List<Question>) msgFromServer.getObj();
					App.setRoot("showExamToCopy");
				}
				if(contentOfMsg.equals("all exams"))
				{
					GlobalDataSaved.allTeacherExamsCreated=(List<Exam>) msgFromServer.getObj();
					App.setRoot("allTeacherExams");
				}
				if(contentOfMsg.equals("all grades for teacher exam"))
				{
					GlobalDataSaved.allTeacherExamGrades=(List<Grade>) msgFromServer.getObj();
					App.setRoot("teacherResultExam");
				}
				if(contentOfMsg.equals("exam questions for principal"))
				{
					GlobalDataSaved.PrincipalExamToShow = (Exam)msgFromServer.getObj();
					App.setRoot("principalShowExam");
				}
			}
            catch(Exception ex){
					System.out.println(ex.getMessage());
				}
			}

	}

	void examFinished(Message msgFromServer) {
		String answers = (String) msgFromServer.getObj();
		List<String> answersList = new ArrayList<String>(Arrays.asList(answers.split(",")));
		GlobalDataSaved.studentAnswers = answersList;
	}

	public static SimpleClient getClient() {
		if (client == null) {//192.168.52.174
			client = new SimpleClient("localhost", 3020);
		}
		return client;
	}

	protected void closeConnectionWithServer() {

	}

}
