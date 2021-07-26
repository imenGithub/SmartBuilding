package fr.laas.sb.entities;

import fr.laas.sb.userAgent.UserAgent;

public abstract class Component {
	
public abstract int display();
public abstract void displayAndHandleChoices(int choice, UserAgent userAgent);

	
protected String name;
private String ip;
public String getIp() {
	return ip;
}
public void setIp(String ip) {
	this.ip = ip;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
}
