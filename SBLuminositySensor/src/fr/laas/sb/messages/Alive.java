package fr.laas.sb.messages;

import fr.laas.sb.entity.luminositySensor.LuminositySensorListener;

public class Alive extends Message {

	@Override
	public void handle(String value) {
		LuminositySensorListener.getServerConnection().sendToBuildingManager("ImAliveLS");

	}

}
