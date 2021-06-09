package se.kth.iv1350.simulatedPOSWithExceptions.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.simulatedPOSWithExceptions.exception.InsufficientPaymentException;
import se.kth.iv1350.simulatedPOSWithExceptions.integration.Accounting;
import se.kth.iv1350.simulatedPOSWithExceptions.integration.ItemDTO;

import static org.junit.jupiter.api.Assertions.*;

class CashRegisterTest {
	Accounting accounting = new Accounting();
	CashRegister cashRegister = new CashRegister();
	Sale sale = new Sale(accounting);

	@BeforeEach
	public void setUp(){
		this.sale.addSoldItem(new ItemDTO("0001", "Milk", 12, 5));
	}

	@Test
	void testAddPayment() {
		double amountPaid = 50;
		try {
			double expResult = this.cashRegister.balance + this.sale.runningTotal;
			this.cashRegister.addPayment(amountPaid, this.sale.getSaleDTO());
			double acctResult = this.cashRegister.balance;
			assertEquals(acctResult, expResult, "Balance was not updated correctly.");
		}
		catch(InsufficientPaymentException e){
			fail("Exception thrown when payment should have passed.");
		}
	}

	@Test
	void testAddPaymentCorrectChange() {
		double amountPaid = 100;
		try {
			double acctResult = this.cashRegister.addPayment(amountPaid, this.sale.getSaleDTO());
			this.sale.registerPayment(amountPaid);
			double expResult = this.sale.getSaleDTO().payment.change;

			assertEquals(acctResult, expResult, "Change was not returned correctly.");
		}
		catch(InsufficientPaymentException e){
			fail("Exception thrown when payment should have passed.");
		}
	}

	@Test
	void testAddPaymentNoChange() {
		try {
			double amountPaid = this.sale.runningTotal;
			double acctResult = this.cashRegister.addPayment(amountPaid, this.sale.getSaleDTO());
			this.sale.registerPayment(amountPaid);
			double expResult = this.sale.getSaleDTO().payment.change;

			assertEquals(acctResult, expResult, "Change was not set to 0 when payment was even.");
		}
		catch(InsufficientPaymentException e){
			fail("Exception thrown when payment should have passed.");
		}
	}
}