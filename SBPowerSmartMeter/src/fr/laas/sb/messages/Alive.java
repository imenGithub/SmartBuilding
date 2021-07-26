package fr.laas.sb.messages;

import fr.laas.sb.entity.powerSmartMeter.PowerSmartMeterListener;

public class Alive extends Message {

	@Override
	public void handle(String value) {
		  PowerSmartMeterListener.getServerConnection().sendToBuildingManager("ImAlivePSM");

	}

}
