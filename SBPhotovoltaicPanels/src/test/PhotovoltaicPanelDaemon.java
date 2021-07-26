package test;
import fr.laas.sb.entity.fr.laas.sb.entity.photovoltaicPanel.PhotovoltaicPanelListener;
import fr.laas.sb.entity.fr.laas.sb.entity.photovoltaicPanel.PhotovoltaicPanelManager;

//PFE
//import fr.laas.sb.entity.thermometer.ThermometerListener;

/**
 * @author Imen
 * --- The main class of the Thermometer application ---
 */
public class PhotovoltaicPanelDaemon {
	private static PhotovoltaicPanelListener photovoltaicPanelListener;
	private static PhotovoltaicPanelManager photovoltaicPanelManager;
	
public static void main(String[] args) {
		
		photovoltaicPanelListener = new PhotovoltaicPanelListener();
		photovoltaicPanelManager = new PhotovoltaicPanelManager();
		
		//Listen to values of the thermometer
		photovoltaicPanelListener.start();
		
		//listen to the building manager requests
		photovoltaicPanelManager.start();
	}

}