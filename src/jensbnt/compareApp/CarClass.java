package jensbnt.compareApp;

import java.util.List;

import jensbnt.util.CarClasses;

public class CarClass {
	
	private CarClasses carClass;
	private List<Car> cars;
	Boolean succesfullLoad;

	CarClass(CarClasses carClass, List<Car> cars, Boolean succesfullLoad) {
		this.carClass = carClass;
		this.cars = cars;
		this.succesfullLoad = succesfullLoad;
	}
	
	public CarClasses getCarClass() {
		return carClass;
	}

	public List<Car> getCars() {
		return cars;
	}
	
	public Boolean isLoaded() {
		return succesfullLoad;
	}
}
