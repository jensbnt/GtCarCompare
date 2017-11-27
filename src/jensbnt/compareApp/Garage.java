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

import jensbnt.util.CarClasses;

public class Garage {

	public static List<Integer> owned_cars;
	public static List<CarClass> classes;
	public static Path cardb = Paths.get("car_database");
	public static Path owned_path = Paths.get(System.getProperty("user.home")).resolve("GTSport/owned_cars.txt");
	
	Garage() throws Exception {
		/* Make owned car file */
		if(!Files.exists(owned_path)) {
			try {
				Files.createDirectories(owned_path.getParent());
				Files.createFile(owned_path);
			} catch (IOException e) {
				throw new Exception("Error finding/creating owned_cars.txt");
			}
		}
		
		/* Rest */
		classes = new ArrayList<>();
		loadOwnedCars();
		
		for(CarClasses carClass : CarClasses.values()) {
			classes.add(new CarClass(carClass));
			parseClass(classes.get(classes.size() - 1).getCars(), carClass);
		}
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
	
	private static void loadOwnedCars() throws Exception {
		try(BufferedReader reader = Files.newBufferedReader(owned_path)) {
			owned_cars = new ArrayList<>();
			
			String line = null;
			while((line = reader.readLine()) != null) {
				owned_cars.add(Integer.parseInt(line));
			}
		} catch (FileNotFoundException e) {
			throw new Exception("'owned_cars.txt' not found!");
		} catch (IOException e) {
			throw new Exception("Error loading files from owned_cars.txt");
		}
	}
	
	public static void saveOwnedCars() {
		try(BufferedWriter writer = Files.newBufferedWriter(owned_path)) {
			for(CarClass carClass : classes) {
				for (Car car : carClass.getCars()) {
					if (car.getOwned()) {
						writer.write(car.getId() + "\n");
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Something went wrong writing the contacts!");
		}
	}
	
	private static void parseClass(List<Car> group, CarClasses carClass) throws Exception {
		try(BufferedReader reader = Files.newBufferedReader(cardb.resolve(carClass.getFileName()), StandardCharsets.ISO_8859_1)) {	
			String line = null;
			while((line = reader.readLine()) != null) {
				parseCar(group, line, carClass.getValue());
			}
		} catch (FileNotFoundException e) {
			throw new Exception("'" + carClass.toString() + "' not found!");
		} catch (IOException e) {
			throw new Exception("Error loading class: " + carClass.toString());
		}catch (Exception e) {
			throw new Exception("Error loading class: " + carClass.toString());
		}
	}
	
	private static void parseCar(List<Car> group, String rawLine, int classIndex) throws Exception {
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
