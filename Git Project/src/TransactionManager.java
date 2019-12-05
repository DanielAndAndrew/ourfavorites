import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/**
* TransactionManager is master class to execute the processes of building transaction summaries
* @author andrewpinkham
*/
public class TransactionManager {

	
	public static final int INVALID_CARD_NUMBER_CODE = 1;
	public static final int CREDIT_LIMIT_EXCEEDED_CODE = 2;

	public static void main(String[] args) {

		TransactionManager manager  = new TransactionManager();
		manager.run();
	}
	
	/**
	 * Method to set up and run the program which will execute in 4 steps:
	 * First step is set up
	 * Second step is determine file paths from user input
	 * Third step is to populate our HashMap of creditCards and read Transactions in from file
	 * Fourth step is to print summaries to the console
	 */
	public void run() {

		System.out.println("This is not the same print line as before");

		//some setup
		TransactionInputOutput inputOutput = new TransactionInputOutput();
		String creditCardFileName;
		String transactionFileName;
		
		//determine file names from user...
		creditCardFileName = inputOutput.getCreditCardsFileName();
		transactionFileName = inputOutput.getTransactionsFileName();
		
		//some important data structures...
		HashMap<String, CreditCard> mapOfAllCards = new HashMap<String, CreditCard>();
		ArrayList<Transaction> listOfFailedTransactions = new ArrayList<Transaction>();
		
		//populate lists...
		inputOutput.populateCardMap(creditCardFileName, mapOfAllCards);
		inputOutput.readTransactions(mapOfAllCards, transactionFileName, listOfFailedTransactions);
		
		
		//write to output file...
		inputOutput.writeDeniedTransactionsToOutput(listOfFailedTransactions);
		inputOutput.writeCardSummariesTo(mapOfAllCards);
		
		System.out.println("Done - everything seemed to have worked fine...");
	}
}
