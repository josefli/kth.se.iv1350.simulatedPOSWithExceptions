package se.kth.iv1350.simulatedPOSWithExceptions.model;

import se.kth.iv1350.simulatedPOSWithExceptions.integration.Printer;

/**
 * This class represents the receipt.
 */

public class Receipt {
	private ReceiptDTO currentReceiptDTO;
	private Printer printer;

	/**
	 * Creates a new receipt with a receiptDTO, an instance of the Printer and calls to print receipt.
	 *
	 * @param saleDTO Sale to make into a receipt.
	 */

	public Receipt(SaleDTO saleDTO){
		this.currentReceiptDTO = new ReceiptDTO(saleDTO);
		this.printer = new Printer();
		this.printer.printReceipt(this.currentReceiptDTO);
	}
}
