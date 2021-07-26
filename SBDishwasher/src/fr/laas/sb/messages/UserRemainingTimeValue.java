package fr.laas.sb.messages;

import fr.laas.sb.entity.dishwasher.DishwasherListener;
import fr.laas.sb.entity.dishwasher.DishwasherManager;

public class UserRemainingTimeValue extends Message {

	@Override
	public void handle(String value) {
		DishwasherManager.getDishW().setRemaining_Time(value);
		  
		  DishwasherListener.getServerConnection().sendToBuildingManager("ResponseRT"+ DishwasherManager.getDishW().getRemaining_Time());
		  
	}

}
