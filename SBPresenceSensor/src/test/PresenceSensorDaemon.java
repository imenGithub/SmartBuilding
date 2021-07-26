package test;
import fr.laas.sb.entity.presenceSensor.PresenceSensorListener;
import fr.laas.sb.entity.presenceSensor.PresenceSensorManager;
//PFE
//import fr.laas.sb.entity.thermometer.ThermometerListener;

/**
 * @author Imen
 */
public class PresenceSensorDaemon {
	private static PresenceSensorListener thermometerListener;
	private static PresenceSensorManager thermometermanager;
	
public static void main(String[] args) {
try{		thermometerListener = new PresenceSensorListener(Integer.parseInt(args[0]));
}catch (Exception e){
		thermometerListener = new PresenceSensorListener(-1);
}
		thermometermanager = new PresenceSensorManager();
		
		//Listen to values of the thermometer
		thermometerListener.start();
		//listen to the building manager requests
		thermometermanager.start();
	}

}