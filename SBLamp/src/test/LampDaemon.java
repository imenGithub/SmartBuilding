package test;
import fr.laas.sb.entity.lamp.LampListener;
import fr.laas.sb.entity.lamp.LampManager;
/**
 * @author Imen
 * --- The main class of the Lamp application ---
 */
public class LampDaemon {
	static LampListener lampListener;
	static LampManager lampManager ;
	public static void main(String[] args) {
try{
	lampListener = new LampListener(Integer.parseInt(args[0])); //here the lamp will be connected
}
		catch (Exception e)
		{
			lampListener = new LampListener(-1); //here the lamp will be connected
		}
			lampManager = new LampManager();
			
			//Listen values from the air conditioner
			lampListener.start();
			//listen to the building manager requests
			lampManager.start();
//			Runtime.getRuntime().addShutdownHook(new Thread() {
//				   @Override
//				   public void run() {
//				    System.out.println("Inside Add Shutdown Hook");
//				   }
//				  });
			//marche si System.exit(0); ou ctrl+c ds cmd 
			 			
			
	}
	
		  


}

