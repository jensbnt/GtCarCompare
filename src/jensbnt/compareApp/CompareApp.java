package jensbnt.compareApp;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jensbnt.gui.CarFrame;

public class CompareApp {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
	            Garage.saveOwnedCars();
	        }
	    }, "Shutdown-thread"));
	}
}
