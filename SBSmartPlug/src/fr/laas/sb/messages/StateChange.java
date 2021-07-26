package fr.laas.sb.messages;

import fr.laas.sb.entity.fr.laas.sb.entity.smartPlug.SmartPlugManager;

public class StateChange extends Message {

private String state;

public StateChange(String st)
{this.state=st;}

public void handle(String value) {
	SmartPlugManager.getsmartP().setStateS(state);

	}

}
