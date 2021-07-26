package fr.laas.sb.messages;

import fr.laas.sb.entity.dishwasher.DishwasherListener;
import fr.laas.sb.entity.dishwasher.DishwasherManager;

public class UserStateValue extends Message {
	String state;
	


	public UserStateValue(String string) {
state=string;
}

	@Override
	public void handle(String value) {
		DishwasherManager.getDishW().setState(state);
		  
		
		
		  if(state.toUpperCase().equals("ON"))
			  DishwasherManager.getDishW().setSendingPeriod(100);
		  else
			  DishwasherManager.getDishW().setMax_end_hour("0");


		 
		  DishwasherListener.getServerConnection().sendToBuildingManager("DWResponseState"+ DishwasherManager.getDishW().getState());
		  DishwasherListener.getServerConnection().sendToBuildingManager("ResponseMax"+DishwasherManager.getDishW().getMax_end_hour());
		  System.out.println(rougeGras+"**********Dishwasher**********");
			System.out.println("*state ==> "+DishwasherManager.getDishW().getState()+"                                  *");
			System.out.println("*Maximum end hour ==>"+DishwasherManager.getDishW().getMax_end_hour()+"                           *");
			System.out.println("************************************************"+reset);
	 
	}

}
