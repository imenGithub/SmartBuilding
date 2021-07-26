package fr.laas.sb.serverBM.client;

import java.util.Hashtable;
import java.util.Vector;

import fr.laas.sb.serverBM.client.entities.Component;


/**
 * Class that contains the preferences of the user
 * @author imen
 *
 */
public class UserAgent extends Component{
	private   Hashtable<String,Double> lampIntensity;
	private   Hashtable<String,Double> AcTemp;

	
//	private double LampIntensity=0;
//	private double TempValueAC=0;
	
	public UserAgent(String name)
	{lampIntensity=new Hashtable<String,Double>();
	AcTemp=new Hashtable<String,Double>();
		this.name=name;}
	
	@Override
	public void deconnetComponent() {
		// TODO Auto-generated method stub
		
	}

	public Hashtable<String,Double> getLampIntensity() {
		return lampIntensity;
	}

	public void setLampIntensity(Hashtable<String,Double> lampIntensity) {
		this.lampIntensity = lampIntensity;
	}

	public Hashtable<String,Double> getAcTemp() {
		return AcTemp;
	}

	public void setAcTemp(Hashtable<String,Double> acTemp) {
		this.AcTemp = acTemp;
	}

}
