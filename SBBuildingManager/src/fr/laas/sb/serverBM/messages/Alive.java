package fr.laas.sb.serverBM.messages;

import java.util.Hashtable;
import java.util.Vector;

import fr.laas.sb.serverBM.client.entities.Component;

/**
 * handle the devices' disconnection
 * @author emna
 *
 */
public class Alive extends Message {

	private String name;

	
	/**
	 * device=> boolean true if the device responds to the SBM request "areYouAlive"
	 */
	@SuppressWarnings("serial")
	private  Hashtable<String, Boolean> aliveList=new Hashtable<String,Boolean>() {{
		put("PowerSmartMeter",false);
		put("Thermometer",false);
		put("PhotovoltaicPanel",false);
		put("SmartPlug",false);


	}};	

	public Alive(String string) {
name=string;
}

	@Override
	public void handle(String client,String value, String msgToSend) {
		if(name.equals("PresenceSensor"))
		{ 
			aliveList.put(client, true);

		}
		if(name.equals("LuminositySensor"))
		{ 
			aliveList.put(client, true);

		}
	}

	
	public boolean isReceivPS(String name) {
		try{return aliveList.get(name);}
		catch (Exception e){return false;}
	}
	
	public boolean isReceivLS(String name) {
		return aliveList.get(name);
	}
	
	public boolean isReceivSmartPlug() {
		return aliveList.get("SmartPlug");
	}

	public void setReceivSmartPlugAlive(boolean receivSmartPlugAlive) {
		aliveList.put("SmartPlug",receivSmartPlugAlive);
	}
	
	
	
	
	
	
	public boolean isReceivPSMAlive() {
		return aliveList.get("PowerSmartMeter");
	}

	public void setReceivPSMAlive(boolean receivPSMAlive) {
		aliveList.put("PowerSmartMeter",receivPSMAlive);
	}

	public boolean isReceivThAlive() {
		return aliveList.get("Thermometer");

	}

	public void setReceivThAlive(boolean receivThAlive) {
		aliveList.put("Thermometer",receivThAlive);

	}

	public boolean isReceivPhPanelAlive() {
		return aliveList.get("PhotovoltaicPanel");
	}

	public void setReceivPhPanelAlive(boolean receivPhPanelAlive) {
		aliveList.put("PhotovoltaicPanel",receivPhPanelAlive);
	}

	public void setReceivPS(String name2, boolean b) {
		aliveList.put(name2,b);		
	}
	
	public void setReceivLS(String name2, boolean b) {
		aliveList.put(name2,b);		
	}

}
