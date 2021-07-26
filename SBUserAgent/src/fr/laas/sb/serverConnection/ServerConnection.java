package fr.laas.sb.serverConnection;

import chat.client.ChatClient;

import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import fr.laas.sb.messages.ConnectedEntity;
import fr.laas.sb.messages.DWResponseMAx;
import fr.laas.sb.messages.DWResponseRemainingTime;
import fr.laas.sb.messages.DWResponseState;
import fr.laas.sb.messages.DeconnectedEntity;
import fr.laas.sb.messages.Message;
import fr.laas.sb.messages.PresenceResponse;
import fr.laas.sb.messages.ResponseConsPlug;
import fr.laas.sb.messages.ResponseInten;
import fr.laas.sb.messages.ResponseLuminosity;
import fr.laas.sb.messages.ResponsePowerCons;
import fr.laas.sb.messages.ResponsePowerProd;
import fr.laas.sb.messages.ResponseState;
import fr.laas.sb.messages.ResponseTempAirCond;
import fr.laas.sb.messages.ResponseTempThermometer;

public class ServerConnection {
	
	private ChatClient chatClient;
	private Vector<String> receiverUserVector;
	private Vector<String> messages; //contains all the messages received by the user agent
	
	private int newPositionVect = 0; //get the position of the new element that will be sent 
	 								//and added in the messages  Vector
	
	private boolean detectedValue = false;
	
	private boolean newConnectedEntityDetected = true;
	
	
	/** 
	 * Contain the message name and its Message class
	 * @author emna
	 */
	@SuppressWarnings("serial")
	private static Hashtable<String,Message> msgList=new Hashtable<String,Message>() {{
		
		put("presenceResponse", new PresenceResponse() );
		put("DWResponseStateon", new DWResponseState("on") );
		put("DWResponseStateoff", new DWResponseState("off") );
		put("ResponseMax", new DWResponseMAx() );
		put("ResponseRT", new DWResponseRemainingTime() );
		put("lumResponse", new ResponseLuminosity() );

		

		put("ResponseCons", new ResponseConsPlug() );


		put("ResponseStateon", new ResponseState("on") );
		put("ResponseStateoff", new ResponseState("off") );
		put("ResponseInten",  new ResponseInten());
		
		put("ResponseTempAirCond", new ResponseTempAirCond());
		
		put("ResponseTempThermometer", new ResponseTempThermometer());
				
		put("ResponsepowerProdPhotovoltaicPanel", new ResponsePowerProd());
		put("ResponsePowerCons", new ResponsePowerCons());

		put("connectedEntitiesStr", new ConnectedEntity() );
		put("deconnect", new DeconnectedEntity() );


		}};
	
	
	
	
	
	
	public boolean getNewConnectedEntityDetected()
	{
		return newConnectedEntityDetected;
	}
	public void setNewConnectedEntityDetected(boolean newConnectedEntityDetected)
	{ 
		this.newConnectedEntityDetected = newConnectedEntityDetected;
	}
	
	public ServerConnection(String configFile)
	{
		chatClient = new ChatClient();
		chatClient.setConfigFile(configFile);
		
		chatClient.connect();
		
		//verify file reading
		System.out.println("");
		System.out.println("**************** User Agent entity ******************");
		System.out.println("");
		System.out.println("**************** config0 file infos ******************");
		System.out.println("User : " + chatClient.getUser());
		System.out.println("Server : " + chatClient.getServer());
		System.out.println("Port : " + chatClient.getPort());
		System.out.println("Session : " + chatClient.getSession());
		System.out.println("*******************************************************");
		
		receiverUserVector = new Vector<String>();
		receiverUserVector.addElement("BuildingManagerUserSession");
			
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
	@SuppressWarnings("unused")
	private static String divNumber(String s)		
	{Boolean e=false;    
	String s2="";
	int i=0;
		
	
		while(e==false && i<s.length())
		{
			try
		{
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
	
	@SuppressWarnings("unused")
	public boolean receiveNewMessage() throws IOException
	{
		boolean NewConnectedEntity = false;
		
		//System.out.println("");
		//System.out.println("*** User Agent : RECEIVE Method ***");
		
		messages = chatClient.getMessages(); //The chatClient API (getMessages method) :
											//will display the msgs [user : msg] in the console
		
		//System.out.print("New message received by the user agent : ");
		
		String client = "", msg="";
		//extract the client and the msg
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
		    		//  System.out.println("-------------------------------------------");
		    		 // System.out.println("	=>Client : " + client);
		    	  }
		    	  else
		    		  if(elementPos==3)
		    		  {if(element.contains("From"))
		    		  {client=element.substring(element.indexOf("From")+4, element.length());
		    		  msg=element.substring(0, element.indexOf("From"));
		    		  }
		    		  else
		    			  msg = element;
		    			//  System.out.println("	=>Message : " + msg);
		    			 // System.out.println("-------------------------------------------");
			    	  }

		      }
		}
		
		newPositionVect = messages.size();
		//**************************************************************
		//extract the content of the msg and update the entities values
		//**************************************************************
		
		
		String msge=msg;
 		 String val="";
 		
 		if(msg.contains("deconnect"))
 		{ NewConnectedEntity = true;
 		this.newConnectedEntityDetected = true;
 		}
 			if(msg.contains("connectedEntitiesStr"))
 			{ msge="connectedEntitiesStr";
 			  val=msg.substring(20, msg.length());
 			  NewConnectedEntity = true;
 		      this.newConnectedEntityDetected = true;
 			}
 		      else
 		      { boolean ver=false;
 		    	  if(client.contains("is"))
 		    		  {try{ Double .parseDouble(client.substring(client.indexOf("is")+2,client.indexOf("is")+3 ));
 		    		  
 		    		  ver=true;}
 		    	  
 		    		  
 		    	  catch (Exception e ){}
 		    		  }
 		    	 if(divNumber(msg)!="")
 	 			 {msge=msg.replace(divNumber(msg), "");
 	 			 val=divNumber(msg);
 	 			 }
 		    	 if(divNumber(client)!=""&& !ver)
 	 			 {	 	 			 val=divNumber(client);
 		    		 client=client.replace(divNumber(client), "");
 			 }
 		    	  
 		    	 if(ver)
  				{  
  					val=client.substring(client.indexOf("is")+2, client.length());
  					String st="is"+val;
		    		 client=client.replace(st, "");
}
 		    	
 		    	  
 		    
 		    	 
 		    	
 		      }
 			
 		
		
//System.out.println("msg est : "+msge+" client : "+client+" val est :"+val);
		try{msgList.get(msge).handle(client,val);}
		catch(Exception e){}
	
//		System.out.println("newConnectedEntity disp in ServerConnect receivMsg (END) : " + newConnectedEntityDetected);
		return NewConnectedEntity;
	}
	
	
	public boolean valueSentDetection()
	{
		
		if(newPositionVect<chatClient.getMessages().size())
			detectedValue = true;
		else
			detectedValue = false;
		
		return detectedValue;
			
	}
	
	
	
	//******* send requests *********************************************

	public void sendMsgToBM(String msg){
		this.sendToBuildingManager(msg);

	} 
}