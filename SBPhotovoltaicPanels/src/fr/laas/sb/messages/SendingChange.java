package fr.laas.sb.messages;

import fr.laas.sb.entity.fr.laas.sb.entity.photovoltaicPanel.PhotovoltaicPanelManager;


public class SendingChange extends Message {

	@Override
	public void handle(String value) {
		 int sending  = Integer.parseInt(value);
		 PhotovoltaicPanelManager.getPhotovoltaicPanel().setSendingPeriod(sending);
	}

}
