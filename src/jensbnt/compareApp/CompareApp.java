package jensbnt.compareApp;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jensbnt.gui.CarFrame;
import jensbnt.util.Logger;

public class CompareApp {
	
	public static void main(String[] args) {
		/* Make logger */
		setupLogger();
		
		/* Make garage */
		setupGarage();
		
		/* Make GUI */
		setupGUI();
		
		/* Execute code on exit */
		setupShutdownHook();
	}
	
	private static void setupLogger() {
		@SuppressWarnings("unused")
		Logger logger = new Logger();
	}
	
	private static void setupGarage() {
		@SuppressWarnings("unused")
		Garage garage = null;
		
		Logger.addLog("Loading garage");
		garage = new Garage();
		Logger.addLog("Loading garage: done");
	}
	
	private static void setupGUI() {
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
	}
	
	private static void setupShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	    		Logger.addLog("Saving Owned Cars");
	    		Garage.saveOwnedCars();
	    		Logger.addLog("Saving Owned Cars: done");
	    		
	    		Logger.addLog("Breaking connection with database");
	    		//AdminDatabase.brakeConnection();
	    		Logger.addLog("Breaking connection with database: done");
	    		
	    		Logger.addLog("Saving Logs");
	            Logger.saveLogs();
	        }
	    }, "Shutdown-thread"));
	}
}
