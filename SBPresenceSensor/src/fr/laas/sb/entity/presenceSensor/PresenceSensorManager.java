package fr.laas.sb.entity.presenceSensor;

import java.util.Calendar;
import java.util.Scanner;


import fr.laas.sb.serverConnection.ServerConnection;


/**
 * @author Imen
 */

public class PresenceSensorManager extends Thread {
	private static PresenceSensor presenceSensor;
	private static double value=15;
	private static Calendar date1 ;

	public PresenceSensorManager()
	{
		
		setPresenceSensor(new PresenceSensor(0));
	    getPresenceSensor().start();
		getPresenceSensor().setPrecision(3);
        System.out.println("precision : " + getPresenceSensor().getPrecision());
		
		
				
		//set the period of sending values to the building manager (100 ms = 0,1s)
		getPresenceSensor().setSendingPeriod(5100); 
		System.out.println("sending period : " + getPresenceSensor().getSendingPeriod() );
		;}
    

	
	public void run()
	{	int compteur=1;	
		while (true ){
	
			    System.out.println("**"+ ServerConnection.getChatClient().getUser()+"*********************************************************************");  
				System.out.println("*                               Sending Period n°"+compteur+"                               *");
				System.out.println("**********************************************************************************");  
				
			 if(value!=FileRW.getPresence(ServerConnection.getChatClient().getUser())){
				 getPresenceSensor().setpresenceValue(FileRW.getPresence(ServerConnection.getChatClient().getUser()));
				 PresenceSensorListener.getServerConnection().sendToBuildingManager("ResponsePresenceSensor"+Double.toString(getPresenceSensor().getpresenceValue()));
					System.out.println("* Presence value ==> "+Double.toString(getPresenceSensor().getpresenceValue())+"   //date d'envoi : "+date1.getInstance().getTime()+"//      *");
					 value=getPresenceSensor().getpresenceValue();}	  

	  
			 try {compteur++;
				System.out.println("*--------------------------------------------------------------------------------*");  
			    System.out.println("*                  Sending Period inactive during:   <"+getPresenceSensor().getSendingPeriod()+">                  *");
			    System.out.println("**********************************************************************************"); 
				Thread.sleep(getPresenceSensor().getSendingPeriod());


		} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
		
				
				
			
				

			}		
			
		}


	public static PresenceSensor getPresenceSensor() {
		return presenceSensor;
	}
	
	



	public static void setPresenceSensor(PresenceSensor ps) {
		PresenceSensorManager.presenceSensor = ps;
	}
}