package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class DWResponseRemainingTime extends Message {

	@Override
	public void handle(String client, String value, String msgToSend) {
		ComponentEnvList.put(client,new Boolean[] {true,true});
		SmartBuildingListener.getClientConnection().getDW(client).setRemaining_Time(value);
		  SmartBuildingListener.getClientConnection().sendToEntity(msgToSend+"From"+client, SmartBuildingListener.getChatClientUserSession(),"UserAgent");		
	
	}

}
