package jensbnt.compareApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Garage {

	public static List<Integer> owned_cars;
	public static List<Car> group3;
	public static List<Car> group4;
	public static List<Car> groupB;

	Garage() throws Exception {
		parseOwned();
		parseGroup(group3 = new ArrayList<>(), "Group3.txt");
		parseGroup(group4 = new ArrayList<>(), "Group4.txt");
		parseGroup(groupB = new ArrayList<>(), "GroupB.txt");
	}
	
	public static List<Car> getGroup(int index) {
		switch(index) {
		case 0:
			return new ArrayList<Car>();
		case 1:
			return group4;
		case 2:
			return group3;
		case 3:
			return groupB;
		default:
			return null;
		}
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
			BufferedReader in = new BufferedReader(new FileReader("owned_cars.txt"));
			
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
	
	private static void parseGroup(List<Car> group, String filename) throws Exception {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			
			String line;
			while((line = in.readLine()) != null) {
				parseCar(group, line);
			}
			
			in.close();
		} catch (Exception e) {
			throw new Exception("Error loading files from " + filename);
		}
	}
	
	private static void parseCar(List<Car> group, String rawLine) throws Exception {
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
		
		group.add(new Car(id, make, name, maxSpeed, acceleration, braking, cornering, stability, bhp, weight, price, owned));
	}
}
