package se.kth.iv1350.simulatedPOSWithExceptions.exception;

/**
 * Thrown when a connection to inventory cannot be established.
 */

public class InventoryUnavailableException extends RuntimeException {

	/**
	 * Creates a new instance of the exception with a message describing the exception.
	 * @param msg Message describing the exception.
	 */
	public InventoryUnavailableException(String msg) {
		super("<log> " + msg + " <log>");
	}
}
