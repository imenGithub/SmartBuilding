package fr.laas.sb.entity.fr.laas.sb.entity.photovoltaicPanel;

import java.util.Calendar;
import java.util.Random;



import fr.laas.sb.serverConnection.ServerConnection;


/**
 * @author Imen
 * --- This class receive requests sent by the building manager and response to it ---
 */

public class PhotovoltaicPanelManager extends Thread {
	private static PhotovoltaicPanel photovoltaicPanel;
	private static double value=15;
	private static Calendar date1 ;

	public PhotovoltaicPanelManager()
	{
		
//		Random rand = new Random();//simulation
//		Double prodAlea = (double) (rand.nextInt(140-20 + 1) + 20);//simulation
//		FileRW.addProd(prodAlea);//simulation
		
		//setPowerSmartMeter(new PowerSmartMeter(prodAlea,FileRW.getCons()));
		setPhotovoltaicPanel(new PhotovoltaicPanel(0));
	    PhotovoltaicPanelListener.getServerConnection().sendToBuildingManager("ResponsepowerProdPhotovoltaicPanel"+Double.toString(getPhotovoltaicPanel().getpowerProd()));		
	    getPhotovoltaicPanel().start();
    	//set the digits precision of the temperature value
		getPhotovoltaicPanel().setPrecision(3);
        System.out.println("precision : " + getPhotovoltaicPanel().getPrecision());
		
		
				
		//set the period of sending values to the building manager (100 ms = 0,1s)
		getPhotovoltaicPanel().setSendingPeriod(36000); 
		System.out.println("sending period : " + getPhotovoltaicPanel().getSendingPeriod() );
		;}
    

	
	public void run()
	{	int compteur=1;	
	int count=0;
		while (true ){
			if(count==4)
				count=0;
			if(count==0)
			{	
			Random rand = new Random();//simulation
			Double prodAlea = (double) (rand.nextInt(140-20 + 1) + 20);//simulation
			FileRW.addProd(prodAlea);//simulation
			photovoltaicPanel.setpowerProd(prodAlea);
			}
			count++;
	//traitement de la boucle d'envoi de la valeur de temperature 
      //System.out.println("traitement boucle d'envoi");
      //System.out.println("ancienne valeur stockée: "+value);
      //System.out.println("nouvelle valeur reçue: "+getPhotovoltaicPanel().getpowerProd());
			if(compteur<10){	
			    System.out.println("**PhotovoltaicPanel*********************************************************************");  
				System.out.println("*                              Sending cycle  n°"+compteur+"                               *");
				System.out.println("**********************************************************************************");  
				}
				
			 if(value!=getPhotovoltaicPanel().getpowerProd()){
				    PhotovoltaicPanelListener.getServerConnection().sendToBuildingManager("ResponsepowerProdPhotovoltaicPanel"+Double.toString(getPhotovoltaicPanel().getpowerProd()));
					System.out.println("*"+"  Produced power  ==> "+Double.toString(getPhotovoltaicPanel().getpowerProd())+"   //date d'envoi : "+date1.getInstance().getTime()+"//      *");
					value=getPhotovoltaicPanel().getpowerProd();}	
	  
			 try {compteur++;
				System.out.println("*--------------------------------------------------------------------------------*");  
			    System.out.println("*                  Sending cycle inactive during:  <"+getPhotovoltaicPanel().getSendingPeriod()+">                  *");
			    System.out.println("**********************************************************************************"); 
				Thread.sleep(getPhotovoltaicPanel().getSendingPeriod());


		} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
		
			
			
				

			}		
			
		}
	


	public static PhotovoltaicPanel getPhotovoltaicPanel() {
		return photovoltaicPanel;
	}
	
	



	public static void setPhotovoltaicPanel(PhotovoltaicPanel PhotovoltaicPanel) {
		PhotovoltaicPanelManager.photovoltaicPanel = PhotovoltaicPanel;
	}
}