package fr.laas.sb.entity.luminositySensor;

import java.util.Calendar;
import java.util.Scanner;


import fr.laas.sb.serverConnection.ServerConnection;


/**
 * @author Imen
 */

public class LuminositySensorManager extends Thread {
	private static LuminositySensor lumSensor;
	private static double value=15;
	private static Calendar date1 ;

	public LuminositySensorManager()
	{
		//setDaemon(true);
		
		
		//create the lumSensor instance
		setlumSensor(new LuminositySensor(0));
	    getlumSensor().start();
    	//set the digits precision of the temperature value
		getlumSensor().setPrecision(3);
        System.out.println("precision : " + getlumSensor().getPrecision());
		
		
				
		//set the period of sending values to the building manager (100 ms = 0,1s)
		getlumSensor().setSendingPeriod(36000); 
		System.out.println("sending period : " + getlumSensor().getSendingPeriod() );
		;}
    

	
	public void run()
	{	int compteur=1;	
		while (true ){
	
				
			    System.out.println("**"+LuminositySensorListener.getServerConnection().getChatClient().getUser()+"*********************************************************************");  
				System.out.println("*                               Sending Period  n°"+compteur+"                               *");
				System.out.println("**********************************************************************************");  
				
				
			 if(value!=getlumSensor().getLumValue()){
				    LuminositySensorListener.getServerConnection().sendToBuildingManager("ResponseLumSensor"+Double.toString(getlumSensor().getLumValue()));
					System.out.println("*"+"  Luminosity value  ==> "+Double.toString(getlumSensor().getLumValue())+"**"+date1.getInstance().getTime()+"//      *");
					value=getlumSensor().getLumValue();}	
	  
			 try {compteur++;
				System.out.println("*--------------------------------------------------------------------------------*");  
			    System.out.println("*                  Sending Period inactive during:  <"+getlumSensor().getSendingPeriod()+">                  *");
			    System.out.println("**********************************************************************************"); 
				Thread.sleep(getlumSensor().getSendingPeriod());


		} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
		
				
				

			}		
			
		}
	


	public static LuminositySensor getlumSensor() {
		return lumSensor;
	}
	
	



	public static void setlumSensor(LuminositySensor lumSensor) {
		LuminositySensorManager.lumSensor = lumSensor;
	}
}