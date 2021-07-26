package test;

import fr.laas.sb.serverBM.FileRW;
import fr.laas.sb.serverBM.SmartBuildingListener;
import fr.laas.sb.serverBM.SmartBuildingManager;


public class SmartBuildingDaemon {
	static SmartBuildingListener smartBuildingListener;
	static SmartBuildingManager smartBuildingManager ;
	static FileRW simulationFile;
	public static void main(String [] args)
	
	{	simulationFile=new FileRW("../SimFile.xml");	
	simulationFile.initiate();
		//receiving messages (from user agent & entities)
		smartBuildingListener = new SmartBuildingListener(simulationFile);
		smartBuildingManager = new SmartBuildingManager(simulationFile);
		smartBuildingListener.start();
	    smartBuildingManager.start();


	  
	    
	    
	}
	


}
