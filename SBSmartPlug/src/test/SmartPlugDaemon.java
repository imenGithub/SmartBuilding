package test;
import fr.laas.sb.entity.fr.laas.sb.entity.smartPlug.SmartPlugListener;
import fr.laas.sb.entity.fr.laas.sb.entity.smartPlug.SmartPlugManager;

//PFE
//import fr.laas.sb.entity.thermometer.ThermometerListener;

/**
 * @author Imen
 * --- The main class of the Thermometer application ---
 */
public class SmartPlugDaemon {
	private static SmartPlugListener photovoltaicPanelListener;
	private static SmartPlugManager photovoltaicPanelManager;
	
public static void main(String[] args) {
try{
	photovoltaicPanelListener = new SmartPlugListener(Integer.parseInt(args[0]));

}catch (Exception e){
		photovoltaicPanelListener = new SmartPlugListener(-1);
}
		photovoltaicPanelManager = new SmartPlugManager();
		 
		//Listen to values of the thermometer
		photovoltaicPanelListener.start();
		
		//listen to the building manager requests
		photovoltaicPanelManager.start();
	}

}