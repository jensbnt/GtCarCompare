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

public class OnlineDatabase implements CarDatabase {

	protected String dbms;
	protected String serverName;
	protected String userName;
	protected String password;
	protected String dbName;
	
	protected Connection conn = null;
	
	public OnlineDatabase(String dbms, String serverName, String userName, String password, String dbName) {
		this.dbms = dbms;
		this.serverName = serverName;
		this.userName = userName;
		this.password = password;
		this.dbName = dbName;
	}
	
	/* Database */
	
	@Override
	public List<Car> getCars(CarClasses carClass) throws CarLoadException {
		makeConnection();
		
		PreparedStatement stmt = null;
		List<Car> cars = new ArrayList<>();
		try {
			stmt = conn.prepareStatement("SELECT * FROM " + carClass.getDatabaseName());
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				int id = carClass.getValue()*1000 + rs.getInt(CarStats.ID.getDatabaseName());
				String make = rs.getString(CarStats.MAKE.getDatabaseName());
				String name = rs.getString(CarStats.NAME.getDatabaseName());
				double maxSpeed = rs.getDouble(CarStats.MAXSPEED.getDatabaseName());
				double acceleration = rs.getDouble(CarStats.ACCELERATION.getDatabaseName());
				double braking = rs.getDouble(CarStats.BRAKING.getDatabaseName());
				double cornering = rs.getDouble(CarStats.CORNERING.getDatabaseName());
				double stability = rs.getDouble(CarStats.STABILITY.getDatabaseName());
				int bhp = rs.getInt(CarStats.POWER.getDatabaseName());
				int weight = rs.getInt(CarStats.WEIGHT.getDatabaseName());
				int price = rs.getInt(CarStats.PRICE.getDatabaseName());
				
				cars.add(new Car(id, make, name, maxSpeed, acceleration, braking, cornering, stability, bhp, weight, price, 0));
			}
		} catch (SQLException e) {
			throw new CarLoadException("CarDatabase fetching car (" + carClass.getDatabaseName() + "): " + e.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new CarLoadException("CarDatabase removing statement: " + e.getMessage());
			}
		}
		
		brakeConnection();
		
		return cars;
	}
	
	/* Connection */
	
	private void makeConnection() throws CarLoadException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:" + dbms + "://" + serverName + "/" + dbName, userName, password);
		} catch (ClassNotFoundException e) {
			throw new CarLoadException("CarDatabase make connection: " + e.getMessage());
		} catch (SQLException e) {
			throw new CarLoadException("CarDatabase make connection: " + e.getMessage());
		}
	}
	
	private void brakeConnection() throws CarLoadException {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new CarLoadException("CarDatabase brake connection: " + e.getMessage());
		}
	}
}
