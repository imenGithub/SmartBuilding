package fr.laas.sb.serverBM.client.entities;

import java.util.Calendar;

import fr.laas.sb.serverBM.FileRW;



/**
 * 
 * @author imen
 *
 */
public class SmartPlug extends Component{
	public String getState() {
		return state;
	}




	private String state="off";
	private double cons=0.0;
	private long measurementPeriod;
	private long sendingPeriod;

	public SmartPlug()
	{
	}
	
	
	
	
	
	public SmartPlug(String name) {
this.name=name;
}





	public void setState(String state) {
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
	
	/**
	 * @author emna	
	 */
		public void deconnetComponent()
		{connState="Dead";
		
		}
}