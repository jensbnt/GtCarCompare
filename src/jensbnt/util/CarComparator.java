package jensbnt.util;

import java.util.Comparator;

import jensbnt.compareApp.Car;

public class CarComparator {

	public static final Comparator<Car> byId = (c1, c2) -> Double.compare(c1.getId(), c2.getId());
	public static final Comparator<Car> byMaxSpeed = (c1, c2) -> Double.compare(c2.getMaxSpeed(), c1.getMaxSpeed());
	public static final Comparator<Car> byAcceleration = (c1, c2) -> Double.compare(c2.getAcceleration(), c1.getAcceleration());
	public static final Comparator<Car> byBraking = (c1, c2) -> Double.compare(c2.getBraking(), c1.getBraking());
	public static final Comparator<Car> byCornering = (c1, c2) -> Double.compare(c2.getCornering(), c1.getCornering());
	public static final Comparator<Car> byStability = (c1, c2) -> Double.compare(c2.getStability(), c1.getStability());
	public static final Comparator<Car> byTotalScore = (c1, c2) -> Double.compare(c2.getTotalScore(), c1.getTotalScore());
	public static final Comparator<Car> byMake = (c1, c2) -> c1.getMake().compareTo(c2.getMake());
	public static final Comparator<Car> byName = (c1, c2) -> c1.getName().compareTo(c2.getName());
	public static final Comparator<Car> byBhp = (c1, c2) -> Integer.compare(c2.getBhp(), c1.getBhp());
	public static final Comparator<Car> byWeight = (c1, c2) -> Integer.compare(c1.getWeight(), c2.getWeight());
	public static final Comparator<Car> byPrice = (c1, c2) -> Integer.compare(c2.getPrice(), c1.getPrice());
	public static final Comparator<Car> byOwned = (c1, c2) -> Boolean.compare(c2.getOwned(), c1.getOwned());

	public static Comparator<Car> getComparatorByStat(CarStats stat) {
		switch (stat) {
		case MAXSPEED:
			return byMaxSpeed;
		case ACCELERATION:
			return byAcceleration;
		case BRAKING:
			return byBraking;
		case CORNERING:
			return byCornering;
		case STABILITY:
			return byStability;
		case TOTAL_SCORE:
			return byTotalScore;
		case MAKE:
			return byMake;
		case NAME:
			return byName;
		case POWER:
			return byBhp;
		case WEIGHT:
			return byWeight;
		case PRICE:
			return byPrice;
		case ID:
			return byId;
		default:
			return byId;
		}
	}
}
