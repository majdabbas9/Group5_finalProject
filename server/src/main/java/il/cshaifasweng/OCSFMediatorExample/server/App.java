package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.IOException;
import aidClasses.Color;
import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

/**
 * Hello world!
 *
 */
public class App
{

    public static SimpleServer server;
    public static void main(String[] args) throws IOException {
        App.server = new SimpleServer(3020);
        App.server.listen();
        System.out.println(Color.GREEN_BOLD+"Server is listening"+Color.ANSI_RESET);
    }
}