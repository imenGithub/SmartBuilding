package fr.laas.sb.entity.thermometer;

import fr.laas.sb.serverConnection.ServerConnection;

//import java.util.Scanner;

/**
 * @author Imen
 * --- This class retrieves information related to the thermometer (the temperature value)---
 * --- The class is a Daemon Thread (running in background)---
 */

public class ThermometerListener extends Thread{
	private static ServerConnection serverConnection;
	
	private boolean entityDetectedValue=false;
	
	//la methode traitement :permet de prendre les valeurs du BM et changer les valeurs du thermometere
public ThermometerListener(){
	//connect the Thermometer to the CM
			//setServerConnection(new ServerConnection("/home/marwa/PFE3/SBThermometer/config/config0"));
			setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBThermometer/config/config0"));
}
	
public void run ()
{
	while (true)
	{ 
		entityDetectedValue = ThermometerListener.getServerConnection().valueSentDetection();
	if (entityDetectedValue  == true)//if the thermometer gets a new request message
		ThermometerListener.getServerConnection().receiveNewMessage();
	//System.out.println(ThermometerManager.getServerConnection().receiveNewMessage());

}
		}

	



public static ServerConnection getServerConnection() {
	return serverConnection;
}


public static void setServerConnection(ServerConnection serverConnection) {
	ThermometerListener.serverConnection = serverConnection;
}
	

}