package fr.laas.sb.messages;

import fr.laas.sb.userAgent.UserAgent;

public class DWResponseMAx extends Message {

	@Override
	public void handle(String client, String val) {
		  UserAgent.getDishW(client).setMax_end_hour(val);
//		  System.out.println("-----> " + val + " maximum end hour " );
	}

}
