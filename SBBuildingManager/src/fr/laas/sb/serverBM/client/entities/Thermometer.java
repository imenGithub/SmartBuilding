package fr.laas.sb.serverBM.client.entities;

import fr.laas.sb.serverBM.FileRW;

/**
 * @author Imen
 * --- This class is a container for the thermometer data. This includes the thermometer informations
 * which consist of the temperature value and the period of sending values to the building manager---
 */
public class Thermometer extends Component{
	
	private double tempValue;
	private int precision;
	private long measurementPeriod;
	private long sendingPeriod;
FileRW f;
public Thermometer()
	{
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param tempValue
	 *            the temperature value of the thermometer
	 * @param precision
	 * 			  the digits precision of the temperature value
	 * @param measurementPeriod
	 * 			  the period of listening/measuring the temperature value
	 * @param sendingPeriod
	 * 			  the period of sending the power smart meter characteristics values to the building manager
	 */
	public Thermometer(FileRW file,String name,double tempValue, long measurementPeriod, long sendingPeriod)
	{this.f=file;
		this.name=name;
		this.tempValue = tempValue;
		this.measurementPeriod = measurementPeriod;
		this.sendingPeriod = sendingPeriod;

	}
	
	/**
	 * Returns the temperature value of the thermometer
	 * 
	 * @return the temperature value of the thermometer
	 */
	public double getTempValue()
	{
		return this.tempValue;
	}
	
	
	/**
	 * Sets the temperature value of the thermometer
	 * 
	 * @param state
	 *            the temperature value of the thermometer
	 */
	public void setTempValue(double tempValue)
	{
		this.tempValue = tempValue;
		
		
		

	}
	
	
	/**
	 * Returns the digits precision of the temperature value 
	 * 
	 * @return the digits precision of the temperature value
	 */
	public double getPrecision()
	{
		return this.precision;
	}
	
	/**
	 * Sets the digits precision of the temperature value 
	 * 
	 * @param precision
	 *            the digits precision of the temperature value 
	 */
	public void setPrecision(int precision)
	{
		this.precision = precision;
	}
	
	
	/**
	 * Returns the period of listening to the temperature value 
	 * 
	 * @return the period of listening to the temperature value
	 */
	public long getMeasurementPeriod()
	{
		return this.measurementPeriod;
	}
	
	/**
	 * Sets the period of listening to the temperature value 
	 * 
	 * @param sendingPeriod
	 *            the period of listening to the temperature value
	 */
	public void setMeasurementPeriod(long listeningPeriod)
	{
		this.measurementPeriod = listeningPeriod;
	}
	
	
	/**
	 * Returns the period of sending the temperature value to the building manager 
	 * 
	 * @return the period of sending the temperature value to the building manager
	 */
	public long getSendingPeriod()
	{
		return this.sendingPeriod;
	}
	
	/**
	 * Sets the period of sending the temperature value to the building manager
	 * 
	 * @param sendingPeriod
	 *            the period of sending the temperature value to the building manager
	 */
	public void setSendingPeriod(long sendingPeriod)
	{// add the simulated energy consumption
		double old=100000/(this.sendingPeriod);
		
		double newV= 100000/(sendingPeriod);//simulation
		this.sendingPeriod = sendingPeriod;
		f.addCons((double) Math.round(newV-old));//simulation
	
	
	}
	/**
	 * @author emna
	 */
	public void deconnetComponent()
	{connState="Dead";
	// subtract the simulated energy consumption
	double old=100000/(this.sendingPeriod);//simulation
	f.addCons((double) Math.round(-old));//simulation

	}
	


}