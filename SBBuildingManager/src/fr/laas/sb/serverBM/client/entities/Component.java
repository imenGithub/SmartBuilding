package fr.laas.sb.serverBM.client.entities;

import fr.laas.sb.serverBM.FileRW;

/**
 * Generic class for all components
 * @author imen
 *
 */
public abstract class Component {
protected String name;
protected String ip;
protected String connState="Dead";
protected String priority="high";
protected FileRW file;

public FileRW getFile() {
	return file;
}
public void setFile(FileRW file) {
	this.file = file;
}
public String getPriority() {
	return priority;
}
public void setPriority(String priority) {
	this.priority = priority;
}
public String getConnState() {
	return connState;
}
public void connectComponent()
{connState="Alive";}
public void deconnetComponent()
{connState="Dead";}


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
