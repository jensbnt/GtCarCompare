package jensbnt.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jensbnt.compareApp.Car;
import jensbnt.compareApp.CarClass;
import jensbnt.util.Logger;

public class OwnedCarDatabase {

	private final static Path owned_path = Paths.get(System.getProperty("user.home")).resolve("GTSport/owned_cars.txt");
	private List<Integer> owned_cars;
	
	public OwnedCarDatabase() throws CarLoadException {
		/* Make owned car file */
		createOwnedFile();
		
		/* Load owned cars */
		loadOwnedCars();
	}
	
	public Boolean contains(Car car) {
		return true;
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
			owned_cars = new ArrayList<>();
			
			String line = null;
			while((line = reader.readLine()) != null) {
				owned_cars.add(Integer.parseInt(line));
			}
		} catch (FileNotFoundException e) {
			throw new CarLoadException("'owned_cars.txt' not found");
		} catch (IOException e) {
			throw new CarLoadException("Error loading files from owned_cars.txt");
		}
	}
	
	public void saveOwnedCars(CarClass carClass) {
		try(BufferedWriter writer = Files.newBufferedWriter(owned_path)) {
			for (Car car : carClass.getCars()) {
				if (car.getOwned()) {
					writer.write(car.getId() + "\n");
				}
			}
		} catch (FileNotFoundException e) {
			Logger.addErrorLog("'owned_cars.txt' not found");
		} catch (IOException e) {
			Logger.addErrorLog("Error saving files to owned_cars.txt");
		}
	}
}
