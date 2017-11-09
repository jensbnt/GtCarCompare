package jensbnt.compareApp;

import jensbnt.gui.CarFrame;

public class CompareApp {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			Garage garage = new Garage();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		new CarFrame();
	}
}
