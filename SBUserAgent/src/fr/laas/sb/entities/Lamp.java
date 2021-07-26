package fr.laas.sb.entities;

import java.util.Scanner;

import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgent.UserAgentListener;
import fr.laas.sb.userAgentUI.UserAgentUI;

/**
 * @author Imen
 * --- This class is a container for the lamp data. This includes the lamp informations which consist of 
 * the state (on/off), the intensity of luminosity and the period of sending values to the building manager ---
 */

public class Lamp extends Component {
	
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
	 * @param sendingPeriod
	 * 			  the period of sending the lamp characteristics values to the building manager
	 */
	public Lamp(String name,String state, double intensity, long sendingPeriod)
	{	this.name=name;
		this.state= state;
		this.intensity=intensity;
		this.sendingPeriod=sendingPeriod;
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

	@Override
	public int display() {

		System.err.println("********************************************** "+name+" Menu **************************");
		System.err.println("   1. Get the state of the lamp (on/off)  ");
		System.err.println("   2. Get the intensity of luminosity ");
		System.err.println("   3. Set the state of the lamp (on/off)  ");
		System.err.println("   4. Set the intensity of luminosity ");
		System.err.println("   5. Go back to the list of Lamps ");
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
		
		while (subChoice != 5)
		{
			if(subChoice == 1) // 1. Get the state of the lamp (on/off)
			{
				//send the lamp state request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("lampStateRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				System.out.print("   The state of the lamp  is :  " );
				System.err.println(UserAgent.getLamp(name).getState());
				
			}
			
			if(subChoice == 2) //2. Get the intensity of luminosity
			{
				//send the intensity of luminosity request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("lampIntensityRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				System.out.print("   The intensity of luminosity  is :  " );
				System.err.println(UserAgent.getLamp(name).getIntensity());
				
			}
			
			if(subChoice == 3) //3. Set the state of the lamp (on/off)
			{
				System.out.println("");
				Scanner sc = new Scanner(System.in);
				System.out.println(" Enter the state of the lamp (on/off) : ");
				String newLampState = sc.nextLine();
				UserAgentListener.getServerConnection().sendMsgToBM("userLampStateValue"+newLampState+"For"+name);
				
			}
			
			if(subChoice == 4) // 4. Set the intensity of luminosity
			{
				System.out.println("");
				Scanner sc = new Scanner(System.in);
				System.out.println(" Enter the intensity of the luminosity : ");
				double newLampIntensity = sc.nextDouble();
				UserAgentListener.getServerConnection().sendMsgToBM("userLampIntenValue"+newLampIntensity+"For"+name);
				
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
		
		if(subChoice == 5) // 5. Go back to the Smart Building Menu
		{ 
			UserAgentUI.displayListComponent(choice);
		}
	
		
	}
	
}
