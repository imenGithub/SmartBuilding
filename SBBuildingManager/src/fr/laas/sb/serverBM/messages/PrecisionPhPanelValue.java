package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class PrecisionPhPanelValue extends Message {

	@Override
	public void handle(String client,String value, String msgToSend) {
		 int precision  = Integer.parseInt(value);
		  SmartBuildingListener.getClientConnection().getPhotovoltaicPanel(client).setPrecision(precision);
	}

}
