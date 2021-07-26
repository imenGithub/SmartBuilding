package fr.laas.sb.entity.lamp;

import java.util.Scanner;
import fr.laas.sb.serverConnection.ServerConnection;

/**
 * @author Imen
 * --- This class retrieves informations related to the lamp (the state and the intensity)---
 * --- The class is a Daemon Thread (running in background)---
 */
public class LampListener extends Thread {
	
	private static ServerConnection serverConnection;
	
	/**
	 * Constructor of the class, defining the thread as Daemon
	 * connecting the lamp to the CM and initializing the period of sending values to the building manager
	 * @param j 
	 */
	
	
	public LampListener(int j)
	{
		//setDaemon(true);
		if(j==-1)
		{
		//connect the lamp to the CM
		//setServerConnection(new ServerConnection("/home/marwa/pfe/SBLamp/config/config0"));
		Scanner sc = new Scanner(System.in);
		   System.err.println("Enter the id of the Lamp : ");
		   String n= sc.nextLine();
		   int i=Integer.parseInt(n);
		
		setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBLamp/config/config"+i));	
		}
		else
			setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBLamp/config/config"+j));	

		
	}
	
	
	/**
	 * A method listening to the new state and the new intensity of the lamp (typed by the user)
	 * and notifying the building manager
	 */
	
	public void run ()
	{
			
		while (true)
		{
boolean entityDetectedValue = LampListener.getServerConnection().valueSentDetection();	
			
			if (entityDetectedValue  == true)//if the lamp gets a new request message
			{
				String msg = LampListener.getServerConnection().receiveNewMessage();
				LampListener.getServerConnection().managemessages(msg);
				
				
				
	
				

			}
			
			
		}
	}
	

	public static ServerConnection getServerConnection() {
		return serverConnection;
	}


	public static void setServerConnection(ServerConnection serverConnection) {
		LampListener.serverConnection = serverConnection;
	}



		

}