package jensbnt.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jensbnt.compareApp.Car;
import jensbnt.util.CarStats;

@SuppressWarnings("serial")
public class CarPanel extends JPanel{

	private List<JLabel> labels;
	private JButton owned;
	private Car car;
	
	CarPanel(Car car) {
		this(car, null);
	}
	
	CarPanel(Car car, CarStats focus) {
		this.car = car;
		
		initLabels(focus);
		initLayout();
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setMaximumSize(new Dimension(8000,22));
	}
	
	private void initLabels(CarStats focus) {
		labels = new ArrayList<>();
		for (CarStats stat : CarStats.values()) {
			if (stat == CarStats.NAME || stat == CarStats.MAKE) {
				labels.add(new JLabel(car.getStringByStat(stat)));
			} else if (stat == CarStats.WEIGHT) {
				labels.add(new JLabel(car.getStringByStat(stat) + " kg"));
			} else if (stat == CarStats.POWER) {
				labels.add(new JLabel(car.getStringByStat(stat) + " BHP"));
			} else if (stat == CarStats.PRICE) {
				labels.add(new JLabel("Cr. " + NumberFormat.getIntegerInstance().format(car.getPrice())));
			} else if (stat != CarStats.ID) {
				labels.add(new JLabel(stat.toString() + ": " + car.getStringByStat(stat)));
			}
			
			if (focus != null && labels.size() > 0) {
				if(stat == focus || stat == CarStats.NAME || stat == CarStats.MAKE) {
					labels.get(labels.size() - 1).setVisible(true);
				} else {
					labels.get(labels.size() - 1).setVisible(false);
				}
			}
		}
		
		owned = new JButton();
		updateOwnedButton();
		initListener();
	}
	
	private void initLayout() {
		setLayout(new GridLayout(1,labels.size()));
		
		for(JLabel label : labels) {
			add(label);
		}
		
		add(owned);
	}
	
	private void initListener() {
		owned.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				car.toggleOwned();
				updateOwnedButton();
			}
			
		});
	}
	
	private void updateOwnedButton() {
		if (car.getOwned()) {
			owned.setText("Owned");
			owned.setBackground(Color.GREEN);
			this.setBackground(Color.lightGray);
		} else {
			owned.setText("Not Owned");
			owned.setBackground(Color.RED);
			this.setBackground(Color.WHITE);
		}
	}
}
