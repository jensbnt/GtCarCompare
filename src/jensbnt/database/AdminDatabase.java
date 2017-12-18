package jensbnt.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jensbnt.compareApp.Car;
import jensbnt.util.CarClasses;
import jensbnt.util.CarStats;
import jensbnt.util.EncryptMD5;
import jensbnt.util.Logger;

public class AdminDatabase extends OnlineDatabase {
	
	private static Boolean adminAccess;
	
	public AdminDatabase(String dbms, String serverName, String userName, String password, String dbName) {
		super(dbName, dbName, dbName, dbName, dbName);
		adminAccess = false;
	}
	
	/* Get access */
	
	public static Boolean hasAccess() {
		return adminAccess;
	}
	
	public void getAcces(String user, String password) {
		adminAccess = EncryptMD5.cryptWithMD5(password).equals(getEncryptedPassword(user));
		
		if (adminAccess) {
			Logger.addLog(user + " logged in as admin");
		} else {
			Logger.addLog(user + " failed to log in as admin");
		}
	}
	
	public void removeAcces() {
		adminAccess = false;
	}
	
	private String getEncryptedPassword(String user) {
		PreparedStatement stmt = null;
		String password = "";
		try {
			stmt = conn.prepareStatement("SELECT Password FROM Admins WHERE User=?");
			stmt.setString(1, user);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				password = rs.getString("Password");
			}
		} catch (SQLException e) {
			Logger.addErrorLog("Admins get password: " + e.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				Logger.addErrorLog("Admins removing statement: " + e.getMessage());
			}
		}
		
		return password;
	}

	/* Database */
	
	public void removeCarDatabase(CarClasses carClass) throws Exception {
		if (adminAccess) {
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement("DROP TABLE " + carClass.getDatabaseName());
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new Exception("CarDatabase remove car class: " + e.getMessage());
			} finally {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new Exception("CarDatabase removing statement: " + e.getMessage());
				}
			}
		} else {
			Logger.addErrorLog("Unauthorized user tried to add car to database");
		}
	}
	
	public void addCarDatabase(CarClasses carClass) throws Exception {
		if (adminAccess) {
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(prepareNewTableStatement(carClass));
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new Exception("CarDatabase add new car class: " + e.getMessage());
			} finally {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new Exception("CarDatabase removing statement: " + e.getMessage());
				}
			}
		} else {
			Logger.addErrorLog("Unauthorized user tried to add car to database");
		}
	}
	
	public void addCarToDatabase(CarClasses carClass, Car car) throws Exception {
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
			throw new Exception("CarDatabase add car: " + e.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new Exception("CarDatabase removing statement: " + e.getMessage());
			}
		}
	}
	
	/* Helper functions */
	
	private static String prepareNewTableStatement(CarClasses carClass) {
		StringBuilder statement = new StringBuilder("CREATE TABLE " + carClass.getDatabaseName() + " (");
		statement.append("ID int NOT NULL AUTO_INCREMENT,");
		
		for(CarStats stat : CarStats.values()) {
			if(stat != CarStats.ID && stat != CarStats.TOTAL_SCORE) {
				statement.append(stat.getDatabaseName() + " " + stat.getDatabaseType() + ",");
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
				statement.append(prefix + stat.getDatabaseName());
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
}
