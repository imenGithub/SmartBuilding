package fr.laas.sb.messages;

import fr.laas.sb.entity.fr.laas.sb.entity.smartPlug.SmartPlugListener;


public class Alive extends Message {

	@Override
	public void handle(String value) {
		SmartPlugListener.getServerConnection().sendToBuildingManager("ImAliveSmartPlug");

	}

}
