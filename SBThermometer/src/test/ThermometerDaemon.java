package test;
import fr.laas.sb.entity.thermometer.ThermometerListener;
//PFE
//import fr.laas.sb.entity.thermometer.ThermometerListener;
import fr.laas.sb.entity.thermometer.ThermometerManager;

/**
 * @author Imen
 * --- The main class of the Thermometer application ---
 */
public class ThermometerDaemon {
	private static ThermometerListener thermometerListener;
	private static ThermometerManager thermometermanager;
	
public static void main(String[] args) {
		
		thermometerListener = new ThermometerListener();
		thermometermanager = new ThermometerManager();
		
		//Listen to values of the thermometer
		thermometerListener.start();
		
		//listen to the building manager requests
		thermometermanager.start();
	}

}