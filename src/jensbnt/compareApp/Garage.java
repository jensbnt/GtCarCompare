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
	
	/* Online database */
	private static final String dbms = "mysql";
	private static final String serverName = "sql11.freesqldatabase.com";
	private static final String userName = "sql11207639";
	private static final String password = "3b9RQuniYk";
	private static final String dbName = "sql11207639";
	
	Garage() {
		/* Set up databases */
		Logger.addLog("Setting up new databases");
		ownedDb = new OwnedCarDatabase();
		offlineDb = new OfflineDatabase();
		onlineDb = new OnlineDatabase(dbms, serverName, userName, password, dbName);
		offlineMode = !onlineDb.hasValidConnection();
		Logger.addLog("Setting up new databases: done");
		
		/* TEMP: START IN OFFLINE MODE */
		//offlineMode = true;
		
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
	
	public static Boolean isLoaded(CarClasses carClassEnum) {
		for(CarClass carClass : classes) {
			if (carClass.getCarClass() == carClassEnum) {
				return carClass.isLoaded();
			}
		}
		
		return false;
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
	
	private static void loadCars() {
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
			
			List<Car> cars = null;
			try {
				cars = db.getCars(carClass);
				classes.add(new CarClass(carClass, cars, true));
			} catch (CarLoadException e) {
				Logger.addErrorLog(e.getMessage());
				classes.add(new CarClass(carClass, new ArrayList<>(), false));
			}
		}
	}
	
	/* OWNING FUNCTIONS */
	
	private static void updateOwnedCars() {
		for(CarClass carClass : classes) {
			if (carClass.isLoaded()) {
				for (Car car : carClass.getCars()) {
					if (car.getOwned() != ownedDb.contains(car)) {
						car.toggleOwned();
					} 
				}
			}
		}
	}
	
	public static void saveOwnedCars() {
		ownedDb.saveOwnedCars(classes);
	}
}
