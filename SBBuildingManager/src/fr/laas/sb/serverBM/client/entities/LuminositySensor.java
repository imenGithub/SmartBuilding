package fr.laas.sb.serverBM.client.entities;

import java.util.Calendar;

import fr.laas.sb.serverBM.FileRW;


/**
 * @author imen
 */
public class LuminositySensor extends Component{
	private double lumValue=0  ;
	private int precision;
	private long measurementPeriod;
	private long sendingPeriod;
 	FileRW f;
	
	public LuminositySensor()
	{
	
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param lumValue
	 *            luminosity value 
	 */
	public LuminositySensor(FileRW file,String name,double lumValue,long measurementPeriod,long sendingPeriod)
	{	this.f=file;
		this.name=name;
		this.lumValue = lumValue;
		this.measurementPeriod = measurementPeriod;
		this.sendingPeriod = sendingPeriod;
		// add a simulated energy consumption
double old=100000/(this.sendingPeriod);
		f.addCons((double) Math.round(old));//simulation

	}
	
	

	/**
	 * Returns the value of the luminosity
	 * 
	 * @return the value of the luminosity
	 */
	
	public double getLumValue(){
		return lumValue;
	}
	
	/**
	 * Sets the value of the luminosity
	 * 
	 * @param state
	 *            the value of the luminosity
	 */
	public void setLumValue(double lumValue)
	{
		this.lumValue = lumValue;
	}
	
	
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
	{// add a simulated energy consumption
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
	//subtract the simulated energy consumption
	double old=100000/(this.sendingPeriod);//simulation
	f.addCons((double) Math.round(-old));//simulation

	}
}