package simcity.Home;

import java.util.ArrayList;
import java.util.List;

import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;
import agent.Role;
import simcity.Building;
import simcity.PersonAgent.RentBill;
import simcity.Home.gui.ApartmentResidentGui;
import simcity.Home.gui.ResidentGui;
import simcity.Home.interfaces.Resident;
import simcity.Home.test.mock.MockResident;


public class Apartment extends Home {

	String type;
	int size;
	int roomNumber;
	public List<Integer> roomNumbers;
	public List<Apartment> rooms;
	public List<String> groceryList;
	//public List<String> groceryBag;
	public List<RentBill> rentBills;
	public List<Food> fridgeFoods;

	public List<ResidentRole> residents; //When the PersonAgent reaches thehome, he needs to be able to look at this class and add this role to his list
	//public String resident = "resident";
	public List<ResidentGui> residentGui;
	ResidentRole res1 = new ResidentRole();
	ApartmentResidentGui res1gui = new ApartmentResidentGui(res1, ScreenFactory.getMeScreen(this.getName()));
	public ResidentRole resident; //When the PersonAgent reaches thehome, he needs to be able to look at this class and add this role to his list
	public String HomeResident = "resident";
	
	public Apartment(String type, int entranceX, int entranceY, int guiX,
			int guiY){
		super(type, entranceX, entranceY, guiX, guiY);
		this.type = type;
		groceryList = new ArrayList<String>();
		rentBills = new ArrayList<RentBill>();
		fridgeFoods = new ArrayList<Food>();	
		residents = new ArrayList<ResidentRole>();
		
		residentGui = new ArrayList<ResidentGui>();
		
		//res1.setGui((RoleGui)new ApartmentResidentGui());
		res1.setGui(res1gui);
		//res1.setApartmentResidentGui(res1gui);
		residents.add(res1);
		rooms = new ArrayList<Apartment>();
		roomNumbers = new ArrayList<Integer>();
		
		//You'll notice for this particular role, resident needs a Person as a parameter. 
		//But, we don't necessarily immediately know who this Person is.
		//Please add ANOTHER constructor that doesnt need a Person parameter
		//(You can still keep the other constructor, resulting in two constructors)
	}
	
	public Apartment(String type, int entranceX, int entranceY, int guiX,
			int guiY, int exitX, int exitY){
		super(type, entranceX, entranceY, guiX, guiY, exitX, exitY);
		this.type = type;
		groceryList = new ArrayList<String>();
		rentBills = new ArrayList<RentBill>();
		fridgeFoods = new ArrayList<Food>();	
		residents = new ArrayList<ResidentRole>();
		
		residentGui = new ArrayList<ResidentGui>();
		
		//res1.setGui((RoleGui)new ApartmentResidentGui());
		res1.setGui(res1gui);
		//res1.setApartmentResidentGui(res1gui);
		residents.add(res1);
		rooms = new ArrayList<Apartment>();
		roomNumbers = new ArrayList<Integer>();
		
		//You'll notice for this particular role, resident needs a Person as a parameter. 
		//But, we don't necessarily immediately know who this Person is.
		//Please add ANOTHER constructor that doesnt need a Person parameter
		//(You can still keep the other constructor, resulting in two constructors)
	}

	public List<RentBill> getRentBills() {
		return rentBills;
	}

	public void setRentBills(List<RentBill> rentBills) {
		this.rentBills = rentBills;
	}
	public List<Apartment> getRooms() {
		return rooms;
	}
	public void setRooms(List<Apartment> rooms) {
		this.rooms = rooms;
	}
	@Override
	public ResidentRole getResident() {
		return residents.get(0);
		//return residents.get(roomNumber-1);
	}
}	

