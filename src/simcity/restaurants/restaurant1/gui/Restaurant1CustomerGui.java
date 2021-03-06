package simcity.restaurants.restaurant1.gui;

import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import Gui.RoleGui;
import Gui.Screen;

import java.awt.*;

public class Restaurant1CustomerGui extends RoleGui {

	private Restaurant1CustomerRole agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;

	//private HostAgent host;

	private enum Command {noCommand, GoToWaitingArea, GoToSeat, GoToCashier, LeaveRestaurant};
	private Command command=Command.noCommand;

	private String myFood;

	public static final int xTable = 200;
	public static final int yTable = 250;

	public Restaurant1CustomerGui(Restaurant1CustomerRole c){ //HostAgent m) {
		super.setColor(myColor.yellow);
		agent = c;
		xPos = 20;
		yPos = 400;
		xDestination = 20;
		yDestination = 400;
	}

	public Restaurant1CustomerGui(Restaurant1CustomerRole c,
			Screen s) {
		super(c, s);
		super.setColor(myColor.yellow);
		agent = c;
		xPos = 20;
		yPos = 400;
		xDestination = 20;
		yDestination = 400;
	}

	public void updatePosition() {
		super.updatePosition();
		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSeat){
				System.out.println("Made it to seat.");
				agent.msgAnimationFinishedGoToSeat();
			}
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				isHungry = false;
				//gui.setCustomerEnabled(agent);
			}
			else if (command==Command.GoToCashier) {
				agent.madeItToCashier();
				isHungry = false;
				//	gui.setCustomerEnabled(agent);
			}
			else if(command == Command.GoToWaitingArea) {
				agent.msgAnimationFinishedWaitingArea();
				isHungry = false;
			}
			command=Command.noCommand;
		}
	}

	public void draw(Graphics g) {
		super.draw(g);
		if(myFood != null){
			g.drawString(myFood, xPos, yPos);
		}
	}

	public void eatingFood(String food) {
		myFood = food.substring(0,2);
	}

	public void doneWithFood() {
		myFood = null;
	}

	public boolean isPresent() {
		return isPresent;
	}
	public void setHungry() {
		isHungry = true;
		agent.gotHungry();
		setPresent(true);
	}
	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}

	public void DoGoToArea(int x, int y) {
		//just have the customer appear at the location
		xPos = x;
		yPos = y;
		xDestination = x;
		yDestination = y;
		//you can choose to do a command later if you want to do an animation
		command = Command.GoToWaitingArea;
	}

	public void DoGoToSeat(int x, int y) {//later you will map seatnumber to table coordinates.
		xDestination = x;
		yDestination = y;
		command = Command.GoToSeat;
	}

	public void DoGoToCashier() {
		xDestination = -40;
		yDestination = 300;
		command = Command.GoToCashier;
	}

	public void DoExitRestaurant() {
		xDestination = -40;
		yDestination = -40;
		command = Command.LeaveRestaurant;
	}
}
