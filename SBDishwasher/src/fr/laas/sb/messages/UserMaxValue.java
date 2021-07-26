package fr.laas.sb.messages;

import fr.laas.sb.entity.dishwasher.DishwasherListener;
import fr.laas.sb.entity.dishwasher.DishwasherManager;

public class UserMaxValue extends Message {

	@Override
	public void handle(String value) {
		DishwasherManager.getDishW().setMax_end_hour(value);
		  DishwasherListener.getServerConnection().sendToBuildingManager("ResponseMax"+DishwasherManager.getDishW().getMax_end_hour());
		  System.out.println(rougeGras+"**********Dishwasher**********");
			System.out.println("*state ==> "+DishwasherManager.getDishW().getState()+"                                  *");
			System.out.println("*Maximum end hour ==>"+DishwasherManager.getDishW().getMax_end_hour()+"                           *");
			System.out.println("************************************************"+reset);
	 
		 // System.out.println("-----> " + intensityString + " user lamp intensity (double)" );
	}

}
