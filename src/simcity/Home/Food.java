package simcity.Home;
public class Food {

	private String type;
	private double price;
	private int cookingTime;
	private int capacity;
	private int lowThreshold;
	int amount;
	//OrderState orderState;
	
	
	public Food (String type) {
		this.type = type;
		
		if (type.equals("Steak")){
			cookingTime = 10;
			//amount = 5;
			amount = 0;
			capacity = 6;
		}
		
		else if (type.equals("Chicken")){
			cookingTime = 8;
			//amount = 4;
			amount = 4;
			capacity = 6;
		}
		
		else if (type.equals("Pizza")) {
			cookingTime = 6;
			//amount = 3;
			amount = 0;
			capacity = 6;
		}
		else if (type.equals("Salad")) {
			cookingTime = 4;
			//amount = 5;
			amount = 0;
			capacity = 6;
		}
		
		//else if (type.equals("Food")) {
			
		//}
		
		//this.amount = 3;//amount of each order the cook has
		//this.capacity = 6;// maximum capacity
		this.lowThreshold = 1;//threshold where cook orders from market
	}
	
	public String getType(){
		return this.type;
	}
	
	public int getCookingTime() {
		return this.cookingTime;
	}
	public double getFoodPrice() {
		return price;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setAmount(int newCap) {
		this.amount = newCap;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getLowThreshold() {
		return lowThreshold;
	}

	
}