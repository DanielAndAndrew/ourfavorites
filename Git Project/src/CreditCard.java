import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
* CreditCard is an implementation of a credit card object
* @author AndrewPinkham
*/
public class CreditCard {
	
	private String cardType;
	private String cardNumber;
	private String accountHolder;
	private double initBalance;
	private BalanceStrategy balanceStrategy;
	private boolean hasWarning;
	private static final String WARNING_MESSAGE = "***** WARNING - credit limit exceeded - *****";
	private ArrayList<Transaction> listOfTransactions;
	
	/**
	 * Constructor to build credit cards
	 * @param1 cardType - a string to depict the type of credit card
	 * @param2 cardNumber - the card's individual number
	 * @param3 accountHolder - the name of the account holder associated with the card
	 * @param4 initBalance - the card's initial balance at beginning of the month
	 * @param5 balanceStrategy - the balance strategy to handle credit card variability
	 */
	public CreditCard(String cardType, String cardNumber, String accountHolder, double initBalance, BalanceStrategy balanceStrategy){
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.accountHolder = accountHolder;
		this.initBalance = initBalance;
		this.balanceStrategy = balanceStrategy;
		listOfTransactions = new ArrayList<Transaction>();
		hasWarning = false;
	}
	
	public double getOverdraftAmount() {
		return balanceStrategy.getOverDraft();
	}

	public double getCreditLimit() {
		return balanceStrategy.getCreditLimit();
	}

	public boolean isHasWarning() {
		return hasWarning;
	}

	public void setHasWarning(boolean hasWarning) {
		this.hasWarning = hasWarning;
	}
	
	public ArrayList<Transaction> getListOfTransactions() {
		return listOfTransactions;
	}
	
	public String getCardType() {
		return cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public double getInitBalance() {
		return initBalance;
	}
	
	public BalanceStrategy getBalanceStrategy() {
		return this.balanceStrategy;
	}
	
	/**
	 * Method to format a credit card into a string layout
	 * @returns a formated string
	 */
	public String toString() {
		
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.UP);
		String ret = "";
		
		ret+=String.format("\n");
		ret+=String.format("Credit Card: ");
		ret+=String.format(this.cardNumber + " ");
		ret+=String.format(this.cardType);
		ret+=String.format("\n");
		ret+=String.format("Card-holder: ");
		ret+=String.format(this.accountHolder);
		ret+=String.format("\n");
		ret+=String.format("%-28s", "Initial balance:");
		ret+=String.format("%28s", String.valueOf(decimalFormat.format(this.initBalance)));
		ret+=String.format("\n");
		ret+=String.format("\n");
		ret+=String.format("Transactions:");
		double runningBalance = initBalance;
		for(int i=0; i<listOfTransactions.size(); i++) {
			
			ret+=String.format("\n");
			ret+=String.format("%8s", listOfTransactions.get(i).getDate());
			ret+=String.format("%9d", listOfTransactions.get(i).getidNum());
			ret+=String.format("   ");
			ret+=String.format("%-20s", listOfTransactions.get(i).getBusiness());
			ret+=String.format("%7s", String.valueOf(decimalFormat.format(listOfTransactions.get(i).getAmount())));
			//String.format("%20s", String.valueOf(decimalFormat.format(listOfTransactions.get(i).getAmount())));
			if(!listOfTransactions.get(i).getIsValid()) {
				ret+=String.format(" ");
				ret+=String.format("%-20s", "Transaction Failed: " + listOfTransactions.get(i).getInvalidReason());
				ret+=String.format("\n");
			}
			else {
				runningBalance+=listOfTransactions.get(i).getAmount();
				ret+=String.format("%9s", String.valueOf(decimalFormat.format(runningBalance)));
			}
		}
		ret+=String.format("\n");
		ret+=String.format("\n");
		ret+=String.format("Balance, before rebate:");
		ret+=String.format("%33s", String.valueOf(decimalFormat.format(getBalanceBeforeRebate())));
		ret+=String.format("\n");
		ret+=String.format("Rebate:");
		ret+=String.format("%49s", String.valueOf(decimalFormat.format(balanceStrategy.getRebateAmount(getSumOfTransactions()))));
		ret+=String.format("\n");
		ret+=String.format("Balance due, after rebate:");
		ret+=String.format("%30s", String.valueOf(decimalFormat.format(getBalanceAfterRebate()))); //ask if I need a helper method...
		ret+=String.format("\n");
		if(isHasWarning()) {
			ret+=String.format(WARNING_MESSAGE);
			ret+=String.format("\n");
		}
		return ret;
	}

	/**
	 * Method to loop through this credit cards transactions and sum the amounts
	 * @returns the sum of transactions
	 */
	private double getSumOfTransactions() {
		double ret = 0;
		for(Transaction transaction: listOfTransactions) {
			if(transaction.getIsValid()) {
				ret+=transaction.getAmount();
			}
		}
		return ret;
	}

	/**
	 * Method to add a given transaction to the card's 
	 * local list of transactions
	 * @param transaction - the transaction to be added
	 */
	public void addTransaction(Transaction transaction) {
		listOfTransactions.add(transaction);
	}

	/**
	 * Method to return the balance on the card after 
	 * all transactions but before the rebate
	 * @return the balance on the card before the rebate
	 */
	public double getBalanceBeforeRebate() {
		return this.initBalance + getSumOfTransactions();
	}
	
	/**
	 * Method to calculate the final balance after the rebate for the card
	 * @param amount - amount you will add to balance
	 * @returns the final balance
	 */
	public double getBalanceAfterRebate() {
		
		return getBalanceBeforeRebate() - balanceStrategy.getRebateAmount(getSumOfTransactions());
	}	
}
