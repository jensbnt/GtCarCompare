package jensbnt.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jensbnt.compareApp.Car;
import jensbnt.compareApp.CarClass;
import jensbnt.util.Logger;

public class OwnedCarDatabase {

	private final static Path owned_path = Paths.get(System.getProperty("user.home")).resolve("GTSport/owned_cars.txt");
	private Map<Integer, Integer> owned_cars;
	
	public OwnedCarDatabase() {
		/* Make owned car file */
		try {
			createOwnedFile();
			loadOwnedCars();
		} catch (CarLoadException e) {
			Logger.addErrorLog(e.getMessage());
			owned_cars = new HashMap<>();
		}
	}
	
	public int contains(Car car) {
		Integer owned = owned_cars.get(car.getId());
		
		if (owned == null) {
			return 0;
		} else {
			return owned;
		}
	}

	private void createOwnedFile() throws CarLoadException {
		if(!Files.exists(owned_path)) {
			Logger.addLog("No 'owned_cars.txt' file, creating...");
			try {
				Files.createDirectories(owned_path.getParent());
				Files.createFile(owned_path);
			} catch (IOException e) {
				throw new CarLoadException("Error creating 'owned_cars.txt' file");
			}
			Logger.addLog("Created 'owned_cars.txt' file");
		}
	}
	
	private void loadOwnedCars() throws CarLoadException {
		try(BufferedReader reader = Files.newBufferedReader(owned_path)) {
			owned_cars = new HashMap<>();
			
			String line = null;
			while((line = reader.readLine()) != null) {
				String[] splitted = line.split(":");
				
				if (splitted.length != 2) {
					throw new CarLoadException("Parsing eror");
				}
				
				owned_cars.put(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
			}
		} catch (FileNotFoundException e) {
			throw new CarLoadException("'owned_cars.txt' not found");
		} catch (IOException e) {
			throw new CarLoadException("Error loading files from owned_cars.txt");
		}
	}
	
	public void saveOwnedCars(List <CarClass> classes) {
		try(BufferedWriter writer = Files.newBufferedWriter(owned_path)) {
			for(CarClass carClass : classes) {
				for (Car car : carClass.getCars()) {
					if (car.getOwned() > 0) {
						writer.write(car.getId() + ":" + car.getOwned() + "\r\n");
					}
				}
			}
		} catch (IOException e) {
			Logger.addErrorLog("Error saving files to owned_cars.txt");
		}
	}
}
