package fr.laas.sb.userAgent;

import java.io.IOException;
import java.util.StringTokenizer;

import fr.laas.sb.serverConnection.ServerConnection;
import fr.laas.sb.userAgentUI.UserAgentUI;


	//listen values from BM (responses)

public class UserAgentListener extends Thread{
	private static ServerConnection serverConnection;

	private UserAgentUI userAgentUI;
	
	public UserAgentListener(UserAgentUI userAgentUI)
	{		this.userAgentUI = userAgentUI;

		//connect the user agent to the CM
		//serverConnection = new ServerConnection("/home/marwa/pfe/SBUserAgent/config/config0");
	UserAgentListener.serverConnection = new ServerConnection("/home/emna/Bureau/Master/Smart Building/SBUserAgent/config/config0");
		//serverConnection = new ServerConnection("SBUserAgent/config/config0");
		
		//setDaemon(true);
	UserAgentListener.getServerConnection().sendToBuildingManager("UserAgentConnected");

	}
	
	@SuppressWarnings("deprecation")
	public void run ()
	{
		while (true)
		{
			//listening
			boolean entityDetectedValue = UserAgentListener.getServerConnection().valueSentDetection();
			boolean NewConnectedEntity = false;
			
			if (entityDetectedValue  == true)//if the UA gets a new message (response) from the BM
			{
				//UserAgentUI.getInstance().stop();
				/*System.out.println("Begin Sleep");
				try {
					this.getUserInterfaceThread().sleep(20000); //20s
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("End Sleep");*/
				
/*				try {
					this.userAgentUI.sleep(20000000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/

				
				//**************************************************************
//				System.out.println("There is a new value received !!!!");
				try {
					NewConnectedEntity = UserAgentListener.getServerConnection().receiveNewMessage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				//***************************************************************
/*				this.userAgentUI = new UserAgentUI();
				this.userAgentUI.start();*/
				
				if(NewConnectedEntity)
				{
					this.userAgentUI.stop();
//					System.out.println("STOP");
					
					this.userAgentUI = new UserAgentUI();
					this.userAgentUI.start();
					
					/*UserAgentUI userAgentUI1 = new UserAgentUI();
					userAgentUI1.start();*/
				}

				
				
				
				//UserAgentUI.getInstance().start();
				//@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				
				//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				
				
				
				
			}
		}
	}

	
	public static ServerConnection getServerConnection()
	{
		return serverConnection;
	}
	public void setServerConnection(ServerConnection serverConnection)
	{
		this.serverConnection = serverConnection;
	}





}
