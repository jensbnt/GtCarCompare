package jensbnt.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jensbnt.compareApp.Car;

@SuppressWarnings("serial")
public class CarPanel extends JPanel{

	private List<JLabel> labels;
	private Car car;
	
	CarPanel(Car car, int... fadeColumns) {
		this.car = car;
		
		initLabels();
		initLayout();
		
		fade(fadeColumns);
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setMaximumSize(new Dimension(8000,20));
	}
	
	private void initLabels() {
		labels = new ArrayList<>();
		
		labels.add(new JLabel(car.getMake()));
		labels.add(new JLabel(car.getName()));
		labels.add(new JLabel("Max Speed: " + car.getMaxSpeed()));
		labels.add(new JLabel("Acceleration: " + car.getAcceleration()));
		labels.add(new JLabel("Braking: " + car.getBraking()));
		labels.add(new JLabel("Cornering: " + car.getCornering()));
		labels.add(new JLabel("Stability: " + car.getStability()));
		labels.add(new JLabel("BHP: " + car.getBhp()));
		labels.add(new JLabel("Weight: " + car.getWeight()));
		labels.add(new JLabel("Price: " + car.getPrice()));
		labels.add(new JLabel("Owned: " + car.getOwned()));
	}
	
	private void initLayout() {
		setLayout(new GridLayout(1,labels.size()));
		
		for(JLabel label : labels) {
			add(label);
		}
	}
	
	private void fade(int[] fadeColumns) {
		for(int fadeColumn : fadeColumns) { // EXCEPTION HANDLING PLS :(
			labels.get(fadeColumn).setVisible(false);
			System.out.println(fadeColumn);
		}
	}
}
