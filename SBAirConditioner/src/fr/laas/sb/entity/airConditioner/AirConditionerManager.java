package fr.laas.sb.entity.airConditioner;

import java.util.Scanner;

/**
 * @author Imen
 * --- This class receive requests sent by the building manager and response to it ---
 * --- The class is a Daemon Thread (running in background)---
 */

public class AirConditionerManager extends Thread{
	
	private static AirConditioner airConditioner;

	public static AirConditioner getAirConditioner() {
		return airConditioner;
	}
	public static void setAirConditioner(AirConditioner airConditioner) {
		AirConditionerManager.airConditioner = airConditioner;
	}

	
	public AirConditionerManager()
	{//create the air conditioner instance
		airConditioner = new AirConditioner(0,0);
		
    	//set the digits precision of the temperature value
        airConditioner.setPrecision(2);
        //System.out.println("precision : " + airConditioner.getPrecision());
        
		//set the period of sending values to the building manager (100 ms = 0,1s)
		airConditioner.setSendingPeriod(100);
		//System.out.println("sending period : " + airConditioner.getSendingPeriod());}
	}
	
	public void run()
	{
		while (true)
		{
			//read the air conditioner characteristics (temperature value) from console
			Scanner sc = new Scanner(System.in);
		    System.err.println("Set the new temperature value of the air conditioner : ");
		    double tempValue = sc.nextDouble(); 
		    airConditioner.setTempValue(tempValue);
		    		    
		    //notify the building manager
		        //wait for period to send values to the building manager
			    try {
					Thread.sleep(airConditioner.getSendingPeriod()); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

			    
			    //send the temperature value of the air conditioner
			    AirConditionerListener.getServerConnection().sendToBuildingManager("AdResponseTempAirCond"+Double.toString(airConditioner.getTempValue())); 
				//send the precision
				AirConditionerListener.getServerConnection().sendToBuildingManager(Long.toString(airConditioner.getSendingPeriod()));
				//System.out.println("Sending to the Building manager ! ");
				
			//wait 0,1s
				try {
					Thread.sleep(100); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			

		}	
		
	}
		

}