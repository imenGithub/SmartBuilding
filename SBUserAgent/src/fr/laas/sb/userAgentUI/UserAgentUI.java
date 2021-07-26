package fr.laas.sb.userAgentUI;
import fr.laas.sb.entities.Component;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Vector;
import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgent.UserAgentListener;

public class UserAgentUI extends Thread{
	
	private static UserAgent userAgent;
	private static Vector<String> connectedEntitiesVector;
	private static Hashtable<Integer,String> connectedEntityList;
	/*
	 * @author Imen
	 * Contain possible entities
	 */
	
	

	public UserAgentUI()
	{
		if(userAgent == null)
		{
			userAgent = new UserAgent(); //=> connect + init entities
			//System.out.println("user agent null");
		}
		
		//instanciate the connectedEntitiesVector vector
		if(connectedEntitiesVector == null)
		{
			connectedEntitiesVector = new Vector<String>();
			//System.out.println("vector entity conn null");
		}
		
		
	}
	

	
	//Principal menu : the Smart Building menu
	public static int displayPrincipalMenu() 
	{
		//System.out.println("====> " + newConnectedEntityDetected);
		System.out.println("");
		//System.out.println("\u001B31;1mhello world!");
		
		System.err.println("*************************************** Smart Building Menu ***************************");
		if(connectedEntitiesVector.size()==0)
			System.err.println ("	There is no connected entity");
		else
		{ connectedEntityList=new Hashtable<Integer,String>() ;
		int i=0;
		if(search("LuminositySensor"))
		{System.err.println("   "+ (i+1) + ". " + "LuminositySensor");
		connectedEntityList.put(i+1, "LuminositySensor");
		i=i+1;
		}
		if(search("Dishwasher"))
		{System.err.println("   "+ (i+1) + ". " + "Dishwasher");
		connectedEntityList.put(i+1, "Dishwasher");
		i=i+1;
		}
			if(search("Lamp"))
			{System.err.println("   "+ (i+1) + ". " + "Lamp");
			connectedEntityList.put(i+1, "Lamp");
			i=i+1;
			}
			if(search("AirConditioner"))
			{
				System.err.println("   "+ (i+1) + ". " + "AirConditioner");
				connectedEntityList.put(i+1, "AirConditioner");
				i=i+1;

			}
			if(search("Thermometer"))
			{
				System.err.println("   "+ (i+1) + ". " + "Thermometer");
				connectedEntityList.put(i+1, "Thermometer");
				i=i+1;
			}
			if(search("PowerSmartMeter"))
			{
				System.err.println("   "+ (i+1) + ". " + "PowerSmartMeter");
				connectedEntityList.put(i+1, "PowerSmartMeter");
				i=i+1;
			}
				if(search("PhotovoltaicPanel"))
				{
				System.err.println("   "+ (i+1) + ". " + "PhotovoltaicPanel");
				connectedEntityList.put(i+1, "PhotovoltaicPanel");
				i=i+1;
				}
				if(search("PresenceSensor"))
				{
				System.err.println("   "+ (i+1) + ". " + "PresenceSensor");
				connectedEntityList.put(i+1, "PresenceSensor");
				i=i+1;
				}
				if(search("Plug"))
				{
				System.err.println("   "+ (i+1) + ". " + "Devices' consumption");
				connectedEntityList.put(i+1, "SmartPlug");
				i=i+1;
				}
				if(search("Sensor"))
				{
				System.err.println("   "+ (i+1) + ". " + "Manual mode ( Desactivate the presense and luminosity sensors)");
				connectedEntityList.put(i+1, "manual");
				}
		
				
		}
		System.err.println("***************************************************************************************");

		/*System.err.println("*************************************** Smart Building Menu ***************************");
		System.err.println("   1. Power Smart Meter ");
		System.err.println("   2. Air Conditioner ");
		System.err.println("   3. Thermometer ");
		System.err.println("   4. Lamp ");
		System.err.println("***************************************************************************************");*/
		
		//wait 0,1s
	
		
		int choice = 0;
		if(connectedEntitiesVector.size()>0)
		{
			System.out.println("");
			Scanner sc=null ;
			try{
			sc= new Scanner(System.in);
			System.out.println(" Type your choice number : ");
			}
			catch(Exception e){}
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			//while(UserAgent.serverConnection.getNewConnectedEntityDetected() == false)
			//{
				choice = sc.nextInt();
				
			//}
			//sc.close();
			
//			System.out.println("after scanner (reading console)");
			
			
/*			//inputStream method***********************************************
			String curLine = "";
			System.out.println(" Type your choice number : ");
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			curLine = in.readLine();
			System.out.println("Current line : " + curLine);
			choice = Integer.parseInt(curLine);
			//***********************************************************************
			*/
		}
		return choice;
	}
	
	
	public static boolean search(String chaine)
	{ for(int i=0;i<connectedEntitiesVector.size();i++)
		{	if(chaine.equals("SmartPlug"))
			{if(connectedEntitiesVector.elementAt(i).contains(chaine))
					return true;
			}
		
	if(!(chaine.equals("SmartPlug")))
	{	if(!connectedEntitiesVector.elementAt(i).contains("SmartPlug"))
		if(connectedEntitiesVector.elementAt(i).contains(chaine))
			return true;
	}
		}
	
	return false;
	}
	
	
	
	
	
	/*
	 * display and handle the choices of the chosen component
	 * @param the name of the component
	 * @author emna
	 */
//	public int displayComponentMenu(String component)
//	{      		return UserAgent.findEntityByName(sub,component).display();  }
	
	public static void manageMenus(String sub,int choice,int subChoice) 
	{	if( subChoice==00)
		firstDisplay(0);
	else if(sub.equals("manual"))
	{ if(subChoice==1)
		UserAgentListener.getServerConnection().sendMsgToBM("manualON");
	
	else
		 if(subChoice==2)
				UserAgentListener.getServerConnection().sendMsgToBM("manualOFF");
	displayListComponent(choice);

	}
		
	else{
		//get the name of the entity from indexChoice in the vector connectedEntitiesVector
		String entityName =  ((Component) UserAgent.getComponentList().get(sub).elementAt( subChoice-1)).getName();
		
		UserAgent.findEntityByName(sub, entityName).displayAndHandleChoices(choice,userAgent);
	}
		
		
			
				
	}
	
	
	public void run () 
	{
		while (true)
		{
			//System.out.println ("newConnectedEntity : " + UserAgent.serverConnection.getNewConnectedEntityDetected());

/*    		boolean entityDetectedValue = UserAgent.serverConnection.valueSentDetection();
			if (entityDetectedValue )
			{
				System.out.println("value sent ===> (UserAgentUI class)");
			}*/
			
//			try {
//				Thread.sleep(500); //0,5s
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
			
			int choice = 0;
			
			if(UserAgentListener.getServerConnection().getNewConnectedEntityDetected() == true)
			{ //System.out.println("hereee detected");
				//this.start();
				/*try {
					this.sleep(3000); //3s
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} */
				
				//System.out.println("(((((( newConnec ==> truuuue (disp in UserAgentUI RUN Thread)))))))");
				UserAgentListener.getServerConnection().setNewConnectedEntityDetected(false) ;
				
				
			UserAgentUI.firstDisplay(choice);
				
				
				/*int choice = 0;
				try {
					choice = this.displayPrincipalMenu();
					UserAgent.serverConnection.setNewConnectedEntityDetected(false) ;
					System.out.println(" newConnEntiBoolVar => " + UserAgent.serverConnection.getNewConnectedEntityDetected());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(choice != 0)
				{
					try {
						this.manageMenus(choice);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
			}
		}
	}



	private static void firstDisplay(int choice) {
//		System.out.println(" --- before display principal menu ");
		//System.out.println("first display");
		choice = UserAgentUI.displayPrincipalMenu();
		//UserAgent.serverConnection.setNewConnectedEntityDetected(false) ;
//		System.out.println(" --- after display principal menu ");
		
		//System.out.println("choice : " + choice);
	
		
	
		
		if(choice!=0)
		displayListComponent(choice);		
	}


	public static void displayListComponent(int choice) {
		String sub="";
		if(connectedEntityList.get(choice).equalsIgnoreCase("manual"))
			{sub="manual";
			System.err.println("1. ON"  );
			System.err.println("2. OFF"  );

			}
		if(connectedEntityList.get(choice).equalsIgnoreCase("LuminositySensor")) //lumSen
		{sub="LuminositySensor";
			for(int i=0; i< UserAgent.getComponentList().get("LuminositySensor").size(); i++)
			{	
				System.err.println("   "+ (i+1) + ". " + ((Component) UserAgent.getComponentList().get("LuminositySensor").get(i)).getName());
			}
		}
		
		
		
		if(connectedEntityList.get(choice).equalsIgnoreCase("PresenceSensor")) //lumSen
		{sub="PresenceSensor";
			for(int i=0; i< UserAgent.getComponentList().get("PresenceSensor").size(); i++)
			{	
				System.err.println("   "+ (i+1) + ". " + ((Component) UserAgent.getComponentList().get("PresenceSensor").get(i)).getName());
			}
		}
		
		
		
		if(connectedEntityList.get(choice).equalsIgnoreCase("Dishwasher")) //DW
		{sub="Dishwasher";
			for(int i=0; i< UserAgent.getComponentList().get("Dishwasher").size(); i++)
			{	
				System.err.println("   "+ (i+1) + ". " + ((Component) UserAgent.getComponentList().get("Dishwasher").get(i)).getName());
			}
		}
		
		if(connectedEntityList.get(choice).equalsIgnoreCase("SmartPlug")) //SB
		{sub="SmartPlug";
			for(int i=0; i< UserAgent.getComponentList().get("SmartPlug").size(); i++)
			{	
				System.err.println("   "+ (i+1) + ". " + ((Component) UserAgent.getComponentList().get("SmartPlug").get(i)).getName());
			}
		}
		
		
		
		
		if(connectedEntityList.get(choice).equalsIgnoreCase("PowerSmartMeter")) //PSM
		{sub="PowerSmartMeter";
			for(int i=0; i< UserAgent.getComponentList().get("PowerSmartMeter").size(); i++)
			{	
				System.err.println("   "+ (i+1) + ". " + ((Component) UserAgent.getComponentList().get("PowerSmartMeter").get(i)).getName());
			}
		}
			if(connectedEntityList.get(choice).equalsIgnoreCase("AirConditioner")) //AirCon 
			{ sub="AirConditioner";
			
			for(int i=0; i< UserAgent.getComponentList().get("AirConditioner").size(); i++)
			{	
				System.err.println("   "+ (i+1) + ". " + ((Component) UserAgent.getComponentList().get("AirConditioner").get(i)).getName());
			}
			}
			if(connectedEntityList.get(choice).equalsIgnoreCase("Thermometer")) //Th
			{sub="Thermometer";	
				for(int i=0; i< UserAgent.getComponentList().get("Thermometer").size(); i++)
				{	
					System.err.println("   "+ (i+1) + ". " + ((Component) UserAgent.getComponentList().get("Thermometer").get(i)).getName());
				}
			}
			
			if(connectedEntityList.get(choice).equalsIgnoreCase("PhotovoltaicPanel")) //PP
			{ sub="PhotovoltaicPanel";	
				for(int i=0; i< UserAgent.getComponentList().get("PhotovoltaicPanel").size(); i++)
				{	
					System.err.println("   "+ (i+1) + ". " + ((Component) UserAgent.getComponentList().get("PhotovoltaicPanel").get(i)).getName());
				}
			}
			if(connectedEntityList.get(choice).equalsIgnoreCase("Lamp")) //Lamp
			{ sub="Lamp";	
				for(int i=0; i< UserAgent.getComponentList().get("Lamp").size(); i++)
				{	
					System.err.println("   "+ (i+1) + ". " + ((Component) UserAgent.getComponentList().get("Lamp").get(i)).getName());
				}
			}
			
			if(choice!=0)
			System.err.println("   00. Go back to the Smart Building Menu  ");

			if(connectedEntitiesVector.size()!=0)
			{
			System.out.println("");
			Scanner sc = new Scanner(System.in);
			System.out.println(" Type your choice number : ");
			int subChoice=0;
				subChoice = sc.nextInt();
				
				
				
				UserAgentUI.manageMenus(sub,choice,subChoice);

			}

	}



	public static UserAgent getUserAgent() {
		return userAgent;
	}



	public static void setUserAgent(UserAgent userAgent) {
		UserAgentUI.userAgent = userAgent;
	}



	public static Vector<String> getConnectedEntitiesVector() {
		return connectedEntitiesVector;
	}



	public static void setConnectedEntitiesVector(
			Vector<String> connectedEntitiesVector) {
		UserAgentUI.connectedEntitiesVector = connectedEntitiesVector;
	}
	
	
	
	
	
/*	public void run ()
	{
		System.out.println("Begin userInterface Thread");
		int choice = 0;
		try {
			choice = UserAgentUI.getInstance().displayPrincipalMenu();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(choice != 0)
		{
			try {
				UserAgentUI.getInstance().manageMenus(choice);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	

	

}