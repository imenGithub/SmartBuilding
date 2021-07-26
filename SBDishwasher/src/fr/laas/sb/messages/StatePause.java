package fr.laas.sb.messages;

import fr.laas.sb.entity.dishwasher.DishwasherListener;
import fr.laas.sb.entity.dishwasher.DishwasherManager;

public class StatePause extends Message {

	@Override
	public void handle(String value) {
	double val=Double.parseDouble(value);
	//Thread that wait for the pause time to turn on the dishwasher after it
	ManageWait mw = new ManageWait(val);
	mw.start();
		  
	}
	

public class ManageWait extends Thread  {
	double waitingTime;
	public ManageWait(double wt)
	{waitingTime=wt;

	}
	public void run ()
	{		DishwasherManager.getDishW().setState("pause");
		try {
	    	 System.out.println("********************************************************************************");

		
	    	 
	    	 Thread.sleep((long) ((waitingTime)));
	                    

			} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
				}
	
		if(DishwasherManager.getDishW().getState().equals("pause"))
		{ DishwasherManager.getDishW().setState("on");
		DishwasherListener.getServerConnection().sendToBuildingManager("DWResponseState"+DishwasherManager.getDishW().getState()); 
		}
	}
	}
	
}
