package jensbnt.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import jensbnt.compareApp.Car;
import jensbnt.util.CarComparator;
import jensbnt.util.CarSort;

public class ButtonListener implements ActionListener {

	private JPanel carPanel;
	private ButtonGroup group;
	private JCheckBox checkOwned;
	private List<JCheckBox> checkGroup;
	
	public ButtonListener(JPanel carPanel, ButtonGroup group, JCheckBox checkOwned, List<JCheckBox> checkGroup) {
		this.carPanel = carPanel;
		this.group = group;
		this.checkOwned = checkOwned;
		this.checkGroup = checkGroup;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		carPanel.removeAll();
		
		/* Get sort method */
		String selectedSort;
		if (group.getSelection() == null) {
			selectedSort = "Id";
		} else {
			selectedSort = group.getSelection().getActionCommand();
		}
		
		/* Get car groups */
		List<Integer> selectedGroups = new ArrayList<>();
		
		for(JCheckBox check : checkGroup) {
			if (check.isSelected())
				selectedGroups.add(checkGroup.indexOf(check));
		}
		
		/* Get car list */
		Car[] list = CarSort.getSortedCars(CarComparator.getComparatorByName(selectedSort), checkOwned.isSelected(), selectedGroups);
		
		for (Car car : list) {
			carPanel.add(new CarPanel(car)); // CAR FILTER
		}
		
		carPanel.revalidate();
		carPanel.repaint();
	}
}
