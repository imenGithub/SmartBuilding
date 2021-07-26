package fr.laas.sb.messages;

import fr.laas.sb.entity.thermometer.ThermometerListener;

public class Alive extends Message {

	@Override
	public void handle(String value) {
		ThermometerListener.getServerConnection().sendToBuildingManager("ImAliveTh");

	}

}
