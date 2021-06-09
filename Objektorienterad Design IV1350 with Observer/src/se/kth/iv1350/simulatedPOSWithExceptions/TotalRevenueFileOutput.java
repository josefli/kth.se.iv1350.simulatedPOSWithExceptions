package se.kth.iv1350.simulatedPOSWithExceptions;

import se.kth.iv1350.simulatedPOSWithExceptions.model.RunningTotalDTO;
import se.kth.iv1350.simulatedPOSWithExceptions.model.SaleObserver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Contains a main method of the log API client, which loads
 * new <code>Logger</code> implementations at runtime.
 */

public class TotalRevenueFileOutput implements SaleObserver {
	private PrintWriter logStream;
	private double totalRevenue;

	/**
	 * Creates a new instance and also creates a new log file.
	 * An existing log file will be deleted.
	 */
	public TotalRevenueFileOutput() {
		try {
			logStream = new PrintWriter(new FileWriter("revenueFileLog.txt"), true);
			totalRevenue = 0;
		} catch (IOException e) {
			System.out.println("Unable to log.");
			e.printStackTrace();
		}
	}

	public void log(String msg){
		logStream.println(msg);
	}

	@Override
	public void newRunningTotal(RunningTotalDTO newTotal) {
		totalRevenue += newTotal.runningTotal;
		log("New revenue: " + newTotal.runningTotal);
		log("New total revenue: " + totalRevenue + "\n\n");
	}
}
