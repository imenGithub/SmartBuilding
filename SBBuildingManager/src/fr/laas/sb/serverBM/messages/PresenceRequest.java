package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class PresenceRequest extends Message {

	@Override
	public void handle(String client, String value, String msgToSend) {
		Double val=	SmartBuildingListener.getClientConnection().getPresenceSensor(client).getpresenceValue();
		SmartBuildingListener.getClientConnection().sendToEntity("presenceResponseFrom"+client+val, SmartBuildingListener.getChatClientUserSession(),"UserAgent");

	}

}
