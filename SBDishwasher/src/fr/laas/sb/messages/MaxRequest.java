package fr.laas.sb.messages;

import fr.laas.sb.entity.dishwasher.DishwasherListener;
import fr.laas.sb.entity.dishwasher.DishwasherManager;

public class MaxRequest extends Message {

	@Override
	public void handle(String value) {
		String max = DishwasherManager.getDishW().getMax_end_hour();
		DishwasherListener.getServerConnection().sendToBuildingManager("ResponseMax"+max);
	}

}
