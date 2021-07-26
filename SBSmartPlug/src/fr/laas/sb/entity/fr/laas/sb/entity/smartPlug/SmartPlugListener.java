package fr.laas.sb.entity.fr.laas.sb.entity.smartPlug;

import java.util.Scanner;

import fr.laas.sb.serverConnection.ServerConnection;


/**
 * @author Imen
 */

public class SmartPlugListener extends Thread{
	
	private static ServerConnection serverConnection;

	private boolean entityDetectedValue=false;
	
public SmartPlugListener(int j)
{
if(j==-1)
	{Scanner sc = new Scanner(System.in);
	   System.err.println("Enter the id of the smartPlug : ");
	   String n= sc.nextLine();
	   int i=Integer.parseInt(n);
	   setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBSmartPlug/config/config"+i));
	}

else
	setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBSmartPlug/config/config"+j));
	
}
public void run ()
{
	while (true)
	{ 
		entityDetectedValue = getServerConnection().valueSentDetection();
	if (entityDetectedValue  == true)//if the smartP gets a new request message
		getServerConnection().receiveNewMessage();
	//System.out.println(smartPManager.getServerConnection().receiveNewMessage());

}
		}


public static ServerConnection getServerConnection() {
	return serverConnection;
}


public static void setServerConnection(ServerConnection serverConnection) {
	SmartPlugListener.serverConnection = serverConnection;
}

	



}