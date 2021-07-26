package fr.laas.sb.messages;

import fr.laas.sb.entity.fr.laas.sb.entity.photovoltaicPanel.PhotovoltaicPanelManager;


public class MeasureChange extends Message {

	@Override
	public void handle(String value) {
		 int measure  = Integer.parseInt(value);
		 PhotovoltaicPanelManager.getPhotovoltaicPanel().setMeasurementPeriod(measure);
	}

}
