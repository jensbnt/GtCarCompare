package jensbnt.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

import jensbnt.compareApp.Garage;

public class CarOwnedListener implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
			JTextField textField = (JTextField) e.getSource();
			textField.setText("");
			Garage.addOwned(e.getActionCommand());
	}
}
