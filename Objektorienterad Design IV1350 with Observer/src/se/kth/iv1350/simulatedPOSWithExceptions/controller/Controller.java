package se.kth.iv1350.simulatedPOSWithExceptions.controller;

import se.kth.iv1350.simulatedPOSWithExceptions.exception.FailedConnectionException;
import se.kth.iv1350.simulatedPOSWithExceptions.exception.InsufficientPaymentException;
import se.kth.iv1350.simulatedPOSWithExceptions.exception.InvalidIdentifierException;
import se.kth.iv1350.simulatedPOSWithExceptions.exception.InventoryUnavailableException;
import se.kth.iv1350.simulatedPOSWithExceptions.integration.*;
import se.kth.iv1350.simulatedPOSWithExceptions.model.*;
import se.kth.iv1350.simulatedPOSWithExceptions.view.TotalRevenueView;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller class, connecting the view with the model and integration layers.
 */

public class Controller {

	private RegistryCreator registryCreator;
	private Sale currentSale;
	private CashRegister cashRegister;
	private List<SaleObserver> saleObservers = new ArrayList<>();

	/**
	 * Creates an instance of the controller class, with required references to manage interaction between layers.
	 *
	 * @param registryCreator The registry creator to which the controller makes calls regarding registries.
	 */

	public Controller(RegistryCreator registryCreator){

		this.registryCreator = registryCreator;
		this.cashRegister = new CashRegister();
	}

	private void updateInventory() {
		try {
			this.registryCreator.getInventory().updateInventory(this.currentSale);
			if(this.currentSale.numberOfItemsUsedToCheckConnectionException == 5) {
				throw new InventoryUnavailableException("Could not connect to inventory.");
			}
		}
		catch(InventoryUnavailableException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Returns the current sale.
	 * @return Current sale.
	 */

	public Sale getSale(){
		return this.currentSale;
	}

	/**
	 * A getter for the current running total DTO.
	 *
	 * @return RunningTotalDTO of the current sale.
	 */

	public RunningTotalDTO getRunningTotal(){
		return this.currentSale.getRunningTotalDTO();
	}

	/**
	 * Begins a new sale by creating a new sale object.
	 */

	public void beginNewSale(){
		this.currentSale = new Sale(this.registryCreator.getAccounting());
		currentSale.addObservers(saleObservers);
	}

	/**
	 * Finalizes the sale and returns the total for the purchase.
	 * @return The current running total DTO.
	 */
	public RunningTotalDTO endSale(){
		return getRunningTotal();
	}

	/**
	 * Searches for a provided item identifier and returns the corresponding itemDTO.
	 *
	 * @param itemIdentifier The identifier being scanned from the item.
	 * @return ItemDTO for the scanned item.
	 * @throws InvalidObjectException If item cannot be found.
	 * @throws FailedConnectionException If a connection to the inventory database cannot be established.
	 */

	public ItemDTO scanItem(String itemIdentifier) throws FailedConnectionException, InvalidObjectException {
		try {
			ItemDTO itemDTO = this.registryCreator.getItemDTO(itemIdentifier);
			this.currentSale.addSoldItem(itemDTO);
			return itemDTO;
		} catch(InventoryUnavailableException e){
			System.out.println(e.getMessage());
			throw new FailedConnectionException("Error while scanning item. Please try again.", e);
		}
		catch (InvalidIdentifierException e) {
			System.out.println(e.getMessage());
			throw new InvalidObjectException("Invalid item ID.");
		}

	}

	/**
	 * Enters the amount paid to the cash register and returns the change.
	 *
	 * @param amount the amount to add to the cash register
	 * @return the amount of change for the purchase
	 */
	public double makePayment(double amount) throws InsufficientPaymentException {
		double change =  this.cashRegister.addPayment(amount, this.currentSale.getSaleDTO());
		this.currentSale.registerPayment(amount);
		updateInventory();
		return change;
	}

	public void addObserver(SaleObserver obs){
		saleObservers.add(obs);
	}
}