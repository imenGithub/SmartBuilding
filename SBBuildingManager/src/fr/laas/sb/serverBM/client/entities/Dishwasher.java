package fr.laas.sb.serverBM.client.entities;

import java.text.DecimalFormat;

import fr.laas.sb.serverBM.FileRW;
import fr.laas.sb.serverBM.SmartBuildingListener;
import fr.laas.sb.serverBM.clientConnection.ClientConnection;


/**
 * @author imen
 */

public class Dishwasher extends Component{

	private String state="off";
	private String max_end_hour="0" ;
	private String remaining_Time="02:00:00" ;
	private long sendingPeriod;
	DecimalFormat df = new DecimalFormat ( ) ;
	private String rt;

	private FileRW f;
	
	public Dishwasher(FileRW file,String name,long sp)
	{	this.f=file;
		this.setPriority("low");
		this.name=name;
		this.sendingPeriod=sp;
		String[] tab=remaining_Time.split(":");
		this.rt=tab[0];
		df.setMaximumFractionDigits ( 3 ) ; //arrondi à 2 chiffres apres la virgules
		df.setMinimumFractionDigits ( 3 ) ;
		df.setDecimalSeparatorAlwaysShown ( true ) ;
	
	}


	public String getState() {
		return state;
	}


	public void setState(String st) {
		// add a simulated energy consumption
		if(st.equals("pause")||st.equals("off"))
		{double old= Double.parseDouble(this.rt)*20;//simulation
		f.addCons((double) Math.round(-old));//simulation
		f.create(SmartBuildingListener.getClientConnection().getDevicePlugList().get(this.getName()),0.0);
		}
		else
		{
		double val= Double.parseDouble(this.rt)*20;//simulation
		f.addCons(val);//simulation
		try{
			f.create(SmartBuildingListener.getClientConnection().getDevicePlugList().get(this.getName()),val);
			
			}
			catch(Exception e)
			{}
		}
		this.state = st;

	}


	public String getMax_end_hour() {
		return max_end_hour;
	}


	public void setMax_end_hour(String max_end_hour) {
		this.max_end_hour = max_end_hour;
	}


	public String getRemaining_Time() {
		return remaining_Time;
	}


	public void setRemaining_Time(String remaining_TimeP) {
		
		this.remaining_Time = remaining_TimeP;

	}
		
		
		


	/**
	 * Returns the period of sending the dishwasher characteristics values to the building manager 
	 * 
	 * @return the period of sending the dishwasher characteristics values to the building manager
	 */
	public long getSendingPeriod()
	{
		return this.sendingPeriod;
	}
	
	/**
	 * Sets the period of sending the dishwasher characteristics values to the building manager
	 * 
	 * @param sendingPeriod
	 *            the period of sending the dishwasher characteristics values to the building manager
	 */
	public void setSendingPeriod(long sendingPeriod)
	{
		this.sendingPeriod = sendingPeriod;
	}


	public void deconnetComponent()
	{connState="Dead";
	// subtract the simulated energy consumption
	double old= Double.parseDouble(this.rt)*20;//simulation
	f.addCons((double) Math.round(-old));//simulation
	f.create(SmartBuildingListener.getClientConnection().getDevicePlugList().get(this.getName()),0.0);

	}

}
	


	
