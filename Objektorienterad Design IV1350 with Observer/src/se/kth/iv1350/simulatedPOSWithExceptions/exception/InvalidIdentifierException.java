package se.kth.iv1350.simulatedPOSWithExceptions.exception;

/**
 * Thrown when an item is scanned which cannot be found in the inventory.
 */
public class InvalidIdentifierException extends Exception {

	/**
	 * Creates a new instance of the exception with a message describing the exception.
	 * @param itemID the item that could not be found.
	 */
	public InvalidIdentifierException(String itemID){
		super("<log> Item with itemID " + itemID + " not in inventory database. <log>");
	}
}
