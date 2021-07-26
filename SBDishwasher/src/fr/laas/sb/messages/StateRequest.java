package fr.laas.sb.messages;

import fr.laas.sb.entity.dishwasher.DishwasherListener;
import fr.laas.sb.entity.dishwasher.DishwasherManager;

public class StateRequest extends Message {

	@Override
	public void handle(String value) {
		String state = DishwasherManager.getDishW().getState();
		DishwasherListener.getServerConnection().sendToBuildingManager("DWResponseState"+state);
	}

}
