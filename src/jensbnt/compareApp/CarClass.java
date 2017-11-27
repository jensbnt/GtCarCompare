package jensbnt.compareApp;

import java.util.ArrayList;
import java.util.List;

import jensbnt.util.CarClasses;

public class CarClass {
	
	private CarClasses carClass;
	private List<Car> cars;

	CarClass(CarClasses carClass) {
		this.carClass = carClass;
		cars = new ArrayList<>();
	}
	
	public CarClasses getCarClass() {
		return carClass;
	}

	public List<Car> getCars() {
		return cars;
	}
}
