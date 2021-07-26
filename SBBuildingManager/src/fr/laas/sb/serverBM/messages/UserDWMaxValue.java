package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class UserDWMaxValue extends Message {

	@Override
	public void handle(String client, String value, String msgToSend) {
		ComponentEnvList.put(client,new Boolean[] {true,false});
	
	 SmartBuildingListener.getClientConnection().getDW(client).setMax_end_hour(value);
//	 SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").setMax_end_hourDW(value);
	 SmartBuildingListener.getClientConnection().sendToEntity(msgToSend, SmartBuildingListener.getChatClientDeviceSession(),client);
	}

}
