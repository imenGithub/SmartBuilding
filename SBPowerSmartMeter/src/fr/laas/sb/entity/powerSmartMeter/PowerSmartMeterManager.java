package fr.laas.sb.entity.powerSmartMeter;


import java.util.Calendar;
import java.util.Random;


import fr.laas.sb.serverConnection.ServerConnection;


/**
 * @author Imen
 * --- This class receive requests sent by the building manager and response to it ---
 */

public class PowerSmartMeterManager extends Thread {
	private static PowerSmartMeter psm;
	

	private static Calendar date ;
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
	public PowerSmartMeterManager()
	{
		
		
		
		//create the thermometer instance
		
		
		//setPowerSmartMeter(new PowerSmartMeter(FileRW.getCons()));
		setPowerSmartMeter(new PowerSmartMeter(FileRW.getCons()-FileRW.getProd()));

		
		PowerSmartMeterListener.getServerConnection().sendToBuildingManager("ResponsePowerCons"+Double.toString(getPowerSmartMeter().getPowerCons()));



	    getPowerSmartMeter().start();
    	
	    //set the digits precision of the temperature value
		getPowerSmartMeter().setPrecision(2);
     	
		//set the period of sending values to the building manager (100 ms = 0,1s)
		getPowerSmartMeter().setSendingPeriod(30000); 
		
		;}
    

	
	public void run()
	{	 int compteur=1;
		
		while (true ){
			
	//traitement de la boucle d'envoi 
		if(compteur<10){	
	    System.out.println(vert+"*******************************PowerSmartMeter************************************");  
		System.out.println("*                               Sending cycle  n°"+compteur+"                               *");
		System.out.println("**********************************************************************************"+reset);  
		}
		
      /*System.out.println("ancienne valeur produite stockée: "+valueP);
      System.out.println("nouvelle valeur produite reçue: "+getPowerSmartMeter().getPowerProd());
      System.out.println("********************************************");
      System.out.println("ancienne valeur consommée stockée: "+valueC);
      System.out.println("nouvelle valeur consommée reçue: "+getPowerSmartMeter().getPowerCons());*/
    
		
		//simulation de FileRW.getCons()
      if(FileRW.getCons()-FileRW.getProd()!=getPowerSmartMeter().getPowerCons()){
    	  getPowerSmartMeter().setPowerCons(FileRW.getCons()-FileRW.getProd());
		PowerSmartMeterListener.getServerConnection().sendToBuildingManager("ResponsePowerCons"+Double.toString(getPowerSmartMeter().getPowerCons()));
		System.out.println("*  Energy consumption ==> "+Double.toString(getPowerSmartMeter().getPowerCons())+"   //date d'envoi : "+date.getInstance().getTime()+"//  *");

        }	
		
	  
	   try {compteur++;
		System.out.println("*--------------------------------------------------------------------------------*");  
	    System.out.println("*                  Sending cycle inactive during:  <"+reset+vertGras+getPowerSmartMeter().getSendingPeriod()+reset+vert+">                  *");
	    System.out.println("**********************************************************************************"); 
		
	    
	    Thread.sleep(getPowerSmartMeter().getSendingPeriod());
                    

		} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
		
				
			
				

			}		
			
		}
	


	public static PowerSmartMeter getPowerSmartMeter() {
		return psm;
	}
	
	



	public static void setPowerSmartMeter(PowerSmartMeter psm) {
		PowerSmartMeterManager.psm = psm;
	}
}