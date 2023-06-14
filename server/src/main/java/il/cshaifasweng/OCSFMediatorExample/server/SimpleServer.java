package il.cshaifasweng.OCSFMediatorExample.server;

import aidClasses.Message;
import com.mysql.cj.xdevapi.Client;
import il.cshaifasweng.OCSFMediatorExample.entities.ManyToMany.*;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Principal;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.examBuliding.*;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Copy;
import il.cshaifasweng.OCSFMediatorExample.entities.gradingSystem.Grade;
import il.cshaifasweng.OCSFMediatorExample.server.Generating.*;
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
import java.util.concurrent.ThreadPoolExecutor;

import aidClasses.GlobalDataSaved;

import il.cshaifasweng.OCSFMediatorExample.entities.educational.*;

import aidClasses.Warning;

import javax.persistence.Query;
import javax.persistence.spi.LoadState;

public class SimpleServer extends AbstractServer {
	public static int counter;

	public static ArrayList<LoggedInClient> _LoggedInList = new ArrayList<>();

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
		configuration.addAnnotatedClass(ExamToExecute.class);
		configuration.addAnnotatedClass(ManualExamToExecute.class);
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

	public static void addQuestion(Question question,List<Integer> CoursesIds,int subjectId,int teacherId) {
		List<Course>questionCourses=GetExamBuliding.getCoursesById(session,CoursesIds);
		Subject questionSubject=GetExamBuliding.getSubjectById(session,subjectId);
		Teacher theTeacher=GetUsers.getTeacherById(session,teacherId);
		//session.flush();
		session.beginTransaction();
		session.clear();
		question.setQuestionSubject(questionSubject);
		question.setTeacherThatCreated(theTeacher);

		session.save(question);
		session.flush();
//
//		question.setQuestionSubject(questionSubject);
//		session.update(question);
//		//session.flush();
//
//		question.setTeacherThatCreated(theTeacher);
//		session.update(question);
//		//session.flush();

		theTeacher.getQuestionsCreated().add(question);
		session.update(theTeacher);

		questionSubject.getSubjectQuestions().add(question);
		session.update(questionSubject);
		session.flush();

		Course_Question cq;
		for(Course course:questionCourses) {
			cq=new Course_Question(course,question);
			session.save(cq);
			//session.flush();

			course.getCourseQuestions().add(cq);
			session.update(course);
			//session.flush();

			question.getQuestionCourses().add(cq);
			session.update(question);
			session.flush();
		}
		session.getTransaction().commit();

		int i;
		for (i = 0 ; i < _LoggedInList.size() && _LoggedInList.get(i).get_class() != 0; i++){}
		if (i == _LoggedInList.size()){
			return;
		} else {
			try {
				List<Question> list = GetEducational.getAllQuestions(session);
				_LoggedInList.get(i).getClient().sendToClient(new Message("UpdateAllQuestionsToPrincipal",list));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void addExam(Exam exam,int teacherId,int courseId,int subjectId,List<Integer> questionsIds,List<Integer> points) {
		Teacher teacher=GetUsers.getTeacherById(session,teacherId);
		Course course=GetExamBuliding.getCourseById(session,courseId);
		Subject subject=GetExamBuliding.getSubjectById(session,subjectId);
		List<Question> questions=GetExamBuliding.getQuestionsById(session,questionsIds);
		/*saving exam*/
		session.beginTransaction();
		session.clear();
		exam.setTeacherThatCreated(teacher);
		exam.setExamSubject(subject);
		exam.setPoints(points);
		exam.setExamCourse(course);
		session.save(exam);
		//session.save(exam);
		//session.flush();
		/*end of saving exam*/

//		/updating exam/
//		exam.setPoints(points);
//		session.update(exam);
//		//session.flush();
//
//		exam.setTeacherThatCreated(teacher);
//		session.update(exam);
//		//session.flush();
//
//		exam.setExamSubject(subject);
//		session.update(exam);
//		//session.flush();
//
//		exam.setExamCourse(course);
//		session.update(exam);
//		//session.flush();
//		/end of updating exam/

		/*updating teacher*/
		teacher.getExamsCreated().add(exam);
		session.update(teacher);
		//session.flush();
		/*end of updating teacher*/

		/*updating course*/
		course.getCourseExams().add(exam);
		session.update(course);
		//session.flush();
		/*end of updating course*/

		/*updating subject*/
		subject.getSubjectExams().add(exam);
		session.update(subject);
		//session.flush();
		/*end of updating subject*/

		Exam_Question eq;
		int i1=0;
		for(Question question:questions) {
			eq=new Exam_Question(exam,question,points.get(i1++));
			session.save(eq);
			//session.flush();

			question.getQuestionExams().add(eq);
			session.update(question);
			//session.flush();

			exam.getExamQuestions().add(eq);
			session.update(exam);
			//session.flush();


		}
		//session.flush();
		//session.clear();
		session.getTransaction().commit();

		int i;
		for (i = 0 ; i < _LoggedInList.size() && _LoggedInList.get(i).get_class() != 0; i++){}
		if (i == _LoggedInList.size()){
			return;
		} else {
			try {
				List<Exam> list = GetEducational.getAllExamsAllNeeded(session);
				_LoggedInList.get(i).getClient().sendToClient(new Message("UpdateAllExamsToPrincipal",list));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void addCompExam(ComputerizedExamToExecute compExam,int teacherId,int examId) {

		Teacher teacher=GetUsers.getTeacherById(session,teacherId);
		Exam exam=GetExamBuliding.getExamById(session,examId);
		session.beginTransaction();
		session.clear();
		session.save(compExam);
		//session.flush();

		compExam.setExam(exam);
		session.update(compExam);
		//session.flush();

		compExam.setTeacherThatExecuted(teacher);
		session.update(compExam);
		//session.flush();

		teacher=compExam.getTeacherThatExecuted();
		teacher.getExecutedExams().add(compExam);
		session.update(teacher);
		//session.flush();


		exam.getCompExamsToExecute().add(compExam);
		session.update(exam);
		session.flush();

		session.getTransaction().commit();
	}
	public static void addManualExam(ManualExamToExecute compExam, int teacherId, int examId) {

		Teacher teacher=GetUsers.getTeacherById(session,teacherId);
		Exam exam=GetExamBuliding.getExamById(session,examId);
		session.beginTransaction();
		session.clear();
		session.save(compExam);
		//session.flush();

		compExam.setExam(exam);
		session.update(compExam);
		//session.flush();

		compExam.setTeacherThatExecuted(teacher);
		session.update(compExam);
		//session.flush();

		teacher=compExam.getTeacherThatExecuted();
		teacher.getExecutedExams().add(compExam);
		session.update(teacher);
		//session.flush();


		exam.getCompExamsToExecute().add(compExam);
		session.update(exam);
		session.flush();

		session.getTransaction().commit();
	}
	public static void addTeacher(Teacher teacher,List<Integer> subjectsIds,List<Integer> coursesIds) {
		List<Course> courses=GetExamBuliding.getCoursesById(session,coursesIds);
		List<Subject> subjects=GetExamBuliding.getSubjectsById(session,subjectsIds);
		session.beginTransaction();
		session.clear();

		session.save(teacher);
		//session.flush();

		Teacher_Subject ts;
		Teacher_Course tc;
		for(Subject subject:subjects) {
			ts=new Teacher_Subject(teacher,subject);
			session.save(ts);
			//session.flush();

			teacher.getTeacherSubjects().add(ts);
			session.update(teacher);
			//session.flush();

			subject.getSubjectTeachers().add(ts);
			session.update(subject);
			//session.flush();
		}
		for(Course course:courses) {
			tc=new Teacher_Course(teacher,course);
			session.save(tc);
			//session.flush();

			teacher.getTeacherCourses().add(tc);
			session.update(teacher);
			//session.flush();

			course.getCourseTeachers().add(tc);
			session.update(course);
			//session.flush();
		}
		session.flush();
		session.getTransaction().commit();
	}

	public static void addStudent(Student student,List<Integer> subjectsids,List<Integer> coursesids) {
		List<Course> courses=GetExamBuliding.getCoursesById(session,coursesids);
		List<Subject> subjects=GetExamBuliding.getSubjectsById(session,subjectsids);
		session.beginTransaction();
		session.clear();

		session.save(student);
		//session.flush();

		Student_Subject ss;
		Student_Course sc;
		for(Subject subject:subjects) {
			ss=new Student_Subject(student,subject);
			session.save(ss);
			//session.flush();

			student.getStudentSubjects().add(ss);
			session.update(student);
			//session.flush();

			subject.getSubjectStudents().add(ss);
			session.update(subject);
			//session.flush();
		}
		for(Course course:courses) {
			sc=new Student_Course(student,course);
			session.save(sc);
			//session.flush();

			student.getStudentCourses().add(sc);
			session.update(student);
			//session.flush();

			course.getCourseStudents().add(sc);
			session.update(course);
			//session.flush();
		}
		session.flush();
		session.getTransaction().commit();
	}

	public static void updateGradeAndCopyToStudent(int copyId, int gradeId, String studentAnswers, int userId, ComputerizedExamToExecute compExam, int examGrade, boolean onTime) {
		session.beginTransaction();
		session.clear();
		Copy copy = GetGrading.getCopyById(session,copyId);
		copy.setAnswers(studentAnswers);
		session.update(copy);
		//session.flush();

		Grade grade = GetGrading.getGradeById(session,gradeId);
		grade.setGrade(examGrade);
		grade.setDoneOnTime(onTime);

		session.update(grade);
		//session.flush();

		copy.setGrade(grade);
		grade.setExamCopy(copy);
		session.update(copy);
		//session.flush();

		session.update(grade);
		//session.flush();

		compExam.getCopies().add(copy);
		session.update(compExam);

		if (onTime) {
			compExam.setNumberOfStudentDoneInTime(compExam.getNumberOfStudentDoneInTime()+1);
			session.update(compExam);
		}
		else {
			compExam.setNumberOfStudentNotDoneInTime(compExam.getNumberOfStudentNotDoneInTime()+1);
			session.update(compExam);
		}
		session.flush();

		session.getTransaction().commit();
	}

	public static void updateGradeAndCopyToManualExam(String studentAnswers, Student user, ManualExamToExecute manualExamToExecute, int examGrade, boolean onTime) {
		session.beginTransaction();
		session.clear();
		Copy copy = GlobalDataSaved.currentCopy;
		copy.setAnswers(studentAnswers);
		copy.setCompExamToExecute(manualExamToExecute);
		session.update(copy);
		//session.flush();

		Grade grade = GlobalDataSaved.currentGrade;
		grade.setGrade(examGrade);
		grade.setDoneOnTime(onTime);

		session.update(grade);
		//session.flush();

		copy.setGrade(grade);
		grade.setExamCopy(copy);
		session.update(copy);
		//session.flush();

		session.update(grade);
		//session.flush();

		manualExamToExecute.getCopies().add(copy);
		session.update(manualExamToExecute);

		if (onTime) {
			manualExamToExecute.setNumberOfStudentDoneInTime(manualExamToExecute.getNumberOfStudentDoneInTime()+1);
			session.update(manualExamToExecute);
		}
		else {
			manualExamToExecute.setNumberOfStudentNotDoneInTime(manualExamToExecute.getNumberOfStudentNotDoneInTime()+1);
			session.update(manualExamToExecute);
		}
		session.flush();

		session.getTransaction().commit();
	}

	public static int[] createGradeAndCopyToStudent(Copy copy, Grade grade, int userId, ComputerizedExamToExecute compExam, int studentsDoingNumber) {
		session.beginTransaction();
		session.clear();
		Student student = GetUsers.getStudentById(session, userId);

		compExam.setNumOfStudentDoing(studentsDoingNumber);
		session.update(compExam);

		copy.setCompExamToExecute(compExam);
		session.update(copy);
		session.flush();

		session.save(grade);
		session.flush();

		copy.setGrade(grade);
		grade.setExamCopy(copy);
		grade.setStudent(student);
		session.update(copy);
		student.setGrades(grade.getStudent().getGrades());
		//session.flush();
		session.update(student);
		session.update(grade);
		session.flush();
		session.getTransaction().commit();

		return new int[]{grade.getId(), copy.getId()};
	}
	public static void createGradeAndCopyToManualExam(String studentAnswers, Student user, ManualExamToExecute manualExamToExecute, int grade, int studentsDoingNumber) {
		session.beginTransaction();
		session.clear();

		manualExamToExecute.setNumOfStudentDoing(studentsDoingNumber);
		session.update(manualExamToExecute);

		Copy copy = new Copy();
		copy.setAnswers(studentAnswers);
		copy.setCompExamToExecute(manualExamToExecute);
		session.save(copy);
		//session.flush();

		Grade grade1 = new Grade(user,grade,true,manualExamToExecute.getExam().getTime(),
				false,manualExamToExecute.getDateOfExam(),manualExamToExecute.getDateOfExam(), false);

		session.save(grade1);
		//session.flush();

		copy.setGrade(grade1);
		grade1.setExamCopy(copy);
		session.update(copy);
		//session.flush();
		session.update(grade1);
		//session.flush();

		GlobalDataSaved.currentGrade = grade1;
		GlobalDataSaved.currentCopy = copy;
		session.flush();
		session.getTransaction().commit();
	}

	public static void teacherApproveStudentGrade(int gradeId,int newGrade,String notes) {
		Grade grade= GetGrading.getGradeById(session,gradeId);
		session.beginTransaction();
		session.clear();

		grade.setGrade(newGrade);
		session.update(grade);
		//session.flush();

		grade.setTeacherApprovement(true);
		session.update(grade);
		//session.flush();

		grade.setTeacherNotes(notes);
		session.update(grade);
		session.flush();

		session.getTransaction().commit();
	}

	public static void updateStudentsNumber(ComputerizedExamToExecute compExam, int studentNumOnTime, int studentNumNotOnTime) {
		session.beginTransaction();
		session.clear();
		compExam.setNumberOfStudentDoneInTime(studentNumOnTime);
		session.update(compExam);
		//session.flush();
		compExam.setNumberOfStudentNotDoneInTime(studentNumNotOnTime);
		session.update(compExam);
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
					for (LoggedInClient _loggedInClient : _LoggedInList) {
						if (_loggedInClient.get_id().equals(users.get(0).getUserID())) {
							Warning warning = new Warning("user already logged in!");
							try {
								client.sendToClient(warning);
								System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
								return;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
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
					//session.clear();
					return;
				}
				boolean isHandled;

				isHandled=HandleMsgTeacher.handleTeacher(session,msgFromClient,contentOfMsg,client);
				if(isHandled)return;

				isHandled= HandleMsgPrincipal.handlePrincipal(session,msgFromClient,contentOfMsg,client);
				if(isHandled)return;

				isHandled= HandleMsgStudent.handleStudent(session,msgFromClient,contentOfMsg,client);
				if(isHandled)return;
				
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
	public static void AddExtraTime(ExamToExecute examToExecute, int ExtraTime){
		session.beginTransaction();
		session.clear();
		examToExecute.setExtraTime(ExtraTime);
		examToExecute.setIsExtraNeeded(1);
		session.update(examToExecute);
		session.flush();
		session.getTransaction().commit();
	}
}
