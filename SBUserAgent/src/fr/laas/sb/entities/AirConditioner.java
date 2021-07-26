package fr.laas.sb.entities;

import java.util.Scanner;

import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgent.UserAgentListener;
import fr.laas.sb.userAgentUI.UserAgentUI;

/**
 * @author Imen
 * --- This class is a container for the air conditioner data. This includes the air conditioner informations
 * which consist of the temperature value, the digits precision of the temperature value and the period of sending values to the building manager ---
 */
public class AirConditioner extends Component {
	
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
	 *            the temperature value of the air conditioner
	 * @param precision
	 * 			  the digits precision of the temperature value         
	 * @param sendingPeriod
	 * 			  the period of sending the air conditioner characteristics values to the building manager
	 */
	public AirConditioner(String name,double tempValue, int precision, long sendingPeriod)
	{	this.name=name;
		this.tempValue = tempValue;
		this.precision = precision;
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

	

	@Override
	public void displayAndHandleChoices(int choice,UserAgent userAgent) {
		int subChoice = this.display();
		
		while (subChoice != 3)
		{
			
			if(subChoice == 1) //1. Get the value of the air conditioner temperature
			{
				//send the value of the air conditioner temperature request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("tempAirCondRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				System.out.print("   The value of the air conditioner temperature is :  ");
				System.err.println(UserAgent.getAirConditioner(name).getTempValue());
			}
			
			if(subChoice == 2) //2. Set the value of the air conditioner temperature value
			{
				System.out.println("");
				Scanner sc = new Scanner(System.in);
				System.out.println(" Enter the air conditioner temperature value : ");
				double newTempValue = sc.nextDouble();
				UserAgentListener.getServerConnection().sendMsgToBM("userTempAirCondValue"+newTempValue+"For"+name);
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
		
		if(subChoice == 3) //3. Go back to the Smart Building Menu
		{			UserAgentUI.displayListComponent(choice);

		}
		
			
	}

	@Override
	public int display() {

		System.err.println("**************************************** "+name+" Menu **************************");
		System.err.println("   1. Get the value of the air conditioner temperature value  ");
		System.err.println("   2. Set the value of the air conditioner temperature value ");
		System.err.println("   3. Go back to the list of Air Conditioners ");
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
}
