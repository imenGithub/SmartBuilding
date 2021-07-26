package fr.laas.sb.entity.fr.laas.sb.entity.photovoltaicPanel;

import fr.laas.sb.serverConnection.ServerConnection;

//import java.util.Scanner;

/**
 * @author Imen
 * --- This class retrieves information related to the photovoltaicPanel (the temperature value)---
 * --- The class is a Daemon Thread (running in background)---
 */

public class PhotovoltaicPanelListener extends Thread{
	private static ServerConnection serverConnection;
	private boolean entityDetectedValue=false;
	
	
	public PhotovoltaicPanelListener()
	{
		//setDaemon(true);
		
		//connect the PhotovoltaicPanel to the CM
		//setServerConnection(new ServerConnection("/home/marwa/PFE3/SBPhotovoltaicPanel/config/config0"));
		setServerConnection(new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBPhotovoltaicPanels/config/config0"));
		//create the PhotovoltaicPanel instance
		}
	
	
	//la methode traitement :permet de prendre les valeurs du BM et changer les valeurs du photovoltaicPanele

public void run ()
{
	while (true)
	{ 
		entityDetectedValue =getServerConnection().valueSentDetection();
	if (entityDetectedValue  == true)//if the photovoltaicPanel gets a new request message
		getServerConnection().receiveNewMessage();
	//System.out.println(PhotovoltaicPanelManager.getServerConnection().receiveNewMessage());

}
		}

	
public static ServerConnection getServerConnection() {
	return serverConnection;
}


public static void setServerConnection(ServerConnection serverConnection) {
	PhotovoltaicPanelListener.serverConnection = serverConnection;
}



}