package jensbnt.compareApp;

public class Car {
	
	private int id;
	private String make;
	private String name;
	private double maxSpeed;
	private double acceleration;
	private double braking;
	private double cornering;
	private double stability;
	private int bhp;
	private int weight;
	private int price;
	private Boolean owned;
	
	Car(int id, String make, String name, double maxSpeed, double acceleration, double braking, double cornering, double stability, int bhp, int weight, int price, Boolean owned) {
		this.id = id;
		this.make = make;
		this.name = name;
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.braking = braking;
		this.cornering = cornering;
		this.stability = stability;
		this.bhp = bhp;
		this.weight = weight;
		this.price = price;
		this.owned = owned;
	}
	
	public int getId() {
		return id;
	}
	
	public String getMake() {
		return make;
	}
	
	public String getName() {
		return name;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public double getAcceleration() {
		return acceleration;
	}
	
	public double getBraking() {
		return braking;
	}

	public double getCornering() {
		return cornering;
	}

	public double getStability() {
		return stability;
	}
	
	public int getBhp() {
		return bhp;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getPrice(){
		return price;
	}
	
	public Boolean getOwned() {
		return owned;
	}
	
	public void toggleOwned() {
		if (owned) {
			owned = false;
		} else {
			owned = true;
		}
	}
	
	/*public String getAttributeByName(String n) {
		switch (n) {
		case "MaxSpeed":
			return maxSpeed + "";
		case "Acceleration":
			return acceleration + "";
		case "Braking":
			return braking + "";
		case "Cornering":
			return cornering + "";
		case "Stability":
			return stability + "";
		case "Make":
			return make + "";
		case "Name":
			return name + "";
		case "BHP":
			return bhp + "";
		case "Weight":
			return weight + "";
		case "Price":
			return price + "";
		default:
			return "";
		}
	}*/
	
	@Override
	public String toString() {
		return String.format("%02d | %.2f - %.2f - %.2f - %.2f - %.2f | %d BHP - %d kg - Cr. %d | %s %s", id, maxSpeed, acceleration, braking, cornering, stability, bhp, weight, price, make, name);
		//return id + "\t" + maxSpeed + " \t " + acceleration + " \t " + braking + " \t " + cornering + " \t " + stability + " \t " + bhp + "\t" + weight + " kg\t  Cr. " + price + "\t" + owned + "\t" + make + " - " + name;
	}

}
