package jensbnt.compareApp;

import java.util.ArrayList;
import java.util.List;

import jensbnt.database.CarDatabase;
import jensbnt.database.CarLoadException;
import jensbnt.database.OfflineDatabase;
import jensbnt.database.OnlineDatabase;
import jensbnt.database.OwnedCarDatabase;
import jensbnt.util.CarClasses;
import jensbnt.util.Logger;

public class Garage {

	private static List<CarClass> classes;
	
	private static OwnedCarDatabase ownedDb;
	private static OfflineDatabase offlineDb;
	private static OnlineDatabase onlineDb;
	private static Boolean offlineMode = false;
	
	Garage() throws CarLoadException {
		/* Set up databases */
		Logger.addLog("Setting up new databases");
		ownedDb = new OwnedCarDatabase();
		offlineDb = new OfflineDatabase();
		onlineDb = new OnlineDatabase();
		offlineMode = !onlineDb.hasValidConnection();
		Logger.addLog("Setting up new databases: done");
		
		/* Load cars */
		Logger.addLog("Loading cars");
		loadCars();
		Logger.addLog("Loading cars: done");
		
		/* Update owned cars */
		Logger.addLog("Updateing owned cars");
		updateOwnedCars();
		Logger.addLog("Updateing owned cars: done");
	}
	
	/* CLASS FUNCTIONS */
	
	public static Boolean offlineModeActivated() {
		return offlineMode;
	}
	
	public static List<Car> getClass(CarClasses carClassEnum) {
		for(CarClass carClass : classes) {
			if (carClass.getCarClass() == carClassEnum) {
				return carClass.getCars();
			}
		}
		
		return new ArrayList<Car>();
	}
	
	public static int getValue() {
		int totalValue = 0;
		for(CarClass carClass : classes) {
			for (Car car : carClass.getCars()) {
				if (car.getOwned()) {
					totalValue += car.getPrice();
				}
			}
		}
		return totalValue;
	}
	
	/* LOADING FUNCTIONS */
	
	private static void loadCars() throws CarLoadException {
		classes = new ArrayList<>();
		CarDatabase db;
		
		if (offlineMode) {
			Logger.addLog("Loading cars in offline mode");
			db = offlineDb;
		} else {
			Logger.addLog("Loading cars in online mode");
			db = onlineDb;
		}
		
		for(CarClasses carClass : CarClasses.values()) {
			Logger.addLog("Fetching class " + carClass.toString() + " from database " + "(" + carClass.getValue() + "/" + CarClasses.values().length + ")");
			List<Car> cars = db.getCars(carClass);
			classes.add(new CarClass(carClass, cars));
		}
	}
	
	/* OWNING FUNCTIONS */
	
	private static void updateOwnedCars() {
		for(CarClass carClass : classes) {
			for (Car car : carClass.getCars()) {
				if (ownedDb.contains(car)) {
					if (!car.getOwned()) {
						car.toggleOwned();
					}
				} else {
					if (car.getOwned()) {
						car.toggleOwned();
					}
				}
			}
		}
	}
	
	public static void saveOwnedCars() {
		for(CarClass carClass : classes) {
			ownedDb.saveOwnedCars(carClass);
		}
	}
}
