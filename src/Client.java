import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Client {

	private final String MODE_STRING = "ocTET";
	private final String FILE_NAME = "Client.txt";
	private static Logger clientLog = Logger.getLogger(Client.class.getName());
	
    public static void main(String[] args) {
    	clientLog.setLevel(Level.ALL);
        new Client().run();
    }
    
    public void run() {
    	try {
			DatagramSocket socket = new DatagramSocket();
			for(int i = 0; i <= 10; i++) {
				DatagramPacket sendPacket;
				if(i % 2 == 0) {
					sendPacket = createPacket(false, FILE_NAME);
				} else {
					sendPacket = createPacket(true, FILE_NAME);
				}
				String sentString = new String(sendPacket.getData());
				clientLog.log(Level.INFO,"Client: "+ sentString);
				socket.send(sendPacket);
				DatagramPacket recievePacket = new DatagramPacket(new byte[sendPacket.getData().length], sendPacket.getData().length);
				socket.receive(recievePacket);
				String recieveString = new String(recievePacket.getData());
				clientLog.log(Level.INFO, "Client: "+ recieveString);
			}
			socket.close();
		} catch (IOException e) {
			clientLog.log(Level.SEVERE, e.getMessage());
		}
    	
    }

	public DatagramPacket createPacket(boolean read, String fileName) throws UnknownHostException {
		byte[] initialArray;
		if(read)
		{
			initialArray = "01".getBytes();
		} else 
		{
			initialArray = "02".getBytes();
		}
		byte[] fileArray = new byte[initialArray.length + fileName.length()];
		System.arraycopy(initialArray, 0, fileArray, 0, initialArray.length);
		System.arraycopy(fileName.getBytes(), 0, fileArray, initialArray.length, fileName.getBytes().length);
		clientLog.log(Level.CONFIG, new String(fileArray));
		byte[] finalArray = new byte[fileArray.length + MODE_STRING.length() + "0".length()];
		System.arraycopy(fileArray, 0, finalArray, 0, fileArray.length);
		System.arraycopy(MODE_STRING.getBytes(), 0, finalArray, fileArray.length, MODE_STRING.getBytes().length);
		System.arraycopy("0".getBytes(), 0, finalArray, fileArray.length+MODE_STRING.getBytes().length, "0".getBytes().length);
		clientLog.log(Level.CONFIG, new String(finalArray));
		return new DatagramPacket(finalArray, finalArray.length, InetAddress.getLocalHost(), 23);
	}
	
}
