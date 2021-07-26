package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class Manual extends Message {
String mode="OFF";
	public Manual(String mode)
	{this.mode=mode;}
	@Override
	public void handle(String client, String value, String msgToSend) {
		SmartBuildingListener.getClientConnection().setManualMode(mode);
	}

}
