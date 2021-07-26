package fr.laas.sb.entities;

import java.util.Scanner;

import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgent.UserAgentListener;
import fr.laas.sb.userAgentUI.UserAgentUI;

/**
 * @author Imen
 * --- This class is a container for the photovoltaicPanel data. This includes the photovoltaicPanel informations
 * which consist of the temperature value and the period of sending values to the building manager---
 */
public class PhotovoltaicPanel extends Component{
	
	private double powerProd;
	private int precision;
	private long measurementPeriod;
	private long sendingPeriod;
	
	public PhotovoltaicPanel()
	{
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param powerProd
	 *            the temperature value of the photovoltaicPanel
	 * @param precision
	 * 			  the digits precision of the temperature value
	 * @param measurementPeriod
	 * 			  the period of listening/measuring the temperature value
	 * @param sendingPeriod
	 * 			  the period of sending the power smart meter characteristics values to the building manager
	 */
	public PhotovoltaicPanel(String name,double powerProd, long measurementPeriod, long sendingPeriod)
	{	this.name=name;
		this.powerProd = powerProd;
		this.measurementPeriod = measurementPeriod;
		this.sendingPeriod = sendingPeriod;

	}
	
	/**
	 * Returns the temperature value of the photovoltaicPanel
	 * 
	 * @return the temperature value of the photovoltaicPanel
	 */
	public double getpowerProd()
	{
		return this.powerProd;
	}
	
	
	/**
	 * Sets the temperature value of the photovoltaicPanel
	 * 
	 * @param state
	 *            the temperature value of the photovoltaicPanel
	 */
	public void setpowerProd(double powerProd)
	{
		this.powerProd = powerProd;
		
		
		

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
		System.err.println("************************************** "+name+" Panel **************************");
		System.err.println("   1. Get the value of the produced power ");
		System.err.println("   2. Go back to the list of Photovoltaic Panels ");
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
	public void displayAndHandleChoices(int choice,UserAgent userAgent) {
int subChoice = this.display();
		
		while (subChoice != 3)
		{
			if(subChoice == 1)//1. Get the value of the produced power
			{
				//send the value of produced power request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("powerProdRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				System.out.print("   The value of the produced power is :  " );
				System.err.println(UserAgent.getPhotovoltaicPanel(name).getpowerProd());
			}
			
			
			try {
				Thread.sleep(100); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			subChoice = this.display();
		
		
		if(subChoice == 2)//3. Go back to the Smart Building Menu 
		{						UserAgentUI.displayListComponent(choice);

		}
		}
	}


}