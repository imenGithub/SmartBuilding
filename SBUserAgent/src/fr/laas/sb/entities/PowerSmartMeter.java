package fr.laas.sb.entities;

import java.util.Scanner;

import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgent.UserAgentListener;
import fr.laas.sb.userAgentUI.UserAgentUI;

/**
 * @author Imen
 * --- This class is a container for the power smart meter data. This includes the power smart meter information
 * which consists of the power consumption value (electricity consumption value) and the period of sending values 
 * to the building manager---
 */
public class PowerSmartMeter extends Component {
	
	private double powerCons;
	private long sendingPeriod;
	
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
	public PowerSmartMeter(String name, double powerCons, long sendingPeriod)
	{	this.name=name;
		this.powerCons = powerCons;
		this.sendingPeriod = sendingPeriod;
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
	{
		this.sendingPeriod = sendingPeriod;
	}

	
	public int display() {

		System.err.println("************************************** "+name+" Menu **************************");
		System.err.println("   1. Get the value of the consumed power ");
		System.err.println("   2. Go back to the list of Power Smart Meters ");
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
	
	public void displayAndHandleChoices(int choice,UserAgent userAgent) {
		int subChoice = this.display();
		while (subChoice != 3)
		{
			
			if(subChoice == 1)//2. Get the value of the consumed power
			{
				//send the value of consumed power request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("powerConsRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.print("   The value of the consumed power is :  ");
				System.err.println(UserAgent.getPowerSmartMeter(name).getPowerCons());
			}
			/*Scanner sc = new Scanner(System.in);
			System.out.println("");
			System.out.println(" Type your choice number : ");
			subChoice = sc.nextInt();*/
			
			//wait 0,1s
			try {
				Thread.sleep(100); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			subChoice = this.display();
		
		
		if(subChoice == 2)//3. Go back to the Smart Building Menu 
		{ 
			UserAgentUI.displayListComponent(choice);

		}
		}
	}
}
