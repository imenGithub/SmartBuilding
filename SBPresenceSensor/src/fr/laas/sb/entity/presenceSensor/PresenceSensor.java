package fr.laas.sb.entity.presenceSensor;

import java.util.Calendar;

import fr.laas.sb.serverConnection.ServerConnection;


/**
 * @author Imen
 */
public class PresenceSensor extends Thread{
	private static Calendar date1 ;
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
	public PresenceSensor(double presenceValue)
	{
		this.presenceValue = presenceValue;
		//set the period of listening/measuring the presence value (100 ms = 0,1s)
		this.setMeasurementPeriod(5000); 
	//	System.out.println("measurement period : " + this.getMeasurementPeriod() );

	}
	
	
	/**
	 * Returns the presence value of the thermometer
	 * 
	 * @return the presence value of the thermometer
	 */

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
	

	
	
	
	////////////////
	public void run()
	{	int compteur=1;	
		while (true){
	
		//traitement de la boucle de mesure de la valeur de presence	
			
				 System.out.println("**"+ServerConnection.getChatClient().getUser()+"*********************************************************************");  
				 System.out.println("*                              Measurement cycle n°"+compteur+"                              *");
				 System.out.println("**********************************************************************************");  
				
			   presenceValue = FileRW.getPresence(ServerConnection.getChatClient().getUser());
				System.out.println("*  Presence value  ==> "+Double.toString(presenceValue)+"   //date de mesure: "+date1.getInstance().getTime()+"//     *");

                //this.setpresenceValue(presenceValue);
				try {compteur++;

				System.out.println("*--------------------------------------------------------------------------------*");  
			    System.out.println("*                   Measurement cycle inactive during: <"+this.getMeasurementPeriod()+">                 *");
			    System.out.println("**********************************************************************************");  

					Thread.sleep(this.getMeasurementPeriod()); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	
}}}