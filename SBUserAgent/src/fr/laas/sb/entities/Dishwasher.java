package fr.laas.sb.entities;

import java.util.Scanner;

import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgent.UserAgentListener;
import fr.laas.sb.userAgentUI.UserAgentUI;



/**
 * @author Imen
 */

public class Dishwasher extends Component{

	private String state="off";
	private String max_end_hour="0" ;
	private String remaining_Time="02:00:00" ;
	private long sendingPeriod;


	
	public Dishwasher(String name,long sp)
	{this.name=name;
	this.sendingPeriod=sp;

	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getMax_end_hour() {
		return max_end_hour;
	}


	public void setMax_end_hour(String max_end_hour) {
		this.max_end_hour = max_end_hour;
	}


	public String getRemaining_Time() {
		return remaining_Time;
	}


	public void setRemaining_Time(String remaining_TimeP) {
		this.remaining_Time = remaining_TimeP;
		
		
	}

	/**
	 * Returns the period of sending the dishwasher characteristics values to the building manager 
	 * 
	 * @return the period of sending the dishwasher characteristics values to the building manager
	 */
	public long getSendingPeriod()
	{
		return this.sendingPeriod;
	}
	
	/**
	 * Sets the period of sending the dishwasher characteristics values to the building manager
	 * 
	 * @param sendingPeriod
	 *            the period of sending the dishwasher characteristics values to the building manager
	 */
	public void setSendingPeriod(long sendingPeriod)
	{
		this.sendingPeriod = sendingPeriod;
	}


	@Override
	public int display() {


		System.err.println("********************************************** "+name+" Menu **************************");
		System.err.println("   1. Get the state of the dishwasher (on/off)  ");
		System.err.println("   2. Get the maximum end hour ");
		System.err.println("   3. Get the remaining time ");
		System.err.println("   4. Set the state of the dishwasher (on/off)  ");
		System.err.println("   5. Set the maximum end hour ");
		System.err.println("   6. Go back to the list of dishwashers ");
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
		
		while (subChoice != 6)
		{
			if(subChoice == 1) // 1. Get the state of the dishwasher(on/off)
			{
				//send the lamp state request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("DwStateRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				System.out.print("   The state of the dishwasher is :  " );
				System.err.println(UserAgent.getDishW(name).getState());
				
			}
			
			if(subChoice == 2) //2. Get the max_end_hour of dishwasher
			{
				//send the request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("DwMaxRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				System.out.print("   The maximum end hour is :  " );
				System.err.println(UserAgent.getDishW(name).getMax_end_hour());
				
			}
			if(subChoice == 3) //3. Get the remaining time
			{
				//send the request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("RTRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				System.out.print("   The remaining time is :  " );
				System.err.println(UserAgent.getDishW(name).getRemaining_Time());
			}
			
			if(subChoice == 4) //4. Set the state of the dishwasher (on/off)
			{
				System.out.println("");
				Scanner sc = new Scanner(System.in);
				System.out.println(" Enter the state of the dishwasher (on/off) : ");
				String newDWState = sc.nextLine();
				UserAgentListener.getServerConnection().sendMsgToBM("userDWStateValue"+newDWState+"For"+name);
				
			}
			
			if(subChoice == 5) // 5. Set the max end hour 
			{
				System.out.println("");
				Scanner sc = new Scanner(System.in);
				System.out.println(" Enter the maximum end hour (hh:mm:ss): ");
				String newDWEnd = sc.next();
				UserAgentListener.getServerConnection().sendMsgToBM("userDWMaxValue"+newDWEnd+"For"+name);
				
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
		
		if(subChoice == 6) // 6. Go back to the Smart Building Menu
		{ 
			UserAgentUI.displayListComponent(choice);
		}
	
		
	}
	



}
	


	
