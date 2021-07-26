package fr.laas.sb.entity.luminositySensor;

import java.util.Calendar;
import java.util.Random;


/**
 * @author Imen
 */
public class LuminositySensor extends Thread{
	private String nameL;
	private static Calendar date1 ;
	private double lumValue=0  ;
	private int precision;
	private long measurementPeriod;
	private long sendingPeriod;
    private int count=-2;
	
	public LuminositySensor()
	{
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param lumValue
	 *            luminosity value 
	 */
	public LuminositySensor(double lumValue)
	{
		this.lumValue = lumValue;
		//set the period of listening/measuring the temperature value (100 ms = 0,1s)
		this.setMeasurementPeriod(29000); 
	//	System.out.println("measurement period : " + this.getMeasurementPeriod() );

	}
	
	
	/**
	 * Returns the value of the luminosity
	 * 
	 * @return the value of the luminosity
	 */
	//genere des valeurs al�atoires avec la precision determinée
	public double getAleaValue()
	{ count++;
	if(count==4||count==-1)
		{ count=0;
		Random rand = new Random();//simulation
		
	double alea = (double) (rand.nextInt(145-5 + 1) + 5);//simulation
	return alea;
		}
	else
		return this.lumValue;
	
//	double vmesure =15 + (Math.random()) *10;
//     int val=0;
//     int k=10;
//     for(int i=1; i< this.getPrecision();i++){
//    	 k=k*10;
//    	 
//     } double z= k*1.0;
//
//
//    val = (int)(vmesure*k); 
//    vmesure = val/z;    
//		return vmesure;
	}
	public double getLumValue(){
		return lumValue;
	}
	
	/**
	 * Sets the value of the luminosity
	 * 
	 * @param state
	 *            the value of the luminosity
	 */
	public void setLumValue(double lumValue)
	{
		this.lumValue = lumValue;
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
	
	
				 System.out.println("** Luminosity Sensor *********************************************************************");  
				 System.out.println("*                               Measurement cycle  n°"+compteur+"                              *");
				 System.out.println("**********************************************************************************");  
				
			   lumValue = this.getAleaValue();
				System.out.println("*  Luminosity  ==> "+Double.toString(lumValue)+"**" +date1.getInstance().getTime()+"//     *");

                //this.setLumValue(lumValue);
				try {compteur++;

				System.out.println("*--------------------------------------------------------------------------------*");  
			    System.out.println("*                  Measurement cycle inactive during: <"+this.getMeasurementPeriod()+">                 *");
			    System.out.println("**********************************************************************************");  

					Thread.sleep(this.getMeasurementPeriod()); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	
}}

	public String getNameL() {
		return nameL;
	}

	public void setNameL(String nameL) {
		this.nameL = nameL;
	}}