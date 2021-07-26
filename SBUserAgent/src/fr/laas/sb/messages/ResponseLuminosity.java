package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;

public class ResponseLuminosity extends Message {

	@Override
	public void handle(String client, String val) {
		double lum = Double.parseDouble(val);
		  UserAgent.getLumSensor(client).setLumValue(lum);
//		  System.out.println("-----> " + val + " value of luminosity (double)" );
	}

}
