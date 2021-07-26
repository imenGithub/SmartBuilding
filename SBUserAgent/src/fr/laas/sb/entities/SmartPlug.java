package fr.laas.sb.entities;

import java.util.Calendar;
import java.util.Scanner;

import fr.laas.sb.userAgent.UserAgent;
import fr.laas.sb.userAgent.UserAgentListener;
import fr.laas.sb.userAgentUI.UserAgentUI;



/**
 * 
 * @author Imen
 *
 */
public class SmartPlug extends Component{
	public String getState() {
		return state;
	}




	private String state="off";
	private double cons=0.0;
	private long measurementPeriod;
	private long sendingPeriod;
	private static Calendar date;

	public SmartPlug()
	{
	}
	
	
	
	
	
	public SmartPlug(String name) {
this.name=name;
}





	public void setState(String state) {
		this.state = state;
	}




	public double getCons() {
		return cons;
	}




	public void setCons(double cons) {
		this.cons = cons;
	}




	public long getMeasurementPeriod() {
		return measurementPeriod;
	}




	public void setMeasurementPeriod(long measurementPeriod) {
		this.measurementPeriod = measurementPeriod;
	}




	public long getSendingPeriod() {
		return sendingPeriod;
	}




	public void setSendingPeriod(long sendingPeriod) {
		this.sendingPeriod = sendingPeriod;
	}





	@Override
	public int display() {
		System.err.println("   1. Get the consumption value  ");
		System.err.println("   2. Go back to the list of Smart Plugs ");
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
			if(subChoice == 1) //1. Get the temperature value
			{
				//send the value of the temperature request to the BM
				UserAgentListener.getServerConnection().sendMsgToBM("ConsRequestFor"+name);
				
				//wait 1s
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				System.out.print("   The measured  consumption is :  " );
				System.err.println( UserAgent.getPlug(name).getCons()+"****"+date.getInstance().getTime());
				
				
				
				
				
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