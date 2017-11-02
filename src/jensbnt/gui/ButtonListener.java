package jensbnt.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;

import jensbnt.compareApp.Car;
import jensbnt.util.CarComparator;
import jensbnt.util.CarSort;

public class ButtonListener implements ActionListener {

	private DefaultListModel<String> listModel;
	private ButtonGroup group;
	private JCheckBox checkOwned;
	private List<JCheckBox> checkGroup;
	
	public ButtonListener(DefaultListModel<String> listModel, ButtonGroup group, JCheckBox checkOwned, List<JCheckBox> checkGroup) {
		this.listModel = listModel;
		this.group = group;
		this.checkOwned = checkOwned;
		this.checkGroup = checkGroup;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		listModel.clear();
		
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
				selectedGroups.add(Integer.parseInt(check.getActionCommand()));
		}
		
		/* Get car list */
		Car[] list = CarSort.getSortedCars(CarComparator.getComparatorByName(selectedSort), checkOwned.isSelected(), selectedGroups);
		
		for (Car car : list) {
			listModel.addElement(car.toString());
		}
	}
}
