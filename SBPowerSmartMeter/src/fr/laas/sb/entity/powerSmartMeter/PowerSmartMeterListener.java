package fr.laas.sb.entity.powerSmartMeter;

import fr.laas.sb.serverConnection.ServerConnection;




/**
* @author Imen
* --- This class retrieves information related to the thermometer (the temperature value)---
* --- The class is a Daemon Thread (running in background)---
*/

public class PowerSmartMeterListener extends Thread{
	
	private static ServerConnection serverConnection;	
	private boolean entityDetectedValue=false;
	
	//la methode traitement :permet de prendre les valeurs du BM et changer les valeurs du thermometere

	public PowerSmartMeterListener()
	{
	//connect the Thermometer to the CM
			//setServerConnection(new ServerConnection("/home/marwa/pfe/SBPowerSmartMeter/config/config0"));
			setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBPowerSmartMeter/config/config0"));
				
	}
	
public void run ()
{
	while (true)
	{ 
		entityDetectedValue = getServerConnection().valueSentDetection();
	if (entityDetectedValue  == true)//if the thermometer gets a new request message
		getServerConnection().receiveNewMessage();
	//System.out.println(PowerSmartMeterManager.getServerConnection().receiveNewMessage());

}
		}

public static ServerConnection getServerConnection() {
	return serverConnection;
}

public static void setServerConnection(ServerConnection serverConnection) {
	PowerSmartMeterListener.serverConnection = serverConnection;
}

	


	



}