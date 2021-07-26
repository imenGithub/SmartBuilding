package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class ResponseState extends Message {
String state;

	

	public ResponseState(String string) {
state=string;}





	@Override
	public void handle(String client,String value,String msgEnv) {
		ComponentEnvList.put(client,new Boolean[] {true,true});
		SmartBuildingListener.getClientConnection().getLamp(client).setState(state);
		SmartBuildingListener.getClientConnection().sendToEntity(msgEnv+"From"+client, SmartBuildingListener.getChatClientUserSession(),"UserAgent");		
	}

}
