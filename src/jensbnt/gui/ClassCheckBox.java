package jensbnt.gui;

import javax.swing.JCheckBox;

import jensbnt.util.CarClasses;

@SuppressWarnings("serial")
public class ClassCheckBox extends JCheckBox {
	
	private CarClasses carClass;

	ClassCheckBox(CarClasses carClass) {
		super(carClass.toString());
		this.carClass = carClass;
	}
	
	public CarClasses getCarClass() {
		return carClass;
	}
}
