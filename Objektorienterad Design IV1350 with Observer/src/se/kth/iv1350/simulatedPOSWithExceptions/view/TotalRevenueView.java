package se.kth.iv1350.simulatedPOSWithExceptions.view;

import se.kth.iv1350.simulatedPOSWithExceptions.model.RunningTotalDTO;
import se.kth.iv1350.simulatedPOSWithExceptions.model.SaleObserver;

public class TotalRevenueView implements SaleObserver {
	private double totalRevenue;

	public TotalRevenueView(){
		totalRevenue = 0;
	}

	@Override
	public void newRunningTotal(RunningTotalDTO newRevenue) {
		addNewRevenue(newRevenue);
		presentCurrentTotal();
	}

	private void addNewRevenue(RunningTotalDTO revenueToAdd){
		totalRevenue += revenueToAdd.runningTotal;
	}

	private void presentCurrentTotal(){
		System.out.print("\n\n##################################\n#\t\t\t\t\t\t\t\t #\n#\t");
		System.out.print("Total revenue view: " + totalRevenue);
		System.out.println("\t #\n#\t\t\t\t\t\t\t\t #\n##################################\n");
	}
}
