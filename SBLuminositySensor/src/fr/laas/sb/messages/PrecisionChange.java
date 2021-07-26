package fr.laas.sb.messages;

import fr.laas.sb.entity.luminositySensor.LuminositySensorManager;


public class PrecisionChange extends Message {

	@Override
	public void handle(String value) {
		 int precision  = Integer.parseInt(value);
  	     LuminositySensorManager.getlumSensor().setPrecision(precision);
	}

}
