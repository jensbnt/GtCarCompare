package jensbnt.compareApp;

import jensbnt.util.CarStats;

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
	private int owned;
	
	public Car(int id, String make, String name, double maxSpeed, double acceleration, double braking, double cornering, double stability, int bhp, int weight, int price, int owned) {
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
	
	public double getTotalScore() {
		return maxSpeed + acceleration + braking + cornering + stability;
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
	
	public int getOwned() {
		return owned;
	}
	
	public void setOwned(int value) {
		owned = value;
	}
	
	public Object getStat(CarStats stat) {
		switch (stat) {
		case MAXSPEED:
			return maxSpeed;
		case ACCELERATION:
			return acceleration;
		case BRAKING:
			return braking;
		case CORNERING:
			return cornering;
		case STABILITY:
			return stability;
		case TOTAL_SCORE:
			return getTotalScore();
		case MAKE:
			return make;
		case NAME:
			return name;
		case POWER:
			return bhp;
		case WEIGHT:
			return weight;
		case PRICE:
			return price;
		case ID:
			return id;
		case OWNED:
			return owned;
		default:
			return null;
		}
	}
	
	@Override
	public String toString() {
		return make + " " + name + "(ID:" + id + ")";
	}

}
