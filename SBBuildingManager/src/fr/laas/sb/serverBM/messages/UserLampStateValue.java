package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class UserLampStateValue extends Message {

	String state;

	public UserLampStateValue(String string) {
state=string;	}

	@Override
	public void handle(String client,String value, String msgEnv) {
		ComponentEnvList.put(client,new Boolean[] {true,false});
		SmartBuildingListener.getClientConnection().sendToEntity(msgEnv, SmartBuildingListener.getChatClientDeviceSession(),client);
		
	}

}
