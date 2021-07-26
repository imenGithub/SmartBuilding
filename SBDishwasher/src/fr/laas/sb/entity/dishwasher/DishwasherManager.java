package fr.laas.sb.entity.dishwasher;

import java.util.Hashtable;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import fr.laas.sb.messages.MaxRequest;
import fr.laas.sb.messages.Message;
import fr.laas.sb.messages.RemainingTimeRequest;
import fr.laas.sb.messages.StatePause;
import fr.laas.sb.messages.StateRequest;
import fr.laas.sb.messages.UserMaxValue;
import fr.laas.sb.messages.UserRemainingTimeValue;
import fr.laas.sb.messages.UserStateValue;


public class DishwasherManager extends Thread {
	private static Dishwasher dishW;

	
	public DishwasherManager()
	{//create the DishW instance
		setDishW(new Dishwasher());
		getDishW().setSendingPeriod(100); }
	
	
	
	
	
	public void run()
	{
		while (true) //receive building manager requests
		{
	
		//read the DishW characteristics (state & hour) from console
		Scanner sc = new Scanner(System.in);
	   System.err.println("Set the new state of the dishwasher (on/off) : ");
	   String state="";
	   try{state= sc.nextLine();}
	   catch(Exception e)
	   {}
	 
	    
	  
	    getDishW().setState(state.toLowerCase());
	    
	    DishwasherListener.getServerConnection().sendToBuildingManager("DWResponseState"+getDishW().getState()); 
		//send the max_start_hour

		DishwasherListener.getServerConnection().sendToBuildingManager("send"+Long.toString(getDishW().getSendingPeriod()));
		System.out.println("Sending to the Building manager ! ");
	    
	  //read the DishW characteristics (state & hour) from console
	  	   System.err.println("Set the max end hour (hh:mm:ss) : ");
	  	   String max="";
	  	   try{max= sc.nextLine();}
	  	   catch(Exception e)
	  	   {}
	  	 
	  	    
	  	  
	  	    getDishW().setMax_end_hour(max);
	  	  DishwasherListener.getServerConnection().sendToBuildingManager(("ResponseMax"+getDishW().getMax_end_hour()));
			//send the sending period
	    //notify the building manager
	    	//wait for period to send values to the building manager
		    try {
				Thread.sleep(getDishW().getSendingPeriod()); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			//send the state
			
			
			
		//wait 0,1s
			try {
				Thread.sleep(100); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

	}		
}

	

	
	

	public static Dishwasher getDishW() {
		return dishW;
	}


	public static void setDishW(Dishwasher dw) {
		DishwasherManager.dishW = dw;
	}
}