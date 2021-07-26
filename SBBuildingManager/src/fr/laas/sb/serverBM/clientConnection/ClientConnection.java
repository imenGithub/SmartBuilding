package fr.laas.sb.serverBM.clientConnection;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

//import test.TestBuildingManager;

import chat.client.ChatClient;
import fr.laas.sb.serverBM.FileRW;
import fr.laas.sb.serverBM.SmartBuildingListener;
import fr.laas.sb.serverBM.SmartBuildingManager;
import fr.laas.sb.serverBM.client.UserAgent;
import fr.laas.sb.serverBM.client.entities.AirConditioner;
import fr.laas.sb.serverBM.client.entities.Dishwasher;
import fr.laas.sb.serverBM.client.entities.Component;
import  fr.laas.sb.serverBM.client.entities.Lamp;
import fr.laas.sb.serverBM.client.entities.LuminositySensor;
import fr.laas.sb.serverBM.client.entities.PhotovoltaicPanel;
import fr.laas.sb.serverBM.client.entities.PowerSmartMeter;
import fr.laas.sb.serverBM.client.entities.PresenceSensor;
import fr.laas.sb.serverBM.client.entities.SmartPlug;
import fr.laas.sb.serverBM.client.entities.Thermometer;
import fr.laas.sb.serverBM.messages.Alive;
import fr.laas.sb.serverBM.messages.Connection;
import fr.laas.sb.serverBM.messages.ConsPlugRequest;
import fr.laas.sb.serverBM.messages.DWRemainingTimeRequest;
import fr.laas.sb.serverBM.messages.DWResponseMax;
import fr.laas.sb.serverBM.messages.DWResponseRemainingTime;
import fr.laas.sb.serverBM.messages.DWResponseState;
import fr.laas.sb.serverBM.messages.DwMaxRequest;
import fr.laas.sb.serverBM.messages.DwStateRequest;
import fr.laas.sb.serverBM.messages.LampIntensityRequest;
import fr.laas.sb.serverBM.messages.LampSendingValue;
import fr.laas.sb.serverBM.messages.LampStateRequest;
import fr.laas.sb.serverBM.messages.LuminosityRequest;
import fr.laas.sb.serverBM.messages.Manual;
import fr.laas.sb.serverBM.messages.Message;
import fr.laas.sb.serverBM.messages.PhPanelMeasurePeriodValue;
import fr.laas.sb.serverBM.messages.PhPanelProdOrSendPeriod;
import fr.laas.sb.serverBM.messages.PowerConsRequest;
import fr.laas.sb.serverBM.messages.PowerProdRequest;
import fr.laas.sb.serverBM.messages.PrecisionPhPanelValue;
import fr.laas.sb.serverBM.messages.PrecisionThValue;
import fr.laas.sb.serverBM.messages.PresenceRequest;
import fr.laas.sb.serverBM.messages.PsmSendPeriod;
import fr.laas.sb.serverBM.messages.ResponsLumSensor;
import fr.laas.sb.serverBM.messages.ResponseInten;
import fr.laas.sb.serverBM.messages.ResponsePlug;
import fr.laas.sb.serverBM.messages.ResponsePowerCons;
import fr.laas.sb.serverBM.messages.ResponsePresenceSensor;
import fr.laas.sb.serverBM.messages.ResponseState;
import fr.laas.sb.serverBM.messages.ResponseTempAirCond;
import fr.laas.sb.serverBM.messages.ResponseTempThermometer;
import fr.laas.sb.serverBM.messages.ResponsepowerProdPhotovoltaicPanel;
import fr.laas.sb.serverBM.messages.TempAirCondRequest;
import fr.laas.sb.serverBM.messages.TempThermometerRequest;
import fr.laas.sb.serverBM.messages.ThMeasurePeriodValue;
import fr.laas.sb.serverBM.messages.ThTempOrSendPeriod;
import fr.laas.sb.serverBM.messages.UserDWMaxValue;
import fr.laas.sb.serverBM.messages.UserDWStateValue;
import fr.laas.sb.serverBM.messages.UserLampIntenValue;
import fr.laas.sb.serverBM.messages.UserLampStateValue;
import fr.laas.sb.serverBM.messages.UserTempAirCondValue;

public class ClientConnection {
	
	//ChatClient chatClient;
	
	/**
	 * ManualMode=on means that devices are automatically adjusted using presence and luminosity sensors
	 * ManualMode=on means that devices are manually adjusted by the user
	 */
	private String ManualMode="OFF";
	private static FileRW file;
	private String plugBigConsumer="";
	private Double greatCons=0.0;
	
	// contains name of the name of the attached device=> name SmartPlug
	private  Hashtable<String, String> devicePlugList=new Hashtable<String, String>() ;
	
	// contains name of the SmartPlug=> the value of consumed energy
	private  Hashtable<String, Double> plugConsListValues=new Hashtable<String, Double>() ;
	
	
	// contains name of the Presence sensor=> name of the attached devices
	private Hashtable<String, Vector> devicePresenceSensorList=new Hashtable<String,Vector>() {{
		put("BedroomPresenceSensor",new Vector<Component>());
		put("KitchenPresenceSensor1",new Vector<Component>());
		put("KitchenPresenceSensor2",new Vector<Component>());
		put("LivingRoomPresenceSensor1",new Vector<Component>());
		put("LivingRoomPresenceSensor2",new Vector<Component>());
		put("BathroomPresenceSensor",new Vector<Component>());

	}};	

	// contains name of the Luminosity sensor=> name of the attached devices
	private static Hashtable<String, Vector> deviceLumSensorList=new Hashtable<String,Vector>() {{
		put("BedroomLuminositySensor",new Vector<Component>());
		put("KitchenLuminositySensor1",new Vector<Component>());
		put("KitchenLuminositySensor2",new Vector<Component>());
		put("LivingRoomLuminositySensor1",new Vector<Component>());
		put("LivingRoomLuminositySensor2",new Vector<Component>());
		put("BathroomLuminositySensor",new Vector<Component>());

	}};



	
	
	
	//demanded luminosity for each luminositySensor in lux
	private static Hashtable<String, Double> valLumSensorList=new Hashtable<String,Double>() {{
		put("BedroomLuminositySensor",120.0);
		put("KitchenLuminositySensor1",100.0);
		put("KitchenLuminositySensor2",100.0);
		put("LivingRoomLuminositySensor1",90.0);
		put("LivingRoomLuminositySensor2",90.0);
		put("BathroomLuminositySensor",80.0);

	}};	
	
	//Area of each room in square meter
	private static Hashtable<String, Double> roomsArea=new Hashtable<String,Double>() {{
		put("Bedroom",8.0);
		put("Kitchen",12.0);
		put("LivingR",16.0);
		put("Bathroo",6.0);

	}};
	
	// if there is no occupant in the room (presence sensor=0) then luminosity sensor of the room is deactivated name=> false
	private static Hashtable<String, Boolean> lumSensorActivated=new Hashtable<String,Boolean>() {{
		put("Bedroom",false);
		put("Kitchen",false);
		put("LivingR",false);
		put("Bathroo",false);

	}};
	
	
// vector that contains all the connected devices
	@SuppressWarnings({ "rawtypes", "serial" })
	private static Hashtable<String, Vector> componentList=new Hashtable<String,Vector>() {{
		put("Lamp",new Vector<Component>());
		put("AirConditioner",new Vector<Component>());
		put("Thermometer",new Vector<Component>());
		put("PowerSmartMeter",new Vector<Component>());
		put("PhotovoltaicPanel",new Vector<Component>());
		put("Dishwasher",new Vector<Component>());
		put("SmartPlug",new Vector<Component>());
		put("LuminositySensor",new Vector<Component>());
		put("PresenceSensor",new Vector<Component>());
		put("UserAgent",new Vector<Component>());

	
	}};
	/** 
	 * Contain the message name and its Message class
	 * @author emna
	 */
	@SuppressWarnings("serial")
	private static  Hashtable<String,Message> msgList=new Hashtable<String,Message>() {{
		
		put("manualON",  new Manual("ON"));
		put("manualOFF",  new Manual("OFF"));

		
		//msg of presence sensor
		put("PresenceSensorConnected",  new Connection("PresenceSensor"));
		put("ResponsePresenceSensor",  new ResponsePresenceSensor());
		put("ImAlivePS",  new Alive("PresenceSensor"));

			
		//msgs of dishwasher
		put("DishwasherConnected",  new Connection("Dishwasher")); 
		put("DWResponseStateon", new DWResponseState("on") );
		put("DWResponseStateoff", new DWResponseState("off") );
		put("ResponseMax",  new DWResponseMax());
		put("ResponseRT",  new DWResponseRemainingTime());

		//msgs of smartPlug
		put("SmartPlugConnected", new Connection("SmartPlug"));
    	put("ResponseConsEntity",new ResponsePlug());
    	put("ImAliveSmartPlug", new Alive("SmartPlug"));
		
		//msgs of Lamp
		put("s",  new LampSendingValue());
		put("ResponseStateon", new ResponseState("on") );put("ResponseStateoff", new ResponseState("off") );
		put("ResponseInten",  new ResponseInten(null));
		put("AdResponseInten",  new ResponseInten("Ad"));

		put("LampConnected", new Connection("Lamp"));
	
		//msgs of AC 
		put("ResponseTempAirCond", new ResponseTempAirCond(null));
		put("AdResponseTempAirCond", new ResponseTempAirCond("Ad"));

		put("AirConditionerConnected", new Connection("AirConditioner"));
	
		
		//msgs of Th
		put("tOrSTh", new ThTempOrSendPeriod()); put("ThermometerConnected", new Connection("Thermometer"));
		put("pTh", new PrecisionThValue());put("m", new ThMeasurePeriodValue());
		put("ResponseTempThermometer", new ResponseTempThermometer());
		put("ImAliveTh", new Alive("Thermometer"));
	

		//msgs of LumSensor
		put("ResponseLumSensor", new ResponsLumSensor());
		put("LuminositySensorConnected", new Connection("LuminositySensor"));
		put("ImAliveLS", new Alive("LuminositySensor"));

		
		//msgs of PhotoPanel
		put("Ph", new PhPanelProdOrSendPeriod());
		put("PhotovoltaicPanelConnected", new Connection("PhotovoltaicPanel"));
		put("pPanel", new PrecisionPhPanelValue());
		put("mPanel", new PhPanelMeasurePeriodValue());
		put("ResponsepowerProdPhotovoltaicPanel", new ResponsepowerProdPhotovoltaicPanel());
		put("ImAlivePanel", new Alive("PhotovoltaicPanel"));
		
		
		//msgs of PSm
		put("PSMPeriod", new PsmSendPeriod());put("c", new ResponsePowerCons());
		put("ResponsePowerCons", new ResponsePowerCons());
		put("PowerSmartMeterConnected", new Connection("PowerSmartMeter"));
		put("ImAlivePSM", new Alive("PowerSmartMeter"));
	
		//msgs of userAgent 
		put("powerProdRequest", new PowerProdRequest());
		put("powerConsRequest", new PowerConsRequest());
		
		put("tempAirCondRequest", new TempAirCondRequest());
		put("userTempAirCondValue", new UserTempAirCondValue());

		
		put("tempThermometerRequest", new TempThermometerRequest());
		
		put("lampStateRequest", new LampStateRequest());put("lampIntensityRequest", new LampIntensityRequest());
		
		put("userLampStateValueon", new UserLampStateValue("on"));put("userLampStateValueoff", new UserLampStateValue("off"));
		put("userLampIntenValue", new UserLampIntenValue());
		put("lampStateRequest", new LampStateRequest());
		
		put("ConsRequest", new ConsPlugRequest());
		
		
		put("DwStateRequest", new DwStateRequest());
		put("DwMaxRequest", new DwMaxRequest());
		put("userDWStateValueon", new UserDWStateValue("on"));
		put("userDWStateValueoff", new UserDWStateValue("off"));
		put("userDWMaxValue", new UserDWMaxValue());
		put("RTRequest", new DWRemainingTimeRequest());
		
		put("luminosityRequest", new LuminosityRequest());
		
		put("presenceRequest", new PresenceRequest());

		
		put("UserAgentConnected", new Connection("UserAgent"));
	

	
	}};
	
	private Vector<String> messages;
	private int newPositionVectForDeviceSession = 0; //get the position of the new element that will be sent 
									 				//and added in the messages  Vector in the device session
	
	//private int newPositionVectForUserSession = 0; //get the position of the new element that will be sent 
	 											//and added in the messages  Vector in the user session
	
	private boolean detectedValueDeviceSession = false;
	//private boolean detectedValueUserSession = false;
	public Hashtable<String, Message> getMsgList() {
		return msgList;
	}


	public void setMsgList(Hashtable<String, Message> msgList) {
		ClientConnection.msgList = msgList;
	}







	

	

	
	
	
	
	
	public Hashtable<String, Vector> getDevicePresenceSensorList() {
		return devicePresenceSensorList;
	}


	public  void setDevicePresenceSensorList(
			Hashtable<String, Vector> devicePresenceSensorList) {
		devicePresenceSensorList = devicePresenceSensorList;
	}


	

	public Hashtable<String, Vector> getDeviceLumSensorList() {
		return this.deviceLumSensorList;
	}


	public void setDeviceLumSensorList(
			Hashtable<String, Vector> deviceLumSensorList) {
		deviceLumSensorList = deviceLumSensorList;
	}


	public  Hashtable<String, String> getDevicePlugList() {
		return this.devicePlugList;
	}
	

	public  void setDevicePlugList(Hashtable<String, String> devicePlugList) {
		this.devicePlugList = devicePlugList;
	}	
	
	/**
	 * Return the entities having a specific type
	 * @author emna
	 * @param listname the name of the entities' type
	 * @return the entities having the corresponding type
	 */
	@SuppressWarnings("unchecked")
	public Vector<Component> findComponentsByName(String listName)
	{ try
	{ return getComponentList().get(listName);}
	catch(Exception e){}
	
					return null;
		}
	
	/**
	 * Return the entity having a specific name
	 * @author emna
	 * @param name the name of the entity' type
	 * @param name the name of the searched entity 
	 * @return the entity having the corresponding name
	 */
	@SuppressWarnings("unchecked")
	public Component findComponentByName(String listName,String name)
	{ 
		Vector<Component> entities=getComponentList().get(listName);
	if(entities!=null)
		{for(int i=0;i<entities.size();i++)
		if(entities.get(i).getName().equalsIgnoreCase(name))
			return entities.get(i);
		}
	return null;
	}
	
	public Component findEntityByOneName(String name)
	{ 
		if(getComponentList()!=null)
		{for(int i=0;i<getComponentList().size();i++)
			{if(getComponentList().get(i)!=null)
			{for(int j=0;j<getComponentList().get(i).size();j++)
				if(((Component)getComponentList().get(i).get(j)).getName().equalsIgnoreCase(name))
					return (Component) getComponentList().get(i).get(j);
			}
			
			}
		}
	return null;
	}
	
	public LuminositySensor getLuminositySensor(String nameLS)
	{ return (LuminositySensor)findComponentByName("LuminositySensor",nameLS);
	}
	
	
	
	public SmartPlug getSmartPlug(String nameSP)
	{ return (SmartPlug)findComponentByName("SmartPlug",nameSP);
	}
	public Dishwasher getDW(String nameDW)
	{ return (Dishwasher)findComponentByName("Dishwasher",nameDW);
	}
	public UserAgent getUserAgentPreferences(String nameUA)
	{ return (UserAgent)findComponentByName("UserAgent",nameUA);
	}
	public Lamp getLamp(String nameLamp)
	{ return (Lamp)findComponentByName("Lamp",nameLamp);
	}
	public AirConditioner getAirConditioner(String nameAC)
	{ return (AirConditioner)findComponentByName("AirConditioner",nameAC);

	}
	public Thermometer getThermometer(String nameTh)
	{ return (Thermometer)findComponentByName("Thermometer",nameTh);
	}
	public PresenceSensor getPresenceSensor(String namePs)
	{ return (PresenceSensor)findComponentByName("PresenceSensor",namePs);
	}
	public PowerSmartMeter getPowerSmartMeter(String namePSM)
	{ return (PowerSmartMeter)findComponentByName("PowerSmartMeter",namePSM);


	}
	public PhotovoltaicPanel getPhotovoltaicPanel(String namePP) {
		return (PhotovoltaicPanel)findComponentByName("PhotovoltaicPanel",namePP);


	}
	
/**
 * Initialize entities' that exists with one instance
 * @author emna	
 */
	@SuppressWarnings("unchecked")
	public ClientConnection(FileRW f)
	{   this.file=f;
Connection.setFile(f);
getComponentList().get("PowerSmartMeter").addElement(new PowerSmartMeter(f,"PowerSmartMeter",0, 29000,30000));
getComponentList().get("Thermometer").addElement(new Thermometer(f,"Thermometer",0,29000,30000));
getComponentList().get("PhotovoltaicPanel").addElement(new PhotovoltaicPanel(f,"PhotovoltaicPanel",0,29000,30000));
getComponentList().get("UserAgent").addElement(new UserAgent("UserAgent"));

		
	}
	
	public ChatClient connectClient(String configFile)
	{
		ChatClient  chatClient = new ChatClient();
		chatClient.setConfigFile(configFile);
		
		//connecting the building manager to the CM of the Device session
		chatClient.connect();
		
		//verify file reading 
		System.out.println("");
		System.out.println("**************** Building Manager ******************");
		System.out.println("**************** config file infos ******************");
		System.out.println("User : " + chatClient.getUser());
		System.out.println("Server : " + chatClient.getServer());
		System.out.println("Port : " + chatClient.getPort());
		System.out.println("Session : " + chatClient.getSession());
		System.out.println("*******************************************************");
		System.out.println("");
		
		return chatClient;
	}
	
	
	public  void sendToEntity(String msg,ChatClient chatClient,String componentName)
	{Vector<String> receiverUserVector = new Vector<String>();
	receiverUserVector.addElement(componentName);
	chatClient.send(msg, receiverUserVector); }
	
	

	
	
	public void receiveNewMessage()
	{
		
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
		{	if(s.substring(i, i+1).equals("-"))
			s2=s.substring(i, s.length());
		else
		{
		    double num = Double.parseDouble(s.substring(i, i+1));
		    s2=s.substring(i, s.length());}
		e=true;    
		}
			catch(NumberFormatException nfe1){
						i++;

			}
		
		
			}
		
		return s2;
	

		}
	
	
	
	
	
	
	
	
	
	public String receiveNewMessage(ChatClient chatClient)
	{
		String  msg = "";
		messages = chatClient.getMessages(); //The chatClient API (getMessages method) :
											//will display the msgs [user : msg] in the console
		
		int newPositionVect = 0;
		
			newPositionVect = this.newPositionVectForDeviceSession;
			//System.out.println("");
			//System.out.println("*** Building manager : RECEIVE Method  ***");//(Device session )
		
		
		//System.out.print("New message received by the BM : ");
		
			
			
		for (int i=newPositionVect; i < messages.size(); i++)
		{   
			//display the message received
			//System.out.println("Imen Display => msg " + i + " => " + messages.elementAt(i));
			
		    //*****************************************************************************************
		    //********* divide the message received to extract the user & its message ******************
		    //*****************************************************************************************
			String element,client = ""; //the received message is composed of 3 elements : 
			//					"element1(client) element2(:) element3(msg)" ==> [client : msg]
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
//		    			  System.out.println("	=>client : " + client);
		    			  //System.out.println("---------------------------------------");
			    	  }
			    	  // msg for which entity
		    		  if(elementPos==3)
		    		  {	if(element.contains("For"))
		    		  {client=element.substring(element.indexOf("For")+3, element.length());
		    		  msg=element.substring(0, element.indexOf("For"));
		    		  }
		    		  else
		    			  msg = element;
//		    			  System.out.println("	=>Message : " + msg);
		    			  //System.out.println("---------------------------------------");
			    	  }
		    		 

		      }
		      String msge=msg;
		  		 String val="";
		  		if(divNumber(msg)!="")
		  			 {msge=msg.replace(divNumber(msg), "");
		  			 val=divNumber(msg);
		  			 }
		  			 //pour element=>Lamp : ip172.55 val=>172.55 et msge=>Lamp : ip
		  		//System.out.println("msge=> "+msge+" val=> "+val+" msgeEnv=> "+msg+" from client connection");

		  		ClientConnection.manageMessages(client,msge, val, msg);
		  		
		      
		      
		    
		      
			
		}
		
			this.newPositionVectForDeviceSession = messages.size();
			return msg;
		}
	
	//detect if there is a new message sent by an entity
	public boolean valueSentFromDeviceSessionDetection()
	{
		//System.out.println("size device msgs : " + SmartBuildingListener.chatClientDeviceSession.getMessages().size());
		if(this.newPositionVectForDeviceSession< SmartBuildingListener.getChatClientDeviceSession().getMessages().size())
		{
			detectedValueDeviceSession = true;
			//System.out.println("size device msgs : " + SmartBuildingListener.getChatClientDeviceSession().getMessages().size());
			//for(int i=0; i<SmartBuildingListener.getChatClientDeviceSession().getMessages().size(); i++)
				//System.out.println("elem vect " + i + " ==> " + SmartBuildingListener.getChatClientDeviceSession().getMessages().elementAt(i));
		}
		else
			detectedValueDeviceSession = false;
		
		return detectedValueDeviceSession;
			
	}

	
	@SuppressWarnings("unchecked")
	public void setUserAgentPreferences(String nameUA,UserAgent userPreferences) {
		getComponentList().get("UserAgent").remove(this.getUserAgentPreferences(nameUA));
		getComponentList().get("UserAgent").addElement(userPreferences);
	}
	
	/**
	 * deconnect the entity having a specific type and name
	 * @author emna
	 * @param listName
	 * @param componentName
	 */
	public void deconnect(String listName,String componentName) {
SmartBuildingListener.connectedEntitiesString=SmartBuildingListener.connectedEntitiesString.replace(componentName+"*", "");
//System.out.println("nv   "+SmartBuildingListener.connectedEntitiesString);
//System.out.println(this.findEntityByName(componentName).getConnState());
this.findComponentByName(listName,componentName).deconnetComponent();	
//System.out.println(this.findComponentByName(listName,componentName).getConnState());
//send a message to the UserAgent to inform him
this.sendToEntity("deconnectFrom"+componentName, SmartBuildingListener.getChatClientUserSession(),"UserAgent");		

}
	public Hashtable<String, Vector> getComponentList() {
		return componentList;
	}
	public  void setComponentList(Hashtable<String, Vector> componentList) {
		ClientConnection.componentList = componentList;
	}

	/**
	 * associate a device to a Smart plug with this manner: BedroomLamp to SmartPlugBedroomLamp (ends with name of the device) 
	 * @param name name of the device
	 * @param listName type of the device
	 */
	public void associateSmartPug(String name,String listName) {
Component e=this.findComponentByName(listName, name);
Vector<Component> listPlug=this.findComponentsByName("SmartPlug");		
if(listPlug!=null)
{for(int i=0;i<listPlug.size();i++)
	if(listPlug.get(i).getName().endsWith(name))
	{devicePlugList.put(name, listPlug.get(i).getName());
	((SmartPlug) listPlug.get(i)).setState("on");
	this.sendToEntity("stateChangeOn", SmartBuildingListener.getChatClientDeviceSession(),listPlug.get(i).getName());
	//System.out.println(name+ " associated to "+listPlug.get(i).getName() );

	}
}
}




	public Hashtable<String, Double> getPlugConsListValues() {
		return this.plugConsListValues;
	}
	/**
	 * 
	 * @return the device that is the biggest consumer of energy
	 */
	
	public Component getBigConsumer()
	{			if(plugBigConsumer!="")
	{
				String deviceName=plugBigConsumer.replace("SmartPlug", "");
				
if(deviceName.contains("Lamp"))
return this.findComponentByName("Lamp", deviceName);
else
	return this.findComponentByName("AirConditioner", deviceName);

	}
	return null;
	}


	public void setPlugConsListValues(Hashtable<String, Double> plugCon1sListValues) {
		this.plugConsListValues = plugConsListValues;
	}
	
	/**
	 * associate presence sensor=> device with this manner: BedroomLamp => BedroomPresenceSensor because when we delete lamp from the first string and PresenceSensor from the second the two strings become the same)  
	 * @param name name of the device
	 * @param listName type of the device
	 */
	@SuppressWarnings("unchecked")
	public void associatePresenceSensor(String name,String listName) {
		Vector<Component> e=this.findComponentsByName("PresenceSensor");
		String rem,rem1="";
		if(listName=="Lamp")
			{for(int i=0;i<e.size();i++)
				{rem=e.get(i).getName().replace("PresenceSensor", "");
				rem1=name.replace("Lamp", "");
				if(rem.equalsIgnoreCase(rem1))
				{		
					Lamp l=(Lamp) this.findComponentByName(listName, name);
					devicePresenceSensorList.get(e.get(i).getName()).addElement(l);
					//System.out.println(l.getName()+ " associated to "+e.get(i).getName() );
                                    
				}
				
		}
		}	
		if(listName=="AirConditioner")
		{for(int i=0;i<e.size();i++)
			{rem=e.get(i).getName().replace("PresenceSensor", "");
			rem1=name.replace("AirConditioner", "");
			if(rem.equalsIgnoreCase(rem1))
			{	devicePresenceSensorList.get(e.get(i).getName()).addElement(this.findComponentByName(listName, name));
			//System.out.println(devicePresenceSensorList.get(e.get(i).getName()));
			}
	}
	}	
		
}
	
	
	/**
	 * associate luminosity sensor=> device with this manner: BedroomLamp => BedroomLuminositySensor because when we delete lamp from the first string and LuminositySensor from the second the two strings become the same)  
	 * @param name name of the device
	 */
	public void associateLumSensor(String name) {
		Vector<Component> e=this.findComponentsByName("LuminositySensor");
		String rem,rem1="";
		for(int i=0;i<e.size();i++)
				{rem=e.get(i).getName().replace("LuminositySensor", "");
				rem1=name.replace("Lamp", "");
				if(rem.equalsIgnoreCase(rem1))
				{		
					Lamp l=(Lamp) this.findComponentByName("Lamp", name);
					deviceLumSensorList.get(e.get(i).getName()).addElement(l);
					//System.out.println(l.getName()+ " associated to"+e.get(i).getName() );
                                    
				}
				
		}
		}


	public static Hashtable<String, Double> getValLumSensorList() {
		return valLumSensorList;
	}


	public static void setValLumSensorList(Hashtable<String, Double> valLumSensorList) {
		ClientConnection.valLumSensorList = valLumSensorList;
	}


	public String getPlugBigConsumer() {
		return plugBigConsumer;
	}


	public void setPlugBigConsumer(String plugBigConsumer) {
		this.plugBigConsumer = plugBigConsumer;
	}


	public Double getGreatCons() {
		return greatCons;
	}


	public void setGreatCons(Double greatCons) {
		this.greatCons = greatCons;
	}	
	
	
	
	
	
	
	
    /** 
	 * Call the method handle of the corresponding message
	 * @author emna
	 * @param client to which message is going to be sent
	 * @param message the message name
	 * @param value the digital value attached to the message
	 * @param msgToSend the message to be sent
	 */
    public static void manageMessages(String client,String message,String value,String msgToSend){
    	try{if(!client.contains("BuildingManager"))
    	{ //System.out.println("client "+client+" value "+value+" msgtosend "+msgToSend);
    		msgList.get(message).handle(client,value,msgToSend);}
    	
    	}
    	catch (Exception e){}
    	
    	
    }


	public static Hashtable<String, Double> getRoomsArea() {
		return roomsArea;
	}


	public static void setRoomsArea(Hashtable<String, Double> roomsArea) {
		ClientConnection.roomsArea = roomsArea;
	}


	public static Hashtable<String, Boolean> getLumSensorActivated() {
		return lumSensorActivated;
	}


	public static void setLumSensorActivated(Hashtable<String, Boolean> lumSensorActivated) {
		ClientConnection.lumSensorActivated = lumSensorActivated;
	}


	public String getManualMode() {
		return ManualMode;
	}


	public void setManualMode(String manualMode) {
		ManualMode = manualMode;
	}
	
	
	
	
	
	
	
	
}
