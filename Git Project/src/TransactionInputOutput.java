import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
/**
* TransactionInputOutput is an I/O class to populate lists 
* from user input and print output
* @author AndrewPinkham
*/
public class TransactionInputOutput {
	
	Scanner in = new Scanner(System.in);
	public static final int INVALID_CARD_NUMBER_CODE = 1;
	public static final int CREDIT_LIMIT_EXCEEDED_CODE = 2;
	
	/**
	 * Method to populate a given HashMap with credit card objects that were parsed from a UI file
	 * @param fileName - the file name of the UI .csv file that contains credit card info
	 * @param mapOfCards - the HashMap that we wish to populate
	 */
	public static void populateCardMap(String fileName, HashMap<String, CreditCard> mapOfCards) {
		
		try {
			//Scanner cardsFile = new Scanner(new FileInputStream("CreditCardAccounts.csv"));
			Scanner cardsFile = new Scanner(new FileInputStream(fileName));
			while(cardsFile.hasNextLine()) {
				String currentLine = cardsFile.nextLine();
				String deliminator = "[,]";
				String[] values = currentLine.split(deliminator);
				String cardNum = values[0];
				String accountHolder = values[1];
				String type = values[2];
				double initBalance = Double.parseDouble(values[3]);
				CreditCardFactory cardFactory = new CreditCardFactory();
				mapOfCards.put(cardNum, cardFactory.getCreditCard(type, cardNum, accountHolder, initBalance));
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Had problem with your credit cards file...");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method to read transactions in from an input file, put them in the correct credit card list, 
	 * and flag them appropriately based on their qualities
	 * @param1 mapOfCards - a full map of credit card objects
	 * @param2 fileName - the file name of the UI .csv file that contains transaction info
	 * @param3 listOfTransactions - the ArrayList that we wish to populate
	 * @param4 badTransactions - a list of bad transactions we will populate
	 */
	public static void readTransactions(HashMap<String, CreditCard> mapOfCards, String fileName, ArrayList<Transaction> badTransactions) {
		
		try {
			//Scanner transactionsFile = new Scanner(new FileInputStream("CreditCardTransactions.csv"));
			Scanner transactionsFile = new Scanner(new FileInputStream(fileName));
			while(transactionsFile.hasNextLine()) {
				String currentLine = transactionsFile.nextLine();
				String deliminator = "[,]";
				String[] values = currentLine.split(deliminator);
				String date = values[0];
				int iD = Integer.parseInt(values[1]);
				String cardNum = values[2];
				String buisness = values[3];
				double amount = Double.parseDouble(values[4]);
				Transaction newTransaction = new Transaction(date, iD, cardNum, buisness, amount);
				//logic to determine status of transactions and flag appropriately:
				
				//if the card exists
				if(mapOfCards.containsKey(cardNum)) {
					//if credit limit is exceeded...
					if(amount + mapOfCards.get(cardNum).getBalanceBeforeRebate() > mapOfCards.get(cardNum).getCreditLimit() + mapOfCards.get(cardNum).getOverdraftAmount()) {
						newTransaction.setInvalidReason(CREDIT_LIMIT_EXCEEDED_CODE);
						newTransaction.setIsValid(false);
						badTransactions.add(newTransaction);
					}
					//if a warning is warranted...
					else if(newTransaction.getAmount() + mapOfCards.get(cardNum).getBalanceBeforeRebate() > mapOfCards.get(cardNum).getCreditLimit()) {
						mapOfCards.get(cardNum).setHasWarning(true);
					}
					//add the transaction to the card
					mapOfCards.get(cardNum).getListOfTransactions().add(newTransaction);
				}
				else {
					newTransaction.setIsValid(false);
					newTransaction.setInvalidReason(INVALID_CARD_NUMBER_CODE);
					badTransactions.add(newTransaction);
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Had problem with your transactions file...");
			System.out.println(e.getMessage());
		}
		/*
		listOfTransactions.add(new Transaction("8/18/17", 567, "5167555332077370", "Lowes", 275.12));
		System.out.println("added Transaction: " + listOfTransactions.get(0).getBuisness());
		listOfTransactions.add(new Transaction("8/22/17", 5632, "6167595378057570", "Staples", 112.12));
		System.out.println("added Transaction: " + listOfTransactions.get(1).getBuisness());
		*/
		
	}
	
	/**
	 * Method to print the failed transactions to the console
	 * @param listOfBadTransactions - the list of failed transactions 
	 */
	public static void writeDeniedTransactionsToOutput(ArrayList<Transaction> listOfBadTransactions) {
			System.out.print("Failed Transactions:");
			System.out.println();
			System.out.println();
			for(Transaction transaction: listOfBadTransactions) {
				System.out.println(transaction.toString());
			}
			System.out.println();
	}

	/**
	 * Method to print the credit card summaries to the console
	 * @param mapOfAllCards - a full list of credit card objects 
	 */
	public static void writeCardSummariesTo(HashMap<String, CreditCard> mapOfAllCards) {
		for (String key : mapOfAllCards.keySet()) {
		    System.out.print(mapOfAllCards.get(key).toString());
		}
	}

	/**
	 * Method to prompt user for name of credit cards file
	 * @return the name of the file
	 */
	public String getCreditCardsFileName() {
		System.out.println("Enter file name for database of credit cards: ");
		return in.nextLine();		
	}
	
	/**
	 * Method to prompt user for name of transactions file
	 * @return the name of the file
	 */
	public String getTransactionsFileName() {
		System.out.println("Enter file name for database of Transactions: ");
		return in.nextLine();
		
	}

}
