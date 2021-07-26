package fr.laas.sb.entities;

import java.util.Calendar;
import java.util.Scanner;

import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgent.UserAgentListener;
import fr.laas.sb.userAgentUI.UserAgentUI;

/**
 * @author Imen
 * --- This class is a container for the thermometer data. This includes the thermometer informations
 * which consist of the temperature value and the period of sending values to the building manager---
 */
public class Thermometer extends Component {
	
	private double tempValue;
	private int precision;
	private long measurementPeriod;
	private long sendingPeriod;
	private static Calendar date;

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
	public Thermometer(String name,double tempValue, int precision, long measurementPeriod, long sendingPeriod)
	{	this.name=name;
		this.tempValue = tempValue;
		this.precision = precision;
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
	{
		this.sendingPeriod = sendingPeriod;
	}

	@Override
	public int display() {

		System.err.println("****************************************** "+name+" Menu **************************");
		System.err.println("   1. Get the temperature value  ");
		System.err.println("   2. Go back to the list of Thermometers ");
		System.err.println("***************************************************************************************");
		
		//wait 0,1s
		try {
			Thread.sleep(100); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		System.out.println("");
		Scanner sc = new Scanner(System.in);
		System.out.println(" Type your choice number : ");
		int subChoice = sc.nextInt();
		return subChoice;
		
	}

	

	@SuppressWarnings("static-access")
	@Override
	public void displayAndHandleChoices(int choice,UserAgent userAgent) {

		int subChoice = this.display();
		
		while (subChoice != 2)
		{
			if(subChoice == 1) //1. Get the temperature value
			{
				//send the value of the temperature request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("tempThermometerRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				System.out.print("   The value of the temperature is :  " );
				System.err.println(UserAgent.getThermometer(name).getTempValue()+"****"+date.getInstance().getTime());
			}
			
			//wait 0,1s
			try {
				Thread.sleep(100); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			subChoice = this.display();
			
		}
		
		if(subChoice == 2) // 2. Go back to the Smart Building Menu
		{
			UserAgentUI.displayListComponent(choice);


		}
				
	}


}
