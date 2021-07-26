package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class PrecisionThValue extends Message {

	@Override
	public void handle(String client,String value, String msgEnv) {

		  int precision  = Integer.parseInt(value);
		  SmartBuildingListener.getClientConnection().getThermometer(client).setPrecision(precision);
	}

}
