/**
* CreditCardFactory is class that generates a new credit card 
* based on several inputs including most importantly, the card type
* @author AndrewPinkham
*/
public class CreditCardFactory {
	/**
	 * Method to create a new credit card object
	 * @param1 creditCardType - the type of the credit card 
	 * @param2 cardNumber - the card's individual number
	 * @param3 accountHolder - the card's account holder name
	 * @param4 initBalance - the initial balance of the card
	 * @return a new CreditCard object with correct balance strategy
	 */
	public CreditCard getCreditCard(String creditCardType, String cardNumber, String accountHolder, double initBalance) {
		switch(creditCardType) {
		case "Gold": 
			return new CreditCard("Gold", cardNumber, accountHolder, initBalance, new BalanceStrategy(0.0, 3000.00, 500.00));
		case "Platinum": 
			return new CreditCard("Platinum", cardNumber, accountHolder, initBalance, new BalanceStrategy(0.02, 5000.00, 1000.00));
		case "Corporate": 
			return new CreditCard("Corporate", cardNumber, accountHolder, initBalance, new BalanceStrategy(0.05, 10000.00, 5000.00));
		default:
			return new CreditCard("Gold", cardNumber, accountHolder, initBalance, new BalanceStrategy(0.0, 3000.00, 500.00));
		}
	}
}
