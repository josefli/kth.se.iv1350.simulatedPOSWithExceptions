package se.kth.iv1350.simulatedPOSWithExceptions.model;

/**
 * DTO class that contains the information of the running total. Instances are immutable.
 */

public final class RunningTotalDTO {

	public final double runningTotal;
	public final double tax;

	/**
	 * Creates a new running total DTO.
	 * @param currentSale The sale for which the running total is to be presented.
	 */

	public RunningTotalDTO(Sale currentSale){
		this.runningTotal = currentSale.runningTotal;
		this.tax = (Math.round(100.0 * currentSale.tax)) / 100.0;
	}
}
