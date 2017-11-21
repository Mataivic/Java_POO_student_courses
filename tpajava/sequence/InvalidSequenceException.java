package tpajava.sequence;

public class InvalidSequenceException extends Exception {

	public InvalidSequenceException() {		
	}

	public InvalidSequenceException(String message) {
		super(message);		
	}

	public InvalidSequenceException(Throwable cause) {
		super(cause);		
	}

	public InvalidSequenceException(String message, Throwable cause) {
		super(message, cause);		
	}

	public InvalidSequenceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);		
	}

}