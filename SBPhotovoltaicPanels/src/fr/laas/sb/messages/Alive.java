package fr.laas.sb.messages;

import fr.laas.sb.entity.fr.laas.sb.entity.photovoltaicPanel.PhotovoltaicPanelListener;


public class Alive extends Message {

	@Override
	public void handle(String value) {
		PhotovoltaicPanelListener.getServerConnection().sendToBuildingManager("ImAlivePanel");

	}

}
