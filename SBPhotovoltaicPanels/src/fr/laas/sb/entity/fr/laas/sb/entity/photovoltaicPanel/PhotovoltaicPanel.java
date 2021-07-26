package fr.laas.sb.entity.fr.laas.sb.entity.photovoltaicPanel;

import java.util.Calendar;
import java.util.Random;



public class PhotovoltaicPanel extends Thread{
	private static Calendar date1 ;
	private double powerProd=0  ;
	private int precision;
	private long measurementPeriod;
	private long sendingPeriod;
	
	/**
	 * Basic constructor
	 * 
	 * @param powerProd
	 *          prod value of the photovoltaicPanel
	 */
	public PhotovoltaicPanel(double powerProd)
	{
		this.powerProd = powerProd;
		//set the period of listening/measuring the temperature value (100 ms = 0,1s)
		this.setMeasurementPeriod(29000); 
	//	System.out.println("measurement period : " + this.getMeasurementPeriod() );
		FileRW.addCons((double) Math.round(100000/29000)); 		//simulation
		

	}
	
	
	
	public double getpowerProd(){
		return powerProd;
	}
	
	/**
	 * Sets the prod value of the photovoltaicPanel
	 * 
	 * @param state
	 *            the prod value of the photovoltaicPanel
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
	
		//traitement de la boucle de mesure de la valeur de temperature	
			if(compteur<10){
				 System.out.println("**PhotovoltaicPanel*********************************************************************");  
				 System.out.println("*                              Measurement cycle  n°"+compteur+"                              *");
				 System.out.println("**********************************************************************************");  
				}

			
				System.out.println("*"+"  Produced power  ==> "+Double.toString(powerProd)+"   //date de mesure: "+date1.getInstance().getTime()+"//     *");
				
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