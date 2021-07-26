package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;


public class ResponsePowerCons extends Message {

	

	@Override
	public void handle(String client,String value) {
		  double powerCons  = Double.parseDouble(value);
		  UserAgent.getPowerSmartMeter("PowerSmartMeter").setPowerCons(powerCons);
//		  System.out.println("-----> " + value + " ConsumedPower (double)" );
		  
	}

}
