package fr.laas.sb.messages;
/**
 * Generic class for all possible message types
 * @author emna
 */
public abstract class Message {
   
	/**
	 * Method that execute a specific actions according to the message type
	 * @author emna
	 * @param value the digital value attached to the message
	 * @param msgToSend the message to be sent
	 * 	 */
public abstract void handle(String value);
}
