package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class ConsPlugRequest extends Message {

	@Override
	public void handle(String client, String value, String msgToSend) {
		SmartBuildingListener.getClientConnection().sendToEntity("ResponseConsFrom"+client+SmartBuildingListener.getClientConnection().getPlugConsListValues().get(client), SmartBuildingListener.getChatClientUserSession(),"UserAgent");

		
		
	}

}
