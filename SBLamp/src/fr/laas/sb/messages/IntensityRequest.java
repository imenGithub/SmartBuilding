package fr.laas.sb.messages;

import fr.laas.sb.entity.lamp.LampListener;
import fr.laas.sb.entity.lamp.LampManager;

public class IntensityRequest extends Message {

	@Override
	public void handle(String value) {
		double intensity = LampManager.getLamp().getIntensity();
		LampListener.getServerConnection().sendToBuildingManager("ResponseInten"+Double.toString(intensity));
	}

}
