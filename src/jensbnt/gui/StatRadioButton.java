package jensbnt.gui;

import javax.swing.JRadioButton;

import jensbnt.util.CarStats;

@SuppressWarnings("serial")
public class StatRadioButton extends JRadioButton {

	private CarStats carStat;
	
	StatRadioButton(CarStats carStat) {
		super(carStat.toString());
		this.carStat = carStat;
	}
	
	public CarStats getCarStat() {
		return carStat;
	}
}
