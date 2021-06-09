package se.kth.iv1350.simulatedPOSWithExceptions.model;

import se.kth.iv1350.simulatedPOSWithExceptions.exception.InsufficientPaymentException;

/**
 * Represents the cash register.
 */

public class CashRegister {

	public double balance;

	/**
	 * Creates a new instance of the cash register with 1000 as balance.
	 */

	public CashRegister(){
		this.balance = 1000;
	}

	/**
	 * Adds a payment to the registers and calculates the change to return.
	 *
	 * @param paymentAmount The amount that was paid.
	 * @param currentSale The purchase which is being paid.
	 * @return The amount of change to give back to the customer.
	 */

	public double addPayment(double paymentAmount, SaleDTO currentSale) throws InsufficientPaymentException {
		this.balance += paymentAmount;
		double change = paymentAmount - currentSale.runningTotal;
		this.balance -= change;
		if(change < 0){
			throw new InsufficientPaymentException(Math.abs(change));
		}
		return change;
	}

}
