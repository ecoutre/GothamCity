package simcity.bank.interfaces;

public interface BankGreeter {
	
	void msgNeedATeller(BankCustomer bankCustomerRole);

	void msgReadyForCustomer(BankTeller bankTellerRole);

	void msgGiveMeATeller(BankRobber bankRobber);

	void addTeller(BankTeller teller);

}