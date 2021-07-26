package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class ResponsepowerProdPhotovoltaicPanel extends Message {

private boolean receiv=false;
	

	@Override
	public void handle(String client,String value, String msgEnv) {
		receiv=true;
		double prod  = Double.parseDouble(value);
		SmartBuildingListener.getClientConnection().getPhotovoltaicPanel(client).setpowerProd(prod);
		SmartBuildingListener.getClientConnection().sendToEntity(msgEnv, SmartBuildingListener.getChatClientUserSession(),"UserAgent");
	}


	public boolean isReceiv() {
		return receiv;
	}


	public void setReceiv(boolean receiv) {
		this.receiv = receiv;
	}
	


}
