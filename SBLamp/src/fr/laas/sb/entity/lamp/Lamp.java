package fr.laas.sb.entity.lamp;


/**
 * @author Imen
 * --- This class is a container for the lamp data. This includes the lamp informations which consist of 
 * the state (on/off), the intensity of luminosity (the luminosity level) and the period of sending values to the building manager ---
 */

public class Lamp {
	
	private String state;
	private double intensity;
	private long sendingPeriod;
	
	
	
	public Lamp()
	{
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param state
	 *            state of the lamp (on/off)
	 * @param intensity
	 *            Intensity of the luminosity
	 * 				
	 */
	public Lamp(String state, double intensity)
	{
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
		this.intensity=intensity;
	}
	
	/**
	 * Returns the period of sending the lamp characteristics values to the building manager 
	 * 
	 * @return the period of sending the lamp characteristics values to the building manager
	 */
	public long getSendingPeriod()
	{
		return this.sendingPeriod;
	}
	
	/**
	 * Sets the period of sending the lamp characteristics values to the building manager
	 * 
	 * @param sendingPeriod
	 *            the period of sending the lamp characteristics values to the building manager
	 */
	public void setSendingPeriod(long sendingPeriod)
	{
		this.sendingPeriod = sendingPeriod;
	}
	
}