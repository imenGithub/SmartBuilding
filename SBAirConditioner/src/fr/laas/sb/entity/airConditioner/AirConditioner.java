package fr.laas.sb.entity.airConditioner;
/**
 * @author Imen
 * --- This class is a container for the air conditioner data. This includes the air conditioner informations
 * which consist of the temperature value, the digits precision of the temperature value and the period of sending values to the building manager ---
 */
public class AirConditioner {
	
	private double tempValue;
	private long sendingPeriod;
	private int precision;
	
	public AirConditioner()
	{
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param tempValue
	 *            Temperature value of the air conditioner
	 */
	public AirConditioner(double tempValue, long sendingPeriod)
	{
		this.tempValue = tempValue;
		this.sendingPeriod = sendingPeriod;
	}
	
	
	/**
	 * Returns the temperature value of the air conditioner
	 * 
	 * @return the temperature value of the air conditioner
	 */
	public double getTempValue()
	{
		return this.tempValue;
	}
	
	
	/**
	 * Sets the temperature value of the air conditioner
	 * 
	 * @param state
	 *            the temperature value of the air conditioner
	 */
	public void setTempValue(double tempValue)
	{
		this.tempValue = tempValue;
	}
	
	/**
	 * Returns the digits precision of the temperature value of the air conditioner
	 * 
	 * @return the digits precision of the temperature value of the air conditioner
	 */
	public int getPrecision()
	{
		return this.precision;
	}
	
	/**
	 * Sets the digits precision of the temperature value of the air conditioner
	 * 
	 * @param precision
	 *            the digits precision of the temperature value of the air conditioner
	 */
	public void setPrecision(int precision)
	{
		this.precision = precision;
	}
	
	
	
	/**
	 * Returns the period of sending the temperature value of the air conditioner to the building manager
	 * 
	 * @return the period of sending the temperature value of the air conditioner to the building manager
	 */
	public long getSendingPeriod()
	{
		return this.sendingPeriod;
	}
	
	/**
	 * Sets the period of sending the temperature value of the air conditioner to the building manager
	 * 
	 * @param sendingPeriod
	 *            the period of sending the temperature value of the air conditioner to the building manager
	 */
	public void setSendingPeriod(long sendingPeriod)
	{
		this.sendingPeriod = sendingPeriod;
	}
}