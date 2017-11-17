package jensbnt.compareApp;

import jensbnt.gui.CarFrame;

public class CompareApp {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Garage garage = null;
		try {
			garage = new Garage();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		new CarFrame();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            System.out.println("-- SAVE OWNED CARS --");
	        }
	    }, "Shutdown-thread"));
	}
}
