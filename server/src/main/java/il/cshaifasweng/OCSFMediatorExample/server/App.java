package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.IOException;
import aidClasses.Color;

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
        System.out.println(Color.RED_BOLD+"Server is listening"+Color.ANSI_RESET);
    }

}
