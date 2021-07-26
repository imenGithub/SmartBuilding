package fr.laas.sb.messages;

import fr.laas.sb.entity.presenceSensor.PresenceSensorManager;

public class MeasureChange extends Message {

	@Override
	public void handle(String value) {
		 int measure  = Integer.parseInt(value);
  	     PresenceSensorManager.getPresenceSensor().setMeasurementPeriod(measure);
	}

}
