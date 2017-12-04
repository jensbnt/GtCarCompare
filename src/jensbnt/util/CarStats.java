package jensbnt.util;

public enum CarStats {
	ID(0, "ID", "ID", "int"),
	MAKE(1, "Make", "MAKE", "text"),
	NAME(2, "Name", "NAME", "text"),
	MAXSPEED(3, "Max. Speed", "MAX_SPEED", "double"),
	ACCELERATION(4, "Acceleration", "ACCELERATION", "double"),
	BRAKING(5, "Braking", "BRAKING", "double"),
	CORNERING(6, "Cornering", "CORNERING", "double"),
	STABILITY(7, "Stability", "STABILITY", "double"),
	TOTAL_SCORE(8, "Total Score", "TOTAL_SCORE", "double"),
	POWER(9, "Power", "POWER", "int"),
	WEIGHT(10, "Weight", "WEIGHT", "int"),
	PRICE(11, "Price", "PRICE", "int");

	private final int value;
	private final String name;
	private final String databaseName;
	private final String databaseType;
	
	CarStats(int value, String name, String databaseName, String databaseType) {
		this.value = value;
		this.name = name;
		this.databaseName = databaseName;
		this.databaseType = databaseType;
	}
	
	public String toString() {
		return name;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getDatabaseName() {
		return databaseName;
	}
	
	public String getDatabaseType() {
		return databaseType;
	}
	
	public static CarStats getName(int value) {
		for(CarStats stat : values()) {
			if (stat.getValue() == value) {
				return stat;
			}
		}
		
		return null;
	}
}
