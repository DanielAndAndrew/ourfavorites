/**
 * BalanceStrategy is an class to handle the variability of CreditCard objects
 * @author AndrewPinkham
 *
 */
public class BalanceStrategy {

	double rebateAmount;
	double creditLimit;
	double overDraft;
	
	/**
	 * Constructor to build a Balance Strategy
	 * @param1 rebateAmount - the card's rebate percentage
	 * @param2 creditLimit - the card's credit limit
	 * @param3 overDraft - the card's overdraft
	 */
	public BalanceStrategy(double rebatePercent, double creditLimit, double overDraft) {
		this.rebateAmount = rebatePercent;
		this.creditLimit = creditLimit;
		this.overDraft = overDraft;
		
	}
	/**
	 * Method to calculate and return the amount of rebate 
	 * awarded to the card at the end of the month
	 * @param balance - the balance before rebate is applied
	 * @returns the calculated rebate amount
	 */
	public double getRebateAmount(double balance) {
		return rebateAmount * balance;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public double getOverDraft() {
		return overDraft;
	}
	
	public String gitExample() {
		return "This is an example edit for git commenting and commiting...";
	}
	
	public String gitExample2() {
		return "This is an example edit for branching!";
	}
	
}
