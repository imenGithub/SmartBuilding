package fr.laas.sb.serverBM.messages;


import java.util.Vector;
import  fr.laas.sb.serverBM.client.entities.Lamp;
import  fr.laas.sb.serverBM.client.entities.AirConditioner;
import fr.laas.sb.serverBM.SmartBuildingListener;
import fr.laas.sb.serverBM.client.entities.Component;
import fr.laas.sb.serverBM.clientConnection.ClientConnection;

public class ResponsePresenceSensor extends Message {
	Boolean dev=false;


	@SuppressWarnings("unchecked")
	@Override
	public void handle(String client, String value, String msgToSend) {
		// a response is received => is alive (not disconnected)
		sensorsEnvList.put(client, true);
		
		double pValue  = Double.parseDouble(value);
		SmartBuildingListener.getClientConnection().getPresenceSensor(client).setpresenceValue(pValue);
		SmartBuildingListener.getClientConnection().sendToEntity("presenceResponseFrom"+client+"is"+pValue, SmartBuildingListener.getChatClientUserSession(),"UserAgent");
	
		// e contain the devices connected to the presence sensor
		Vector<Component> e=SmartBuildingListener.getClientConnection().getDevicePresenceSensorList().get(client);
	 if(pValue==0.0) {
		SmartBuildingListener.getClientConnection();
		// if there is nobody in the room => deactivate the luminosity sensor of the room
		ClientConnection.getLumSensorActivated().put(client.substring(0, 7),false);
	} else {
		SmartBuildingListener.getClientConnection();
		ClientConnection.getLumSensorActivated().put(client.substring(0, 7),true);
	}
	 
	 //if the devices are managed automatically 
	 if(SmartBuildingListener.getClientConnection().getManualMode().equals("OFF"))
			
		{ 
			for(int i=0;i<e.size();i++)
		
		{	 
				// if the device is a lamp that is on and the presence sensor =0 => decrease the intensity of lamp each period until it is switched off
				if(e.get(i).getName().contains("Lamp") && pValue==0.0 && ((Lamp) e.get(i)).getState().equals("on"))
			{ 			
					dev=true;
					ManageWait mw = new ManageWait( e.get(i),client);
					mw.start();
			}
				// if the device is a lamp that is off and the presence sensor =1 => turn on the lamp				
				if(e.get(i).getName().contains("Lamp")&& pValue==1.0 && ((Lamp) e.get(i)).getState().equals("off"))
				{
				switchOnLamp((Lamp) e.get(i));

				}
				
				// if the SBM is decreasing the intensity of a lamp because there is no person and after that the presence sensor=1 (somebody entering)=> restore the intensity value of the lamp
				if(dev && e.get(i).getName().contains("Lamp")&& pValue==1.0 && ((Lamp) e.get(i)).getState().equals("on"))
				{
				switchOnLamp((Lamp) e.get(i));

				}
				
				// if the device is an ac that is on and the presence sensor =0 => switch off the ac after a period
				if(e.get(i).getName().contains("AirConditioner")&& pValue==0.0 && ((AirConditioner) e.get(i)).getTempValue()!=0.0)
			{ 		
					ManageWait mw = new ManageWait(e.get(i),client);
					mw.start();

			}
				
				
		
		}
		
		}
		
		}
		
		
		
		



	

/**
 * switch on a Lamp l with the preferred luminosity of the room if there is no LuminositySensor associated or in coordination with the luminosity sensor if it is associated
 * @param l a Lamp
 */
	private void switchOnLamp(Lamp l) {

		String nomLumSens=l.getName().replace("Lamp", "LuminositySensor");
		//System.out.println("nom LumSensor "+nomLumSens);
		if(!(SmartBuildingListener.getClientConnection().getDeviceLumSensorList().get(nomLumSens).isEmpty()))
			{
			this.fixLuminosity(nomLumSens);
			}
		else
		{ 			

			SmartBuildingListener.getClientConnection();
			double diff=ClientConnection.getValLumSensorList().get(nomLumSens);
			SmartBuildingListener.getClientConnection().sendToEntity("userlampintenvalue"+diff, SmartBuildingListener.getChatClientDeviceSession(),l.getName());
		}
					
	}

		
	

/**
 * fix the luminosity of the Lamp attached to a luminosity sensor
 * @param client luminosity sensor name
 */
	public void fixLuminosity(String client)
	{Vector<Component> e=SmartBuildingListener.getClientConnection().getDeviceLumSensorList().get(client);
	if(e!=null)
	{ 
		for(int i=0;i<e.size();i++)
	
	{	
			// if the luminosity in the room is not enough (required luminosity is not reached)
	if(SmartBuildingListener.getClientConnection().getLuminositySensor(client).getLumValue()< SmartBuildingListener.getClientConnection().getValLumSensorList().get(client))
	{	
		//add the necessary value to the intensity lamp
		// formula to convert from lux to lumen => 1 lux= 1lumen*area
		Double diff=
	SmartBuildingListener.getClientConnection().getValLumSensorList().get(client)-
	SmartBuildingListener.getClientConnection().getLuminositySensor(client).getLumValue();
	diff=diff*SmartBuildingListener.getClientConnection().getRoomsArea().get(client.substring(0, 7));
		if(((Lamp) e.get(i)).getIntensity()!=diff)
		{
			
		SmartBuildingListener.getClientConnection().sendToEntity("userlampintenvalue"+diff, SmartBuildingListener.getChatClientDeviceSession(),((Lamp) e.get(i)).getName());

		}
		}

	}
	}
		
	}


/**
 * class that manage the waiting time
 * @author emna
 *
 */

public class ManageWait extends Thread  {
	private Component c;
	private String namePresenceSens;
	public ManageWait(Component comp,String n)
	{this.c=comp;
	this.namePresenceSens=n;

	}
	public void run ()
	{		String name=c.getName();


	// if there is not occupant in the room during 9min=> swith off the ac
		if (name.contains("AirConditioner"))
		{
			  try {
			    	 System.out.println("********************************************************************************");

				
			    	 
			    	 Thread.sleep((long) ((90000)));//9min
			                    

					} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
						}
			
			
			if(SmartBuildingListener.getClientConnection().getPresenceSensor(namePresenceSens).getpresenceValue()==0.0)
		{ 
				SmartBuildingListener.getClientConnection().getAirConditioner(name).setTempValue(Double.parseDouble("0"));
			SmartBuildingListener.getClientConnection().sendToEntity("userTempAirCondValue0.0", SmartBuildingListener.getChatClientDeviceSession(),name);
		}
		}
		
		
	
	// after each period of 4min if it still there is no occupant in the room=> decrease the lamp intensity until it is switched off
	if (name.contains("Lamp"))
	{  Double old=((Lamp)c).getIntensity();
	boolean offLamp=false;
		while(!offLamp && SmartBuildingListener.getClientConnection().getPresenceSensor(namePresenceSens).getpresenceValue()==0.0)
		  { try {
		    	 System.out.println("********************************************************************************");

			
		    	 
		    	 Thread.sleep((long) ((9000)));//4min
		                    

				} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
					}
		
			if( SmartBuildingListener.getClientConnection().getPresenceSensor(namePresenceSens).getpresenceValue()==0.0)
			{
			double newVal= ((Lamp)c).getIntensity()-(old*0.1);
			if(newVal>0.0)
		SmartBuildingListener.getClientConnection().sendToEntity("userlampintenvalue"+newVal, SmartBuildingListener.getChatClientDeviceSession(),name);
			else
				offLamp=true;
			}
		  }
	if(offLamp && SmartBuildingListener.getClientConnection().getPresenceSensor(namePresenceSens).getpresenceValue()==0.0)
	 {((Lamp)c).setState("off");
		SmartBuildingListener.getClientConnection().sendToEntity("userlampstatevalueoff", SmartBuildingListener.getChatClientDeviceSession(),name);
dev=false;
	 }
		
}	
}
}
}
