package fr.laas.sb.entity.luminositySensor;

import java.util.Scanner;

import fr.laas.sb.serverConnection.ServerConnection;

/**
 * @author Imen
 */

public class LuminositySensorListener extends Thread{
	
	
	private boolean entityDetectedValue=false;
	
	private static ServerConnection serverConnection;

	public LuminositySensorListener(int j){//connect the lumSensor to the CM
		if(j==-1)
		{Scanner sc = new Scanner(System.in);
		   System.err.println("Enter the id of the LuminositySensor : ");
		   String n= sc.nextLine();
		   int i=Integer.parseInt(n);
		setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBLuminositySensor/config/config"+i));
	}
	else
		setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBLuminositySensor/config/config"+j));
	}

public void run ()
{
	while (true)
	{ 
		entityDetectedValue = LuminositySensorListener.getServerConnection().valueSentDetection();
	if (entityDetectedValue  == true)//if the lumSensor gets a new request message
		LuminositySensorListener.getServerConnection().receiveNewMessage();
	//System.out.println(ThermometerManager.getServerConnection().receiveNewMessage());

}
		}


public static ServerConnection getServerConnection() {
	return serverConnection;
}


public static void setServerConnection(ServerConnection serverConnection) {
	LuminositySensorListener.serverConnection = serverConnection;
}

}