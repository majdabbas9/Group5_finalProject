package il.cshaifasweng.OCSFMediatorExample.client;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import aidClasses.Warning;

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
					Message msgFromServer=(Message) msg;
					String contentOfMsg=msgFromServer.getMsg();
					if(contentOfMsg.equals("successful login")) {
						User LogedInUser=(User) msgFromServer.getObj();
						GlobalDataSaved.connectedUser=LogedInUser;
						if(LogedInUser.getClass().equals(Student.class)) {
							App.setRoot("studentHome");
						}
						if(LogedInUser.getClass().equals(Teacher.class))
						{
							App.setRoot("teacherHome");
						}
					}
					if(contentOfMsg.equals("successful logout")) {
						App.setRoot("login");
					}
					if(contentOfMsg.equals("added the question successfully")) {
						GlobalDataSaved.connectedUser=(User) msgFromServer.getObj();
						System.out.println("teacher :"+GlobalDataSaved.connectedUser.getFirstName()+"added question");
						App.setRoot("buildExam");
					}
					if(contentOfMsg.equals("added the exam successfully")) {
						App.setRoot("buildExam");
					}
					if(contentOfMsg.equals("added the CompExam successfully")) {
						App.setRoot("teacherHome");
					}
					if(contentOfMsg.equals("sending teacher subjects")) {
						GlobalDataSaved.teacherSubjects=(List<Subject>)msgFromServer.getObj();
						return;
					}
					if(contentOfMsg.equals("sending teacher courses")) {
						GlobalDataSaved.teacherCourses=(List<Course>)msgFromServer.getObj();
						return;
					}
				}
            catch (Exception ex) {
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
		protected void closeConnectionWithServer()
		{

		}

}
