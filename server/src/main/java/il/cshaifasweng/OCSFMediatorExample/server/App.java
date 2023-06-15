package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.IOException;
import aidClasses.Color;
import aidClasses.GlobalDataSaved;
import aidClasses.Message;
import javafx.application.Application;
import javafx.application.Platform;
import org.greenrobot.eventbus.EventBus;

/**
 * Hello world!
 *
 */
public class App
{
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException
    {
        server = new SimpleServer(3020);
        server.listen();
        System.out.println(Color.GREEN_BOLD+"Server is listening"+Color.ANSI_RESET);
    }

}
