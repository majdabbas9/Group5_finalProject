/**
 * Sample Skeleton for 'login.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import aidClasses.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class Login {

    @FXML // fx:id="loginBtn"
    private Button loginBtn; // Value injected by FXMLLoader

    @FXML // fx:id="password"
    private PasswordField password; // Value injected by FXMLLoader

    @FXML // fx:id="userName"
    private TextField userName; // Value injected by FXMLLoader

    @FXML // fx:id="warning"
    private Text warning; // Value injected by FXMLLoader

    @FXML
    void logIn(ActionEvent event)throws IOException {
        if(userName.getText().equals(""))
        {
            warning.setText("user name missing !!");
            return;
        }
        if(password.getText().equals(""))
        {
            warning.setText("password missing !!");
            return;
        }

        String [] user={userName.getText(),password.getText()};
        Message msg = new Message("#login", user); // creating a msg to the server demanding the students
        SimpleClient.getClient().sendToServer(msg); // sending the msg to the server
    }
}
