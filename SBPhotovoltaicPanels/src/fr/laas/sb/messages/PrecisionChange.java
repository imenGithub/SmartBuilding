package fr.laas.sb.messages;

import fr.laas.sb.entity.fr.laas.sb.entity.photovoltaicPanel.PhotovoltaicPanelManager;



public class PrecisionChange extends Message {

	@Override
	public void handle(String value) {
		 int precision  = Integer.parseInt(value);
		 PhotovoltaicPanelManager.getPhotovoltaicPanel().setPrecision(precision);
	}

}
