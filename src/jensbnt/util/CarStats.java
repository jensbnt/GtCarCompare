package jensbnt.util;

public enum CarStats {
	ID(0, "ID"),
	MAKE(1, "Make"),
	NAME(2, "Name"),
	MAXSPEED(3, "Max. Speed"),
	ACCELERATION(4, "Acceleration"),
	BRAKING(5, "Braking"),
	CORNERING(6, "Cornering"),
	STABILITY(7, "Stability"),
	BHP(8, "Bhp"),
	WEIGHT(9, "Weight"),
	PRICE(10, "Price");

	private final int value;
	private final String name;
	
	CarStats(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	public int getValue() {
		return value;
	}
	
	public static CarStats value(String name) {
		for(CarStats stat : CarStats.values()) {
			if(stat.toString().equals(name)) {
				return stat;
			}
		}
		
		return null;
	}
}
