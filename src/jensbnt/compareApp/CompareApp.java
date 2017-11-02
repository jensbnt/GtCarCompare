package jensbnt.compareApp;

import jensbnt.gui.CarFrame;

public class CompareApp {
	
	public static void main(String[] args) {
		if (!setupGarage()) {
			return;
		}
		
		new CarFrame();
	}
	
	@SuppressWarnings("unused")
	private static Boolean setupGarage() {
		try {
			Garage garage = new Garage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
}
