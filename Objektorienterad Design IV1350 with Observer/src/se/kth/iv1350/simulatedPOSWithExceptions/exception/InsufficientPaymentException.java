package se.kth.iv1350.simulatedPOSWithExceptions.exception;

/**
 * Thrown when an item is scanned which cannot be found in the inventory.
 */

public class InsufficientPaymentException extends Exception{
	/**
	 * Creates a new instance of the exception with a message describing the exception.
	 * @param difference the amount that is missing for the payment amount to be sufficient
	 */
	public InsufficientPaymentException(double difference){
		super("The payment does not cover the total of the sale. " + difference + " SEK is lacking.");
	}
}
