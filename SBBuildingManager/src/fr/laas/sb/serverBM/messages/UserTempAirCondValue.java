package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class UserTempAirCondValue extends Message {

	


	@Override
	public void handle(String client,String value, String msgEnv) {
		ComponentEnvList.put(client,new Boolean[] {true,false});
		double tempVal  = Double.parseDouble(value);
		SmartBuildingListener.getClientConnection().getAirConditioner(client).setTempValue(tempVal);
		 SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").getAcTemp().put(client, tempVal);
		SmartBuildingListener.getClientConnection().sendToEntity(msgEnv, SmartBuildingListener.getChatClientDeviceSession(),client);		
	}

}
