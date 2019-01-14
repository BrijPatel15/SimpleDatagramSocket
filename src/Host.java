import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Host {

	private static Logger hostLogger = Logger.getLogger(Host.class.getName());
	
    public static void main(String[] args) {
        new Host().run();
    }
	
    private void run() {
    	try {
    		DatagramSocket recieveSocket = new DatagramSocket(23);
    		while(true)
    		{
    			DatagramPacket recievePacket = new DatagramPacket(new byte[265], 265);
    		}
    	} catch(IOException e) {
    		hostLogger.log(Level.SEVERE, e.getMessage());
    	}
    }
}
