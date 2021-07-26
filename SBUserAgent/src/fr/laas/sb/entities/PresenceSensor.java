package fr.laas.sb.entities;

import java.util.Calendar;
import java.util.Scanner;

import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgent.UserAgentListener;
import fr.laas.sb.userAgentUI.UserAgentUI;



/**
 * @author Imen
 */
public class PresenceSensor extends Component{
	private static Calendar date1;
	private double presenceValue=0  ;
	private int precision;
	private long measurementPeriod;
	private long sendingPeriod;

	
	public PresenceSensor()
	{
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param presenceValue
	 *            presence value of the room
	 */
	public PresenceSensor(String name,double presenceValue,long measurementPeriod,long sendingPeriod)
	{	this.name=name;
	this.presenceValue = presenceValue;
	this.measurementPeriod = measurementPeriod;
	this.sendingPeriod = sendingPeriod;

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
	{

		this.sendingPeriod = sendingPeriod;
	}

	@Override
	public int display() {

		System.err.println("****************************************** "+name+" Menu **************************");
		System.err.println("   1. Get the presence value  ");
		System.err.println("   2. Go back to the list of presence sensors ");
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

	@Override
	public void displayAndHandleChoices(int choice, UserAgent userAgent) {
int subChoice = this.display();
		
		while (subChoice != 2)
		{
			if(subChoice == 1) //1. Get the luminosity value
			{
				//send the value of the temperature request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("presenceRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				System.out.print("   The value of the presence is :  " );
				System.err.println(UserAgent.getPresenceSensor(name).getpresenceValue()+"****"+date1.getInstance().getTime());
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