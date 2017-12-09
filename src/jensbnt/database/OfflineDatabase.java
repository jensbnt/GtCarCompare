package jensbnt.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jensbnt.compareApp.Car;
import jensbnt.util.CarClasses;

public class OfflineDatabase implements CarDatabase {

	private final static Path cardb = Paths.get("car_database");
	
	public OfflineDatabase() {
	}

	@Override
	public List<Car> getCars(CarClasses carClass) throws CarLoadException {
		List<Car> cars = new ArrayList<>();
		
		try(BufferedReader reader = Files.newBufferedReader(cardb.resolve(carClass.getFileName()), StandardCharsets.ISO_8859_1)) {	
			String line = null;
			int counter = 1;
			while((line = reader.readLine()) != null) {
				Car newCar = parseCarFromLine(line, (carClass.getValue() * 1000) + (counter++));
				cars.add(newCar);
			}
		} catch (FileNotFoundException e) {
			throw new CarLoadException("'" + carClass.toString() + "' not found");
		} catch (IOException | CarLoadException e) {
			throw new CarLoadException("Can't parse from " + carClass.getFileName());
		}
		
		return cars;
	}
	
	/* Helper functions */
	
	private static Car parseCarFromLine(String rawLine, int generatedId) throws CarLoadException {
		String[] splittedLine = rawLine.split(" / ");
		
		if (splittedLine.length != 10)
			throw new CarLoadException("Parsing error");
		
		int id = generatedId;
		String make = splittedLine[0];
		String name = splittedLine[1];
		double maxSpeed = Double.parseDouble(splittedLine[2]);
		double acceleration = Double.parseDouble(splittedLine[3]);
		double braking = Double.parseDouble(splittedLine[4]);
		double cornering = Double.parseDouble(splittedLine[5]);
		double stability = Double.parseDouble(splittedLine[6]);
		int bhp = Integer.parseInt(splittedLine[7]);
		int weight = Integer.parseInt(splittedLine[8]);
		int price = Integer.parseInt(splittedLine[9]);
		
		return new Car(id, make, name, maxSpeed, acceleration, braking, cornering, stability, bhp, weight, price, 0);
	}

}
