package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;


public class ResponseInten extends Message {



	@Override
	public void handle(String client,String value) {
		double intensity  = Double.parseDouble(value);
		  UserAgent.getLamp(client).setIntensity(intensity);
//		  System.out.println("-----> " + value + " intensity of luminosity (double)" );
	}

}
