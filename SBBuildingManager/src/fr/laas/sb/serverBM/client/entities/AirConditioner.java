package fr.laas.sb.serverBM.client.entities;

import java.text.DecimalFormat;

import fr.laas.sb.serverBM.FileRW;
import fr.laas.sb.serverBM.SmartBuildingListener;
import fr.laas.sb.serverBM.clientConnection.ClientConnection;

/**
 * @author Imen
 * --- This class is a container for the air conditioner data. This includes the air conditioner informations
 * which consist of the temperature value, the digits precision of the temperature value and the period of sending values to the building manager ---
 */
public class AirConditioner extends Component{
	
	private double tempValue=0;
	private int precision;
	DecimalFormat df = new DecimalFormat ( ) ;
private FileRW f;
	public AirConditioner()
	{
		
	}
	
	/**
	 * Basic constructor
	 * 
	 * @param tempValue
	 *            the temperature value of the air conditioner
	 * @param precision
	 * 			  the digits precision of the temperature value         
	 * @param sendingPeriod
	 * 			  the period of sending the air conditioner characteristics values to the building manager
	 */
	public AirConditioner(FileRW file,String name,double tempValue, int precision)
	{	this.name=name;
		this.tempValue = tempValue;
		this.precision = precision;
		df.setMaximumFractionDigits ( 3 ) ; //arrondi à 2 chiffres apres la virgules
		df.setMinimumFractionDigits ( 3 ) ;
		df.setDecimalSeparatorAlwaysShown ( true ) ;
		this.f=file;
	}
	
	
	/**
	 * Returns the temperature value of the air conditioner
	 * 
	 * @return the temperature value of the air conditioner
	 */
	public double getTempValue()
	{
		return this.tempValue;
	}
	
	
	/**
	 * Sets the temperature value of the air conditioner
	 * 
	 * @param state
	 *            the temperature value of the air conditioner
	 */
	public void setTempValue(double tempValue)
	{ // add a simulated energy consumption
		double old=1000/(this.tempValue);//simulation
		double newV= 1000/(tempValue);//simulation
		double cons;
		if(this.tempValue!=0.0)
		cons=(double) (newV-old);
		else
		cons=newV;
		
		if(tempValue==0.0)
			f.addCons(-old);//simulation
		else
		{
		f.addCons(cons);//simulation
		}
		this.tempValue = tempValue;

				
		try{
			String st=df.format(newV);
			cons=Double.parseDouble(st.replace(',', '.'));	
		f.create(SmartBuildingListener.getClientConnection().getDevicePlugList().get(this.getName()),cons);
		
		}
		catch(Exception e)
		{}
		
		
		
		
		
		
	}
	
	/**
	 * Returns the digits precision of the temperature value of the air conditioner
	 * 
	 * @return the digits precision of the temperature value of the air conditioner
	 */
	public int getPrecision()
	{
		return this.precision;
	}
	
	/**
	 * Sets the digits precision of the temperature value of the air conditioner
	 * 
	 * @param precision
	 *            the digits precision of the temperature value of the air conditioner
	 */
	public void setPrecision(int precision)
	{
		this.precision = precision;
	}
	
	
/**
 * @author emna
 */
	public void deconnetComponent()
	{
		connState="Dead";
		// subtract the simulated energy consumption
		double old=(1000/this.tempValue);//simulation
	f.addCons((double) (-old));//simulation
	f.create(SmartBuildingListener.getClientConnection().getDevicePlugList().get(this.getName()),0.0);


	}
	
	
	
	
}