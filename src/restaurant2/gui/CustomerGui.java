package restaurant2.gui;

import restaurant2.CustomerAgent;
import restaurant2.HostAgent;

import java.awt.*;

public class CustomerGui implements Gui{

	private CustomerAgent agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;
	private boolean doDrawString = false;
    private String displayString = "";

	//private HostAgent host;
	RestaurantGui gui;

	private int xPos, yPos, speed = 2, tableStarter = 1, xBuffer = 5,xMult = 5, yBuffer = 15;
	private int cashierXCoord = 10, cashierYCoord = 300;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToSeat, LeaveRestaurant, PayForFood};
	private Command command=Command.noCommand;

	public static final int xTable = 200;
	public static final int yTable = 250;
	public static final int exitCoord = -40;
	public static final int rectWidth = 20;
	public static final int rectHeight = 20;

	public CustomerGui(CustomerAgent c, RestaurantGui gui){ //HostAgent m) {
		agent = c;
		xPos = exitCoord;
		yPos = exitCoord;
		xDestination = exitCoord;
		yDestination = exitCoord;
		//maitreD = m;
		this.gui = gui;
	}

	public void updatePosition() {
		if (xPos < xDestination)
			xPos+= speed;
		else if (xPos > xDestination)
			xPos-= speed;

		if (yPos < yDestination)
			yPos+= speed;
		else if (yPos > yDestination)
			yPos-= speed;

		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSeat) agent.msgAnimationFinishedGoToSeat();
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				gui.setCustomerEnabled(agent);
			}
			else if (command == Command.PayForFood) {
				agent.msgAnimationFinishedGoToCashier();
			}
			command=Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, rectWidth, rectHeight);
		
		if(doDrawString) {
			if(displayString.length() > 2) {
				g.setColor(Color.MAGENTA);
				g.drawString(displayString, xPos + xBuffer, yPos);
			}
			else {
				g.setColor(Color.BLACK);
				g.drawString(displayString, xPos + xBuffer*xMult, yPos + yBuffer);
			}
        	
        }
		
	}
	
	public void setString(String str) {
    	displayString = str;
    }
    
    public void showString(boolean show) {
    	doDrawString = show;
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

	public void DoGoToSeat(int seatnumber) {//later you will map seatnumber to table coordinates.
		xDestination = xTable + ((seatnumber-tableStarter) * 80);
		yDestination = yTable - ((seatnumber-tableStarter) * 80);
		command = Command.GoToSeat;
	}

	public void DoExitRestaurant() {
		xDestination = exitCoord;
		yDestination = exitCoord;
		command = Command.LeaveRestaurant;
	}

	public void DoGoToCashier() {
		xDestination = cashierXCoord;
		yDestination = cashierYCoord;
		command = Command.PayForFood;
		//command = Command.LeaveRestaurant;
	}

	public void goToWaitingPosition(int i) {
		xDestination = 20;
		yDestination = 20 * i + 20;
		
	}
}
