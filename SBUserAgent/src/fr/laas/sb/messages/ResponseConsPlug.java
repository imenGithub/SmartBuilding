package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;

public class ResponseConsPlug extends Message {

	@Override
	public void handle(String client, String val) {
		 double value  = Double.parseDouble(val);
		  UserAgent.getPlug(client).setCons(value);
//		  System.out.println("-----> " + value + " is the consumption measured by "+client );
	}

}
