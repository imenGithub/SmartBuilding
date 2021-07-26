package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class DWResponseState extends Message {
String state;

	

	public DWResponseState(String string) {
state=string;}





	@Override
	public void handle(String client,String value,String msgEnv) {
		ComponentEnvList.put(client,new Boolean[] {true,true});
		SmartBuildingListener.getClientConnection().getDW(client).setState(state);
		SmartBuildingListener.getClientConnection().sendToEntity(msgEnv+"From"+client, SmartBuildingListener.getChatClientUserSession(),"UserAgent");		
	}

}
