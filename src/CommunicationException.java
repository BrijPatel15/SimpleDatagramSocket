/**
 * Exception thrown when the String sent from Client is invalid
 * */
public class CommunicationException extends Exception{
    public CommunicationException(String errorMessage) {
        super(errorMessage);
    }
}
