package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class LuminosityRequest extends Message {

	@Override
	public void handle(String client, String value, String msgToSend) {

	Double val=	SmartBuildingListener.getClientConnection().getLuminositySensor(client).getLumValue();
	SmartBuildingListener.getClientConnection().sendToEntity("lumResponseFrom"+client+val, SmartBuildingListener.getChatClientUserSession(),"UserAgent");

	}

}
