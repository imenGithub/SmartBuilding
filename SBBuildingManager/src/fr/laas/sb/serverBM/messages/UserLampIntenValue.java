package fr.laas.sb.serverBM.messages;

import java.text.DecimalFormat;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class UserLampIntenValue extends Message {
	

	@Override
	public void handle(String client,String value, String msgEnv) {
		ComponentEnvList.put(client,new Boolean[] {true,false});
			 double intensity  = Double.parseDouble(value);
		 SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").getLampIntensity().put(client, intensity);		 
		  SmartBuildingListener.getClientConnection().sendToEntity(msgEnv, SmartBuildingListener.getChatClientDeviceSession(),client);
		  //System.out.println("-----> " + intensityString + " user lamp intensity (double)" );		
	}

}
