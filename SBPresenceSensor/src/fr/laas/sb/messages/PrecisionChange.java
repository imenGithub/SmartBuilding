package fr.laas.sb.messages;

import fr.laas.sb.entity.presenceSensor.PresenceSensorManager;


public class PrecisionChange extends Message {

	@Override
	public void handle(String value) {
		 int precision  = Integer.parseInt(value);
  	     PresenceSensorManager.getPresenceSensor().setPrecision(precision);
	}

}
