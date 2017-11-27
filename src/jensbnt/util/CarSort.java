package jensbnt.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import jensbnt.compareApp.Car;
import jensbnt.compareApp.Garage;

public class CarSort {

	public static Car[] getSortedCars(Comparator<Car> compare, Boolean filterOwned, List<CarClasses> selectedClasses) {
		List<Car> master = new ArrayList<>();
		
		/* Add groups */
		for (CarClasses carClass : selectedClasses) {
			master = appendGroup(master, Garage.getClass(carClass));
		}
		
		/* return master group */
		Stream<Car> carStream = master.stream().sorted(compare).filter(car -> !filterOwned || car.getOwned());//.limit(15);
		
		return carStream.toArray(Car[]::new);
	}
	
	private static List<Car> appendGroup(List<Car> master, List<Car> group) {
		for(Car car : group) {
			master.add(car);
		}
		
		return master;
	}
}
