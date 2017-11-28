package jensbnt.compareApp;

import java.util.ArrayList;
import java.util.List;

import jensbnt.util.CarClasses;

public class CarClass {
	
	private CarClasses carClass;
	private List<Car> cars;

	CarClass(CarClasses carClass) {
		this(carClass, new ArrayList<>());
	}
	
	CarClass(CarClasses carClass, List<Car> cars) {
		this.carClass = carClass;
		this.cars = cars;
	}
	
	public CarClasses getCarClass() {
		return carClass;
	}

	public List<Car> getCars() {
		return cars;
	}
}
