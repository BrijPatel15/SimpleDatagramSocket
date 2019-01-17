import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {

	private Logger serverLogger = Logger.getLogger(Server.class.getName());
	private final String PATTERN_MATCHER = "([^0-9]+(\\.(?i)(txt)))0[^0-9]*0$";
	public static void main(String[] args) throws CommunicationException {
		new Server().run();
	}
	
	private void run() throws CommunicationException {
		try {
			DatagramSocket receiveSocket = new DatagramSocket(69);

			while(true) {
				DatagramPacket recievePacket = new DatagramPacket (new byte[100], 100);
				receiveSocket.receive(recievePacket);
//				serverLogger.log(Level.INFO, "Recieved from Host: "+Arrays.toString(recievePacket.getData()));
//				serverLogger.log(Level.INFO, new String("Recieved from Host: "+new String(recievePacket.getData())));
				serverLogger.log(Level.INFO, "Recieved from Host: "+Arrays.toString(recievePacket.getData()));
				serverLogger.log(Level.INFO, new String("Recieved from Host: "+new String(recievePacket.getData())));
				DatagramPacket sendPacket = createPacket(recievePacket);
//				serverLogger.log(Level.INFO, "Sent to Host: "+Arrays.toString(sendPacket.getData()));
//				serverLogger.log(Level.INFO, new String("Recieved from Client: "+new String(sendPacket.getData())));
				System.out.println("Sent to Host: "+Arrays.toString(sendPacket.getData()));
				System.out.println(new String("Recieved from Client: "+new String(sendPacket.getData())));
				DatagramSocket sendSocket = new DatagramSocket();
				sendSocket.send(sendPacket);
				sendSocket.close();
			}
		} catch (IOException e) {
			serverLogger.log(Level.SEVERE, e.getMessage());
		}
	}
	
	private DatagramPacket createPacket(DatagramPacket inputPacket) throws CommunicationException {
		String recievedString = new String(inputPacket.getData());
		boolean validInput = validate(recievedString);
		if(recievedString.substring(0, 2).equals("01") && validInput) {
			return new DatagramPacket("0301".getBytes(), "0301".length(), inputPacket.getAddress(), inputPacket.getPort());
		}
		else if(recievedString.substring(0, 2).equals("02") && validInput) {
			return new DatagramPacket("0400".getBytes(), "0400".length(), inputPacket.getAddress(), inputPacket.getPort());
		}
		return null;
	}

	private boolean validate(String inputString) throws CommunicationException {
		if(inputString.substring(0, 2).equals("01") || inputString.substring(0, 2).equals("02")){
			String checkString = inputString.substring(2).trim();
			String txtFirstPart = checkString.contains("0") ? checkString.substring(0,checkString.indexOf("0")) : null; 
			String txtSecondPart = checkString.lastIndexOf("0") != -1 ? checkString.substring(checkString.indexOf("0") + 1, checkString.lastIndexOf("0")) : null;
			Pattern pattern = Pattern.compile(PATTERN_MATCHER);
			Matcher matcher = pattern.matcher(checkString);
			if(matcher.matches() == true) {
				if(txtFirstPart != null && txtSecondPart != null && containsDigit(txtFirstPart) == false && 
						containsDigit(txtSecondPart) == false) {
					return true;
				}
			} else {
				throw new CommunicationException("The proper format has not been followed");
			}
		}
		throw new CommunicationException("First two bytes were not 01 or 02");
	}

	public final boolean containsDigit(String s) {
	    if (s != null && !s.isEmpty()) {
	        for (char c : s.toCharArray()) {
	            if (Character.isDigit(c) == true) {
	                return true;
	            }
	        }
	    }

	    return false;
	}
	
}
