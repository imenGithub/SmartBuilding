package fr.laas.sb.entity.powerSmartMeter;

import java.util.Calendar;



/**
 * @author Imen
 * --- This class is a container for the power smart meter data. This includes the power smart meter information
 * which consists of the value of produced power (produced electricity) , the value of the consumed power 
 * and the period of sending values to the building manager---
 */
public class PowerSmartMeter extends Thread {
	private static Calendar date ;
	private double powerCons=0;
	private long sendingPeriod;
	private int precision;
	private long measurementPeriod;
	///////
	String rouge="\u001B[31m";
	String vert="\u001B[32m";
	String jaune="\u001B[33m";
	String bleu="\u001B[34m";
	String magenta="\u001B[36m";
	String cyan="\u001B[37m";
	String rougeGras="\u001b[1;31m";
	String vertGras="\u001b[1;32m";
	String jauneGras="\u001b[1;33m";
	String bleuGras="\u001b[1;34m";
	String magentaGras="\u001b[1;35m";
	String cyanGras="\u001b[1;36m";
	String reset="\u001b[0m";
	//////
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
	 */
	public PowerSmartMeter( double powerCons)
	{
		this.powerCons = powerCons;
		//set the period of listening/measuring the temperature value (100 ms = 0,1s)
				this.setMeasurementPeriod(29000);
				FileRW.addCons((double) Math.round(100000/29000));//simulation


	}
	
	/**
	 * Returns the value of produced power
	 * 
	 * @return the value of produced power
	 */
	//genere des valeurs aléatoires avec la precision determinée
		/*public double getAléaValue()
		{double aleatoire =1 + (Math.random()) *1;
	     int val=0;
	     int k=10;
	     for(int i=1; i< this.getPrecision();i++){
	    	 k=k*10;
	    	 
	     } double z= k*1.0;


	    val = (int)(aleatoire*k); 
	    aleatoire = val/z;    
			return aleatoire;
		}*/

	
	
	
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

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public long getMeasurementPeriod() {
		return measurementPeriod;
	}

	public void setMeasurementPeriod(long measurementPeriod) {
		this.measurementPeriod = measurementPeriod;
	}
	
////////////////
public void run()
{ int compteur=1;
		
while (true){
//traitement de la boucle de mesure de la valeur de temperature	
if(compteur<10){
    System.out.println("*******************************PowerSmartMeter************************************");  
 System.out.println("*                              Measurement cycle  n°"+compteur+"                              *");
 System.out.println("**********************************************************************************");  

}

		System.out.println("* Energy consumption ==> "+Double.toString(powerCons)  +date.getInstance().getTime()+"// *");
	    try {compteur++;

		System.out.println("*--------------------------------------------------------------------------------*");  
	    System.out.println("*                  Measurement cycle inactive during: <"+reset+jauneGras+this.getMeasurementPeriod()+reset+jaune+">                 *");
	    System.out.println("**********************************************************************************"+reset);  

			Thread.sleep(this.getMeasurementPeriod()); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

}}
	
}
