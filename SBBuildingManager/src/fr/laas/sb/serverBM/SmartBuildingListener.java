package fr.laas.sb.serverBM;

import chat.client.ChatClient;
import fr.laas.sb.serverBM.clientConnection.ClientConnection;

public class SmartBuildingListener extends Thread {
	private  static ClientConnection clientConnection;
	private static ChatClient chatClientDeviceSession;
	private static ChatClient chatClientUserSession;
	public static String connectedEntitiesString = "connectedEntitiesStr";
	private FileRW file;
	
	/**
	 * Constructor of the class, defining the thread as Daemon
	 * and connecting the Building Manager to the CM
	 */
	
	public SmartBuildingListener(FileRW f)
	{this.file=f;

		
		clientConnection = new ClientConnection(f);
		
		//connect the building manager to the device session
		//setChatClientDeviceSession(clientConnection.connectClient("/home/marwa/pfe/SBBuildingManager/config/config0"));
		setChatClientDeviceSession(clientConnection.connectClient("/home/emna/Bureau/Master/Smart Building/SBBuildingManager/config/config0"));
		
		//connect the building manager to the user session
		//setChatClientUserSession(clientConnection.connectClient("/home/marwa/pfe/SBBuildingManager/config/config1"));
		setChatClientUserSession(clientConnection.connectClient("/home/emna/Bureau/Master/Smart Building/SBBuildingManager/config/config1"));
	}
	
	
	/**
	 * A method listening to the new values sent by the entities and the UA
	 * 
	 */
	public void run ()
	{				


		while (true)
		{ //listening
			
			boolean entityDetectedValue = clientConnection.valueSentFromDeviceSessionDetection();
			if (entityDetectedValue  == true)//if the BM gets a new message from an entity
			{
				//System.out.println("There is a new value received !!!!");
				String msg=clientConnection.receiveNewMessage(getChatClientDeviceSession()); 
			
			}
			
			
		}
			                                          


	}
	public  static ClientConnection getClientConnection() {
		return clientConnection;
	}


	public void setClientConnection(ClientConnection clientConnection) {
		this.clientConnection = clientConnection;
	}

	public static ChatClient getChatClientDeviceSession() {
		return chatClientDeviceSession;
	}


	public void setChatClientDeviceSession(ChatClient chatClientDeviceSession) {
		this.chatClientDeviceSession = chatClientDeviceSession;
	}


	public static ChatClient getChatClientUserSession() {
		return chatClientUserSession;
	}


	public static void setChatClientUserSession(ChatClient chatClientUserSession) {
		SmartBuildingListener.chatClientUserSession = chatClientUserSession;
	}
}
