package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class ResponseInten extends Message {
String param=null;


	public ResponseInten(String p) {
this.param=p;	}

	@Override
	public void handle(String client,String value, String msgEnv) {
		ComponentEnvList.put(client,new Boolean[] {true,true});
		double intensity  = Double.parseDouble(value);
		if(param!=null)
			 SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").getLampIntensity().put(client, intensity);		 

		SmartBuildingListener.getClientConnection().getLamp(client).setIntensity(intensity);
		  SmartBuildingListener.getClientConnection().sendToEntity(msgEnv+"From"+client+"is"+intensity, SmartBuildingListener.getChatClientUserSession(),"UserAgent");		
	
	}

}
