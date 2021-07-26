package fr.laas.sb.serverBM.client.entities;

import java.text.DecimalFormat;

import fr.laas.sb.serverBM.FileRW;
import fr.laas.sb.serverBM.SmartBuildingListener;

/**
 * @author Imen
 * --- This class is a container for the lamp data. This includes the lamp informations which consist of 
 * the state (on/off), the intensity of luminosity and the period of sending values to the building manager ---
 */

public class Lamp extends Component{
	
	private String state;
	private double intensity;
	private FileRW f;

	DecimalFormat df = new DecimalFormat ( ) ;
	
	public Lamp()
	{ 
		
		df.setMaximumFractionDigits ( 3 ) ; //arrondi à 2 chiffres apres la virgules
		df.setMinimumFractionDigits ( 3 ) ;
		df.setDecimalSeparatorAlwaysShown ( true ) ;
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param state
	 *            state of the lamp (on/off)
	 * @param intensity
	 *            Intensity of the luminosity
	 * @param sendingPeriod
	 * 			  the period of sending the lamp characteristics values to the building manager
	 */
	public Lamp(FileRW file,String name,String state, double intensity)
	{	this.f=file;
		this.name=name;
		this.state= state;
		this.intensity=intensity;
		
		

	}
	
	/**
	 * Returns the state of the lamp
	 * 
	 * @return the state of the lamp
	 */
	public String getState()
	{
		return state;
	}
	
	/**
	 * Sets the state of the lamp
	 * 
	 * @param state
	 *            the state of the lamp
	 */
	public void setState(String state)
	{
		
		this.state=state;

	}
	
	/**
	 * Returns the intensity of luminosity
	 * 
	 * @return the intensity of luminosity
	 */
	public double getIntensity()
	{
		return intensity;
	}
	
	/**
	 * Sets the intensity of luminosity
	 * 
	 * @param intensity
	 *            the intensity of luminosity
	 */
	public void setIntensity(double intensity)
	{ 		
		// add a simulated energy consumption
		double old=(this.intensity)/220;//simulation
		double newV= (intensity)/220;//simulation

		double cons;
		if(this.intensity!=0.0)
		cons=newV-old;
		else
		cons=newV;
		
		String st=df.format(cons);
		cons=Double.parseDouble(st.replace(',', '.'));
		f.addCons(cons);//simulation
				this.intensity=intensity;

				
		try{
			String st1=df.format(newV);
			newV=Double.parseDouble(st1.replace(',', '.'));
			
		f.create(SmartBuildingListener.getClientConnection().getDevicePlugList().get(this.getName()),newV);
		
		}
		catch(Exception e)
		{}
	}

/**
 * @author emna	
 */
	public void deconnetComponent()
	{connState="Dead";
	// subtract simulated energy consumption
	double old=(this.intensity)/220;//simulation
	String st=df.format(old);
	old=Double.parseDouble(st.replace(',', '.'));
	f.addCons((double) (-old));//simulation
	f.create(SmartBuildingListener.getClientConnection().getDevicePlugList().get(this.getName()),0.0);

	}
	
	
	
}