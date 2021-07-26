package fr.laas.sb.messages;

import fr.laas.sb.entity.airConditioner.AirConditionerListener;
import fr.laas.sb.entity.airConditioner.AirConditionerManager;
/**
 * @author emna
 */
public class UserTempValue extends Message {

	@Override
	public void handle(String value) {
		 double tempVal  = Double.parseDouble(value);
		  AirConditionerManager.getAirConditioner().setTempValue(tempVal);
		  AirConditionerListener.getServerConnection().sendToBuildingManager("ResponseTempAirCond"+ AirConditionerManager.getAirConditioner().getTempValue());
		  System.out.println("**********Air Conditioner**********");
			System.out.println("*Temperature ==> "+AirConditionerManager.getAirConditioner().getTempValue()+"                             *");
			System.out.println("***************************************************");
	}

}
