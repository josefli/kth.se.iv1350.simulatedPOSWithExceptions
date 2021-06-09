package se.kth.iv1350.simulatedPOSWithExceptions.exception;

/**
 * Thrown when a connection to accounting cannot be established.
 */
public class AccountingUnavailableException extends RuntimeException {
	/**
	 * Creates a new instance of the exception with a message describing the exception.
	 * @param msg Message describing the exception.
	 */

	public AccountingUnavailableException(String msg) {
		super("<log> " + msg + "<log>");
	}
}
