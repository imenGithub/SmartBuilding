package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class PsmSendPeriod extends Message {

	@Override
	public void handle(String client,String value, String msgEnv) {
		 long sendingPeriod  = Long.parseLong(value);
		 SmartBuildingListener.getClientConnection().getPowerSmartMeter(client).setSendingPeriod(sendingPeriod);
	}

}
