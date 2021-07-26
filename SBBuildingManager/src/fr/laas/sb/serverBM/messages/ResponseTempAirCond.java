package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class ResponseTempAirCond extends Message {

	String param=null;
public ResponseTempAirCond (String p)
{this.param=p;}

	@Override
	public void handle(String client,String value, String msgEnv) {
		 double tempValue  = Double.parseDouble(value);

		if(param!=null)
			 SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").getAcTemp().put(client, tempValue);

		ComponentEnvList.put(client,new Boolean[] {true,true});

		 SmartBuildingListener.getClientConnection().getAirConditioner(client).setTempValue(tempValue);
		 SmartBuildingListener.getClientConnection().sendToEntity(msgEnv+"From"+client+"is"+tempValue, SmartBuildingListener.getChatClientUserSession(),"UserAgent");		
	}

}
