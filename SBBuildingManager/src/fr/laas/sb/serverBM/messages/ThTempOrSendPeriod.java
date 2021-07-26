package fr.laas.sb.serverBM.messages;
import fr.laas.sb.serverBM.SmartBuildingListener;

public class ThTempOrSendPeriod extends Message {

	@Override
	public void handle(String client,String value, String msgEnv) {
			 try //if the message is a long => the period of sending to the building manager
	   	  {
				 long sendingPeriod  = Long.parseLong(value);
				 SmartBuildingListener.getClientConnection().getThermometer(client).setSendingPeriod(sendingPeriod);
	   		// System.out.println(msg + "-----> sending period (long)" );
	   	  } 
	   	  catch (NumberFormatException nfe) 
	   	  {
	   		   //if the message is a double => temperature value 
		    	
		    		    double tempValue  = Double.parseDouble(value);
		    		    SmartBuildingListener.getClientConnection().getThermometer(client).setTempValue(tempValue);
		    		    //System.out.println("-----> " + msg + " temperature value (double)" );
		    	  
	   		  
		}

}
}