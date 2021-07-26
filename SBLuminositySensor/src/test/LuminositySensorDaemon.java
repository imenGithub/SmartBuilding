package test;
import fr.laas.sb.entity.luminositySensor.LuminositySensorListener;
import fr.laas.sb.entity.luminositySensor.LuminositySensorManager;
//PFE
//import fr.laas.sb.entity.thermometer.ThermometerListener;

/**
 * @author Imen
 * --- The main class of the Thermometer application ---
 */
public class LuminositySensorDaemon {
	private static LuminositySensorListener thermometerListener;
	private static LuminositySensorManager thermometermanager;
	
public static void main(String[] args) {
	try{
		thermometerListener = new LuminositySensorListener(Integer.parseInt(args[0]));
	}catch (Exception e){
		thermometerListener = new LuminositySensorListener(-1);
	}

		thermometermanager = new LuminositySensorManager();
		
		//Listen to values of the thermometer
		thermometerListener.start();
		
		//listen to the building manager requests
		thermometermanager.start();
	}

}