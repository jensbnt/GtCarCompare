package jensbnt.compareApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Garage {

	public static List<Integer> owned_cars;
	public static List<CarClass> classes;
	
	public static String[] classNames = { "N100", "N200", "N400", "N500", "N600", "N700", "N800", "N1000", "Group4", "Group3", "Group1", "GroupB", "GroupX" };

	Garage() throws Exception {
		classes = new ArrayList<>();
		parseOwned();
		
		for(int index = 0; index < classNames.length; index++) {
			classes.add(new CarClass());
			parseGroup(classes.get(index).getCars(), "car_database/" + classNames[index] + ".txt", index);
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
	
	public static void addOwned(Car car) {
		FileWriter fw = null;
		try {
		    String filename = "owned_cars.txt";
		    fw = new FileWriter(filename,true);
		    fw.write(car.getId() + "\n");
		} catch(IOException ioe) {
		    System.err.println("IOException: " + ioe.getMessage());
		} finally {
		    try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void parseOwned() throws Exception {
		try {
			BufferedReader in = new BufferedReader(new FileReader("car_database/owned_cars.txt"));
			
			owned_cars = new ArrayList<>();
			
			String line;
			while((line = in.readLine()) != null) {
				owned_cars.add(Integer.parseInt(line));
			}
			
			in.close();
		} catch (Exception e) {
			throw new Exception("Error loading files from owned_cars.txt");
		}
	}
	
	private static void parseGroup(List<Car> group, String filename, int groupIndex) throws Exception {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			
			String line;
			while((line = in.readLine()) != null) {
				parseCar(group, line, groupIndex);
			}
			
			in.close();
		} catch (Exception e) {
			throw new Exception("Error loading files from " + filename);
		}
	}
	
	private static void parseCar(List<Car> group, String rawLine, int groupIndex) throws Exception {
		String[] splittedLine = rawLine.split(",");
		
		if (splittedLine.length != 11)
			throw new Exception("Parsing error");
		
		int id = Integer.parseInt(splittedLine[0]);
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
		
		group.add(new Car((1+groupIndex)*1000 + id, make, name, maxSpeed, acceleration, braking, cornering, stability, bhp, weight, price, owned));
	}
}
