package se.kth.iv1350.simulatedPOSWithExceptions.exception;

/**
 * Thrown when the connection to a database cannot be established.
 */
public class FailedConnectionException extends RuntimeException {

	/**
	 * Creates a new instance of the exception with a message describing the exception and the cause
	 * specifying the type of connection.
	 * @param msg Message describing the exception.
	 */

	public FailedConnectionException(String msg, Exception cause){
		 super(msg, cause);
	}
}
