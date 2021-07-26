package fr.laas.sb.entity.presenceSensor;

import java.util.Scanner;

import fr.laas.sb.serverConnection.ServerConnection;

//import java.util.Scanner;

/**
 * @author Imen
 */

public class PresenceSensorListener extends Thread{
	
	private static ServerConnection serverConnection;
	private boolean entityDetectedValue=false;

	
	public PresenceSensorListener (int j)
	{ if(j==-1)
	{//connect the presenceSensor to the CM
		Scanner sc = new Scanner(System.in);
		   System.err.println("Enter the id of the PresenceSensor : ");
		   String n= sc.nextLine();
		   int i=Integer.parseInt(n);
			setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBPresenceSensor/config/config"+i));
	}
	else
		setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBPresenceSensor/config/config"+j));

	}
	
	
	//la methode traitement :permet de prendre les valeurs du BM et changer les valeurs du presenceSensor

public void run ()
{
	while (true)
	{ 
		entityDetectedValue =getServerConnection().valueSentDetection();
	if (entityDetectedValue  == true)//if the thermometer gets a new request message
		getServerConnection().receiveNewMessage();
	//System.out.println(ThermometerManager.getServerConnection().receiveNewMessage());

}
		}

public static ServerConnection getServerConnection() {
	return serverConnection;
}

public static void setServerConnection(ServerConnection serverConnection) {
	PresenceSensorListener.serverConnection = serverConnection;
}

	


}