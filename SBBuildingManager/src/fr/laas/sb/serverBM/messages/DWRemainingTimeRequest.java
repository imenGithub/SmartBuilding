package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class DWRemainingTimeRequest extends Message {

	@Override
	public void handle(String client, String value, String msgToSend) {
		ComponentEnvList.put(client,new Boolean[] {true,false});
		
		
		SmartBuildingListener.getClientConnection().sendToEntity(msgToSend, SmartBuildingListener.getChatClientDeviceSession(),client);
		
	}

}
