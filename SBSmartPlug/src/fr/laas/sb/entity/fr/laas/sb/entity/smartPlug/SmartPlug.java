package fr.laas.sb.entity.fr.laas.sb.entity.smartPlug;

import java.util.Calendar;



/**
 * 
 * @author Imen
 *
 */
public class SmartPlug extends Thread{


	private static Calendar date1 ;
	private String name;
	private String state;
	private double cons=0.0;
	private long measurementPeriod;
	private long sendingPeriod;
	Double value=0.0;
	public SmartPlug(String name)
	{		this.name=name;
			this.setMeasurementPeriod(29000); 
			this.setStateS("off");
	}
	
	
	
	
	////////////////
	public void run()
	{	int compteur=1;	
		while (true){
				
			if(this.state=="on")
			{ 
				 System.out.println("**"+this.name+"*********************************************************************");  
				 System.out.println("*                              Measurement cycle n°"+compteur+"                              *");
				 System.out.println("**********************************************************************************");  
				

				 try{ 
						value=FileRW.getContentBalise(this.getNameS());
						}catch(Exception e){}
						if(value!=this.getCons())
							 this.setCons(value); 
				System.out.println("*"+"  Consumed power  ==> "+Double.toString(cons)+"   //date de mesure: "+date1.getInstance().getTime()+"//     *");
				
				try {compteur++;

				System.out.println("*--------------------------------------------------------------------------------*");  
			    System.out.println("*                 Measurement cycle inactive during: <"+this.getMeasurementPeriod()+">                 *");
			    System.out.println("**********************************************************************************");  
						
					Thread.sleep(this.getMeasurementPeriod()); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} }	
				else
				{	 try {
					 System.out.println("**"+this.name+"*********************************************************************");  
					 System.out.println("*"+this.name+" not connected"+"                             *");
					 System.out.println("**********************************************************************************");  
					
						Thread.sleep(this.getMeasurementPeriod());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
}}
	public String getStateS() {
		return this.state;
	}




	public void setStateS(String state) {
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




	public String getNameS() {
		return name;
	}




	public void setNameS(String name) {
		this.name = name;
	}
	


	
	




}