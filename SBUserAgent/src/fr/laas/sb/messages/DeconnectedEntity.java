package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgentUI.UserAgentUI;

public class DeconnectedEntity extends Message {

	@Override
	public void handle(String client, String val) {
		UserAgentUI.getConnectedEntitiesVector().remove(client);
		if(client.contains("Lamp"))
	  UserAgent.getComponentList().get("Lamp").remove(UserAgent.findEntityByName("Lamp",client));
		if(client.contains("AirConditioner"))
  		  UserAgent.getComponentList().get("AirConditioner").remove(UserAgent.findEntityByName("AirConditioner",client));
		

		
	}

}
