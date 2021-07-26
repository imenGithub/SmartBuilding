package fr.laas.sb.messages;
/**
 * Generic class for all possible message types
 * @author Imen
 */
public abstract class Message {
	String rougeGras="\u001b[1;31m";
	String reset="\u001b[0m";
	/**
	 * Method that execute a specific actions according to the message type
	 * @author emna
	 * @param value the digital value attached to the message
	 * @param msgToSend the message to be sent
	 * 	 */
public abstract void handle(String value);
}
