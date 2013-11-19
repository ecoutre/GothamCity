package simcity.restaurant_evan.src.restaurant;

import simcity.restaurant_evan.src.restaurant.interfaces.Customer;
import simcity.restaurant_evan.src.restaurant.interfaces.Waiter;



public class Order {
	Waiter waiter;
	public Food choice;
	int tableNumber;
	public OrderState os;
	//int inventory;
	double totalPrice;
	public Customer customer;
	boolean outOfStock = false;
	
	
	public Order(Waiter waiter, String foodChoice, int tableNumber, OrderState os, Customer customer){
		this.waiter = waiter;
		this.tableNumber = tableNumber;
		this.choice = new Food(foodChoice);
		this.os = os;
		this.customer = customer;
		//this.totalPrice = totalPrice;
	}
	
	public enum OrderState {pending, haveInventory, outOfInventory, reordering, cooking, doneCooking, requestCheck, paying, idle};
	
	 public void setTotalPrice(double t) {
		totalPrice = t;
	}
		
}

