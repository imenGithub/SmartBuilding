package fr.laas.sb.messages;

import fr.laas.sb.entity.powerSmartMeter.PowerSmartMeterManager;



public class PrecisionChange extends Message {

	@Override
	public void handle(String value) {
		 int precision  = Integer.parseInt(value);
	   		PowerSmartMeterManager.getPowerSmartMeter().setPrecision(precision);

	   		//  System.out.println("-----> " + precision + " precision (int)" );
	}

}
