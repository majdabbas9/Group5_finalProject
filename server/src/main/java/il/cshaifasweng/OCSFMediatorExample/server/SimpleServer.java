package il.cshaifasweng.OCSFMediatorExample.server;

import aidClasses.Message;
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
import il.cshaifasweng.OCSFMediatorExample.server.Generating.GetUsers;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.educational.*;

import aidClasses.Warning;

import javax.persistence.Query;

public class SimpleServer extends AbstractServer {
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
		configuration.addAnnotatedClass(Student.class);
		configuration.addAnnotatedClass(Teacher.class);
		configuration.addAnnotatedClass(Course.class);
		configuration.addAnnotatedClass(Subject.class);
		configuration.addAnnotatedClass(ComputerizedExamToExecute.class);
		configuration.addAnnotatedClass(Exam.class);
		configuration.addAnnotatedClass(Question.class);
		configuration.addAnnotatedClass(Copy.class);
		configuration.addAnnotatedClass(Grade.class);

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
	public void logout(User user)
	{
		session.beginTransaction();
		user.setConnected(false);
		session.update(user);
		session.flush();
		session.clear();
		session.getTransaction().commit();
	}
	public void login(User user)
	{
		session.beginTransaction();
		user.setConnected(true);
		session.update(user);
		session.flush();
		session.clear();
		session.getTransaction().commit();
	}
	public void addQuestion(Question question)
	{
		session.beginTransaction();
		session.save(question);
		session.flush();

		Teacher teacher=question.getTeacherThatCreated();
		teacher.getQuestionsCreated().add(question);
		session.update(teacher);
		session.flush();

		Subject subject=question.getQuestionSubject();
		subject.getSubjectQuestions().add(question);
		session.update(subject);
		session.flush();

		for (Course course:question.getQuestionCourses())
		{
			course.getCourseQuestions().add(question);
			session.update(course);
			session.flush();
		}
		session.clear();
		session.getTransaction().commit();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		if(msg.getClass().equals(Message.class)) {
			try {
				Message msgFromClient=(Message) msg;
				String contentOfMsg=msgFromClient.getMsg();
				if(contentOfMsg.equals("#login")) {
                String []userDetails=(String[])(((Message) msg).getObj());
				String userName=userDetails[0];
				String password=userDetails[1];
				String q="from User where userName='"+userName+"'";
				Query query=session.createQuery(q);
				List<User> users=(List<User>) (query.getResultList());
				if(users.size()==0)
				{
					Warning warning = new Warning("there is no such a username");
					try {
						client.sendToClient(warning);
						System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				if(!users.get(0).getPassWord().equals(password))
				{
					Warning warning = new Warning("wrong password");
					try {
						client.sendToClient(warning);
						System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				if(users.get(0).getConnected())
				{
					Warning warning = new Warning("user already logged in!");
					try {
						client.sendToClient(warning);
						System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				login(users.get(0));
				Message msgToClient = new Message("successful login",users.get(0));
				client.sendToClient(msgToClient);
				return;
				}
				if(contentOfMsg.equals("#logout"))
				{
					User userToLogout=(User) msgFromClient.getObj();
					logout(userToLogout);
					Message msgToClient = new Message("successful logout",null);
					client.sendToClient(msgToClient);
				}
				if(contentOfMsg.equals("#addQuestion"))
				{
					Question question=(Question)msgFromClient.getObj();
					addQuestion(question);
					Message messageToClient = new Message("added the question successfully");
					try {
						client.sendToClient(messageToClient);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
			}

			}
        else
			{
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
