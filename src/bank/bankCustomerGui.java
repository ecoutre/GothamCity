package bank;

import java.awt.Color;

import Gui.RoleGui;

public class bankCustomerGui extends RoleGui {
	
	BankCustomerRole bankCustomer;
	boolean atTeller;
	int tellerNumber, prevX = 0, prevY = 0;
	
	public bankCustomerGui(BankCustomerRole bankCustomer) {
		this.bankCustomer = bankCustomer;
		myColor = Color.green;
		atTeller = false;
		xPos = 20;
		yPos = 20;	
	}
	
	public void updatePosition() {
		super.updatePosition();
		
		if(xPos == 520 && xDestination == 520 &&
			yPos == 350 + tellerNumber*50 && 
			yDestination == 350 + tellerNumber*50 
			&& !atTeller) {
				atTeller = true;
				bankCustomer.msgAtTeller();
		}
		if(xPos != xDestination ||
			yPos != yDestination)
			atTeller = false;
			
		
		prevX = xPos;
		prevY = yPos;
	}
	
	public void GoToTeller(int tellerIndex) {
		xDestination = 520;
		yDestination = 350 + tellerIndex * 50;
		
		tellerNumber = tellerIndex;
	}

	public void GetInLine(int waitingNumber) {
		xDestination = 350 - (waitingNumber) * 24;
		yDestination = 330;
	}
	
	public void LeaveBank() {
		xDestination = -20;
		yDestination = 300;
	}

	public void setX(int x) { xPos = x;}
	
	public void setY(int y) { yPos = y;}
}
