package test;


import fr.laas.sb.entity.powerSmartMeter.PowerSmartMeterListener;
import fr.laas.sb.entity.powerSmartMeter.PowerSmartMeterManager;

/**
 * @author Imen
 * --- The main class of the Power smart meter application ---
 */
public class PowerSmartMeterDaemon {
	static PowerSmartMeterListener powerSmartMeterListener ;
	static PowerSmartMeterManager powerSmartMeterManager ;
public static void main(String[] args) {
	
	
	powerSmartMeterListener = new PowerSmartMeterListener();//here the power smart meter will be connected
	powerSmartMeterManager = new PowerSmartMeterManager(); 
	
	//Listen to values of the power smart meter	
	powerSmartMeterListener.start();
	
	//listen to the building manager requests
	powerSmartMeterManager.start();
	
	
		
	}

}
