package se.kth.iv1350.simulatedPOSWithExceptions.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.simulatedPOSWithExceptions.exception.FailedConnectionException;
import se.kth.iv1350.simulatedPOSWithExceptions.exception.InsufficientPaymentException;
import se.kth.iv1350.simulatedPOSWithExceptions.exception.InvalidIdentifierException;
import se.kth.iv1350.simulatedPOSWithExceptions.integration.ItemDTO;
import se.kth.iv1350.simulatedPOSWithExceptions.integration.RegistryCreator;
import se.kth.iv1350.simulatedPOSWithExceptions.model.RunningTotalDTO;

import java.io.InvalidObjectException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
	private RegistryCreator regCreator = new RegistryCreator();
	private Controller contr = new Controller(regCreator);

	@BeforeEach
	void setUp() {
		this.contr.beginNewSale();
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testBeginNewSaleNotNull() {
		assertNotNull(this.contr.getSale());
	}

	@Test
	void testEndSaleRunningTotal() {
		this.contr.getSale().addSoldItem(new ItemDTO("0001", "Milk", 12, 5));
		RunningTotalDTO expResult = this.contr.getSale().getRunningTotalDTO();
		RunningTotalDTO acctResult = this.contr.endSale();
		assertEquals(expResult, acctResult, "RunningTotalDTO of sale is not same as end sale running total.");
	}

	@Test
	void testScanItemAddedTOSoldItems() {
		try {
			ItemDTO item = this.contr.scanItem("00001");
			boolean expResult = true;
			boolean actResult = this.contr.getSale().getSaleDTO().soldItems.containsKey(item);
			assertEquals(expResult, actResult, "Scanned item was not added to sale and returned correctly.");
		}
		catch(InvalidObjectException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testScanMultipleItemsAddedTOSoldItems() {
		try {
			ItemDTO firstItem = this.contr.scanItem("00001");
			ItemDTO secondItem = this.contr.scanItem("00001");
			ItemDTO thirdItem = this.contr.scanItem("00001");
			boolean expResult = true;
			boolean actResultFirst = this.contr.getSale().getSaleDTO().soldItems.containsKey(firstItem);
			assertEquals(expResult, actResultFirst, "First Scanned item was not added to sale and " +
					  "returned correctly.");

			boolean actResultSecond = this.contr.getSale().getSaleDTO().soldItems.containsKey(secondItem);
			assertEquals(expResult, actResultSecond, "Second scanned item was not added to sale and " +
					  "returned correctly.");

			boolean actResultThird = this.contr.getSale().getSaleDTO().soldItems.containsKey(thirdItem);
			assertEquals(expResult, actResultThird, "Third scanned item was not added to sale and " +
					  "returned correctly.");

		}
		catch(InvalidObjectException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testScanItemUpdateRunningTotal() {
		String itemID = "00001";
		try {
			this.contr.scanItem(itemID);
			double expResult = this.regCreator.getItemDTO(itemID).price;
			double actResult = this.contr.getSale().getSaleDTO().runningTotal;
			assertEquals(expResult, actResult, "Running total was incorrectly updated.");
		}
		catch(InvalidObjectException | InvalidIdentifierException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testScanMultipleItemsRunningTotalUpdated() {
		try {
			ItemDTO firstItem = this.contr.scanItem("00001");
			ItemDTO secondItem = this.contr.scanItem("00001");
			ItemDTO thirdItem = this.contr.scanItem("00001");
			double expResult = firstItem.price + secondItem.price + thirdItem.price;
			double actResult = this.contr.getSale().getSaleDTO().runningTotal;
			assertEquals(expResult, actResult, "Running total was not updated correctly after " +
					  "multiple items were added.");
		}
		catch(InvalidObjectException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testScanItemAddedInvalidID() {
		String itemID = "00007";
		try {
			this.contr.scanItem(itemID);
			fail("Did not throw error at invalid item ID.");
		}
		catch(InvalidObjectException e){
			assertTrue(true);
		}
	}

	@Test
	void testScanItemAddedConnectionError() {
		String itemID = "00000";
		try {
			ItemDTO item = this.contr.scanItem(itemID);
			fail("Did not throw connection error.");
		}
		catch(FailedConnectionException e){
			assertTrue(true);
		}
		catch(InvalidObjectException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testScanItemTwice() {
		try {
			this.contr.scanItem("00001");
			ItemDTO item = this.contr.scanItem("00001");
			boolean expResult = true;
			boolean actResult = this.contr.getSale().getSaleDTO().soldItems.containsKey(item);
			assertEquals(expResult, actResult, "Scanned item was not added to sale and returned correctly.");
		}
		catch(InvalidObjectException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testScanItemBetweenTwoItems() {
		try {
			this.contr.scanItem("00001");
			ItemDTO item = this.contr.scanItem("00002");
			this.contr.scanItem("00001");
			boolean expResult = true;
			boolean actResult = this.contr.getSale().getSaleDTO().soldItems.containsKey(item);
			assertEquals(expResult, actResult, "Scanned item was not added to sale and returned correctly.");
		}
		catch(InvalidObjectException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testScanItemIncreaseQuantity() {
		try {
			this.contr.scanItem("00001");
			ItemDTO item = this.contr.scanItem("00001");
			int expResult = 2;
			int actResult = this.contr.getSale().getSaleDTO().soldItems.get(item);
			assertEquals(expResult, actResult, "Scanned item was not added to sale and returned correctly.");
		}
		catch(InvalidObjectException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testScanItemIncreaseQuantityNotScannedImmediatelyAfter() {
		try {
			this.contr.scanItem("00001");
			this.contr.scanItem("00004");
			this.contr.scanItem("00001");
			this.contr.scanItem("00003");
			ItemDTO item = this.contr.scanItem("00001");
			int expResult = 3;
			int actResult = this.contr.getSale().getSaleDTO().soldItems.get(item);
			assertEquals(expResult, actResult, "Item was scanned multiple times but the sold " +
					  "quantity was not increased.");
		} catch (InvalidObjectException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetRunningTotal() {
		try {
			ItemDTO firstAddedItem = this.contr.scanItem("00001");
			ItemDTO secondAddedItem = this.contr.scanItem("00002");
			double expTotal = firstAddedItem.price + secondAddedItem.price;
			double expTax = Math.round(100.0 * (firstAddedItem.tax * firstAddedItem.price
					  + secondAddedItem.tax * secondAddedItem.price) / expTotal) / 100.0;

			RunningTotalDTO runningTotalDTO = this.contr.getRunningTotal();
			double actTotal = runningTotalDTO.runningTotal;
			double actTax = runningTotalDTO.tax;

			assertEquals(expTotal, actTotal, "The expected total and actual total differs.");
			assertEquals(expTax, actTax, "The expected tax and actual tax differs.");
		}
		catch(InvalidObjectException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testMakePayment() {
		try {
			ItemDTO item = this.contr.scanItem("00001");
			double amount = 50;
			this.contr.endSale();
			double actChange = this.contr.makePayment(amount);
			double expChange = amount - item.price;

			double actPaidAmount = this.contr.getSale().getSaleDTO().payment.getAmountPaid();

			assertEquals(expChange, actChange, "The expected change differs from the actual registered change.");
			assertEquals(amount, actPaidAmount, "The paid amount registered does not match " +
					  "the actual amount paid.");
		}
		catch(InvalidObjectException | InsufficientPaymentException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testMakePaymentSameAsRunningTotal() {
		try {
			ItemDTO item = this.contr.scanItem("00001");
			double amount = item.price;
			this.contr.endSale();
			double actChange = this.contr.makePayment(amount);
			double expChange = amount - item.price;

			double actPaidAmount = this.contr.getSale().getSaleDTO().payment.getAmountPaid();

			assertEquals(expChange, actChange, "Actual change was not zero.");
			assertEquals(amount, actPaidAmount, "The paid amount registered does not match " +
					  "the actual amount paid.");
		}
		catch(InsufficientPaymentException e){
			fail(e.getMessage() + "But payment was sufficient.");
		}
		catch(InvalidObjectException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testMakePaymentTooLittle() {
		try {
			ItemDTO item = this.contr.scanItem("00001");
			double amount = item.price - 5;
			this.contr.endSale();
			this.contr.makePayment(amount);
			fail("Payment insufficient, but no exception was raised.");
		}
		catch(InsufficientPaymentException e){
			assertTrue(true);
		}
		catch(InvalidObjectException e){
			fail(e.getMessage());
		}
	}
	@Test
	void testMakePaymentNoChange() {
		try {
			ItemDTO item = this.contr.scanItem("00001");
			double amount = item.price;
			this.contr.endSale();
			double actChange = this.contr.makePayment(amount);
			double expChange = 0;

			double actPaidAmount = this.contr.getSale().getSaleDTO().payment.getAmountPaid();

			assertEquals(expChange, actChange, "Change is returned despite that the paid amount" +
					  " equals the running total.");
			assertEquals(amount, actPaidAmount, "The paid amount registered does not match " +
					  "the actual amount paid.");
		}
		catch(InvalidObjectException | InsufficientPaymentException e){
			fail(e.getMessage());
		}
	}

	@Test
	void testAmountPaidInventoryUpdated() {
		try {
			boolean expResult = true;
			boolean actResultBeforeOperation = this.regCreator.getInventory().inventoryUpdated;
			assertNotEquals(expResult, actResultBeforeOperation, "Inventory set " +
					  "as updated without payment being made.");

			this.contr.makePayment(100);
			this.regCreator.getInventory().updateInventory(this.contr.getSale());
			boolean actResult = this.regCreator.getInventory().inventoryUpdated;
			assertEquals(expResult, actResult, "Inventory was not set to updated despite payment being made.");

		} catch (InsufficientPaymentException e) {
			fail(e.getMessage() + " But payment was sufficient.");
		}
	}
}

