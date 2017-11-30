package jensbnt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jensbnt.compareApp.Car;
import jensbnt.util.CarClasses;
import jensbnt.util.CarStats;
import jensbnt.util.Logger;

public class CarDatabase {

	private final static String dbms = "mysql";
	private final static String serverName = "sql11.freesqldatabase.com";
	private final static String userName = "sql11207639";
	private final static String password = "3b9RQuniYk";
	private final static String dbName = "sql11207639";
	//private final static String portNumber = "3306";
	
	private static Connection conn = null;
	
	public CarDatabase() {
		makeConnection();
	}
	
	private static void makeConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:" + dbms + "://" + serverName + "/" + dbName, userName, password);
		} catch (ClassNotFoundException e) {
			Logger.addErrorLog("CarDatabase make connection: " + e.getMessage());
		} catch (SQLException e) {
			Logger.addErrorLog("CarDatabase make connection: " + e.getMessage());
		}
	}
	
	public static void brakeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			Logger.addErrorLog("CarDatabase brake connection: " + e.getMessage());
		}
	}
	
	public static Boolean hasValidConnection() {
		return conn != null;
	}
	
	public static void addCarDatabase(CarClasses carClass) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(prepareNewTableStatement(carClass));
			stmt.executeUpdate();
		} catch (SQLException e) {
			Logger.addErrorLog("CarDatabase add new car class: " + e.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				Logger.addErrorLog("CarDatabase removing statement: " + e.getMessage());
			}
		}
	}
	
	private static String prepareNewTableStatement(CarClasses carClass) {
		StringBuilder statement = new StringBuilder("CREATE TABLE " + carClass.getDatabaseName() + " (");
		statement.append("ID int NOT NULL AUTO_INCREMENT,");
		
		for(CarStats stat : CarStats.values()) {
			if(stat != CarStats.ID && stat != CarStats.TOTAL_SCORE) {
				statement.append(stat.getDatbaseName() + " " + stat.getDatabaseType() + ",");
			}
		}
		
		statement.append("PRIMARY KEY (ID));");
		
		return statement.toString();
	}
	
	private static String prepareAddCarStatement(CarClasses carClass) {
		StringBuilder statement = new StringBuilder("INSERT INTO " + carClass.getDatabaseName() + " (");
		
		String prefix = "";
		for(CarStats stat : CarStats.values()) {
			if(stat != CarStats.ID && stat != CarStats.TOTAL_SCORE) {
				statement.append(prefix + stat.getDatbaseName());
				prefix = ",";
			}
		}
		
		statement.append(") VALUES (");
		
		prefix = "";
		for(CarStats stat : CarStats.values()) {
			if(stat != CarStats.ID && stat != CarStats.TOTAL_SCORE) {
				statement.append(prefix + "?");
				prefix = ",";
			}
		}
		
		statement.append(")");
		
		return statement.toString();
	}
	
	public static void addCarToDatabase(CarClasses carClass, Car car) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(prepareAddCarStatement(carClass));
			
			int counter = 1;
			for(CarStats stat : CarStats.values()) {
				if(stat != CarStats.ID && stat != CarStats.TOTAL_SCORE) {
					if(stat.getDatabaseType().compareTo("int") == 0) {
						stmt.setInt(counter++, (int) car.getStat(stat));
					} else if(stat.getDatabaseType().compareTo("double") == 0) {
						stmt.setDouble(counter++, (double) car.getStat(stat));
					} else if(stat.getDatabaseType().compareTo("text") == 0) {
						stmt.setString(counter++, (String) car.getStat(stat));
					}
				}
			}
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			Logger.addErrorLog("CarDatabase add car: " + e.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				Logger.addErrorLog("CarDatabase removing statement: " + e.getMessage());
			}
		}
	}
	
	public static List<Car> getCarsFromDatabase(CarClasses carClass) {
		PreparedStatement stmt = null;
		List<Car> cars = new ArrayList<>();
		try {
			stmt = conn.prepareStatement("SELECT * FROM " + carClass.getDatabaseName());
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				int id = carClass.getValue()*1000 + rs.getInt(CarStats.ID.getDatbaseName());
				String make = rs.getString(CarStats.MAKE.getDatbaseName());
				String name = rs.getString(CarStats.NAME.getDatbaseName());
				double maxSpeed = rs.getDouble(CarStats.MAXSPEED.getDatbaseName());
				double acceleration = rs.getDouble(CarStats.ACCELERATION.getDatbaseName());
				double braking = rs.getDouble(CarStats.BRAKING.getDatbaseName());
				double cornering = rs.getDouble(CarStats.CORNERING.getDatbaseName());
				double stability = rs.getDouble(CarStats.STABILITY.getDatbaseName());
				int bhp = rs.getInt(CarStats.POWER.getDatbaseName());
				int weight = rs.getInt(CarStats.WEIGHT.getDatbaseName());
				int price = rs.getInt(CarStats.PRICE.getDatbaseName());
				
				cars.add(new Car(id, make, name, maxSpeed, acceleration, braking, cornering, stability, bhp, weight, price, false));
			}
		} catch (SQLException e) {
			Logger.addErrorLog("CarDatabase fetching car (" + carClass.getDatabaseName() + "): " + e.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				Logger.addErrorLog("CarDatabase removing statement: " + e.getMessage());
			}
		}
		
		return cars;
	}
}
