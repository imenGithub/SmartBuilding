package fr.laas.sb.messages;

import fr.laas.sb.entity.airConditioner.AirConditionerListener;
import fr.laas.sb.entity.airConditioner.AirConditionerManager;
/**
 * @author emna
 */
public class TempsRequest extends Message {

	@Override
	public void handle(String value) {
		double tempValue = AirConditionerManager.getAirConditioner().getTempValue();
		AirConditionerListener.getServerConnection().sendToBuildingManager("ResponseTempAirCond"+Double.toString(tempValue));
	}

}
