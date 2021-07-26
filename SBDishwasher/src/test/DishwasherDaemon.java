package test;
import fr.laas.sb.entity.dishwasher.DishwasherListener;
import fr.laas.sb.entity.dishwasher.DishwasherManager;
/**
 * @author Imen
 */
public class DishwasherDaemon {
	static DishwasherListener dishWListener;
	static DishwasherManager dishWManager ;
	public static void main(String[] args) {

			dishWListener = new DishwasherListener(); //here the lamp will be connected
			dishWManager = new DishwasherManager();
			
			//Listen values from the air conditioner
			dishWListener.start();
			//listen to the building manager requests
			dishWManager.start();
//			Runtime.getRuntime().addShutdownHook(new Thread() {
//				   @Override
//				   public void run() {
//				    System.out.println("Inside Add Shutdown Hook");
//				   }
//				  });
			//marche si System.exit(0); ou ctrl+c ds cmd 
			 			
			
	}
	
		  


}

