package jensbnt.database;

import java.util.List;

import jensbnt.compareApp.Car;
import jensbnt.util.CarClasses;

public interface CarDatabase {
	
	List<Car> getCars(CarClasses carClass) throws CarLoadException;
}
