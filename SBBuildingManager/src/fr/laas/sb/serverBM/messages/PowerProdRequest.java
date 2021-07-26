package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class PowerProdRequest extends Message {

	@Override
	public void handle(String client,String value, String msgEnv) {
		SmartBuildingListener.getClientConnection().sendToEntity(msgEnv, SmartBuildingListener.getChatClientDeviceSession(),client);

	}

}
