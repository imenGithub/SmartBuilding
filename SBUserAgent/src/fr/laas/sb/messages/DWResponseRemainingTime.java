package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;

public class DWResponseRemainingTime extends Message {

	@Override
	public void handle(String client, String val) {
		UserAgent.getDishW(client).setRemaining_Time(val);
//		  System.out.println("-----> " + val + " operating duration " );
	}

}
