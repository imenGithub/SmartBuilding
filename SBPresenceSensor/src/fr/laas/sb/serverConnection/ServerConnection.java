package fr.laas.sb.serverConnection;

import chat.client.ChatClient;

import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import fr.laas.sb.messages.Alive;
import fr.laas.sb.messages.MeasureChange;
import fr.laas.sb.messages.Message;
import fr.laas.sb.messages.PrecisionChange;
import fr.laas.sb.messages.SendingChange;

public class ServerConnection {
	
	private static ChatClient chatClient;
	public static ChatClient getChatClient() {
		return chatClient;
	}



	public void setChatClient(ChatClient chatClient) {
		this.chatClient = chatClient;
	}


	private Vector<String> receiverUserVector;
	private Vector<String> messages; //contains all the messages received by the thermometer
	private int newPositionVect = 0; //get the position of the new element that will be sent 
	 								//and added in the messages  Vector
	
	private boolean detectedValue = false;
	
	
	/** 
	 * Contain the message name and its Message class
	 * @author Imen
	 */
	@SuppressWarnings("serial")
	private static Hashtable<String,Message> msgList=new Hashtable<String,Message>() {{

		put("precision", new PrecisionChange() );
		put("measure", new MeasureChange() );
		put("areYouAlivePS", new Alive() );

		put("sending",  new SendingChange());}};
		
	
	public ServerConnection(String configFile)
	{
		chatClient = new ChatClient();
		chatClient.setConfigFile(configFile);
		
		chatClient.connect();
		
		//verify file reading
		System.out.println("");
		System.out.println("**************** PresenceSensor entity ******************");
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
		this.sendToBuildingManager("PresenceSensorConnected");
		
	}
	
	
	
	public void sendToBuildingManager(String msg)
	{
		chatClient.send(msg, receiverUserVector); //The chatClient API (send method) :
													//will display the msg [user : msg] in the console
		
	}
	
	

	/*
	 * 
	 * @author emna
	 * @param string 
	 * @return number that exist in the string 
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
	
	
	
	
	public String receiveNewMessage()
	{
		//System.out.println("");
		//System.out.println("*** Thermometer : RECEIVE Method ***");
		
		messages = chatClient.getMessages(); //The chatClient API (getMessages method) :
											//will display the msgs [user : msg] in the console
		
		
		@SuppressWarnings("unused")
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
		    		  //System.out.println("-------------------------------------------");
		    		  //System.out.println("	=>Client : " + client);
		    	  }
		    	  else
		    		  if(elementPos==3)
		    		  {
		    			  msg = element;
		    			  //System.out.println("	=>Message : " + msg);
		    			  //System.out.println("-------------------------------------------");
			    	  }
		      }
		}
		
		newPositionVect = messages.size();
		
		String msge=msg;
		 String val="";
		if(divNumber(msg)!="")
			 {msge=msg.replace(divNumber(msg), "");
			 val=divNumber(msg);
			 }
		try{
		msgList.get(msge).handle(val);
		}
		catch(Exception e){}

		
						
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
	
	
	

}