package fr.laas.sb.entity.airConditioner;

import java.util.Scanner;

import test.AirConditionerDaemon;

import fr.laas.sb.serverConnection.ServerConnection;

/**
 * @author Imen
 * --- This class retrieves information related to the air conditioner (the temperature value)---
 * --- The class is a Daemon Thread (running in background)---
 */
public class AirConditionerListener extends Thread{
	
	private static ServerConnection serverConnection;
	
	/**
	 * Constructor of the class, defining the thread as Daemon, connecting the air conditioner to the CM
	 * and initializing the period of sending values to the building manager
	 * @param j 
	 */
	public AirConditionerListener(int j)
	{
		//setDaemon(true);
		
		//connect the air conditioner to the CM
		if(j==-1)
		{Scanner sc = new Scanner(System.in);
		   System.err.println("Enter the id of the AirConditioner : ");
		   String n= sc.nextLine();
		   int i=Integer.parseInt(n);
		serverConnection = new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBAirConditioner/config/config"+i);
		}
		else
			serverConnection = new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBAirConditioner/config/config"+j);

		
	}
	/*
	 * 
	 * @author emna
	 * @param string 
	 * @return number that exists in the string 
	 */
	private static String divNumber(String s)		
	{
	Boolean e=false;    
	String s2="";
	int i=0;
		while(e==false && i<s.length())
		{
			try
		{
		    @SuppressWarnings("unused")
			double num = Double.parseDouble(s.substring(i, i+1));
		    s2=s.substring(i, s.length());
		e=true;    
		}
			catch(NumberFormatException nfe1){
						i++;

			}
		
		
			}
		
		return s2;
	

		}
	
	
	/**
	 * A method listening to the new temperature value of the air conditioner (typed by the user)
	 * and notifying the building manager
	 */
	public void run ()
	{
		while (true) //receive building manager requests
		{			
			boolean entityDetectedValue = AirConditionerListener.getServerConnection().valueSentDetection();	
			
			if (entityDetectedValue  == true)//if the air conditioner gets a new request message
			{
				String msg = AirConditionerListener.getServerConnection().receiveNewMessage();
				
				String msge=msg;
				 String val="";
				if(divNumber(msg)!="")
					 {msge=msg.replace(divNumber(msg), "");
					 val=divNumber(msg);
					 }
				serverConnection.manageMessages(msge, val);
		

				
				
				
					
				
			}
			
		}
	}
			
	

	public static ServerConnection getServerConnection() {
		return serverConnection;
	}

	public static void setServerConnection(ServerConnection serverConnection) {
		AirConditionerListener.serverConnection = serverConnection;
	}

}