package fr.laas.sb.entity.dishwasher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import fr.laas.sb.serverConnection.ServerConnection;

/**
 * @author Imen
 */
public class DishwasherListener extends Thread {
	
	private static ServerConnection serverConnection;
	
	/**
	 * Constructor of the class, defining the thread as Daemon
	 */
	
	
	public DishwasherListener()
	{
		//setDaemon(true);
		
		//connect the dishwasher to the CM
		setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBDishwasher/config/config0"));
		
		
		
		
	}
	
	
	/**
	 * A method listening to the new state and the new max_start_hour of the DishW (typed by the user)
	 * and notifying the building manager
	 */
	
	public void run ()
	{
			
		while (true)
		{		
			
			
			if(DishwasherManager.getDishW().getMax_end_hour()!="0")
			{SimpleDateFormat h = new SimpleDateFormat ("hh:mm:ss");		 
			Date currentTime_1 = new Date();
			 
			String dateString = h.format(currentTime_1);
			if(dateString.equals(DishwasherManager.getDishW().getMax_end_hour()))
				DishwasherManager.getDishW().setMax_end_hour("0");
				
				
				
				
			}
			
			
			
			boolean entityDetectedValue = DishwasherListener.getServerConnection().valueSentDetection();	
			
			if (entityDetectedValue  == true)//if the lamp gets a new request message
			{
				String msg = DishwasherListener.getServerConnection().receiveNewMessage();
				
				
		

			}
			
			
		}
	}
	
	  
			
			
			
		

	public static ServerConnection getServerConnection() {
		return serverConnection;
	}


	public static void setServerConnection(ServerConnection serverConnection) {
		DishwasherListener.serverConnection = serverConnection;
	}


	

		

}