package fr.laas.sb.messages;

import fr.laas.sb.entity.lamp.LampListener;
import fr.laas.sb.entity.lamp.LampManager;

public class UserStateValue extends Message {
	String state;
	


	public UserStateValue(String string) {
state=string;
}

	@Override
	public void handle(String value) {
		LampManager.getLamp().setState(state);
		  if(state.toUpperCase().equals("ON")){
			  LampManager.getLamp().setIntensity(1000);}
		  else
		  {
			  LampManager.getLamp().setIntensity(0);

		  }
		  LampListener.getServerConnection().sendToBuildingManager("ResponseState"+ LampManager.getLamp().getState());
		  LampListener.getServerConnection().sendToBuildingManager("ResponseInten"+ LampManager.getLamp().getIntensity());
		  System.out.println(rougeGras+"**********Caractéristiques de la Lampe**********");
			System.out.println("*state ==> "+LampManager.getLamp().getState()+"                                  *");
			System.out.println("*Intensity ==>"+LampManager.getLamp().getIntensity()+"                           *");
			System.out.println("************************************************"+reset);
	 
		 // System.out.println("-----> " + state + " user lamp state (String)" );
	}

}
