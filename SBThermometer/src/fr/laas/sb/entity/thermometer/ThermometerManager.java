package fr.laas.sb.entity.thermometer;

import java.util.Calendar;


import fr.laas.sb.serverConnection.ServerConnection;


/**
 * @author Imen
 * --- This class receive requests sent by the building manager and response to it ---
 */

public class ThermometerManager extends Thread {
	private static Thermometer thermometer;
	private static double value=15;
	private static Calendar date1 ;

	public ThermometerManager()
	{
		//setDaemon(true);
		
		
		//create the thermometer instance
		setThermometer(new Thermometer(0));
	    getThermometer().start();
    	//set the digits precision of the temperature value
		getThermometer().setPrecision(3);
        System.out.println("precision : " + getThermometer().getPrecision());
		
		
				
		//set the period of sending values to the building manager (100 ms = 0,1s)
		getThermometer().setSendingPeriod(36000); 
		System.out.println("sending period : " + getThermometer().getSendingPeriod() );
		;}
    

	
	public void run()
	{	int compteur=1;	
		while (true ){
	
			    System.out.println("**Thermometer*********************************************************************");  
				System.out.println("*                               Sending Period  n°"+compteur+"                               *");
				System.out.println("**********************************************************************************");  
				
			 if(value!=getThermometer().getTempValue()){
				    ThermometerListener.getServerConnection().sendToBuildingManager("ResponseTempThermometer"+Double.toString(getThermometer().getTempValue()));
					System.out.println("*   Température  ==> "+Double.toString(getThermometer().getTempValue())+"    *");
					value=getThermometer().getTempValue();}	
	  
			 try {compteur++;
				System.out.println("*--------------------------------------------------------------------------------*");  
			    System.out.println("*                  Sending Period  inactive during:  <"+getThermometer().getSendingPeriod()+">                  *");
			    System.out.println("**********************************************************************************"); 
				Thread.sleep(getThermometer().getSendingPeriod());


		} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
		
			
				
			
				

			}		
			
		}
	
	


	public static Thermometer getThermometer() {
		return thermometer;
	}
	
	



	public static void setThermometer(Thermometer thermometer) {
		ThermometerManager.thermometer = thermometer;
	}
}