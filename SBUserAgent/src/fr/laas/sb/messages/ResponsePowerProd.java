package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;


public class ResponsePowerProd extends Message {

	

	@Override
	public void handle(String client,String value) {
		  double powerProd  = Double.parseDouble(value);
		  UserAgent.getPhotovoltaicPanel("PhotovoltaicPanel").setpowerProd(powerProd);
//		  System.out.println("-----> " + value + " ProducedPower (double)" );	
	}

}
