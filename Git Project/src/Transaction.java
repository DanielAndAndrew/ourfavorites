/**
* Transaction is an implementation of a credit card transaction object
* @author AndrewPinkham
*/
public class Transaction {

	private String date;
	private int idNum;
	private String cardNumber;
	private String business;
	private double amount;
	private boolean isValid;
	private static final String INVAL_CARD_NUM = "Invalid card number";
	private static final String LIM_EXCEED = "Credit limit exceeded";
	private String invalidReason;
	
	/**
	 * Constructor to build transactions
	 * @param1 date - the date of the transaction
	 * @param2 idNum - the transaction's unique identifier number
	 * @param3 cardNumber - the card number associated with the transaction
	 * @param4 business - the business associated with the transaction
	 * @param5 amount - the amount exchanged in the transaction
	 */
	public Transaction(String date, int idNum, String cardNumber, String business, double amount) {
		this.date = date;
		this.idNum = idNum;
		this.cardNumber = cardNumber;
		this.business = business;
		this.amount = amount;
		isValid = true;	
	}
	
	public String getDate() {
		return date;
	}

	public int getidNum() {
		return idNum;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getBusiness() {
		return business;
	}

	public double getAmount() {
		return amount;
	}
	
	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	public boolean getIsValid() {
		return isValid;
	}
	
	public String getInvalidReason() {
		return invalidReason;
	}
	
	/**
	 * Method to set the parameter invalidReason 
	 * to one of two global string options
	 * @param reason - a 0 or 1 to denote the reason
	 */
	public void setInvalidReason(int reason) {
		if(reason == TransactionManager.INVALID_CARD_NUMBER_CODE) {
			invalidReason = INVAL_CARD_NUM;
		}
		if(reason == TransactionManager.CREDIT_LIMIT_EXCEEDED_CODE) {
			invalidReason = LIM_EXCEED;
		}
	}
	
	/**
	 * Method to return a string format of a transaction 
	 * @returns a string format of a transaction
	 */
	public String toString() {
		String ret = "";
		ret+= String.format("%8s", getDate());
		ret+=String.format("%9d", getidNum());
		ret+=String.format("   ");
		ret+=String.format("%-22s", getBusiness());
		ret+=String.format("%7s", String.valueOf(getAmount()));
		ret+=String.format(" Transaction Failed: ");
		ret+=String.format(getInvalidReason());
		ret+=String.format("                                               ");
		return ret;
	}

}
