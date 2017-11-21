package jensbnt.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import jensbnt.compareApp.Car;
import jensbnt.util.CarComparator;
import jensbnt.util.CarSort;
import jensbnt.util.CarStats;

public class ButtonListener implements ActionListener {

	private JPanel carPanel;
	private List<JRadioButton> sortingRadioButtons;
	private List<JCheckBox> classCheckBoxes;
	private JCheckBox checkOwned;
	private JCheckBox checkFocus;
	
	public ButtonListener(JPanel carPanel, List<JRadioButton> sortingRadioButtons, List<JCheckBox> classCheckBoxes, JCheckBox checkOwned, JCheckBox checkFocus) {
		this.carPanel = carPanel;
		this.sortingRadioButtons = sortingRadioButtons;
		this.classCheckBoxes = classCheckBoxes;
		this.checkOwned = checkOwned;
		this.checkFocus = checkFocus;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		carPanel.removeAll();
		
		/* Get sort method */
		CarStats selectedSortStat = CarStats.ID;
		for (JRadioButton button : sortingRadioButtons) {
			if (button.isSelected()) {
				selectedSortStat = CarStats.value(button.getText());
			}
		}
		
		/* Get car groups */
		List<Integer> selectedGroups = new ArrayList<>();
		
		for(JCheckBox check : classCheckBoxes) {
			if (check.isSelected())
				selectedGroups.add(classCheckBoxes.indexOf(check));
		}
		
		/* Get car list */
		Car[] list = CarSort.getSortedCars(CarComparator.getComparatorByStat(selectedSortStat), checkOwned.isSelected(), selectedGroups);
		
		for (Car car : list) {
			if (checkFocus.isSelected()) {
				carPanel.add(new CarPanel(car, selectedSortStat));
			} else {
				carPanel.add(new CarPanel(car));
			}
		}
		
		carPanel.revalidate();
		carPanel.repaint();
	}
}
