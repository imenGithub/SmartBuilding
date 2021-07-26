package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class DWResponseMax extends Message {

	@Override
	public void handle(String client, String value, String msgToSend) {
		ComponentEnvList.put(client,new Boolean[] {true,true});
		SmartBuildingListener.getClientConnection().getDW(client).setMax_end_hour(value);
		  SmartBuildingListener.getClientConnection().sendToEntity(msgToSend+"From"+client, SmartBuildingListener.getChatClientUserSession(),"UserAgent");		
	
	}

}
