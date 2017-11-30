package jensbnt.compareApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jensbnt.database.CarDatabase;
import jensbnt.util.CarClasses;
import jensbnt.util.Logger;

public class Garage {

	private static List<Integer> owned_cars;
	private static List<CarClass> classes;
	private static Path cardb = Paths.get("car_database");
	private static Path owned_path = Paths.get(System.getProperty("user.home")).resolve("GTSport/owned_cars.txt");
	
	private static Boolean offlineMode = false;
	
	Garage() throws Exception {
		@SuppressWarnings("unused")
		CarDatabase cardatabase = new CarDatabase();
		
		/* Make owned car file */
		if(!Files.exists(owned_path)) {
			Logger.addLog("No 'owned_cars.txt' file, creating...");
			try {
				Files.createDirectories(owned_path.getParent());
				Files.createFile(owned_path);
			} catch (IOException e) {
				throw new Exception("Error finding/creating owned_cars.txt");
			}
			Logger.addLog("Created 'owned_cars.txt' file");
		}
		
		/* Load owned cars */
		Logger.addLog("Loading owned cars");
		loadOwnedCars();
		Logger.addLog("Loading owned cars: done");
		
		/* Load cars */
		classes = new ArrayList<>();
		offlineMode = !CarDatabase.hasValidConnection();
		
		if (offlineMode) {	// Load from files
			Logger.addLog("Loading cars in offline mode");
			for(CarClasses carClass : CarClasses.values()) {
				Logger.addLog("Parsing class " + carClass.toString() + " from files " + "(" + carClass.getValue() + "/" + CarClasses.values().length + ")");
				classes.add(new CarClass(carClass));
				parseClassFromFiles(classes.get(classes.size() - 1).getCars(), carClass);
			}
		} else {	// Load from database
			Logger.addLog("Loading cars in online mode");
			for(CarClasses carClass : CarClasses.values()) {
				Logger.addLog("Fetching class " + carClass.toString() + " from database " + "(" + carClass.getValue() + "/" + CarClasses.values().length + ")");
				List<Car> cars = CarDatabase.getCarsFromDatabase(carClass);
				classes.add(new CarClass(carClass, cars));
			}
			
			updateOwnedCars();
		}
		
		Logger.addLog("Loading cars: done");
		
		CarDatabase.brakeConnection();
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
	
	/* ONLINE FUNCTIONS */
	
	@SuppressWarnings("unused")
	private static void loadCarsToDatabase() { // DANGEROUS FUNCTION!!!
		int totalCars = 0;
		for (CarClass carClass : classes) {
			totalCars += carClass.getCars().size();
		}
		
		int carCounter = 1;
		for (CarClass carClass : classes) {
			System.out.println("Loading carclass: " + carClass.getCarClass().toString());
			
			// ADD TABLE
			CarDatabase.addCarDatabase(carClass.getCarClass());
			
			List<Car> cars = carClass.getCars();
			for(Car car : cars) {
				System.out.println(String.format("%.2f", (float) (carCounter++*100) / totalCars) + "% - Loading car: " + car.getId());
				
				// ADD CAR
				CarDatabase.addCarToDatabase(carClass.getCarClass(), car);
			}
		}
	}
	
	/* OFFLINE FUNCTIONS */
	
	private static void loadOwnedCars() throws Exception {
		try(BufferedReader reader = Files.newBufferedReader(owned_path)) {
			owned_cars = new ArrayList<>();
			
			String line = null;
			while((line = reader.readLine()) != null) {
				owned_cars.add(Integer.parseInt(line));
			}
		} catch (FileNotFoundException e) {
			throw new Exception("'owned_cars.txt' not found");
		} catch (IOException e) {
			throw new Exception("Error loading files from owned_cars.txt");
		}
	}
	
	public static void saveOwnedCars() throws Exception {
		try(BufferedWriter writer = Files.newBufferedWriter(owned_path)) {
			for(CarClass carClass : classes) {
				for (Car car : carClass.getCars()) {
					if (car.getOwned()) {
						writer.write(car.getId() + "\n");
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new Exception("'owned_cars.txt' not found");
		} catch (IOException e) {
			throw new Exception("Error saving files to owned_cars.txt");
		}
	}
	
	private static void updateOwnedCars() {
		for(CarClass carClass : classes) {
			for (Car car : carClass.getCars()) {
				if (owned_cars.contains(car.getId())) {
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
	
	private static void parseClassFromFiles(List<Car> group, CarClasses carClass) throws Exception {
		try(BufferedReader reader = Files.newBufferedReader(cardb.resolve(carClass.getFileName()), StandardCharsets.ISO_8859_1)) {	
			String line = null;
			while((line = reader.readLine()) != null) {
				parseCarFromLine(group, line, carClass.getValue());
			}
		} catch (FileNotFoundException e) {
			throw new Exception("'" + carClass.toString() + "' not found");
		} catch (IOException e) {
			throw new Exception("Error loading class: " + carClass.toString());
		} catch (Exception e) {
			throw new Exception("Error loading class: " + carClass.toString());
		}
	}
	
	private static void parseCarFromLine(List<Car> group, String rawLine, int classIndex) throws Exception {
		String[] splittedLine = rawLine.split(",");
		
		if (splittedLine.length != 11)
			throw new Exception("Parsing error");
		
		int id = classIndex*1000 + Integer.parseInt(splittedLine[0]);
		String make = splittedLine[1];
		String name = splittedLine[2];
		double maxSpeed = Double.parseDouble(splittedLine[3]);
		double acceleration = Double.parseDouble(splittedLine[4]);
		double braking = Double.parseDouble(splittedLine[5]);
		double cornering = Double.parseDouble(splittedLine[6]);
		double stability = Double.parseDouble(splittedLine[7]);
		int bhp = Integer.parseInt(splittedLine[8]);
		int weight = Integer.parseInt(splittedLine[9]);
		int price = Integer.parseInt(splittedLine[10]);
		
		Boolean owned = owned_cars.contains(id);
		
		group.add(new Car(id, make, name, maxSpeed, acceleration, braking, cornering, stability, bhp, weight, price, owned));
	}
}
