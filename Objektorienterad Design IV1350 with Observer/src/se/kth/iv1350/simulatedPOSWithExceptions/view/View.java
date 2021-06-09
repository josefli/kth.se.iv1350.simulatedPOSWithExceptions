package se.kth.iv1350.simulatedPOSWithExceptions.view;

import se.kth.iv1350.simulatedPOSWithExceptions.TotalRevenueFileOutput;
import se.kth.iv1350.simulatedPOSWithExceptions.controller.Controller;
import se.kth.iv1350.simulatedPOSWithExceptions.exception.FailedConnectionException;
import se.kth.iv1350.simulatedPOSWithExceptions.exception.InsufficientPaymentException;
import se.kth.iv1350.simulatedPOSWithExceptions.integration.ItemDTO;

import java.io.InvalidObjectException;

/**
 * Represents the view.
 */

public class View {

	private Controller controller;

	/**
	 * Creates a view.
	 * @param contr Controller that the view communicates with.
	 */

	public View(Controller contr) {
		this.controller = contr;
		controller.addObserver(new TotalRevenueView());
		controller.addObserver(new TotalRevenueFileOutput());
	}

	/**
	 * Runs the steps of a sale with hard coded parameters and method calls.
	 */

	public void hardCodedSale() {
		double amountCustomerPays = 100;
		this.controller.beginNewSale();

		presentNewScannedItem("00001");
		presentNewScannedItem("00002");
		presentNewScannedItem("00000");
		presentNewScannedItem("00001");
		presentNewScannedItem("00006");
		presentNewScannedItem("00003");

		System.out.println("End sale.");
		System.out.println("Final running total: " + this.controller.endSale().runningTotal + " SEK");
		System.out.println("Total tax of sale: " + this.controller.getRunningTotal().tax + "%\n\n");
		try {
			this.controller.makePayment(amountCustomerPays);
		}
		catch(InsufficientPaymentException e){
			System.out.println(e.getMessage());
		}

		this.controller.beginNewSale();

		presentNewScannedItem("00001");
		presentNewScannedItem("00002");
		System.out.println("End sale.");
		System.out.println("Final running total: " + this.controller.endSale().runningTotal + " SEK");
		System.out.println("Total tax of sale: " + this.controller.getRunningTotal().tax + "%\n\n");
		try {
			this.controller.makePayment(amountCustomerPays);
		}
		catch(InsufficientPaymentException e){
			System.out.println(e.getMessage());
		}
	}

	private void presentNewTotal(){
		System.out.println("Current running total:\t   " + this.controller.getRunningTotal().runningTotal + " SEK");
		System.out.println("Current total tax:\t\t\t " + this.controller.getRunningTotal().tax + "%\n");
	}

	private void presentNewScannedItem(String itemID) {
		try{
			ItemDTO item = this.controller.scanItem(itemID);
			System.out.println("Item scanned: " + item.itemDescription + "\t\t" + item.price + "SEK");
			presentNewTotal();
		}
		catch(FailedConnectionException | InvalidObjectException e){
			System.out.println(e.getMessage() + "\n");
		}
	}
}
