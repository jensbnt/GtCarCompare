package jensbnt.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import jensbnt.compareApp.Car;
import jensbnt.compareApp.Garage;

public class CarSort {

	public static Car[] getSortedCars(Comparator<Car> compare, Boolean filterOwned, List<Integer> selectedGroup) {
		List<Car> master = new ArrayList<>();
		
		/* Add groups */
		for (Integer index : selectedGroup) {
			master = appendGroup(master, Garage.getGroup(index));
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
