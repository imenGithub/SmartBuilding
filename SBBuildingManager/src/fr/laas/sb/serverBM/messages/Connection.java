package fr.laas.sb.serverBM.messages;


import fr.laas.sb.serverBM.FileRW;
import fr.laas.sb.serverBM.SmartBuildingListener;
import fr.laas.sb.serverBM.client.entities.AirConditioner;
import fr.laas.sb.serverBM.client.entities.Dishwasher;
import fr.laas.sb.serverBM.client.entities.Lamp;
import fr.laas.sb.serverBM.client.entities.LuminositySensor;
import fr.laas.sb.serverBM.client.entities.PresenceSensor;
import fr.laas.sb.serverBM.client.entities.SmartPlug;
/**
 * handle the connection of new devices
 * @author emna
 *
 */
public class Connection extends Message {
String name;
static FileRW file;

	public FileRW getFile() {
	return file;
}

public static void setFile(FileRW file) {
	Connection.file = file;
}

	public Connection(String string) {
		 name=string;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handle(String client,String value, String msgEnv) {
		// System.out.println("name"+name);

		//send the list of connected entities to the UserAgent
		  if(name=="UserAgent")
			  SmartBuildingListener.getClientConnection().sendToEntity(SmartBuildingListener.connectedEntitiesString, SmartBuildingListener.getChatClientUserSession(),"UserAgent");		
		  else
			  {
			  if(name=="LuminositySensor")
			  {
				  SmartBuildingListener.getClientConnection().getComponentList().get("LuminositySensor").addElement(new LuminositySensor(file,client,0,29000,30000));
				  SmartBuildingListener.getClientConnection().getLuminositySensor(client).connectComponent();
			
			  }
			  if(name=="PresenceSensor")
			  {
				  SmartBuildingListener.getClientConnection().getComponentList().get("PresenceSensor").addElement(new PresenceSensor(file,client,0,5000,5100));
				  SmartBuildingListener.getClientConnection().getPresenceSensor(client).connectComponent();
			
			  }
			  
			  
			  if(name=="SmartPlug")
			  {
				  SmartBuildingListener.getClientConnection().getComponentList().get("SmartPlug").addElement(new SmartPlug(client));
				  SmartBuildingListener.getClientConnection().getSmartPlug(client).connectComponent();
			
			  }
			  
			  
			  
			  if(name=="Lamp")
		  {
			  SmartBuildingListener.getClientConnection().getComponentList().get("Lamp").addElement(new Lamp(file,client,"off",0));
			  //change the attribute connState="Alive"
			  SmartBuildingListener.getClientConnection().getLamp(client).connectComponent();
			  SmartBuildingListener.getClientConnection().associateSmartPug(client,"Lamp");
			  SmartBuildingListener.getClientConnection().associatePresenceSensor(client,"Lamp");
			  SmartBuildingListener.getClientConnection().associateLumSensor(client);


			  

			  
		  }
		  if(name=="PowerSmartMeter")
				SmartBuildingListener.getClientConnection().getPowerSmartMeter(client).connectComponent();
		  if(name=="AirConditioner")
		  {		
				SmartBuildingListener.getClientConnection().getComponentList().get("AirConditioner").addElement(new AirConditioner(file,client,0,0));
				SmartBuildingListener.getClientConnection().getAirConditioner(client).connectComponent();
				  SmartBuildingListener.getClientConnection().associateSmartPug(client,"AirConditioner");
				  SmartBuildingListener.getClientConnection().associatePresenceSensor(client,"AirConditioner");

		  }
		  if(name=="Thermometer")
				SmartBuildingListener.getClientConnection().getThermometer(client).connectComponent();
		  if(name=="PhotovoltaicPanel")
				SmartBuildingListener.getClientConnection().getPhotovoltaicPanel(client).connectComponent();
		if(name=="Dishwasher")  
			{ 
			SmartBuildingListener.getClientConnection().getComponentList().get("Dishwasher").addElement(new Dishwasher(file,"Dishwasher",100));
			SmartBuildingListener.getClientConnection().getDW(client).connectComponent();
			SmartBuildingListener.getClientConnection().associateSmartPug(client,"Dishwasher");

			}
		  
		  SmartBuildingListener.connectedEntitiesString = SmartBuildingListener.connectedEntitiesString +client+"*";
		  //send the connectedEntitiesString to the user agent
		//System.out.println("aaaaa"+SmartBuildingListener.connectedEntitiesString);
		  SmartBuildingListener.getClientConnection().sendToEntity(SmartBuildingListener.connectedEntitiesString, SmartBuildingListener.getChatClientUserSession(),"UserAgent");		
	}
}
	


	}





