package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;


public class ResponseState extends Message {
String state;
	

	public ResponseState(String string) {
state=string;}





	@Override
	public void handle(String client,String value) {
		  UserAgent.getLamp(client).setState(state);
//		  System.out.println("-----> " + state + " state of the lamp (String)" );
		
	}

}
