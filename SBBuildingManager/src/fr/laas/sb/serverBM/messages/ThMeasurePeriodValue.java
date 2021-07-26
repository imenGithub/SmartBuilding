package fr.laas.sb.serverBM.messages;

import fr.laas.sb.serverBM.SmartBuildingListener;

public class ThMeasurePeriodValue extends Message {

	@Override
	public void handle(String client,String value, String msgEnv) {
		  long measurementPeriod = Long.parseLong(value);
		  SmartBuildingListener.getClientConnection().getThermometer(client).setMeasurementPeriod(measurementPeriod);
		  //System.out.println("-----> " + measurementPeriodString + " measurement period (long)" );
	}

}
