package fr.laas.sb.serverBM.client.entities;

import fr.laas.sb.serverBM.FileRW;

/**
 * @author imen
 * --- This class is a container for the photovoltaicPanel data. This includes the photovoltaicPanel informations
 * which consist of the production value and the period of sending values to the building manager---
 */
public class PhotovoltaicPanel extends Component{
	
	private double powerProd;
	private int precision;
	private long measurementPeriod;
	private long sendingPeriod;
	FileRW f;
	public PhotovoltaicPanel()
	{
		
	}
	

	public PhotovoltaicPanel(FileRW file,String name,double powerProd, long measurementPeriod, long sendingPeriod)
	{	this.f=file;
		this.name=name;
		this.powerProd = powerProd;
		this.measurementPeriod = measurementPeriod;
		this.sendingPeriod = sendingPeriod;

	}
	
	/**
	 * Returns the production value of the photovoltaicPanel
	 * 
	 * @return the production value of the photovoltaicPanel
	 */
	public double getpowerProd()
	{
		return this.powerProd;
	}
	
	
	/**
	 * Sets the production value of the photovoltaicPanel
	 * 
	 * @param state
	 *            the production value of the photovoltaicPanel
	 */
	public void setpowerProd(double powerProd)
	{
		this.powerProd = powerProd;
		
		
		

	}
	
	
	/**
	 * Returns the digits precision of the production value 
	 * 
	 * @return the digits precision of the production value
	 */
	public double getPrecision()
	{
		return this.precision;
	}
	
	/**
	 * Sets the digits precision of the production value 
	 * 
	 * @param precision
	 *            the digits precision of the production value 
	 */
	public void setPrecision(int precision)
	{
		this.precision = precision;
	}
	
	
	/**
	 * Returns the period of listening to the production value 
	 * 
	 * @return the period of listening to the production value
	 */
	public long getMeasurementPeriod()
	{
		return this.measurementPeriod;
	}
	
	/**
	 * Sets the period of listening to the production value 
	 * 
	 * @param sendingPeriod
	 *            the period of listening to the production value
	 */
	public void setMeasurementPeriod(long listeningPeriod)
	{
		this.measurementPeriod = listeningPeriod;
	}
	
	
	/**
	 * Returns the period of sending the production value to the building manager 
	 * 
	 * @return the period of sending the production value to the building manager
	 */
	public long getSendingPeriod()
	{
		return this.sendingPeriod;
	}
	
	/**
	 * Sets the period of sending the production value to the building manager
	 * 
	 * @param sendingPeriod
	 *            the period of sending the production value to the building manager
	 */
	public void setSendingPeriod(long sendingPeriod)
	{// add a simulated energy consumption
		double old=100000/(this.sendingPeriod);
		
		double newV= 100000/(sendingPeriod);//simulation
		this.sendingPeriod = sendingPeriod;
		f.addCons((double) Math.round(newV-old));//simulation
	
	
	}
	
	public void deconnetComponent()
	{connState="Dead";
	// subtract the simulated energy consumption
	double old=100000/(this.sendingPeriod);//simulation
	f.addCons((double) Math.round(-old));//simulation

	}
	


}