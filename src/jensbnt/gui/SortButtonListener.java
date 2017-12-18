package jensbnt.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

import jensbnt.compareApp.Car;
import jensbnt.util.CarClasses;
import jensbnt.util.CarComparator;
import jensbnt.util.CarSort;
import jensbnt.util.CarStats;

public class SortButtonListener implements ActionListener {

	private CarTableModel carTableModel;
	private List<StatRadioButton> sortingRadioButtons;
	private List<ClassCheckBox> classCheckBoxes;
	private JCheckBox checkOwned;
	
	public SortButtonListener(CarTableModel carTableModel, List<StatRadioButton> sortingRadioButtons, List<ClassCheckBox> classCheckBoxes, JCheckBox checkOwned) {
		this.carTableModel = carTableModel;
		this.sortingRadioButtons = sortingRadioButtons;
		this.classCheckBoxes = classCheckBoxes;
		this.checkOwned = checkOwned;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		carTableModel.clear();
		
		/* Get sort method */
		CarStats selectedSortStat = CarStats.ID;
		for (StatRadioButton button : sortingRadioButtons) {
			if (button.isSelected()) {
				selectedSortStat = button.getCarStat();
			}
		}
		
		/* Get car class */
		List<CarClasses> selectedClasses = new ArrayList<>();
		
		for(ClassCheckBox check : classCheckBoxes) {
			if (check.isSelected()) {
				selectedClasses.add(check.getCarClass());
			}
		}
		
		/* Get car list */
		Car[] list = CarSort.getSortedCars(CarComparator.getComparatorByStat(selectedSortStat), checkOwned.isSelected(), selectedClasses);
		
		for (Car car : list) {
			carTableModel.addRow(car);
		}
	}
}
