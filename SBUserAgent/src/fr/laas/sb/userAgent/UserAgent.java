package fr.laas.sb.userAgent;

import java.util.Hashtable;
import java.util.Vector;

import fr.laas.sb.entities.AirConditioner;
import fr.laas.sb.entities.Dishwasher;
import fr.laas.sb.entities.Component;
import fr.laas.sb.entities.Lamp;
import fr.laas.sb.entities.LuminositySensor;
import fr.laas.sb.entities.PhotovoltaicPanel;
import fr.laas.sb.entities.PowerSmartMeter;
import fr.laas.sb.entities.PresenceSensor;
import fr.laas.sb.entities.SmartPlug;
import fr.laas.sb.entities.Thermometer;
import fr.laas.sb.serverConnection.ServerConnection;

public class UserAgent{
	
	private static Hashtable<String, Vector> componentList=new Hashtable<String,Vector>() {{
		put("Lamp",new Vector<Component>());
		put("AirConditioner",new Vector<Component>());
		put("Thermometer",new Vector<Component>());
		put("LuminositySensor",new Vector<Component>());
		put("PresenceSensor",new Vector<Component>());
		put("PowerSmartMeter",new Vector<Component>());
		put("PhotovoltaicPanel",new Vector<Component>());
		put("Dishwasher",new Vector<Component>());
		put("SmartPlug",new Vector<Component>());


	}};
	
	
	public static Hashtable<String, Vector> getComponentList() {
		return componentList;
	}



	public static void setComponentList(Hashtable<String, Vector> componentList) {
		UserAgent.componentList = componentList;
	}



	@SuppressWarnings("unchecked")
	public UserAgent()
	{	

	
	
	
	}

	
	
	/**
	 * Return the entity having a specific name 
	 * @author emna
	 * @param name the name of the entity
	 * @return the entity having the corresponding name
	 */
	public Vector<Component> findEntitiesByName(String listName)
	{ try
	{ return getComponentList().get(listName);}
	catch(Exception e){}
	
					return null;
		}
	public static Component findEntityByName(String listName,String name)
	{ 
		Vector<Component> entities=getComponentList().get(listName);
	if(entities!=null)
		{for(int i=0;i<entities.size();i++)
		if(entities.get(i).getName().equalsIgnoreCase(name))
			return entities.get(i);
		}
	return null;
	}
	
	public static PresenceSensor getPresenceSensor(String nameP)
	{ return (PresenceSensor)findEntityByName("PresenceSensor",nameP);
	}
	
	public static LuminositySensor getLumSensor(String nameP)
	{ return (LuminositySensor)findEntityByName("LuminositySensor",nameP);
	}
	
	public static SmartPlug getPlug(String nameP)
	{ return (SmartPlug)findEntityByName("SmartPlug",nameP);
	}
	
	
	
	public static Dishwasher getDishW(String nameD)
	{ return (Dishwasher)findEntityByName("Dishwasher",nameD);
	}
	
	public static Lamp getLamp(String nameLamp)
	{ return (Lamp)findEntityByName("Lamp",nameLamp);
	}
	public static AirConditioner getAirConditioner(String nameAC)
	{ return (AirConditioner)findEntityByName("AirConditioner",nameAC);

	}
	public static Thermometer getThermometer(String nameTh)
	{ return (Thermometer)findEntityByName("Thermometer",nameTh);
	}
	public static PowerSmartMeter getPowerSmartMeter(String namePSM)
	{ return (PowerSmartMeter)findEntityByName("PowerSmartMeter",namePSM);


	}
	public static PhotovoltaicPanel getPhotovoltaicPanel(String namePP) {
		return (PhotovoltaicPanel)findEntityByName("PhotovoltaicPanel",namePP);


	}
	
	
	
	
	
	

	
	
	
	


	

	

}
