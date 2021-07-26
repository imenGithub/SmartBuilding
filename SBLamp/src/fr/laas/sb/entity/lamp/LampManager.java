package fr.laas.sb.entity.lamp;

import java.util.Hashtable;
import java.util.Scanner;

import fr.laas.sb.messages.IntensityRequest;
import fr.laas.sb.messages.Message;
import fr.laas.sb.messages.StateRequest;
import fr.laas.sb.messages.UserIntenValue;
import fr.laas.sb.messages.UserStateValue;



public class LampManager extends Thread {
	private static Lamp lamp;

	
		
	public LampManager(){//create the lamp instance
		setLamp(new Lamp("off", 0));
		
		//set the period of sending values to the building manager (100 ms = 0,1s)
		getLamp().setSendingPeriod(100); 
		//System.out.println("sending period : " + getLamp().getSendingPeriod() );
		}
	/*
	 * 
	 * @author emna
	 * @param string 
	 * @return number that exist in the string 
	 */
	
	public void run()
	{
		while (true) //receive building manager requests
		{	
			
			//read the lamp characteristics (state & intensity) from console
			Scanner sc = new Scanner(System.in);
		   System.err.println("Set the new state of the lamp (on/off) : ");
		   String state="";
		   try{state= sc.nextLine();}
		   catch(Exception e)
		   {}
		 
		    
		    double intensity = 0;
		    if(state.equals("on") | state.equals("On") | state.equals("ON"))
		    {
		    	System.err.println("Set the new lamp intensity : ");
		    	intensity= sc.nextDouble();  
		    }
		    
		    getLamp().setState(state);
		    getLamp().setIntensity(intensity);
			
		    //notify the building manager
		    	//wait for period to send values to the building manager
			    try {
					Thread.sleep(getLamp().getSendingPeriod()); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				//send the state
				LampListener.getServerConnection().sendToBuildingManager("ResponseState"+getLamp().getState()); 
				//send the intensity
				LampListener.getServerConnection().sendToBuildingManager("AdResponseInten"+Double.toString(getLamp().getIntensity()));

				//send the sending period
				LampListener.getServerConnection().sendToBuildingManager("s"+Long.toString(getLamp().getSendingPeriod()));
				System.out.println("Sending to the Building manager ! ");
				
			//wait 0,1s
				try {
					Thread.sleep(100); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

		}		
	}


			
			
			
			
			
			
			
			
			
			
			
			
			

	public static Lamp getLamp() {
		return lamp;
	}


	public static void setLamp(Lamp lamp) {
		LampManager.lamp = lamp;
	}	


}