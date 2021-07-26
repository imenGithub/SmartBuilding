package fr.laas.sb.messages;

import fr.laas.sb.entity.dishwasher.DishwasherListener;
import fr.laas.sb.entity.dishwasher.DishwasherManager;

public class RemainingTimeRequest extends Message {

	@Override
	public void handle(String value) {
		String op =DishwasherManager.getDishW().getRemaining_Time();
		DishwasherListener.getServerConnection().sendToBuildingManager("ResponseRT"+op);
	}

}
