package fr.laas.sb.messages;

import fr.laas.sb.entity.presenceSensor.PresenceSensorListener;

public class Alive extends Message {

	@Override
	public void handle(String value) {
		PresenceSensorListener.getServerConnection().sendToBuildingManager("ImAlivePS");

	}

}
