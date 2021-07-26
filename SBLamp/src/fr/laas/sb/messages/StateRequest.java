package fr.laas.sb.messages;

import fr.laas.sb.entity.lamp.LampListener;
import fr.laas.sb.entity.lamp.LampManager;

public class StateRequest extends Message {

	@Override
	public void handle(String value) {
		String state = LampManager.getLamp().getState();
		LampListener.getServerConnection().sendToBuildingManager("ResponseState"+state);
	}

}
