package fr.laas.sb.entity.dishwasher;


/**
 * @author Imen
 */

public class Dishwasher {

	private String state="off";
	private String max_end_hour="0" ;
	private String remaining_Time="02:00:00" ;
	private long sendingPeriod;


	public Dishwasher()
	{
		
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
		//if the dishwasher finished the maximum end hour is set to 0
		if(state.equals("off"))
			this.setMax_end_hour("0");
	}


	public String getMax_end_hour() {
		return max_end_hour;
	}


	public void setMax_end_hour(String max_end_hour) {
		this.max_end_hour = max_end_hour;
	}


	public String getRemaining_Time() {
		return remaining_Time;
	}


	public void setRemaining_Time(String remaining_Time) {
		this.remaining_Time = remaining_Time;
	}

	/**
	 * Returns the period of sending the dishwasher characteristics values to the building manager 
	 * 
	 * @return the period of sending the dishwasher characteristics values to the building manager
	 */
	public long getSendingPeriod()
	{
		return this.sendingPeriod;
	}
	
	/**
	 * Sets the period of sending the dishwasher characteristics values to the building manager
	 * 
	 * @param sendingPeriod
	 *            the period of sending the dishwasher characteristics values to the building manager
	 */
	public void setSendingPeriod(long sendingPeriod)
	{
		this.sendingPeriod = sendingPeriod;
	}







}
	


	
