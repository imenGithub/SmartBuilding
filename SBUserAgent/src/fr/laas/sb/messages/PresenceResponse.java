package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;

public class PresenceResponse extends Message {

	@Override
	public void handle(String client, String val) {
		double p = Double.parseDouble(val);
		  UserAgent.getPresenceSensor(client).setpresenceValue(p);
//		  System.out.println("-----> " + val + " value of presence " );
	}

}
