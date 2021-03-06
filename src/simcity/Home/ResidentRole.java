package simcity.Home;

//import restaurant.WaiterAgent.Menu;
import simcity.Home.gui.ApartmentResidentGui;
import simcity.Home.gui.ResidentGui;
import simcity.Home.interfaces.Resident;
import Gui.RoleGui;
import Gui.ScreenFactory;
import agent.Agent;
import agent.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import simcity.Home.Food;
import simcity.Item;
import simcity.PersonAgent;
import simcity.PersonAgent.HungerState;
import simcity.PersonAgent.JobState;
import simcity.PersonAgent.MoneyState;
import simcity.PersonAgent.RentBill;
import simcity.PersonAgent.RentState;
import trace.AlertLog;
import trace.AlertTag;

/**
 * Home Resident Role
 */
public class ResidentRole extends Role implements Resident {
	private String name;
	Timer timer = new Timer();

	//private ResidentGui residentGui;
	ResidentGui residentGui;
	ApartmentResidentGui apartmentResidentGui;
	// public Food foodChoice;
	String type;
	public PersonAgent person;
	// public PersonAgent accountHolder;
	private Home home;
	public Apartment apartment;
	public int roomNumber;
	private double wallet;
	public Map<String, Food> fridgeFoods = new HashMap<String, Food>();
	public List<Map<String, Food>> fridges = new ArrayList<Map<String, Food>>();
	public Map<String, Food> foods = new HashMap<String, Food>();
	public Map<String, Integer> groceryList = new HashMap<String, Integer>();
	public Map<String, Integer> groceryBag = new HashMap<String, Integer>();
	public List<RentBill> rentBills = new ArrayList<RentBill>();

	// public List<FoodChoice> cookingList;

	public Random random = new Random();
	public boolean leaveRestaurant = random.nextBoolean();
	public boolean hungry = false;
	public boolean checkedGroceryBag = false;

	// private String choice;

	// hack for gui
	public enum HomeState {
		DoingNothing, CheckingFoodSupply, Cooking, Plating, Eating, Clearing, LeavingHome, GoingToBed, Sleeping, PayingRent,  
		checkingMailbox, GoingToMailbox, checkingGroceryBag, PuttingGroceriesInFridge, GoingToPutGroceriesInFridge, GoingToFridgeToCheckFood, LeftHouse
	};

	public HomeState state = HomeState.DoingNothing;// The start state

	public enum HomeEvent {
		none, gotHungry, collectedIngredients, EmptyFridge, doneCooking, donePlating, doneEating,
		doneClearing, gotSleepy, doneSleeping, payRent, atFridge, checkMailbox, atMailbox, checkGroceryBag, putGroceriesInFridge, leaveHome, LeftHouse, goingToEntrance
	};

	public HomeEvent event = HomeEvent.none;

	/**
	 * Constructor for CustomerHome class
	 * 
	 * @param name
	 *            name of the customer
	 * @param gui
	 *            reference to the customergui so the customer can send it
	 *            messages
	 */
	public ResidentRole(PersonAgent p) {
		super(p);

		//myPerson = p;
		name = myPerson.name;
		residentGui = (ResidentGui)super.gui;

		Food f = new Food("Chicken");
		fridgeFoods.put("Chicken", f);

		f = new Food("Steak");
		fridgeFoods.put("Steak", f);

		f = new Food("Pizza");
		fridgeFoods.put("Pizza", f);

		f = new Food("Salad");
		fridgeFoods.put("Salad", f);
		
	
		
		
		//roomNumber = (int)Math.random() % 6 + 1;
		//roomNumber = 3;

	}

	public ResidentRole() {
		Food f = new Food("Chicken");
		fridgeFoods.put("Chicken", f);

		f = new Food("Steak");
		fridgeFoods.put("Steak", f);

		f = new Food("Pizza");
		fridgeFoods.put("Pizza", f);

		f = new Food("Salad");
		fridgeFoods.put("Salad", f);

		residentGui = (ResidentGui)super.gui;
		
		//roomNumber = (int)Math.random() % 6 + 1;
		//sroomNumber = 3;
	}
	//public ResidentRole() {
		
//	}
	@Override
	public void startBuildingMessaging(){
		state = HomeState.DoingNothing;
		event = HomeEvent.none;
		if(this.myPerson.getMyHome().getName().contains("Apartment")){
			//System.err.println("INSIDE IF STATEMENT**************");
		//	((ApartmentResidentGui) residentGui).DoGoToRoomEntrance();
			event = HomeEvent.goingToEntrance;
		}
		else if(!(this.myPerson.getMyHome().getName().contains("Apartment"))){
		msgCheckMailbox(); //message to start Home gui
		//gotHungry();
		//gotSleepy();
		//wakeUp();
		//msgCheckGroceryBag();
		}
	}
	public void atRoomEntrance() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"at apartment room");
		//print("reached room entrance");
		event = HomeEvent.none;
		msgCheckMailbox();
		
	}

	/**
	 * hack to establish connection to Host agent.
	 */


	// Messages

	//initial starting message
	public Vector<Item> getInventory(){
		 Vector<Item> inventory = new Vector<Item>();
		 for (Map.Entry<String, Food> f : fridgeFoods.entrySet())//check food amount
		 {
			 inventory.add(new Item(f.getKey(), f.getValue().amount));
		       /*AlertLog.getInstance().logInfo(AlertTag.GUI, "CookRole",
		           "Key: " + f.getKey() + " Value: " + f.getValue());*/
		     }
		     AlertLog.getInstance().logInfo(AlertTag.GUI, "CookRole",
		         inventory.toString());
		     return inventory;
		   }
		   
		   public void updateItem(String s, int hashCode) {
		     // TODO Auto-generated method stub
		     Food f = fridgeFoods.get(s);
		     fridgeFoods.get(s).amount = hashCode;
		   }
		   
	public void msgCheckMailbox() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				" check your mailbox for mail");
		//System.out.println("check your mailbox for mail");
		state = HomeState.DoingNothing;

		if(state == HomeState.DoingNothing){
			//System.err.println("State is doing nothing and event is check MailBox");
		}
		else{
			//System.err.println("State is NOT doing nothing and event is check MailBox");
		}
		event = HomeEvent.checkMailbox;
		//print("check mailbox statechanged");
		stateChanged();

	}

	public void atMailbox() {
		event = HomeEvent.atMailbox;
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"at mailbox");
		stateChanged();
	}

	public void atFridge() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"at fridge ");
		//System.out.println("atfridge********************");
		//print("Size of fridge = " + fridgeFoods.get("Chicken").getAmount());
		event = HomeEvent.atFridge;
		//print("reached fridge statechanged");
		stateChanged();
	}

	public void gotSleepy() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"I'm sleepy. Time for bed. ");
		//System.out.println("I'm sleepy");
		event = HomeEvent.gotSleepy;
		stateChanged();
	}
	public void wakeUp() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"Good morning ");
		//System.out.println("Good morning");
		event = HomeEvent.doneSleeping;
		stateChanged();
	}
	/*
	public void msgCheckGroceryBag() {
		event = HomeEvent.checkGroceryBag;
		stateChanged();
	}
	 */

	/**
	 * Scheduler. Determine what action is called for, and do it.
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		// CustomerHome is a finite state machine
		// System.out.println("Calling resident scheduler");

		// if (state == HomeState.DoingNothing && event == HomeEvent.none){
		// returnToHomePosition();
		// return true;
		// }
//		if (state == HomeState.DoingNothing && event == HomeEvent.goingToEntrance) {
//			state = HomeState.goingToEntrance;
//			goToMailbox();
//			return true;
//		}

		//mailbox scheduling events
		if(myPerson.myJob != null){
			if (myPerson.myJob.state == JobState.GoToWorkSoon ) {
				state = HomeState.LeavingHome;
				dropEverything();
				return true;
			}
		}
		
		if (state == HomeState.DoingNothing && event == HomeEvent.checkMailbox) {
			state = HomeState.GoingToMailbox;
			goToMailbox();
			return true;
		}
		if (state == HomeState.GoingToMailbox && event == HomeEvent.atMailbox) {
			state = HomeState.checkingMailbox;
			//System.err.println("checking mail");
			checkMail();
			return true;
		}
		if (state == HomeState.checkingMailbox && event == HomeEvent.payRent) {
			state = HomeState.PayingRent;
			payRent(home.getRentBills());

			// payRent(myPerson.new RentBill(myPerson, 10));
			// payRent(new RentBill(myPerson, 10));
			// payRent(rb);
			return true;
		}
		if (state == HomeState.PayingRent && event == HomeEvent.none) {
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		if (state == HomeState.checkingMailbox && event == HomeEvent.none) {
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}



		//grocery bag scheduling events
		if (myPerson.groceryBag.size() > 0 && !checkedGroceryBag){
			//System.out.println("GroceryBag msg in sch *HI***");
			checkGroceryBag();
			checkedGroceryBag = true;
			state = HomeState.DoingNothing;
			//print("Checked the grocery bag");
			return true;
		}
		if (state == HomeState.DoingNothing && event == HomeEvent.putGroceriesInFridge) {
			//System.out.println("Groceries Fridge msg in sch *HI***");
			state = HomeState.GoingToPutGroceriesInFridge;
			goToFridge();
			return true;
		}
		if (state == HomeState.GoingToPutGroceriesInFridge && event == HomeEvent.atFridge) {
			//System.out.println("Groceries Fridge DONE msg in sch *HI***");
			state = HomeState.PuttingGroceriesInFridge;
			putGroceriesInFridge(myPerson.getGroceryBag());
			return true;
		}
		if (state == HomeState.PuttingGroceriesInFridge && event == HomeEvent.none) {
			//System.out.println("Groceries Fridge DONE msg in sch *HI***");
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		//need to account for when grocerybag is empty *************************************************
		/*
		if (state == HomeState.checkingGroceryBag && event == HomeEvent.none) {
			//System.out.println("CheckingGroceryBag msg in sch *HI***");
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		 */



		//got hungry scheduling events

		if (state == HomeState.DoingNothing  && event == HomeEvent.gotHungry) {
			// System.out.println("CHECking food supply");
			//System.out.println("Check Fridge msg in sch *HI***");
			//print("Im hungry, so i will check the fridge");
			state = HomeState.GoingToFridgeToCheckFood;
			goToFridge();
			//checkFridge();
			return true;
		}
		if (state == HomeState.GoingToFridgeToCheckFood && event == HomeEvent.atFridge) {
			//System.out.println("Check Food Supply msg in sch *HI****************");
			state = HomeState.CheckingFoodSupply;
			type = checkFoodSupply();
			return true;
		}
		if (state == HomeState.CheckingFoodSupply && event == HomeEvent.EmptyFridge) {
			//System.out.println("NOTHING 2 msg in sch *HI***++++");
			state = HomeState.LeavingHome; 
			if(this.myPerson.getMyHome().getName().contains("Apartment")){
				
			exitRoom();
		
			}
			else
			exitHome();
			return true;
		}

		if (state == HomeState.CheckingFoodSupply && event == HomeEvent.collectedIngredients) {
			//System.out.println("Check cook food msg in sch *HI***");
			state = HomeState.Cooking;
			goToStove(type);
			return true;
		}
		if (state == HomeState.Cooking && event == HomeEvent.doneCooking) {
			//System.out.println("Cookingmsg in sch *HI***");
			state = HomeState.Plating;
			goToPlatingArea();
			return true;
		}
		if (state == HomeState.Plating && event == HomeEvent.donePlating) {
			//System.out.println("Plating msg in sch *HI***");
			state = HomeState.Eating;
			goToTable();
			return true;
		}
		if (state == HomeState.Eating && event == HomeEvent.doneEating) {
			//System.out.println("Clearing msg in sch *HI***");
			state = HomeState.Clearing;
			goToSink();
			return true;
		}
		if (state == HomeState.Clearing && event == HomeEvent.doneClearing) {
			//System.out.println("NOTHING msg in sch *HI***");
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}


		/*if (state == HomeState.LeavingHome && event == HomeEvent.none) {
			//System.out.println("NOTHING 3 msg in sch *HI***");
			state = HomeState.DoingNothing;
			//returnToHomePosition();
			return true;
		}*/

		//taking money into mind - Nikhil
		if((myPerson.hungerState == HungerState.FeedingHunger  ||
				myPerson.hungerState == HungerState.Famished ||
				myPerson.hungerState == HungerState.Hungry ||
				myPerson.hungerState == HungerState.Starving) && event == HomeEvent.none){// && !hungry) {
			hungry = true;
			//print("make person get some food");
			state = HomeState.DoingNothing;
			gotHungry();
			return true;
		}






		if (state == HomeState.DoingNothing && event == HomeEvent.gotSleepy) {
			//System.out.println("SLEEPING ***");
			state = HomeState.Sleeping;
			goToBed();
			return true;
		}
		if (state == HomeState.Sleeping && event == HomeEvent.doneSleeping) {
			//System.out.println("SLEEPING ***");
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}


		/*
		 if (state == HomeState.CheckingFoodSupply && event ==
		 HomeEvent.checkedEmptyFridge){ state = HomeState.DoingNothing;
		 LeavingRestaurant exitHome(); return true; }
		 */
		
		//Evan, can you reevaluate this addition hunter and I made? - Nikhil TODO
		if(state == HomeState.DoingNothing && event == HomeEvent.none) {
			state = HomeState.checkingMailbox;
			//returnToHomePosition();
			return true;
		}
		
		//System.err.println("DEADLOOCKKKK");
		return false;
	}

	// Actions



	private void dropEverything() {
		if(this.myPerson.getMyHome().getName().contains("Apartment")){
			exitRoom();
			}
			else
			exitHome();
	}

	//checking mailbox when walking in and paying rent bills
	public void goToMailbox() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"going to mailbox");
		residentGui.checkingMail = true;
		//print("go to mailbox");
		residentGui.DoGoToMailbox();

	}

	public void checkMail() {
		//rentBills.clear(); //CHANGE THIS ****************************
		//print("Inside checkMail function **********");
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"going to check mail");
		if (rentBills.size() > 0) {
			AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE,
					this.getName(), "has rent bills to pay");
			//print("rent bills is greater than 0");
			event = HomeEvent.payRent;
			//stateChanged();
		}

		else {
			AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE,
					this.getName(), "no rent bills to pay");
			//print("rent bills is 0");
			event = HomeEvent.none;
			//stateChanged();
		}
		//event = HomeEvent.none;
		//state = HomeState.DoingNothing;
		residentGui.checkingMail = false;
	}
	public void payRent(List<RentBill> rentBills) {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"paying rent");
		for (RentBill rb : rentBills) {
			if (rb.state == RentState.NotPaid) {
				myPerson.goPayBill(rb);
			}
		}
		event = HomeEvent.none;
		//stateChanged();
	}




	//checking grocery bag when walking in and putting groceries in fridge
	public void checkGroceryBag() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"checking Grocery Bag ");
		checkedGroceryBag= true;

		if (groceryBag.size() > 0) {
			event = HomeEvent.putGroceriesInFridge;

			//stateChanged();
		} else
			event = HomeEvent.none;
		//stateChanged();
	}
	public void goToFridge() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"going to fridge ");
		residentGui.DoGoToFridge();
	}

	public void putGroceriesInFridge(Map<String, Integer> groceryBag) {
		//residentGui.DoGoToFridge();
		//print("putting item Steak into fridge. My grocery bag is filled with " + groceryBag.get("Steak"));
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"Putting groceries in the fridge. My grocery bag contains: Chicken: " + groceryBag.get("Chicken") + " Steak: " + 
						groceryBag.get("Steak") + " Pizza: " + groceryBag.get("Pizza") + " Salad: " + groceryBag.get("Salad") );
		groceryBag = myPerson.groceryBag;
		if(groceryBag != null){
			int temp = groceryBag.get("Chicken");
			
			//if(this.myPerson.getMyHome().getName().contains("Apartment")){
				//roomNumber
			//}
			fridgeFoods.get("Chicken").setAmount(fridgeFoods.get("Chicken").getAmount() + groceryBag.get("Chicken"));
			fridgeFoods.get("Steak").setAmount(fridgeFoods.get("Steak").getAmount() + groceryBag.get("Steak"));
			fridgeFoods.get("Pizza").setAmount(fridgeFoods.get("Pizza").getAmount() + groceryBag.get("Pizza"));
			fridgeFoods.get("Salad").setAmount(fridgeFoods.get("Salad").getAmount() + groceryBag.get("Salad"));
		}
		//print("Grocery bag ")
		//fridgeFoods.get("Steak").setAmount(fridgeFoods.get("Steak").getAmount() + groceryBag.get("Steak"));
		
		/*fridgeFoods.get("Steak").setAmount(10);
		groceryBag.put("Steak", 0);
		fridgeFoods.get("Chicken").setAmount(
				fridgeFoods.get("Chicken").getAmount()
				+ groceryBag.get("Chicken"));
		groceryBag.put("Chicken", 0);
		fridgeFoods.get("Pizza").setAmount(
				fridgeFoods.get("Pizza").getAmount() + groceryBag.get("Pizza"));
		groceryBag.put("Pizza", 0);
		fridgeFoods.get("Salad").setAmount(
				fridgeFoods.get("Salad").getAmount() + groceryBag.get("Salad"));
		groceryBag.put("Salad", 0);
*/
		checkedGroceryBag = false;

		myPerson.groceryBag.clear();

		event = HomeEvent.none;
	}



	//got hungry events
	public void gotHungry() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"I'm hungry");
		//System.out.println("I'm hungry");
		event = HomeEvent.gotHungry;
		stateChanged();
	}

	public String checkFoodSupply() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"checking food supply");
		//System.out.println("check food supply **********");
		if(!myPerson.groceryBag.isEmpty())
			putGroceriesInFridge(myPerson.groceryBag);
		String choice = randomizeFoodChoice();
		//String choice = "Chicken"; //hard coded in chicken as only choice TODO
		Food f = fridgeFoods.get(choice);

		if (checkInventory(f)) {
			// print("cook is cooking the food");
			int amount = f.getAmount() - 1;
			f.setAmount(amount);
			// DoCooking(o);
			// print(this.getName() + " is cooking the food");
			if (amount <= f.getLowThreshold()) {
				addToGroceryList(f);
			}
			AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE,
					this.getName(), "collected ingredients ");
			//System.out.println("Collected Food");
			event = HomeEvent.collectedIngredients;
			stateChanged();
			//person.hungerState = HungerState.FeedingHunger;
			//stateChanged();
			// CookGui.order = o.choice.getType();
			// CookGui.tableNumber = o.tableNumber;
			// CookGui.cooking = true;
			// CookGui.plating = false;
			// check low threshold
		} else {
			// o.outOfStock = true;
			AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE,
					this.getName(), "no more " + choice + "in fridge.");
			//System.out.println( "no more " + choice + "in fridge.");
			addToGroceryList(f);
			//print("about to change state to check empty fridge");
			event = HomeEvent.EmptyFridge;
			//stateChanged();
		}
		
//		if(myPerson.moneyState != MoneyState.Low){
//			event = HomeEvent.EmptyFridge;
//		}
		return choice;
	}

	public void goToStove(String type) {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"going to stove to cook food ");
		//System.out.println("resident cooking Food");
		residentGui.DoGoToStove();
	}

	public void atStove() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"at Stove");
		residentGui.cooking = true;
		Food myChoice = new Food(type);
		DoCooking(myChoice); // cooking timer
	}

	private void DoCooking(Food f) {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"cooking food");
		//System.out.println("Cooking food");

		int cookingTime = f.getCookingTime();
		timer.schedule(new TimerTask() {
			public void run() {
				event = HomeEvent.doneCooking;
				//print("done cooking statechanged");
				stateChanged();
			}
		}, cookingTime * 150);
		residentGui.cooking = false;
	}

	public void goToPlatingArea() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"going to plating area ");
		//System.out.println("resident plating Food");
		residentGui.DoGoToPlatingArea();
		
		//ApartmentResidentGui.DoGoToPlatingArea();
		
	}

	public void atPlatingArea() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(), "at plating area ");
		residentGui.plating = true;
		DoPlating();
		//stateChanged();
	}

	private void DoPlating() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"plating food ");
		//System.out.println("Plating food");
		timer.schedule(new TimerTask() {
			public void run() {
				//print("done plating statechanged");
				residentGui.plating = false;
				event = HomeEvent.donePlating;
				stateChanged();
			}
		}, 1500);
		//residentGui.plating = false;
	}

	public void goToTable() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"going to table ");
		//System.out.println("resident eating Food");
		// residentGui.waitingForFood=false;
		// residentGui.receivedFood=true;
		residentGui.DoGoToTable();
		// myPerson.hungerState = HungerState.NotHungry;
		// stateChanged();
		// customerGui.receivedFood=false;
		// isHungry = false;
	}

	public void AtTable() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"at table ");
		//residentGui.eating = true;
		DoEatFood();
	}

	public void DoEatFood(){
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"Eating food ");
			residentGui.eating = true;
		timer.schedule(new TimerTask() {
			public void run() {
				// residentGui.eating = true;
				//AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE,
				//		this.getName(), "finished eating");
				//System.out.println("resident done eating");
				residentGui.eating = false;
				event = HomeEvent.doneEating;
				hungry = false;
				myPerson.justAte();
			}
		}, 1500);
	}

	public void goToSink() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"going to sink");
		//System.out.println("resident cleaning plates");
		
		residentGui.DoClearFood();
	}

	public void atSink() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"at sink");
		residentGui.clearing = true;
		clearFood();
	}

	public void clearFood() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"cleaning plates ");
		//System.out.println("resident cleaning plates");

		stateChanged();
		timer.schedule(new TimerTask() {
			public void run() {
				//System.out.println("resident done cleaning");
				event = HomeEvent.doneClearing;
				//print("done clearing statechanged");
				residentGui.clearing = false;
				stateChanged();
			}
		}, 1500);
	}
	public void exitRoom(){
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"leaving apartment room ");
		((ApartmentResidentGui)residentGui).DoExitRoom();
	}
	public void atRoomExit(){
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"left apartment room ");
		exitHome();
		
	}
	//leaving home
	public void exitHome() {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"leaving home");
		//myPerson.homeNeedsGroceries(groceryList);
		residentGui.DoExitHome();
		//event = HomeEvent.none;
		state = HomeState.LeftHouse;
		event = HomeEvent.LeftHouse;
		//stateChanged();
		//myPerson.leftBuilding(this);
		//state = HomeState.DoingNothing;

	}

	public void returnToHomePosition() {
		//print("Going to home position");
		residentGui.DoReturnToHomePosition();
		event = HomeEvent.none;
		//stateChanged();
	}


	public void goToBed() {
		residentGui.DoGoToBed();
		DoSleeping();
	}

	public void atBed() {
		residentGui.sleeping = true;
		//event = HomeEvent.doneSleeping;
		//stateChanged();
	}

	public void exited() {
		myPerson.leftBuilding(this);
		//event = HomeEvent.none;
	}


	public void atHome() {

	}


	// Accessors, etc.
	private void DoSleeping() {
		int bedTime = 6; // 8am
		int alarmTime = 22;
		// try {
		// Thread.currentThread().sleep(10000);
		// } catch(Exception e) {
		// System.out.println(e.getMessage());0
		// }
	}


	private boolean checkInventory(Food f) {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"Checking fridge inventory. Fridge contains: Chicken: " + fridgeFoods.get("Chicken").getAmount() + " Steak: " +
						fridgeFoods.get("Steak").getAmount() + " Pizza: "+ fridgeFoods.get("Pizza").getAmount() + " Salad: " + 
						fridgeFoods.get("Salad").getAmount());
		//System.out.println("checking Fridge Inventory");
		//System.err.println("amount of chicken = " + fridgeFoods.get("Chicken").getAmount());
		
		int amount = f.getAmount();
		if (amount > 0) {
			return true;
		}
		return false;
	}

	private void addToGroceryList(Food f) {
		AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
				"Adding groceries to grocery list ");
		//System.err.println("add to grocery list and go get groceries");
		groceryList.put(f.getType(), f.getCapacity());
		myPerson.homeNeedsGroceries(groceryList);
	}

	public String getName() {
		return name;
	}

	private String randomizeFoodChoice() {
		String choice = null;

		Random r = new Random();
		int x;

		x = r.nextInt(4) + 1;

		if (x == 1) {
			choice = "Salad";
			AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE,
					this.getName(), "wants to eat Salad ");
			//System.out.println("choice = Salad");
		}
		if (x == 2) {
			choice = "Pizza";
			AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE,
					this.getName(), "wants to eat Pizza ");
			//System.out.println("choice = pizza");
		}
		if (x == 3) {
			choice = "Chicken";
			AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE,
					this.getName(), "wants to eat Chicken");
			//System.out.println("choice = Chicken");
		}
		if (x == 4) {
			choice = "Steak";
			AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE,
					this.getName(), "wants to eat Steak ");
			//System.out.println("choice = Steak");
		}

		return choice;
	}

	public void setGui(RoleGui g) {
		super.setGui(g);
		residentGui = (ResidentGui)g;
		//apartmentResidentGui = (ApartmentResidentGui)g;
	}

	public ResidentGui getGui() {
		return residentGui;
	}


	public class FoodChoice {
		public Food choice;
		double totalPrice;
		boolean outOfStock = false;

		public FoodChoice(String foodChoice) {

			this.choice = new Food(foodChoice);
			// this.totalPrice = totalPrice;
		}

		public void setTotalPrice(double t) {
			totalPrice = t;
		}

	}

}
