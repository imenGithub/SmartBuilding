package fr.laas.sb.messages;

import fr.laas.sb.entity.presenceSensor.PresenceSensorManager;

public class SendingChange extends Message {

	@Override
	public void handle(String value) {
		 int sending  = Integer.parseInt(value);
  	     PresenceSensorManager.getPresenceSensor().setSendingPeriod(sending);
	}

}
