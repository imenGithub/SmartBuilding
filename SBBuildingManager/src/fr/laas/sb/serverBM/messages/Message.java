package fr.laas.sb.serverBM.messages;

import java.util.Hashtable;

/**
 * Generic class for all possible message types
 * @author emna
 */
public abstract class Message {
	/**
	 * controls if the sensor periodically send a msg : entityName=>Boolean[] contains 2 boolean values (the first is sending(true/false)
	 * the second is receive(true/false) 
	 */
	@SuppressWarnings("serial")
	protected static Hashtable<String,Boolean> sensorsEnvList=new Hashtable<String,Boolean>() {{
	 }};
	 
	 /**
		 * controls if the device respond to the msg sent : ComponentName=>Boolean[] contains 2 boolean values (the first is sending(true/false)
		 * the second is receive(true/false) 
		 */
	 @SuppressWarnings("serial")
		protected static Hashtable<String,Boolean[]> ComponentEnvList=new Hashtable<String,Boolean[]>() {{
		 }};
	 
	 
	public static Hashtable<String, Boolean> getSensorsEnvList() {
		return sensorsEnvList;
	}

	public static void setSensorsEnvList(Hashtable<String, Boolean> sensorsEnvList) {
		Message.sensorsEnvList = sensorsEnvList;
	}

	

	public static Hashtable<String, Boolean[]> getComponentEnvList() {
		return ComponentEnvList;
	}

	public static void setComponentEnvList(
			Hashtable<String, Boolean[]> componentEnvList) {
		ComponentEnvList = componentEnvList;
	}

	/**
	 * Method that execute a specific actions according to the message type
	 * @author emna
	 * @param value the digital value attached to the message
	 * @param msgToSend the message to be sent
	 * 	 */
public abstract void handle(String client,String value, String msgToSend);
}
