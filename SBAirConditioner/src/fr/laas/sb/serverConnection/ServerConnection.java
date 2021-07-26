package fr.laas.sb.serverConnection;

import chat.client.ChatClient;

import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import fr.laas.sb.messages.Message;
import fr.laas.sb.messages.TempsRequest;
import fr.laas.sb.messages.UserTempValue;

public class ServerConnection {
	
	private ChatClient chatClient;
	private Vector<String> receiverUserVector;
	private Vector<String> messages; //contains all the messages received by the air conditioner
	private int newPositionVect = 0; //get the position of the new element that will be sent 
	 								//and added in the messages  Vector
	
	private boolean detectedValue = false;
	/** 
	 * Contain the message name and its Message class
	 * @author emna
	 */
	
	@SuppressWarnings("serial")
	private static Hashtable<String,Message> msgList=new Hashtable<String,Message>() {{
		put("tempAirCondRequest", new TempsRequest() );
		put("userTempAirCondValue",  new UserTempValue());}};
	public ServerConnection(String configFile)
	{
		chatClient = new ChatClient();
		chatClient.setConfigFile(configFile);
		
		chatClient.connect();
		
		//verify file reading
		System.out.println("");
		System.out.println("**************** Air Conditioner entity ******************");
		System.out.println("");
		System.out.println("**************** config0 file infos ******************");
		System.out.println("User : " + chatClient.getUser());
		System.out.println("Server : " + chatClient.getServer());
		System.out.println("Port : " + chatClient.getPort());
		System.out.println("Session : " + chatClient.getSession());
		System.out.println("*******************************************************");
		
		receiverUserVector = new Vector<String>();
		receiverUserVector.addElement("BuildingManager");
		
		
		//inform the building manager that the air conditioner is connected
		this.sendToBuildingManager("AirConditionerConnected");
		//send the ip adress to the building manager
				//this.sendToBuildingManager("ip"+chatClient.getServer());
				//this.sendToBuildingManager("ipAC192.168.0.112");
	}
	
	
	
	public void sendToBuildingManager(String msg)
	{
		chatClient.send(msg, receiverUserVector); //The chatClient API (send method) :
													//will display the msg [user : msg] in the console
		
	}
	
	
	public String receiveNewMessage()
	{
		//System.out.println("");
		//System.out.println("*** Air Conditioner : RECEIVE Method ***");
		
		messages = chatClient.getMessages(); //The chatClient API (getMessages method) :
											//will display the msgs [user : msg] in the console
		
		//System.out.print("New message received by the air conditioner : ");
		
		String client, msg="";
		for (int i=newPositionVect; i < messages.size(); i++)
		{
			//display the message received
			//System.out.println("Imen Display => msg " + i + " => " + messages.elementAt(i));
			
		    //*****************************************************************************************
		    //********* divide the message received to extract the user & its message ******************
		    //*****************************************************************************************
			String element; //the received message is composed of 3 elements : 
			//					"element1(client) element2(:) element3(msg)" ==> [client : msg]
			client ="";  msg = "";
			int elementPos = 0;
			
			StringTokenizer st = new StringTokenizer (messages.elementAt(i)," ");
		      while (st.hasMoreTokens())
		      {
		    	  element = st.nextToken();
		    	  elementPos++;
		    	  //System.out.println("element : -" + element + "-");
		    	  
		    	  if(elementPos==1)
		    	  {
		    		  client = element;
		    		 // System.out.println("-------------------------------------------");
		    		  //System.out.println("	=>Client : " + client);
		    	  }
		    	  else
		    		  if(elementPos==3)
		    		  {
		    			  msg = element;
		    			  //System.out.println("	=>Message : " + msg);
			    	  }
		      }
		}
		
		newPositionVect = messages.size();
		return msg;
	}
	
	
	public boolean valueSentDetection()
	{
		
		if(newPositionVect<chatClient.getMessages().size())
			detectedValue = true;
		else
			detectedValue = false;
		
		return detectedValue;
			
	}
	public void manageMessages(String msge,String val)
	{ try{
		msgList.get(msge).handle(val);
		}
		catch(Exception e){}}
	
	

}