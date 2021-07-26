package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;


public class ResponseTempThermometer extends Message {

	

	@Override
	public void handle(String client,String value) {
		  double tempValue  = Double.parseDouble(value);
		  UserAgent.getThermometer("Thermometer").setTempValue(tempValue);
//		  System.out.println("-----> " + value + " temperature value measured by the thermometer (double)" );	
	}

}
