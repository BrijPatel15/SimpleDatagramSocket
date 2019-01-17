import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Host {

	private static Logger hostLogger = Logger.getLogger(Host.class.getName());
	
    public static void main(String[] args) {
        new Host().run();
    }
	/**
	 * Creates two datagramsockets one to receive packets from the client on port 23 and another to send packets to the Server.
	 * It prints out the information received and also the information that is sent
	 * */
    private void run() {
    	try {
    		DatagramSocket recieveSocket = new DatagramSocket(23);
    		DatagramSocket sendSocket = new DatagramSocket();
    		while(true)
    		{
    			DatagramPacket recievePacketClient = new DatagramPacket(new byte[100], 100);
    			recieveSocket.receive(recievePacketClient);
//    			hostLogger.log(Level.INFO, "Recieved from Client: "+Arrays.toString(recievePacketClient.getData()));
//    			hostLogger.log(Level.INFO, new String("Recieved from Client: "+new String(recievePacketClient.getData())));
    			System.out.println("Recieved from Client: "+Arrays.toString(recievePacketClient.getData()));
    			System.out.println(new String("Recieved from Client: "+new String(recievePacketClient.getData())));
    			DatagramPacket sendPacketServer = new DatagramPacket(recievePacketClient.getData(), recievePacketClient.getData().length, InetAddress.getLocalHost(), 69);
    			System.out.println("Send to Server: " + new String(sendPacketServer.getData()));
    			sendSocket.send(sendPacketServer);
    			DatagramPacket recievePacketServer = new DatagramPacket(new byte[100], 100);
    			sendSocket.receive(recievePacketServer);
//    			hostLogger.log(Level.INFO, "Recieved from Server: "+Arrays.toString(recievePacketServer.getData()));
//    			hostLogger.log(Level.INFO, new String("Recieved from Server: "+recievePacketServer.getData()));
    			System.out.println("Recieved from Server: "+Arrays.toString(recievePacketServer.getData()));
    			System.out.println(new String("Recieved from Server: "+recievePacketServer.getData()));
    			DatagramPacket sendPacketClient = new DatagramPacket(recievePacketServer.getData(), recievePacketServer.getData().length, recievePacketClient.getAddress(), recievePacketClient.getPort());
//    			hostLogger.log(Level.INFO, "Sent to Client: "+Arrays.toString(sendPacketClient.getData()));
//    			hostLogger.log(Level.INFO, new String("Sent to Client: "+new String(sendPacketClient.getData())));
    			System.out.println("Sent to Client: "+Arrays.toString(sendPacketClient.getData()));
    			System.out.println(new String("Sent to Client: "+new String(sendPacketClient.getData())));
    			sendSocket.send(sendPacketClient);
    		}
    	} catch(IOException e) {
    		hostLogger.log(Level.SEVERE, e.getMessage());
    	}
    }
}
