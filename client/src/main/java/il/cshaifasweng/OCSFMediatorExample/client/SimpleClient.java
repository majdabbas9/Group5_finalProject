package il.cshaifasweng.OCSFMediatorExample.client;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import aidClasses.Warning;

import javax.xml.crypto.Data;
import java.io.IOException;

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
						GlobalDataSaved.connectedUser=null;
						App.setRoot("login");
					}
					if(contentOfMsg.equals("added the question successfully")) {
						GlobalDataSaved.connectedUser=(Teacher)msgFromServer.getObj();
						App.setRoot("buildExam");
					}
					if(contentOfMsg.equals("added the exam successfully")) {
						GlobalDataSaved.connectedUser=(Teacher)msgFromServer.getObj();
						App.setRoot("buildExam");
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

}
