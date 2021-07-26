package fr.laas.sb.entity.fr.laas.sb.entity.smartPlug;

import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;
import fr.laas.sb.serverConnection.ServerConnection;


/**
 * @author Imen
 */

public class SmartPlugManager extends Thread {
	private static SmartPlug smartP;
	private static double value=-1;
	private static Calendar date1 ;

	public SmartPlugManager()
	{
		

		setsmartP(new SmartPlug(SmartPlugListener.getServerConnection().getChatClient().getUser()));
    	
				
		//set the period of sending values to the building manager (100 ms = 0,1s)
		getsmartP().setSendingPeriod(36000); 
		System.out.println("sending period : " + getsmartP().getSendingPeriod() );
	    getsmartP().start();

		;}
    

	
	public void run()
	{	int compteur=1;	
		while (true ){
			
			if(value!=getsmartP().getCons()){
				value=getsmartP().getCons();
			System.out.println("**"+getsmartP().getNameS()+"*********************************************************************");  
			System.out.println("*                              Sending cycle  n°"+compteur+"                               *");
			System.out.println("**********************************************************************************"); 
			
			
			SmartPlugListener.getServerConnection().sendToBuildingManager("ResponseConsEntity"+Double.toString(getsmartP().getCons()));
				System.out.println("  Consumed power ==> "+Double.toString(getsmartP().getCons())+"   //date d'envoi : "+date1.getInstance().getTime()+"//  *");	
			}
			try {compteur++;
			System.out.println("*--------------------------------------------------------------------------------*");  
		    System.out.println("*                  Sending cycle inactive during:  <"+getsmartP().getSendingPeriod()+">                  *");
		    System.out.println("**********************************************************************************"); 
			
		    
		    Thread.sleep(getsmartP().getSendingPeriod());
	                    

			} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
			}
			

				
			}
	



	public static SmartPlug getsmartP() {
		return smartP;
	}
	
	



	public static void setsmartP(SmartPlug smartP) {
		SmartPlugManager.smartP = smartP;
	}
}