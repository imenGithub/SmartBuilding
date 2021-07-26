package fr.laas.sb.serverBM.messages;

import java.util.Vector;

import fr.laas.sb.serverBM.SmartBuildingListener;
import fr.laas.sb.serverBM.client.entities.Component;
import fr.laas.sb.serverBM.client.entities.Lamp;

public class ResponsLumSensor extends Message {

	@SuppressWarnings("static-access")
	@Override
	public void handle(String client, String value, String msgToSend) {
		sensorsEnvList.put(client, true);
		double lValue  = Double.parseDouble(value);
		SmartBuildingListener.getClientConnection().getLuminositySensor(client).setLumValue(lValue);
		SmartBuildingListener.getClientConnection().sendToEntity("lumResponseFrom"+client+"is"+lValue, SmartBuildingListener.getChatClientUserSession(),"UserAgent");
		
		 //if the devices are managed automatically 
		if(SmartBuildingListener.getClientConnection().getManualMode().equals("OFF"))
			
		{	
		//if there is occupant in the room (luminosity sensor activated)	
		if(SmartBuildingListener.getClientConnection().getLumSensorActivated().get(client.substring(0, 7)))
		{
		Vector<Component> e=SmartBuildingListener.getClientConnection().getDeviceLumSensorList().get(client);
		if(e!=null)
		{ 
			for(int i=0;i<e.size();i++)
		
		{			
				// if the luminosity in the room is not enough (required luminosity is not reached)
		if(SmartBuildingListener.getClientConnection().getLuminositySensor(client).getLumValue()< SmartBuildingListener.getClientConnection().getValLumSensorList().get(client))
		{	
			//add the necessary value to the intensity lamp
			// formula to convert from lux to lumen => 1 lux= 1lumen*area
			Double diff=SmartBuildingListener.getClientConnection().getValLumSensorList().get(client)-SmartBuildingListener.getClientConnection().getLuminositySensor(client).getLumValue();
	 try{

		diff=diff*SmartBuildingListener.getClientConnection().getRoomsArea().get(client.substring(0, 7));
		((Lamp) e.get(i)).setIntensity(((Lamp) e.get(i)).getIntensity()+diff);
			SmartBuildingListener.getClientConnection().sendToEntity("userlampintenvalue"+((Lamp) e.get(i)).getIntensity()+diff, SmartBuildingListener.getChatClientDeviceSession(),((Lamp) e.get(i)).getName());

		
	 }
		catch(Exception ee){}
			}
		
		else
		{

			 //if the luminosity in the room is higher than the required luminosity	and tha lamp is on => decrease the lamp intensity or switch it off if it is necessary 	
			if(SmartBuildingListener.getClientConnection().getLuminositySensor(client).getLumValue()> SmartBuildingListener.getClientConnection().getValLumSensorList().get(client))
				 if(((Lamp) e.get(i)).getState().equals("on"))	
				 {
			{	Double diff=SmartBuildingListener.getClientConnection().getLuminositySensor(client).getLumValue()-SmartBuildingListener.getClientConnection().getValLumSensorList().get(client);
		 try{
			diff=diff*SmartBuildingListener.getClientConnection().getRoomsArea().get(client.substring(0, 7));
			Double diff1=((Lamp) e.get(i)).getIntensity()-diff;
			if(diff1>0)
				{((Lamp) e.get(i)).setIntensity(diff1);
				SmartBuildingListener.getClientConnection().sendToEntity("userlampintenvalue"+diff1, SmartBuildingListener.getChatClientDeviceSession(),((Lamp) e.get(i)).getName());

				}
			else
			 if(diff1<0 && ((Lamp) e.get(i)).getState().equals("on"))	
				{((Lamp) e.get(i)).setState("off");
				SmartBuildingListener.getClientConnection().sendToEntity("userlampstatevalueoff", SmartBuildingListener.getChatClientDeviceSession(),((Lamp) e.get(i)).getName());
				}
		 }
			catch(Exception ee){}
				}
			
			
			
			
			
		
			
			
			
		}
			
		}
	
		}
	}
	}

	}
	}
}
