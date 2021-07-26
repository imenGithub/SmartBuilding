package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class PhPanelProdOrSendPeriod extends Message {

	@Override
	public void handle(String client,String value, String msgEnv) {
			 try //if the message is a long => the period of sending to the building manager
	   	  {
				 long sendingPeriod  = Long.parseLong(value);
				 SmartBuildingListener.getClientConnection().getPhotovoltaicPanel(client).setSendingPeriod(sendingPeriod);
	   		// System.out.println(msg + "-----> sending period (long)" );
	   	  } 
	   	  catch (NumberFormatException nfe) 
	   	  {
	   		   //if the message is a double => powerProd value 
		    	
		    		    double prod  = Double.parseDouble(value);
		    		    SmartBuildingListener.getClientConnection().getPhotovoltaicPanel(client).setpowerProd(prod);
		    		    //System.out.println("-----> " + msg + " temperature value (double)" );
		    	  
	   		  
		}

}

}
