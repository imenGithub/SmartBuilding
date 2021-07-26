package fr.laas.sb.messages;

import fr.laas.sb.entity.luminositySensor.LuminositySensorManager;

public class MeasureChange extends Message {

	@Override
	public void handle(String value) {
		 int measure  = Integer.parseInt(value);
  	     LuminositySensorManager.getlumSensor().setMeasurementPeriod(measure);
	}

}
