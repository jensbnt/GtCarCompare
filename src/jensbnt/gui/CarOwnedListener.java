package jensbnt.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import jensbnt.compareApp.Car;
import jensbnt.compareApp.Garage;

public class CarOwnedListener implements ActionListener {
	
	private JList<Car> listArea;
	
	public CarOwnedListener(JList<Car> listArea) {
		this.listArea = listArea;
	}

	public void actionPerformed(ActionEvent e) {
		Car car = listArea.getSelectedValue();

		Garage.addOwned(car);
		
		if (car != null) {
			car.toggleOwned();
		}
	}
}
