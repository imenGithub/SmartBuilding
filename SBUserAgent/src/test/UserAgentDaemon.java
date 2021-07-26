package test;
import fr.laas.sb.userAgent.UserAgentListener;
import fr.laas.sb.userAgentUI.UserAgentUI;

public class UserAgentDaemon {
	private static UserAgentUI userAgentUI;
	private static UserAgentListener userAgentListener; 
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% JUST FOR TEST %%%%%%%%%%%%%
	/*private Thread userIntefaceThread;
	public Thread getUserInterfaceThread()
	{
		return this.userIntefaceThread;
	}
	public void setUserInterfaceThread(Thread userInterfaceThread)
	{
		this.userIntefaceThread = userInterfaceThread;
	}*/
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	public static void main (String[] args)
	{
		userAgentUI = new UserAgentUI();
		userAgentListener = new UserAgentListener(userAgentUI);
		
		userAgentUI.start();
		userAgentListener.start();
		
		
		
/*		//connect the UA
		UserAgentUI userAgentUI = new UserAgentUI();
		
		UserAgentUIThread userAgentUIThread = new UserAgentUIThread();
		userAgentUIThread.setUserAgentUI(userAgentUI);
		Thread userInterfaceThread = new Thread (userAgentUIThread);//====> thread 2 declaration
		
		
		//listen to values sent from the BM
		UserAgentListener userAgentListener = new UserAgentListener();
		Thread listenerThread = new Thread (userAgentListener);//====> thread 1 declaration
		
		
		//listenerThread.setPriority(Thread.MAX_PRIORITY);// 1   Thread.MAX_PRIORITY
		//userIntefaceThread.setPriority(Thread.MIN_PRIORITY);// 2   Thread.MIN_PRIORITY
		
		userInterfaceThread.start();
		listenerThread.start();*/
		
		

		
		
		
		
		
		
		
		
		
		
		
		/*		int choice = userAgentUI.displayPrincipalMenu();
		if(choice != 0)
		{
			userAgentUI.manageMenus(choice);
		}*/
		
		
		
		
		
		//Afficher un menu au user
		//1. Get the lamp characteristics ------------> sous menu => state, intensity
		//2. Get the air conditioner characteristics....
		//3. ....
		
		//UserAgent userAgent = new UserAgent();
		//userAgent.sendHelloMsgToBMTest();

		
	}
	
}
