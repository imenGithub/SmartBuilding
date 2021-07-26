package fr.laas.sb.serverBM.client.entities;

import java.util.Calendar;

import fr.laas.sb.serverBM.FileRW;


/**
 * @author imen
 */
public class PresenceSensor extends Component{
	private double presenceValue=0  ;
	private int precision;
	private long measurementPeriod;
	private long sendingPeriod;
	FileRW f;
	
	public PresenceSensor()
	{
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param presenceValue
	 *            presence value of the room
	 */
	public PresenceSensor(FileRW file,String name,double presenceValue,long measurementPeriod,long sendingPeriod)
	{	
		this.f=file;
		this.name=name;
	this.presenceValue = presenceValue;
	this.measurementPeriod = measurementPeriod;
	this.sendingPeriod = sendingPeriod;

	double old=100000/(this.sendingPeriod);
	
	f.addCons((double) Math.round(old));//simulation
	}
	
	

	public double getpresenceValue(){
		return presenceValue;
	}
	
	/**
	 * Sets the presence value of the thermometer
	 * 
	 * @param state
	 *            the presence value of the thermometer
	 */
	public void setpresenceValue(double presenceValue)
	{
		this.presenceValue = presenceValue;
	}
	
	
	/**
	 * Returns the digits precision of the presence value 
	 * 
	 * @return the digits precision of the presence value
	 */
	public int getPrecision()
	{
		return this.precision;
	}
	
	/**
	 * Sets the digits precision of the presence value 
	 * 
	 * @param precision
	 *            the digits precision of the presence value 
	 */
	public void setPrecision(int precision)
	{
		this.precision = precision;
	}
	/**
	 * Returns the period of listening to the presence value 
	 * 
	 * @return the period of listening to the presence value
	 */
	public long getMeasurementPeriod()
	{
		return this.measurementPeriod;
	}
	

	/**
	 * Sets the period of listening to the presence value 
	 * 
	 * @param sendingPeriod
	 *            the period of listening to the presence value
	 */
	public void setMeasurementPeriod(long listeningPeriod)
	{
		this.measurementPeriod = listeningPeriod;}
	/**
	 * Returns the period of sending the presence value to the building manager 
	 * 
	 * @return the period of sending the presence value to the building manager
	 */
	public long getSendingPeriod()
	{
		return this.sendingPeriod;
	}
	
	/**
	 * Sets the period of sending the presence value to the building manager
	 * 
	 * @param sendingPeriod
	 *            the period of sending the presence value to the building manager
	 */
	public void setSendingPeriod(long sendingPeriod)
	{// add the simulated energy consumption
double old=100000/(this.sendingPeriod);
		
		double newV= 100000/(sendingPeriod);//simulation
		this.sendingPeriod = sendingPeriod;
		f.addCons((double) Math.round(newV-old));//simulation	}
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