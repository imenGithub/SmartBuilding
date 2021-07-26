package fr.laas.sb.messages;

import java.text.DecimalFormat;

import fr.laas.sb.entity.lamp.LampListener;
import fr.laas.sb.entity.lamp.LampManager;

public class UserIntenValue extends Message {

	@Override
	public void handle(String value) {
		double intensity  = Double.parseDouble(value);
		 DecimalFormat df = new DecimalFormat ( ) ;

		df.setMaximumFractionDigits ( 3 ) ; //arrondi à 2 chiffres apres la virgules
		df.setMinimumFractionDigits ( 3 ) ;
		df.setDecimalSeparatorAlwaysShown ( true ) ;
		String st=df.format(intensity);
		intensity=Double.parseDouble(st.replace(',', '.'));
		LampManager.getLamp().setIntensity(intensity);
		LampManager.getLamp().setState("on");
		  LampListener.getServerConnection().sendToBuildingManager("ResponseState"+ LampManager.getLamp().getState());
		  LampListener.getServerConnection().sendToBuildingManager("ResponseInten"+ LampManager.getLamp().getIntensity());
		  System.out.println(rougeGras+"**********Caractéristiques de la Lampe**********");
			System.out.println("*state ==> "+LampManager.getLamp().getState()+"                                  *");
			System.out.println("*Intensity ==>"+LampManager.getLamp().getIntensity()+"                           *");
			System.out.println("************************************************"+reset);
	 
		 // System.out.println("-----> " + intensityString + " user lamp intensity (double)" );
	}

}
