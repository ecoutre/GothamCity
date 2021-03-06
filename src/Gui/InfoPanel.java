package Gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

import simcity.PersonAgent;

public class InfoPanel extends JPanel {
	JLabel welcome;	
	JLabel name;
	JLabel location;
	JLabel money;
	
	public InfoPanel(){
		setPreferredSize(new Dimension(1000,50));
		setMinimumSize(new Dimension(1000,50));
		setMaximumSize(new Dimension(1000,50));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		welcome = new JLabel ("Welcome to Arkham City");
		welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(welcome);
	}
	public void updateInfoPanel(PersonAgent p){//update this method to display any information you want from a person.

		this.removeAll();
		setBackground(Color.WHITE);
		//paintBackground();
		//remove(welcome);
		
		JPanel namePanel = new JPanel();
		name = new JLabel("Name: " + p.getName());
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel locationPanel = new JPanel();
		location = new JLabel("Location: " + p.getCurrentBuilding().getName());
		location.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel moneyPanel = new JPanel();
		money = new JLabel("$: " + p.getMoney());
		
		namePanel.add(name);
		add(namePanel);
		add(Box.createRigidArea(new Dimension(10,10)));
		locationPanel.add(location);
		add(locationPanel);
		add(Box.createRigidArea(new Dimension(10,10)));
		moneyPanel.add(money);
		add(moneyPanel);
		validate();
	}
}
