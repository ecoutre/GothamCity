package simcity.bank;

import java.awt.Graphics;

import simcity.PersonAgent;
import simcity.TheCity;
import simcity.PersonAgent.MoneyState;
import simcity.PersonAgent.RentBill;
import simcity.Robot;
import Gui.MainScreen;
import Gui.Screen;
import Gui.ScreenFactory;

public class bankAnimationPanel extends Screen{
	//Bank b = TheCity.getBank();
	
	public bankAnimationPanel() {
		super();
		//populate();
	}
	
	public void paintBackground(Graphics g){
		super.paintBackground(g);
		g.drawLine(545, 120, 545, 620);
		g.drawLine(150, 325, 450, 325);
		g.drawLine(150, 355, 450, 355);
	}
	
	public void populate() {
		/*PersonAgent person = new Robot("customer"), 
				person2 = new Robot("greeter"), 
				person3 = new Robot("teller"), 
				person4 = new Robot("customer2");
		
		person4.rentBills.add(person4.new RentBill(person, 20));
		person4.moneyState = MoneyState.Low;
		person.moneyState = MoneyState.High;
		
		BankCustomerRole bankCustomer = new BankCustomerRole(person);
		BankCustomerRole bankCustomer2 = new BankCustomerRole(person4);
		BankGreeterRole bankGreeter = new BankGreeterRole(person2);
		BankTellerRole bankTeller = new BankTellerRole(person3);
		
		BankDatabase db = new BankDatabase();
		
		bankCustomer.setGreeter(bankGreeter);
		bankCustomer2.setGreeter(bankGreeter);
		bankGreeter.addTeller(bankTeller);
		bankTeller.setGreeter(bankGreeter);
		bankTeller.setBankDatabase(db);
		
		bankCustomer.setTransactions();
		bankCustomer2.setTransactions();
		
		bankCustomerGui customerGui = new bankCustomerGui(bankCustomer);
		BankGreeterGui greeterGui = new BankGreeterGui(bankGreeter);
		bankCustomerGui customerGui2 = new bankCustomerGui(bankCustomer2);
		BankTellerGui tellerGui = new BankTellerGui(bankTeller);*/
		
		/*bankCustomer.setGui(customerGui, 50, 50);
		bankCustomer2.setGui(customerGui2, 10, 10);*/
		/*bankGreeter.setGui(greeterGui);
		bankTeller.setGui(tellerGui);
		
		//b.setGreeter(bankGreeter);
		
		person.addRole(bankCustomer);
		person.startThread();
		
		person2.addRole(bankGreeter);
		person2.startThread();
		
		person3.addRole(bankTeller);
		person3.startThread();
		
		person4.addRole(bankCustomer2);
		person4.startThread();*/
		
		//bankCustomer.msgGoToTeller(bankTeller);
	}
}
