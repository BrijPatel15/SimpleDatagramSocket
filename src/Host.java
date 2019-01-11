import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Host {

    public static void main(String[] args) {
        new Host().run();
    }
	
    private void run() {
    	try {
    		DatagramSocket socket = new DatagramSocket(23);
    		while(true)
    		{
    			DatagramPacket recievePacket = new DatagramPacket(new byte[265], 265);
    		}
    	} catch(IOException e) {
    		
    	}
    }
}
