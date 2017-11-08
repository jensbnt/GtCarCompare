package jensbnt.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jensbnt.compareApp.Car;

@SuppressWarnings("serial")
public class CarPanel extends JPanel{

	private JLabel make;
	private JLabel name;
	private JLabel maxSpeed;
	private JLabel acceleration;
	private JLabel braking;
	private JLabel cornering;
	private JLabel stability;
	private JLabel bhp;
	private JLabel weight;
	private JLabel price;
	private JLabel owned;
	
	CarPanel(Car car) {
		initLabels();
		initLayout();
		
		this.setBorder(BorderFactory.createLineBorder(Color.black)); 
		
		this.make.setText(car.getMake());
		this.name.setText(car.getName());
		this.maxSpeed.setText("Max Speed: " + car.getMaxSpeed());
		this.acceleration.setText("Acceleration: " + car.getAcceleration());
		this.braking.setText("Braking: " + car.getBraking());
		this.cornering.setText("Cornering: " + car.getCornering());
		this.stability.setText("Stability: " + car.getStability());
		this.bhp.setText("BHP: " + car.getBhp());
		this.weight.setText("Weight: " + car.getWeight());
		this.price.setText("Price: " + car.getPrice());
		this.owned.setText("Owned: " + car.getOwned());
		
		this.setMaximumSize(new Dimension(8000,30));
	}
	
	private void initLabels() {
		make = new JLabel();
		name = new JLabel();
		maxSpeed = new JLabel();
		acceleration = new JLabel();
		braking = new JLabel();
		cornering = new JLabel();
		stability = new JLabel();
		bhp = new JLabel();
		weight = new JLabel();
		price = new JLabel();
		owned = new JLabel();
	}
	
	private void initLayout() {
		setLayout(new GridLayout(1,11));
		add(make);
		add(name);
		add(maxSpeed);
		add(acceleration);
		add(braking);
		add(cornering);
		add(stability);
		add(bhp);
		add(weight);
		add(price);
		add(owned);
	}
}
