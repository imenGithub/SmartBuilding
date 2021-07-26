package fr.laas.sb.entity.thermometer;

import java.util.Calendar;


/**
 * @author Imen
 * --- This class is a container for the thermometer data. This includes the thermometer informations
 * which consist of the temperature value, the digits precision of the temperature value, 
 * the period of listening to the temperature value (measurementPeriod) and the period of sending values to the building manager---
 */
public class Thermometer extends Thread{
	private static Calendar date1 ;
	private double tempValue=0  ;
	private int precision;
	private long measurementPeriod;
	private long sendingPeriod;

	
	public Thermometer()
	{
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param tempValue
	 *            Temperature value of the thermometer
	 */
	public Thermometer(double tempValue)
	{
		this.tempValue = tempValue;
		//set the period of listening/measuring the temperature value (100 ms = 0,1s)
		this.setMeasurementPeriod(29000); 
	//	System.out.println("measurement period : " + this.getMeasurementPeriod() );
		FileRW.addCons((double) Math.round(100000/29000)); 		//simulation

	}
	
	
	/**
	 * Returns the temperature value of the thermometer
	 * 
	 * @return the temperature value of the thermometer
	 */
	//genere des valeurs al�atoires avec la precision determinée
	public double getAleaValue()
	{double vmesure =15 + (Math.random()) *10;
     int val=0;
     int k=10;
     for(int i=1; i< this.getPrecision();i++){
    	 k=k*10;
    	 
     } double z= k*1.0;


    val = (int)(vmesure*k); 
    vmesure = val/z;    
		return vmesure;
	}
	public double getTempValue(){
		return tempValue;
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
	public int getPrecision()
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
		this.measurementPeriod = listeningPeriod;}
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
	

	
	
	
	////////////////
	public void run()
	{	int compteur=1;	
		while (true){
	
		
				 System.out.println("**Therometer*********************************************************************");  
				 System.out.println("*                              Measurement cycle n°"+compteur+"                              *");
				 System.out.println("**********************************************************************************");  
				
			   tempValue = this.getAleaValue();
				System.out.println("*  Temperature  ==> "+Double.toString(tempValue)+"**"+date1.getInstance().getTime()+"//     *");

                //this.setTempValue(tempValue);
				try {compteur++;

				System.out.println("*--------------------------------------------------------------------------------*");  
			    System.out.println("*                  Measurement cycle inactive during: <"+this.getMeasurementPeriod()+">                 *");
			    System.out.println("**********************************************************************************");  

					Thread.sleep(this.getMeasurementPeriod()); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	
}}}