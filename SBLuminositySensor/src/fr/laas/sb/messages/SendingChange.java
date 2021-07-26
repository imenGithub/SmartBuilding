package fr.laas.sb.messages;

import fr.laas.sb.entity.luminositySensor.LuminositySensorManager;

public class SendingChange extends Message {

	@Override
	public void handle(String value) {
		 int sending  = Integer.parseInt(value);
  	     LuminositySensorManager.getlumSensor().setSendingPeriod(sending);
	}

}
