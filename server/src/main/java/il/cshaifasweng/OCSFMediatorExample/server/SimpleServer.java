package il.cshaifasweng.OCSFMediatorExample.server;

import aidClasses.Message;
import com.mysql.cj.xdevapi.Client;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.*;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Principal;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.ComputerizedExamToExecute;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GenerateAll;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetEducational;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetExamBuliding;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetUsers;
import il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient.HandleMsgPrincipal;
import il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient.HandleMsgStudent;
import il.cshaifasweng.OCSFMediatorExample.server.HandleMsgFromClient.HandleMsgTeacher;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.LoggedInClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aidClasses.GlobalDataSaved;

import il.cshaifasweng.OCSFMediatorExample.entities.educational.*;

import aidClasses.Warning;

import javax.persistence.Query;
import javax.persistence.spi.LoadState;

public class SimpleServer extends AbstractServer {

	private static ArrayList<LoggedInClient> _LoggedInList = new ArrayList<>();

	private static Session session;

	public static Session getSession() {
		return session;
	}

	public static void setSession(Session session) {
		SimpleServer.session = session;
	}


	private static SessionFactory getSessionFactory() throws HibernateException {

		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Principal.class);
		configuration.addAnnotatedClass(Student.class);
		configuration.addAnnotatedClass(Teacher.class);
		configuration.addAnnotatedClass(Course.class);
		configuration.addAnnotatedClass(Subject.class);
		configuration.addAnnotatedClass(ComputerizedExamToExecute.class);
		configuration.addAnnotatedClass(Exam.class);
		configuration.addAnnotatedClass(Question.class);
		configuration.addAnnotatedClass(Copy.class);
		configuration.addAnnotatedClass(Grade.class);
		configuration.addAnnotatedClass(Teacher_Subject.class);
		configuration.addAnnotatedClass(Teacher_Course.class);
		configuration.addAnnotatedClass(Course_Question.class);
		configuration.addAnnotatedClass(Exam_Question.class);
		configuration.addAnnotatedClass(Student_Course.class);
		configuration.addAnnotatedClass(Student_Subject.class);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}


	public SimpleServer(int port) {
		super(port);
		SessionFactory sessionFactory = getSessionFactory();
		session = sessionFactory.openSession();
		session.beginTransaction();
		GenerateAll.generateEducational(session);  // moving the students to the database
		//GetUsers.generateUsers(session);
		session.getTransaction().commit();
	}

	public void logout(User user) {
		session.beginTransaction();
		session.clear();
		user.setConnected(false);
		session.update(user);
		session.flush();
		session.getTransaction().commit();
	}

	public void login(User user) {
		session.beginTransaction();
		user.setConnected(true);
		session.update(user);
		session.flush();
		session.getTransaction().commit();
	}

	public static void addQuestion(Question question,List<Course> questionCourses,Subject questionSubject,Teacher theTeacher) {
		//session.flush();
		session.beginTransaction();
		session.clear();
		session.save(question);
		session.flush();

		question.setQuestionSubject(questionSubject);
		session.update(question);
		session.flush();

		question.setTeacherThatCreated(theTeacher);
		session.update(question);
		session.flush();

		theTeacher.getQuestionsCreated().add(question);
		session.update(theTeacher);
		session.flush();

		Subject subject = question.getQuestionSubject();
		subject.getSubjectQuestions().add(question);
		session.update(subject);
		session.flush();

		Course_Question cq;
		for(Course course:questionCourses)
		{
			cq=new Course_Question(course,question);
			session.save(cq);
			session.flush();

			course.getCourseQuestions().add(cq);
			session.update(course);
			session.flush();

			question.getQuestionCourses().add(cq);
			session.update(question);
			session.flush();
		}

		//session.clear();
		session.getTransaction().commit();

		int i;
		for (i = 0 ; i < _LoggedInList.size() && _LoggedInList.get(i).get_class() != 0; i++){}
		if (i == _LoggedInList.size()){
			return;
		} else {
			try	{
				List<Question> list = GetEducational.getAllQuestions(session);
				_LoggedInList.get(i).getClient().sendToClient(new Message("UpdateAllQuestionsToPrincipal",list));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void addExam(Exam exam,Teacher teacher,Course course,Subject subject,List<Question> questions,List<Integer> points) {
		/*saving exam*/
		session.beginTransaction();
		session.clear();
		session.save(exam);
		session.flush();
		/*end of saving exam*/

		/*updating exam*/
		exam.setPoints(points);
		session.update(exam);
		session.flush();

		exam.setTeacherThatCreated(teacher);
		session.update(exam);
		session.flush();

		exam.setExamSubject(subject);
		session.update(exam);
		session.flush();

		exam.setExamCourse(course);
		session.update(exam);
		session.flush();
		/*end of updating exam*/

		/*updating teacher*/
		teacher.getExamsCreated().add(exam);
		session.update(teacher);
		session.flush();
		/*end of updating teacher*/

		/*updating course*/
		course.getCourseExams().add(exam);
		session.update(course);
		session.flush();
		/*end of updating course*/

		/*updating subject*/
		subject.getSubjectExams().add(exam);
		session.update(subject);
		session.flush();
		/*end of updating subject*/

		Exam_Question eq;
		for(Question question:questions)
		{
			eq=new Exam_Question(exam,question);
			session.save(eq);
			session.flush();

			question.getQuestionExams().add(eq);
			session.update(question);
			session.flush();

			exam.getExamQuestions().add(eq);
			session.update(exam);
			session.flush();


		}
		int subjectId=subject.getId()-1;
		int courseId=course.getId()-1;
		int examId=exam.getId()-1;
		String exam_ID="";
		if(subjectId<10)
		{
			exam_ID+="0"+subjectId;
		}
		else
		{
			exam_ID+=subjectId;
		}
		if(courseId<10)
		{
			exam_ID+="0"+courseId;
		}
		else
		{
			exam_ID+=courseId;
		}
		if(examId<10)
		{
			exam_ID+="0"+examId;
		}
		else
		{
			exam_ID+=examId;
		}
		exam.setExam_ID(exam_ID);
		session.update(exam);
		session.flush();
		session.getTransaction().commit();
	}
	public static void addCompExam(ComputerizedExamToExecute compExam,Teacher teacher,Exam exam)
	{
		session.beginTransaction();
		session.clear();
		session.save(compExam);
		session.flush();

		compExam.setExam(exam);
		session.update(compExam);
		session.flush();

		compExam.setTeacherThatExecuted(teacher);
		session.update(compExam);
		session.flush();

		 teacher=compExam.getTeacherThatExecuted();
		teacher.getExecutedExams().add(compExam);
		session.update(teacher);
		session.flush();


		exam.getCompExamsToExecute().add(compExam);
		session.update(exam);
		session.flush();

		session.getTransaction().commit();
	}
	public static void addTeacher(Teacher teacher,List<Subject> subjects,List<Course> courses) {
		session.beginTransaction();
		session.clear();

		session.save(teacher);
		session.flush();

		Teacher_Subject ts;
		Teacher_Course tc;
		for(Subject subject:subjects)
		{
			ts=new Teacher_Subject(teacher,subject);
			session.save(ts);
			session.flush();

			teacher.getTeacherSubjects().add(ts);
			session.update(teacher);
			session.flush();

			subject.getSubjectTeachers().add(ts);
			session.update(subject);
			session.flush();
		}
		for(Course course:courses)
		{
			tc=new Teacher_Course(teacher,course);
			session.save(tc);
			session.flush();

			teacher.getTeacherCourses().add(tc);
			session.update(teacher);
			session.flush();

			course.getCourseTeachers().add(tc);
			session.update(course);
			session.flush();
		}
		session.getTransaction().commit();
	}

	public static void addStudent(Student student,List<Subject> subjects,List<Course> courses) {
		session.beginTransaction();
		session.clear();

		session.save(student);
		session.flush();

		Student_Subject ss;
		Student_Course sc;
		for(Subject subject:subjects)
		{
			ss=new Student_Subject(student,subject);
			session.save(ss);
			session.flush();

			student.getStudentSubjects().add(ss);
			session.update(student);
			session.flush();

			subject.getSubjectStudents().add(ss);
			session.update(subject);
			session.flush();
		}
		for(Course course:courses)
		{
			sc=new Student_Course(student,course);
			session.save(sc);
			session.flush();

			student.getStudentCourses().add(sc);
			session.update(student);
			session.flush();

			course.getCourseStudents().add(sc);
			session.update(course);
			session.flush();
		}
		session.getTransaction().commit();
	}

	public static void updateGradeAndCopyToStudent(String studentAnswers, Student user, ComputerizedExamToExecute compExam, int examGrade, boolean onTime) {
		session.beginTransaction();
		session.clear();
		Copy copy = GlobalDataSaved.currentCopy;
		copy.setAnswers(studentAnswers);
		copy.setCompExamToExecute(compExam);
		session.update(copy);
		session.flush();

		Grade grade = GlobalDataSaved.currentGrade;
		grade.setGrade(examGrade);
		grade.setDoneOnTime(onTime);

		session.update(grade);
		session.flush();

		copy.setGrade(grade);
		grade.setExamCopy(copy);
		session.update(copy);
		session.flush();

		session.update(grade);
		session.flush();

		compExam.getCopies().add(copy);
		session.update(compExam);
		session.flush();

		session.getTransaction().commit();
	}

	public static void createGradeAndCopyToStudent(String studentAnswers, Student user, ComputerizedExamToExecute compExam, int grade) {
		session.beginTransaction();
		session.clear();

		Copy copy = new Copy();
		copy.setAnswers(studentAnswers);
		copy.setCompExamToExecute(compExam);
		session.save(copy);
		session.flush();

		Grade grade1 = new Grade(user,grade,false,compExam.getExam().getTime(),
				false,compExam.getDateOfExam(),compExam.getDateOfExam(), false);

		session.save(grade1);
		session.flush();

		copy.setGrade(grade1);
		grade1.setExamCopy(copy);
		session.update(copy);
		session.flush();
		session.update(grade1);
		session.flush();

		GlobalDataSaved.currentGrade = grade1;
		GlobalDataSaved.currentCopy = copy;
		session.getTransaction().commit();
	}
	public static void teacherApproveStudentGrade(Grade grade,int newGrade,String notes)
	{
		session.beginTransaction();
		session.clear();

		grade.setGrade(newGrade);
		session.update(grade);
		session.flush();

		grade.setTeacherApprovement(true);
		session.update(grade);
		session.flush();

		grade.setTeacherNotes(notes);
		session.update(grade);
		session.flush();

		session.getTransaction().commit();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		if (msg.getClass().equals(Message.class)) {
			try {
				Message msgFromClient = (Message) msg;
				String contentOfMsg = msgFromClient.getMsg();
				if (contentOfMsg.equals("#login")) {
					String[] userDetails = (String[]) (((Message) msg).getObj());
					String userName = userDetails[0];
					String password = userDetails[1];

					String queryString="FROM User WHERE userName = : userName";
					Query query = session.createQuery(queryString,User.class);
					query.setParameter("userName",userName);
					List<User> users=query.getResultList();
					if (users.size() == 0) {
						Warning warning = new Warning("there is no such a username");
						try {
							client.sendToClient(warning);
							System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
							return;
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
					if (!users.get(0).getPassWord().equals(password)) {
						Warning warning = new Warning("wrong password");
						try {
							client.sendToClient(warning);
							System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
							return;
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
					if (users.get(0).getConnected()) {
						Warning warning = new Warning("user already logged in!");
						try {
							client.sendToClient(warning);
							System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
							return;
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
					//login(users.get(0));
					if(users.get(0).getClass().equals(Principal.class)){
						_LoggedInList.add(new LoggedInClient(client,0,users.get(0).getUserID()));
					} else if (users.get(0).getClass().equals(Teacher.class)) {
						_LoggedInList.add(new LoggedInClient(client,1,users.get(0).getUserID()));
					} else if (users.get(0).getClass().equals(Student.class)) {
						_LoggedInList.add(new LoggedInClient(client,2,users.get(0).getUserID()));
					}
					Message msgToClient = new Message("successful login", users.get(0));
					client.sendToClient(msgToClient);
					return;
				}
				if (contentOfMsg.equals("#logout")) {
					User userToLogout = (User) msgFromClient.getObj();
					//logout(userToLogout);
					Iterator<LoggedInClient> iterator = _LoggedInList.iterator();
					while (iterator.hasNext()) {
						LoggedInClient _loggedInClient = iterator.next();
						if (_loggedInClient.getClient() == client) {
							iterator.remove();
							break;
						}
					}
					Message msgToClient = new Message("successful logout", null);
					client.sendToClient(msgToClient);
					return;
				}
				boolean isHandled;

				isHandled=HandleMsgTeacher.handleTeacher(session,msgFromClient,contentOfMsg,client);
				if(isHandled)return;

				isHandled= HandleMsgPrincipal.handlePrincipal(session,msgFromClient,contentOfMsg,client);
				if(isHandled)return;

				isHandled= HandleMsgStudent.handleStudent(session,msgFromClient,contentOfMsg,client);
				if(isHandled)return;

				if (contentOfMsg.equals("#close")) {
					session.close();
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		} else {
			String msgString = msg.toString();
			if (msgString.startsWith("#warning")) {
				Warning warning = new Warning("Warning from server!");
				try {
					client.sendToClient(warning);
					System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}


	}
}
