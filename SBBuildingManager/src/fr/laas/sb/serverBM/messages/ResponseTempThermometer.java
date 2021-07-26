package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class ResponseTempThermometer extends Message {
private boolean receiv=false;
	

	@Override
	public void handle(String client,String value, String msgEnv) {
		receiv=true;
		double tempValue  = Double.parseDouble(value);
		SmartBuildingListener.getClientConnection().getThermometer(client).setTempValue(tempValue);
		SmartBuildingListener.getClientConnection().sendToEntity(msgEnv, SmartBuildingListener.getChatClientUserSession(),"UserAgent");
		  //System.out.println("-----> " + tempValueString + " tempValue measured by the thermometer (double)" );		
	}


	public boolean isReceiv() {
		return receiv;
	}


	public void setReceiv(boolean receiv) {
		this.receiv = receiv;
	}
	

}
