package jensbnt.compareApp;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jensbnt.gui.CarFrame;
import jensbnt.util.Logger;

public class CompareApp {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		/* Make logger */
		Logger logger = new Logger();
		
		/* Make garage */
		Garage garage = null;
		Logger.addLog("Loading garage");
		try {
			garage = new Garage();
		} catch (Exception e) {
			Logger.addErrorLog("Error loading garage: " + e.getMessage());
			return;
		}
		Logger.addLog("Loading garage: done");
		
		/* Make GUI */
		Logger.addLog("Loading GUI");
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			Logger.addErrorLog(e1.getMessage());
		} catch (InstantiationException e1) {
			Logger.addErrorLog(e1.getMessage());
		} catch (IllegalAccessException e1) {
			Logger.addErrorLog(e1.getMessage());
		} catch (UnsupportedLookAndFeelException e1) {
			Logger.addErrorLog(e1.getMessage());
		}
		
		new CarFrame();
		Logger.addLog("Loading GUI: done");
		
		/* Execute code on exit */
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	    		Logger.addLog("Saving Owned Cars");
	    		
	            try {
					Garage.saveOwnedCars();
				} catch (Exception e) {
					Logger.addErrorLog("Error saving owned cars" + e.getMessage());
				}
	            
	    		Logger.addLog("Saving Owned Cars: done");
	    		Logger.addLog("Saving Logs");
	            Logger.saveLogs();
	        }
	    }, "Shutdown-thread"));
	}
}
