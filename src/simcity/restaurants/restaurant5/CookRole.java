package simcity.restaurants.restaurant5;

import agent.Agent;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.Timer;
import java.util.Map;
import java.util.TimerTask;

import simcity.PersonAgent;
import simcity.restaurants.restaurant5.interfaces.*;
import simcity.restaurants.restaurant5.gui.*;

/**
 * Restaurant Cook Agent
 */
public class CookRole extends Role implements Cook{

	public List<Order> orders =  Collections.synchronizedList(new ArrayList<Order>());
	Timer timer = new Timer();
	private Semaphore moving = new Semaphore(0,true);
	
	//private List<Food> stock = new ArrayList<Food>();
	private List<MyMarket> markets = Collections.synchronizedList(new ArrayList<MyMarket>());
	public Map<String, Food> foodMap = new HashMap<String, Food>();
	private String name;
	
	private List<String> neededFoods = new ArrayList<String>();
	private List<Integer> neededAmounts = new ArrayList<Integer>();

	//boolean orderFood = false;
	//MarketState order = MarketState.free;

	private CookGui cookGui = null;

	public CookRole(String n) {
		Food steak = new Food("Steak", 5);
		Food chicken = new Food("Chicken", 4);
		Food salad = new Food("Salad", 2);
		Food pizza = new Food("Pizza", 3);

		/*stock.add(steak);
		stock.add(chicken);
		stock.add(salad);
		stock.add(pizza);
		 */

		foodMap.put("Steak", new Food("Steak", 5));
		foodMap.put("Chicken", new Food("Chicken", 4));
		foodMap.put("Salad", new Food("Salad", 2));
		foodMap.put("Pizza", new Food("Pizza", 3));

		name = n;

	}

	// Messages

	public CookRole(PersonAgent cookPerson) {
		// TODO Auto-generated constructor stub
		super(cookPerson);
		Food steak = new Food("Steak", 5);
		Food chicken = new Food("Chicken", 4);
		Food salad = new Food("Salad", 2);
		Food pizza = new Food("Pizza", 3);

		/*stock.add(steak);
		stock.add(chicken);
		stock.add(salad);
		stock.add(pizza);
		 */

		foodMap.put("Steak", new Food("Steak", 5));
		foodMap.put("Chicken", new Food("Chicken", 4));
		foodMap.put("Salad", new Food("Salad", 2));
		foodMap.put("Pizza", new Food("Pizza", 3));
	}

	//v2
	public void hereIsAnOrder(Waiter w, String choice, int table){//(order)
		print("Received order for " + choice + " from " + w.getName() + " at table " + table);
		orders.add(new Order(w,choice,table));
		stateChanged();
	}

	public void foodDone(Order o){//(order o)
		print(o.choice + " is finished cooking!");
		o.s = OrderState.cooked;
		stateChanged();
	}

	//v2.1
	public void hereIsFood(Market m, String s, int amount){
		print("Received " + amount + " " + s + " from " + m.getName());
		for (Map.Entry<String, Food> food : foodMap.entrySet())
		{
			if(food.getValue().type.equals(s)){
				food.getValue().amount += amount;
				food.getValue().fs = FoodState.stocked;
				//print("" + food.getValue().amount);
			}
		}
		for(MyMarket market : markets){
			if(market.m == m){
				market.ms = MarketState.idle;
			}
		}
		stateChanged();
	}

	public void hereIsSomeFood(Market m, String s, int amount){
		print("Market " + m.getName() + " could not deliver all food. Recieved " + amount + " " + s + "from " + m.getName());
		for (Map.Entry<String, Food> food : foodMap.entrySet())
		{
			if(food.getValue().type.equals(s)){
				food.getValue().amount += amount;
				food.getValue().fs = FoodState.partialStock;
				//print("Total " + food.getValue().type + " is " + food.getValue().amount);
			}
		}
		for(MyMarket market : markets){
			if(market.m == m){
				market.ms = MarketState.outOfStock;
			}
		}

		stateChanged();
	}
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		print("Picking and excuting");
		synchronized(orders){
			for(Order o: orders){
				if(o.s == OrderState.pending){
					tryCookIt(o);
					return true;
				}
				if(o.s == OrderState.cooked){
					plateIt(o);
					return true;
				}
			}
		}
		for (Map.Entry<String, Food> f : foodMap.entrySet())//check food amount
		{

			if((f.getValue().amount <= f.getValue().low ) || (f.getValue().fs == FoodState.partialStock)){
				if(f.getValue().fs != FoodState.ordering){
					orderFood(f.getValue());
					f.getValue().fs = FoodState.ordering;
				}
			}
		}
		return false;

		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void tryCookIt(final Order o){//final hack allows timer to accesss order
		//DoCooking(o);
		print("trying to cook it");
		if(o.f.amount == 0){
			o.s = OrderState.invalid;
			handleOutOfFood(o);
			return;
		}
		o.s = OrderState.cooking;
		o.f.amount--;
		print("time is" );
		DoGoToGrill();
		timer.schedule(new TimerTask() {
			String foodName = o.choice;
			public void run() {
				print("Done cooking " + foodName);
				foodDone(o);
			}
		},

		(foodMap.get(o.choice).cookingTime*1000));//getHungerLevel() * 1000);//how long to wait before running task*/
	}

	private void plateIt(Order o){//order o
		//DoPlating(o)
		DoGoToPlating();
		o.waiter.hereIsAnOrder(this, o.choice, o.table);
		o.s = OrderState.sent;
	}

	private void DoGoToPlating() {
		cookGui.DoGoToPlating();
		try {
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}
	}
	
	private void DoGoToGrill(){
		print("this far");
		cookGui.DoGoToGrill();
		try {
			print("acquiring");
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}
	}

	private void handleOutOfFood(Order o){
		Menu temp = new Menu();
		int bugCounter = 0;
		for (Map.Entry<String, Food> f : foodMap.entrySet())//check food amount
		{

			if(f.getValue().amount == 0){
				temp.remove(f.getKey());
				bugCounter++;
			}
		}
		if(bugCounter == 4){
			temp = null;
		}
		o.waiter.outOfOrder(o.choice, o.table, temp);
		orders.remove(o);
	}

	private void orderFood(Food f){
		synchronized(markets){
			for(MyMarket mm : markets){
				if( mm.ms == MarketState.idle){
					mm.m.iNeedFood(this, f.type, 5);
					mm.ms = MarketState.ordered;
					return;
				}
			}
		}
	}
    //animation stuff
	public void doneMoving(){
		moving.release();
	}

	//utilities

	public String returnAmounts(){
		String result = "Cook: ";
		for (Map.Entry<String, Food> f : foodMap.entrySet())//check food amount
		{
			String temp = "error";
			if(f.getKey().equalsIgnoreCase("Steak")){
				temp = "ST";
			}
			else if(f.getKey().equalsIgnoreCase("Chicken")){
				temp = "CK";
			}
			else if(f.getKey().equalsIgnoreCase("Pizza")){
				temp = "PZ";
			}
			else if(f.getKey().equalsIgnoreCase("Salad")){
				temp = "SL";
			}
			else{
				temp = "failed";
			}
			result += temp + ":" + f.getValue().amount + " ";
		}
		return result;

	}

	public void addMarket(Market m){
		MyMarket temp = new MyMarket(m);
		markets.add(temp);
	}

	public void setGui(CookGui gui) {
		cookGui = gui;
	}

	public CookGui getGui() {
		return cookGui;
	}

	public String getName(){
		return name;
	}

	private enum MarketState
	{idle, ordered, outOfStock, busy, free};

	private enum OrderState{
		pending,cooking,cooked,sent, invalid};

		private enum FoodState{
			stocked, ordering, partialStock};

			private class MyMarket{
				Market m;
				MarketState ms = MarketState.idle;
				public MyMarket(Market market){
					m = market;
				}

			}
			private class Order{
				Waiter waiter;
				String choice;
				int table;
				OrderState s;
				Food f;
				public Order(Waiter w, String c, int t){
					waiter = w;
					choice = c;
					table = t;
					f = foodMap.get(c);//maybe alter this
					s = OrderState.pending;
				}
			}
			private class Food{
				String type;
				int cookingTime;
				int amount;
				public Food(String n, int i){
					type = n;
					cookingTime = i;
					amount = 5;
				}
				int low = 2;
				FoodState fs = FoodState.stocked;

			}
}
