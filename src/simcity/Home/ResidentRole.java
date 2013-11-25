package simcity.Home;


//import restaurant.WaiterAgent.Menu;
import simcity.Home.gui.ResidentGui;
import simcity.Home.interfaces.Resident;
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

import simcity.Home.Food;
import simcity.PersonAgent;
import simcity.PersonAgent.RentBill;

/**
 * Home Resident Role
 */
public class ResidentRole extends Role implements Resident{
	private String name;
	Timer timer = new Timer();
	
	private ResidentGui residentGui;
	//public Food foodChoice;
	String type;
	public PersonAgent person;
	public PersonAgent accountHolder;

	private double wallet;
	public List<Food> fridgeFoods= new ArrayList<Food>();
	public Map<String, Food> foods = new HashMap<String, Food>();
	public Map<String, Integer> groceryList = new HashMap<String, Integer>();
	private List<RentBill> rentBills = new ArrayList<RentBill>();
	
	//public List<FoodChoice> cookingList;
	
	public Random random = new Random();
	public boolean leaveRestaurant = random.nextBoolean();
	
	//private String choice;

	 //hack for gui
	public enum HomeState
	{DoingNothing, CheckingFoodSupply, Cooking, Plating, Eating, Clearing, LeavingHome, 
 GoingToBed, Sleeping, PayingRent, GoingToFridge, checkingMailbox, GoingToMailbox}; 
	private HomeState state = HomeState.DoingNothing;//The start state

	//other Homestates:Seated, askedToOrder, ordered, DoneEating

	public enum HomeEvent 
	{none, gotHungry, collectedIngredients,checkedEmptyFridge, doneCooking, donePlating, 
		doneEating, doneClearing, gotSleepy, doneSleeping, payRent, atFridge, checkMailbox, atMailbox};
	HomeEvent event = HomeEvent.none;
 

	/**
	 * Constructor for CustomerHome class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public ResidentRole(PersonAgent p){
		super(p);
		
		myPerson = p;
		name = myPerson.name;
		
		Food f = new Food ("Chicken");
		foods.put("Chicken", f);
		
		f = new Food ("Steak");
		foods.put("Steak", f);
		
		f = new Food ("Pizza");
		foods.put("Pizza", f);
		
		f = new Food ("Salad");
		foods.put("Salad", f);
	
	}
	public ResidentRole(){
		Food f = new Food ("Chicken");
		foods.put("Chicken", f);
		
		f = new Food ("Steak");
		foods.put("Steak", f);
		
		f = new Food ("Pizza");
		foods.put("Pizza", f);
		
		f = new Food ("Salad");
		foods.put("Salad", f);
	
	}
	
	/**
	 * hack to establish connection to Host agent.
	 */
	

	public String getCustomerName() {
		return name;
	}
	
	public double getWallet() {  
		return wallet;
	}
	
	public void setWallet(double newWallet) {
		this.wallet = newWallet;
	}
	
	// Messages

	public void gotHungry() {//from animation
		System.out.println("I'm hungry");
		event = HomeEvent.gotHungry;
		stateChanged();
	}

	public void gotSleepy() {
		System.out.println("I'm sleepy");
		event = HomeEvent.gotSleepy;
		stateChanged();
	}
	
	public void msgCheckMailbox() {
		System.out.println("check your mailbox for mail");
		event = HomeEvent.checkMailbox;
		stateChanged();
	}

	public void AtTable() {
		event = HomeEvent.doneEating;
		stateChanged();
		
	}

	public void atSink() {
		event = HomeEvent.doneClearing;
		stateChanged();
		
		
	}

	public void atPlatingArea() {
		event = HomeEvent.donePlating;
		stateChanged();
		
	}

	public void atStove() {
		Food myChoice = new Food(type);
		DoCooking(myChoice); //cooking timer
		
	}

	public void atBed() {
		event = HomeEvent.doneSleeping;
		stateChanged();
		
	}

	public void atFridge() {
		event = HomeEvent.atFridge;
		stateChanged();
		
	}
	
	public void atMailbox() {
		event = HomeEvent.atMailbox;
		stateChanged();
		
	}


	public void exited() {
		// TODO Auto-generated method stub
		
	}
	//@Override
	public void doLeaveBuilding(){
		
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		//	CustomerHome is a finite state machine
		//System.out.println("Calling resident scheduler");
		
		//if (state == HomeState.DoingNothing && event == HomeEvent.none){
			//returnToHomePosition();
			//return true;
		//}
		if (state == HomeState.DoingNothing && event == HomeEvent.checkMailbox){
			state = HomeState.GoingToMailbox;
			gotToMailbox();
			return true;
		}
		if (state == HomeState.GoingToMailbox && event == HomeEvent.atMailbox){
			state = HomeState.checkingMailbox;
			checkMail();
			return true;
		}
		if (state == HomeState.GoingToMailbox && event == HomeEvent.none){
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		if (state == HomeState.checkingMailbox && event == HomeEvent.payRent){
			state = HomeState.PayingRent;
			payRent(myPerson.new RentBill(myPerson, 10));
			//payRent(new RentBill(myPerson, 10));
			//payRent(rb);
			return true;
		}
		if (state == HomeState.DoingNothing && event == HomeEvent.gotHungry ){
			//System.out.println("CHECking food supply");
			state = HomeState.GoingToFridge;
			checkFridge();
			return true;
		}
		if (state == HomeState.GoingToFridge && event == HomeEvent.atFridge ){
			//System.out.println("CHECking food supply");
			state = HomeState.CheckingFoodSupply;
			type = checkFoodSupply();
			return true;
		}
		if (state == HomeState.DoingNothing && event == HomeEvent.gotSleepy ){
			state = HomeState.Sleeping;
			goToBed();
			return true;
		}
		if (state == HomeState.CheckingFoodSupply && event == HomeEvent.collectedIngredients){
			state = HomeState.Cooking;
			cookFood(type);
			return true;
		}
		if(state == HomeState.Cooking && event == HomeEvent.doneCooking){
			state = HomeState.Plating;
			plateFood();
			return true;
		}
		if (state == HomeState.Plating && event == HomeEvent.donePlating){
			state = HomeState.Eating;
			eatFood();
			return true;
		}
		if (state == HomeState.Eating && event == HomeEvent.doneEating){
			state = HomeState.Clearing;
			clearFood();
			return true;
		}
		if (state == HomeState.Clearing && event == HomeEvent.doneClearing){
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		if (state == HomeState.CheckingFoodSupply && event == HomeEvent.checkedEmptyFridge){
			state = HomeState.LeavingHome;
			goToMarket();
			return true;
		}
		if (state == HomeState.LeavingHome && event == HomeEvent.none){
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		
		
		return false;
	}

	

	// Actions

	private void gotToMailbox() {
		residentGui.DoGoToMailbox();
		
	}
	public void checkMail(){
		if(rentBills.size() > 0){
			event = HomeEvent.payRent;
			stateChanged();
		}
			
		else{
			event = HomeEvent.none;
			stateChanged();
		}
		
	}
	public void payRent(RentBill rb) {
		myPerson.goPayBill(rb);
		
	}

	private void goToBed() {
		residentGui.DoGoToBed();
		DoSleeping();
		
		
	}

	private void returnToHomePosition() {
		residentGui.DoReturnToHomePosition();
		event = HomeEvent.none;
		stateChanged();
		
	}

	private void clearFood() {
		System.out.println("resident cleaning plates");
		residentGui.DoClearFood();
		try {
			Thread.currentThread().sleep(5000);
		}catch(Exception e) {
			System.out.print(e.getMessage());
		}
		System.out.println("resident done cleaning");
		
	}

	private void eatFood() {
		System.out.println("resident eating Food");
		//residentGui.waitingForFood=false;
		//residentGui.receivedFood=true;
		residentGui.DoGoToTable();
		try {
			Thread.currentThread().sleep(5000);
		}catch(Exception e) {
			System.out.print(e.getMessage());
		}
		System.out.println("resident done eating");
		myPerson.justAte();
		//myPerson.hungerState =  HungerState.NotHungry;
		//stateChanged();
		//customerGui.receivedFood=false;
				//isHungry = false;
	}

	private void plateFood() {
		System.out.println("resident plating Food");
		residentGui.DoGoToPlatingArea();
		DoPlating();
		
		
	}

	private void cookFood(String type) {
		System.out.println("resident cooking Food");
		residentGui.DoGoToStove();
				
		
	}

	private void checkFridge() {
		residentGui.DoGoToFridge();
		System.out.println("checking food supply");
		
		
	}

	private String checkFoodSupply() {
		String choice = randomizeFoodChoice();
		//String choice = "food";
		Food f = foods.get(choice);
			
		if (checkInventory(f)) {
			//print("cook is cooking the food");
			int amount = f.getAmount() - 1;
			f.setAmount (amount);			
			//DoCooking(o);
			//print(this.getName() + " is cooking the food");
			event = HomeEvent.collectedIngredients;
			stateChanged();
			//CookGui.order = o.choice.getType();
			//CookGui.tableNumber = o.tableNumber;
			//CookGui.cooking = true;
			//CookGui.plating = false;
			
			// check low threshold
			if (amount <= f.getLowThreshold()) { 
				addToGroceryList(f);
			}
		}
		else {
			event = HomeEvent.checkedEmptyFridge;
			//o.outOfStock = true;
			System.out.println(this.getName() + " has run out of " + choice);
			stateChanged();
		}
		
		return choice;
		
	}
	
	private void goToMarket() {
		residentGui.DoExitHome();
		event = HomeEvent.none;
		stateChanged();
		
		
	}

	//TODO Fix this Evan
	// Accessors, etc.
	private void DoSleeping() {
		try {
			Thread.currentThread().sleep(10000);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private void DoCooking(Food f) {
		System.out.println("Do Cooking");

		int cookingTime = f.getCookingTime();
		try {
			Thread.currentThread().sleep(cookingTime*500);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		event = HomeEvent.doneCooking;
		stateChanged();
	}
	private void DoPlating() {
		try {
			//CookGui.cooking = false;
			//CookGui.plating = true;
			Thread.currentThread().sleep(2000);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private boolean checkInventory(Food f) {
		System.out.println("checking Fridge Inventory");
		int amount = f.getAmount(); 
		if (amount > 0) {
			return true;
		}
		return false;
	}
	
	private void addToGroceryList(Food f) {
		 groceryList.put(f.getType(), f.getAmount());
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
		
		if(x == 1) {
			choice = "Salad";
			System.out.println("choice = Salad");
		}
		if(x == 2) {
			choice = "Pizza";
			System.out.println("choice = pizza");
		}
		if(x == 3){
			choice = "Chicken";
			System.out.println("choice = Chicken");
		}
		if(x == 4){
			choice = "Steak";
			System.out.println("choice = Steak");
		}
	
		return choice;
	}
/*
	public int getHungerLevel() {
		return hungerLevel;
	}
	public void setDonePayingState() {
		event = HomeEvent.donePaying;
		stateChanged();
	}

/*
	//public String toString() {
		//return "customer " + getName();
	//}



	*/
	public void setGui(ResidentGui g) {
		residentGui = g;
	}

	public ResidentGui getGui() {
		return residentGui;
	}
	
	public double getWalletAmount() {
		return wallet;
	}
	
	public class FoodChoice {
		public Food choice;
		double totalPrice;
		boolean outOfStock = false;
		
		public FoodChoice(String foodChoice){
			
			this.choice = new Food(foodChoice);
			//this.totalPrice = totalPrice;
		}
		
		 public void setTotalPrice(double t) {
			totalPrice = t;
		}
			
	}

}
