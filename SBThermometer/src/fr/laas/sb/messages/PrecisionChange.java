package fr.laas.sb.messages;

import fr.laas.sb.entity.thermometer.ThermometerManager;


public class PrecisionChange extends Message {

	@Override
	public void handle(String value) {
		 int precision  = Integer.parseInt(value);
  	     ThermometerManager.getThermometer().setPrecision(precision);
	}

}
