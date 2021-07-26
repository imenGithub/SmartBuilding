package fr.laas.sb.messages;

import fr.laas.sb.entity.thermometer.ThermometerManager;

public class MeasureChange extends Message {

	@Override
	public void handle(String value) {
		 int measure  = Integer.parseInt(value);
  	     ThermometerManager.getThermometer().setMeasurementPeriod(measure);
	}

}
