package il.cshaifasweng.OCSFMediatorExample.client;

import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Principal;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.appUsers.User;
import il.cshaifasweng.OCSFMediatorExample.entities.educational.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import aidClasses.Warning;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class SimpleClient extends AbstractClient {


    private static SimpleClient client = null;

    private SimpleClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg.getClass().equals(Warning.class)) {
            Warning warning = (Warning) msg;
            if (((Warning) msg).getMessage().equals("The User was Added to the System")) {
                try {
                    App.setRoot("principalHome");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            EventBus.getDefault().post(new WarningEvent((Warning) msg));
        }
        if (msg.getClass().equals(Message.class)) {
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
                if (contentOfMsg.equals("successful logout")) {
                    GlobalDataSaved.connectedUser = null;
                    App.setRoot("login");
                }
                if (contentOfMsg.equals("added the question successfully")) {
                    GlobalDataSaved.connectedUser = (Teacher) msgFromServer.getObj();
                    App.setRoot("buildExam");
                }
                if (contentOfMsg.equals("added the exam successfully")) {
                    GlobalDataSaved.connectedUser = (Teacher) msgFromServer.getObj();
                    App.setRoot("buildExam");
                }
                if (contentOfMsg.equals("All Subjects Given")) {
                    GlobalDataSaved.subjects = FXCollections.observableArrayList();
                    GlobalDataSaved.subjects.addAll((List<Subject>) msgFromServer.getObj());
                }
                if (contentOfMsg.equals("Teacher Added Successfully")) {
                    GlobalDataSaved.AddFlag = true;

                }
                if (contentOfMsg.equals("Student Added Successfully")) {
                    GlobalDataSaved.AddFlag = true;
                }
            } catch (Exception ex) {
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
