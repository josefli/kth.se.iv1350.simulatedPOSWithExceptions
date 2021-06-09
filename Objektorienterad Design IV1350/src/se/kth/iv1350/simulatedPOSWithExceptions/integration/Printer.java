package se.kth.iv1350.simulatedPOSWithExceptions.integration;

import se.kth.iv1350.simulatedPOSWithExceptions.model.ReceiptDTO;

/**
 * Represents the external printer.
 */
public class Printer {

	/**
	 * Creates an instance of the printer used as a reference. An empty method.
	 */
	public Printer(){

	}

	/**
	 * Prints the receiptDTO created by receipt.
	 *
	 * @param receiptDTO Receipt to be printed.
	 */
	public void printReceipt(ReceiptDTO receiptDTO){
		System.out.println(receiptDTO.fullReceipt);
	}
}
