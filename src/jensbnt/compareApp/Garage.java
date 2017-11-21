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

public class Garage {

	public static List<Integer> owned_cars;
	public static List<CarClass> classes;
	public static Path cardb = Paths.get("car_database");
	public static Path owned_path = Paths.get(System.getProperty("user.home")).resolve("GTSport/owned_cars.txt");
	
	public static String[] classNames = { "N100", "N200", "N400", "N500", "N600", "N700", "N800", "N1000", "Group4", "Group3", "Group1", "GroupB", "GroupX" };

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
		
		for(int index = 0; index < classNames.length; index++) {
			classes.add(new CarClass());
			parseGroup(classes.get(index).getCars(), classNames[index], index);
		}
	}
	
	public static List<Car> getClass(int index) {
		try {
			return classes.get(index).getCars();
		} catch (IndexOutOfBoundsException e) {
			return new ArrayList<Car>();
		}
	}
	
	public static String[] getClassNames() {
		return classNames;
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
	
	private static void parseGroup(List<Car> group, String classname, int groupIndex) throws Exception {
		try(BufferedReader reader = Files.newBufferedReader(cardb.resolve(classname + ".txt"), StandardCharsets.ISO_8859_1)) {	
			String line = null;
			while((line = reader.readLine()) != null) {
				parseCar(group, line, groupIndex);
			}
		} catch (FileNotFoundException e) {
			throw new Exception("'" + classname + "' not found!");
		} catch (IOException e) {
			throw new Exception("Error loading class: " + classname);
		}
	}
	
	private static void parseCar(List<Car> group, String rawLine, int groupIndex) throws Exception {
		String[] splittedLine = rawLine.split(",");
		
		if (splittedLine.length != 11)
			throw new Exception("Parsing error");
		
		int id = (1+groupIndex)*1000 + Integer.parseInt(splittedLine[0]);
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
