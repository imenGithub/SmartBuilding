package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;


public class ResponseTempAirCond extends Message {

	

	@Override
	public void handle(String client,String value) {
		  double tempValue  = Double.parseDouble(value);
		  UserAgent.getAirConditioner(client).setTempValue(tempValue);
//		  System.out.println("-----> " + value+ " temperature value of the air conditioner (double)" );
	}

}
