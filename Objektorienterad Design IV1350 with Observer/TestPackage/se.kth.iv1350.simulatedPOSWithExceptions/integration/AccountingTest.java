package se.kth.iv1350.simulatedPOSWithExceptions.integration;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.simulatedPOSWithExceptions.model.Sale;

import static org.junit.jupiter.api.Assertions.*;

class AccountingTest {
	private Accounting accounting = new Accounting();
	private Sale sale = new Sale(this.accounting);

	@Before
	public void beforeAll(){
		ItemDTO item = new ItemDTO("00001", "Milk", 10, 12);
		this.sale.addSoldItem(item);
	}

	@Test
	void testInitialLoggedSaleFalse(){
		boolean expResult = false;
		boolean actResult = this.accounting.saleLogged;
		assertEquals(expResult, actResult, "Sale marked as logged without being logged.");
	}

	@Test
	void testLoggedSaleSetToTrue(){
		boolean expResult = true;
		this.accounting.logSale(this.sale.getSaleDTO());
		boolean actResult = this.accounting.saleLogged;
		assertEquals(expResult, actResult, "Sale not marked as logged despite being logged.");
	}
}