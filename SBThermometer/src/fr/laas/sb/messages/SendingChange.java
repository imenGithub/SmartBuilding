package fr.laas.sb.messages;

import fr.laas.sb.entity.thermometer.ThermometerManager;

public class SendingChange extends Message {

	@Override
	public void handle(String value) {
		 int sending  = Integer.parseInt(value);
  	     ThermometerManager.getThermometer().setSendingPeriod(sending);
	}

}
