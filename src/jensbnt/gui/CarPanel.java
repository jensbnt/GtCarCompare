package jensbnt.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		this(car, 0);
	}
	
	CarPanel(Car car, int focus) {
		this.car = car;
		
		initLabels();
		initLayout();
		
		focus(focus);
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.setMaximumSize(new Dimension(8000,20));
	}
	
	private void initLabels() {
		labels = new ArrayList<>();
		for (CarStats stat : CarStats.values()) {
			if (stat == CarStats.NAME || stat == CarStats.MAKE) {
				labels.add(new JLabel(car.getStringByStat(stat)));
			} else if (stat != CarStats.ID) {
				labels.add(new JLabel(stat.toString() + ": " + car.getStringByStat(stat)));
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
			this.setBackground(Color.LIGHT_GRAY);
		} else {
			owned.setText("Not Owned");
			owned.setBackground(Color.RED);
			this.setBackground(Color.WHITE);
		}
	}
	
	private void focus(int focus) { // TEMP. DISABLED
		/*if (focus != 0) {
			for(int i = 2; i < labels.size(); i++) {
				if(i == focus) {
					labels.get(i).setVisible(true);
				} else {
					labels.get(i).setVisible(false);
				}
			}
		}*/
	}
}
