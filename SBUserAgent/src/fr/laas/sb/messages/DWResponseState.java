package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;

public class DWResponseState extends Message {
String state;
	

	public DWResponseState(String string) {
state=string;}





	@Override
	public void handle(String client,String value) {
		  UserAgent.getDishW(client).setState(state);
//		  System.out.println("-----> " + state + " state of the dishwasher (String)" );
		
	}

}
