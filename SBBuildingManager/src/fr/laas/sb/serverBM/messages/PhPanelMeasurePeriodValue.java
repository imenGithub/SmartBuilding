package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class PhPanelMeasurePeriodValue extends Message {

	@Override
	public void handle(String client,String value, String msgToSend) {
		long measurementPeriod = Long.parseLong(value);
		  SmartBuildingListener.getClientConnection().getPhotovoltaicPanel(client).setMeasurementPeriod(measurementPeriod);
	}

}
