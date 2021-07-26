package test;

import fr.laas.sb.entity.airConditioner.AirConditionerListener;
import fr.laas.sb.entity.airConditioner.AirConditionerManager;

/**
 * @author Imen
 * --- The main class of the Air conditioner application ---
 */
public class AirConditionerDaemon {
	static AirConditionerListener airConditionerListener ;
	static AirConditionerManager airConditionerManager;
	
	public static void main(String[] args) {	
	try{
		airConditionerListener = new AirConditionerListener(Integer.parseInt(args[0]));
	}
catch (Exception e)
{airConditionerListener = new AirConditionerListener(-1);}

		
		airConditionerManager = new AirConditionerManager();
		
		//Listen values from the air conditioner
		airConditionerListener.start();
		
		//listen to the building manager requests
		airConditionerManager.start();
	}
}
