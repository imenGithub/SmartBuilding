package fr.laas.sb.messages;

import java.util.StringTokenizer;


import fr.laas.sb.entities.AirConditioner;
import fr.laas.sb.entities.Dishwasher;
import fr.laas.sb.entities.Lamp;
import fr.laas.sb.entities.LuminositySensor;
import fr.laas.sb.entities.PhotovoltaicPanel;
import fr.laas.sb.entities.PowerSmartMeter;
import fr.laas.sb.entities.PresenceSensor;
import fr.laas.sb.entities.SmartPlug;
import fr.laas.sb.entities.Thermometer;
import fr.laas.sb.serverConnection.ServerConnection;
import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgentUI.UserAgentUI;

public class ConnectedEntity extends Message {

	@SuppressWarnings("unchecked")
	@Override
	public void handle(String client,String value) {
//		System.out.println("test  : connectedEntitiesStr");
//		System.out.println("onlyConnectedEntities : " + value);
//		
//		System.out.println("=== Display connected entities ===> ");
		String elementVector;
		
		StringTokenizer st = new StringTokenizer (value, "*");
	      while (st.hasMoreTokens())
	      {
	    	  elementVector = st.nextToken();

//	    	  System.out.println("connected entity : " + elementVector);
	    	  
	    	  //verify if the entity exists in the connected entities vector, else add it
	    	  boolean exist = false;
	    	  for  (int i=0; i<UserAgentUI.getConnectedEntitiesVector().size(); i++)
	    	  {
	    		  if (UserAgentUI.getConnectedEntitiesVector().elementAt(i).equals(elementVector))
	    		  {
	    			  exist=true;
	    			  break;
	    		  }
	    	  }
	    	  
	    	//if the entity does not exist in the connected entities vector => add it 
	    	  if (! exist)
	    	  { //set the vector of the connected entities (connectedEntitiesVector)
	    		  UserAgentUI.getConnectedEntitiesVector().add(elementVector);
	    		
	    		if(elementVector.contains("Lamp")&& ! elementVector.contains("SmartPlug"))
	  		  {	    		

	    			UserAgent.getComponentList().get("Lamp").addElement(new Lamp(elementVector,"off",0,0));

	  		
	  		  }
	    		if(elementVector.contains("AirConditioner") && ! elementVector.contains("SmartPlug"))
		  		  {	    		

		    			UserAgent.getComponentList().get("AirConditioner").addElement(new AirConditioner(elementVector,0,0,0));


		  		  }
	    		if(elementVector.contains("PresenceSensor") && ! elementVector.contains("SmartPlug"))
		  		  {	    		

		    			UserAgent.getComponentList().get("PresenceSensor").addElement(new PresenceSensor(elementVector,0,0,0));


		  		  }
	    		if(elementVector.contains("LuminositySensor") && ! elementVector.contains("SmartPlug"))
		  		  {	    		

		    			UserAgent.getComponentList().get("LuminositySensor").addElement(new LuminositySensor(elementVector,0,0,0));


		  		  }
	    		
	    		if(elementVector.contains("Thermometer"))
		  		  {	    		

		    			UserAgent.getComponentList().get("Thermometer").addElement(new Thermometer("Thermometer",0,0,0,0));


		  		  }	
	    		
	    		if(elementVector.contains("PowerSmartMeter"))
		  		  {	    		

		    			UserAgent.getComponentList().get("PowerSmartMeter").addElement(new PowerSmartMeter("PowerSmartMeter",0, 0));


		  		  }	
	    		if(elementVector.contains("PhotovoltaicPanel"))
		  		  {	    		

		    			UserAgent.getComponentList().get("PhotovoltaicPanel").addElement(new PhotovoltaicPanel("PhotovoltaicPanel",0,0,0));


		  		  }	
	    		
	    		
	    		if(elementVector.contains("SmartPlug"))
		  		  {	    		

		    			UserAgent.getComponentList().get("SmartPlug").addElement(new SmartPlug(elementVector));


		  		  }
	    		
	    		
	    		if(elementVector.contains("Dishwasher") && ! elementVector.contains("SmartPlug"))
		  		  {	    		

		    			UserAgent.getComponentList().get("Dishwasher").addElement(new Dishwasher("Dishwasher",100));


		  		  }		
	    		
	    	  }
	    	  
	      }	
	      
	      
	    			
}	

}
