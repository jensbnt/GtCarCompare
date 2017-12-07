package jensbnt.compareApp;

import java.util.ArrayList;
import java.util.List;

import jensbnt.database.CarLoadException;
import jensbnt.database.OfflineDatabase;
import jensbnt.database.OnlineDatabase;
import jensbnt.database.OwnedCarDatabase;
import jensbnt.gui.GarageUpdateListener;
import jensbnt.util.CarClasses;
import jensbnt.util.Logger;

public class Garage {

	private static List<CarClass> classes;
	
	private static List<GarageUpdateListener> listeners = new ArrayList<>();
	
	private static OwnedCarDatabase ownedDb;
	private static OfflineDatabase offlineDb;
	private static OnlineDatabase onlineDb;
	
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

	public static void addListener(GarageUpdateListener toAdd) {
	    listeners.add(toAdd);
	}
	
	/* LOADING FUNCTIONS */
	
	private static void loadCars() {
		classes = new ArrayList<>();
		
		for(GarageUpdateListener listener : listeners) {
			listener.newUpdate(true);
		}
		
		for(CarClasses carClass : CarClasses.values()) {
			Logger.addLog("Fetching class " + carClass.toString() + " from database " + "(" + carClass.getValue() + "/" + CarClasses.values().length + ")");
			
			for(GarageUpdateListener listener : listeners) {
				listener.updateValueChanged((carClass.getValue()*100)/CarClasses.values().length);
			}
			
			try {	// TRY TO LOAD ONLINE
				classes.add(new CarClass(carClass, onlineLoad(carClass), true));
			} catch (CarLoadException e) {
				Logger.addErrorLog("Online load fail: " + e.getMessage());
				try {	// TRY TO LOAD OFFLINE
					classes.add(new CarClass(carClass, offlineLoad(carClass), true));
				} catch (CarLoadException e1) {	// LOADING FAILED
					Logger.addErrorLog(e1.getMessage());
					classes.add(new CarClass(carClass, new ArrayList<>(), false));
				}
			}
		}
		
		for(GarageUpdateListener listener : listeners) {
			listener.newUpdate(false);
		}
	}
	
	private static List<Car> offlineLoad(CarClasses carClass) throws CarLoadException {
		List<Car> cars = offlineDb.getCars(carClass);
		return cars;
	}
	
	private static List<Car> onlineLoad(CarClasses carClass) throws CarLoadException {
		List<Car> cars = onlineDb.getCars(carClass);
		return cars;
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
