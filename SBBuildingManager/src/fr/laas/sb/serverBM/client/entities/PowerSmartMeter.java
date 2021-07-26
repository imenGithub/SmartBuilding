package fr.laas.sb.serverBM.client.entities;

import fr.laas.sb.serverBM.FileRW;

/**
 * @author Imen
 * --- This class is a container for the power smart meter data. This includes the power smart meter information
 * which consists of the power consumption value (electricity consumption value) and the period of sending values 
 * to the building manager---
 */
public class PowerSmartMeter extends Component{
	
	private double powerCons;
	private long sendingPeriod;
	private long measurementPeriod;
	FileRW f;
	
	public PowerSmartMeter()
	{ 
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param powerProd
	 * 			  the value of produced power
	 * @param powerCons
	 *            the value of consumed power
	 * @param sendingPeriod
	 * 			  the period of sending the power smart meter characteristics values to the building manager
	 */
	public PowerSmartMeter(FileRW file,String name, double powerCons,long measurementPeriod, long sendingPeriod)
	{	this.f=file;
		this.name=name;
		this.powerCons = powerCons;
		this.sendingPeriod = sendingPeriod;
		this.measurementPeriod = measurementPeriod;


	}
	
	
	

	
	/**
	 * Returns the power consumption value
	 * 
	 * @return the power consumption value
	 */
	public double getPowerCons()
	{
		return this.powerCons;
	}
	
	/**
	 * Sets the power consumption value
	 * 
	 * @param powerCons
	 *            the power consumption value
	 */
	public void setPowerCons(double powerCons)
	{
		this.powerCons = powerCons;
	}
	
	
	/**
	 * Returns the period of sending the power consumption value to the building manager 
	 * 
	 * @return the period of sending the power consumption value to the building manager
	 */
	public long getSendingPeriod()
	{
		return this.sendingPeriod;
	}
	
	/**
	 * Sets the period of sending the power consumption value to the building manager
	 * 
	 * @param sendingPeriod
	 *            the period of sending the power consumption value to the building manager
	 */
	public void setSendingPeriod(long sendingPeriod)
	{// add a simulated energy consumption
		double old=100000/(this.sendingPeriod);//simulation
		double newV= 100000/(sendingPeriod);//simulation
		this.sendingPeriod = sendingPeriod;
		f.addCons((double) Math.round(newV-old));//simulation
		}
	public long getMeasurementPeriod() {
		return measurementPeriod;
	}

	public void setMeasurementPeriod(long measurementPeriod) {
		this.measurementPeriod = measurementPeriod;
	}

/**
 * @author emna
 */
	public void deconnetComponent()
	{connState="Dead";
	// subtract the simulated energy consumption
	double old=100000/(this.sendingPeriod);//simulation
	f.addCons(-old);//simulation


	}
	
	
}