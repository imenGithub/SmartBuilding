package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class ResponsePowerCons extends Message {
private boolean receiv=false;

	@Override
	public void handle(String client,String value, String msgEnv) {
		setReceiv(true);
double powerCons  = Double.parseDouble(value);
		SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").setPowerCons(powerCons);
		SmartBuildingListener.getClientConnection().sendToEntity(msgEnv, SmartBuildingListener.getChatClientUserSession(),"UserAgent");		
	}

	public boolean isReceiv() {
		return receiv;
	}

	public void setReceiv(boolean receiv) {
		this.receiv = receiv;
	}


	
	

}
