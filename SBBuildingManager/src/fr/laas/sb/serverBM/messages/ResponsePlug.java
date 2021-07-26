package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class ResponsePlug extends Message {
	private boolean receiv=false;

	@Override
	public void handle(String client, String value, String msgToSend) {
		setReceiv(true);
		SmartBuildingListener.getClientConnection().getPlugConsListValues().put(client, Double.parseDouble(value));			
		SmartBuildingListener.getClientConnection().sendToEntity("ResponseConsFrom"+client+"is"+SmartBuildingListener.getClientConnection().getPlugConsListValues().get(client), SmartBuildingListener.getChatClientUserSession(),"UserAgent");

		// modify the biggest consumer if its consumption is the biggest
	if(client.contains("Lamp")||client.contains("AirConditioner"))
{ 
	if(Double.parseDouble(value)>SmartBuildingListener.getClientConnection().getGreatCons())
{// System.out.println("Big consumer received from "+client);
	SmartBuildingListener.getClientConnection().setGreatCons(Double.parseDouble(value));
	SmartBuildingListener.getClientConnection().setPlugBigConsumer(client);
}
}
	}

			public boolean isReceiv() {
				return receiv;
			}

			public void setReceiv(boolean receiv) {
				this.receiv = receiv;
			}


			
			

		}