package fr.laas.sb.serverBM;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Vector;
import fr.laas.sb.serverBM.client.entities.AirConditioner;
import fr.laas.sb.serverBM.client.entities.Dishwasher;
import fr.laas.sb.serverBM.client.entities.Component;
import fr.laas.sb.serverBM.client.entities.Lamp;
import fr.laas.sb.serverBM.client.entities.PresenceSensor;
import fr.laas.sb.serverBM.client.entities.LuminositySensor;
import fr.laas.sb.serverBM.client.entities.SmartPlug;
import fr.laas.sb.serverBM.messages.Alive;
import fr.laas.sb.serverBM.messages.ResponsepowerProdPhotovoltaicPanel;
import fr.laas.sb.serverBM.messages.Message;
import fr.laas.sb.serverBM.messages.ResponsePowerCons;
import fr.laas.sb.serverBM.messages.ResponseTempThermometer;
import fr.laas.sb.serverBM.messages.ResponsePresenceSensor;


public class SmartBuildingManager extends Thread{
	String rouge="\u001B[31m";
	String vert="\u001B[32m";
	String jaune="\u001B[33m";
	String bleu="\u001B[34m";
	String cyan="\u001B[36m";
	String magenta="\u001B[35m";
	String gris="\u001B[37m";
	String rougeGras="\u001b[1;31m";
	String vertGras="\u001b[1;32m";
	String jauneGras="\u001b[1;33m";
	String bleuGras="\u001b[1;34m";
	String magentaGras="\u001b[1;35m";
	String cyanGras="\u001b[1;36m";
	String reset="\u001b[0m";
	/** 
	 *  variables  to manage the devices' (sensors) disconnection 
	 * example: if the PSM did not send a value during three sendingPeriod the it is disconnected
	 * @author emna
	 */
	int countDeadPSM=0;
	int countDeadPS=0;
	int countDeadLS=0;
	int countDeadTh=0;
	int countDeadPhPanel=0;
	
	int oneTimeMajor=0;
	
	/** 
	 *  the default minor and major adaptation thresholds
	 * @author emna
	 */
	double minor_adpat_threshold=0;
	double major_adpat_threshold=50;
	
	/** 
	 * threshold for values restoration
	 * @author emna
	 */
	double thrMinorRest=-20;
	
	
	double bigCons=0.0;
	double consPause=0.0;
	
	/** 
	 * the default values of the devices' flexibility (for the adaptation)
	 * @author emna
	 */
@SuppressWarnings("serial")
private static Hashtable<String,Double> componentConfList=new Hashtable<String,Double>() {{
	put("Lamp",900.0); put("AirCond",1.0);put("Sensor",2900.0); }}; // for sensor 1000 equals 1ms
	
	/** 
	 * the default maximum values of the devices' flexibility (for the adaptation)
	 * @author emna
	 */
	private static Hashtable<String,Double> componentMaxConfList=new Hashtable<String,Double>() {{
		put("Lamp",1000.0); put("AirCond",1.5); }};
	
	String bigConsumer;
	private int compteur=1;
	private boolean augmentation=false;
	private boolean confort=false;
	private FileRW f;
	private boolean testMinor=false;
	private boolean testMajor=false;
	private boolean normalConsumption=false;
	private boolean normalPauseConsumption=false;
	private boolean normalBigConsumption=false;
	private boolean twoNormalConsumption=false;

	
	long getMeasurementPeriodThInit=SmartBuildingListener.getClientConnection().getThermometer("Thermometer").getMeasurementPeriod();
	long getSendingPeriodThInit=SmartBuildingListener.getClientConnection().getThermometer("Thermometer").getSendingPeriod();
	long getSendingPeriodPSMInit=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getSendingPeriod();
	long getMeasurementPeriodPSMInit=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getMeasurementPeriod();
	
    public SmartBuildingManager(FileRW file){
    	this.f=file;
    }
    

    public double addValue(double value,int compteur,String compName){
    	
    	if(compName.equals("Sensor"))
        	value=value+(componentConfList.get(compName)*compteur);
    	else
    	
    	if(compteur==100)
        	value=value+componentMaxConfList.get(compName);

    	else
        	value=value+componentConfList.get(compName);
    	return value;
    }
    public double reduceValue(double value,int compteur,String compName){
    	if(compName.equals("Sensor"))
        	value=value-((componentConfList.get(compName))*compteur);
    	if(compteur==100)
        	value=value-componentMaxConfList.get(compName);

    	else
        	value=value-componentConfList.get(compName);
    	return value;
    }

   
	public void increasePeriod(){
		
		
	//augmentation des periodes du thermometre	
	SmartBuildingListener.getClientConnection().getThermometer("Thermometer").setMeasurementPeriod((long) this.addValue(getMeasurementPeriodThInit,compteur,"Sensor"));
	SmartBuildingListener.getClientConnection().getThermometer("Thermometer").setSendingPeriod((long) this.addValue(getSendingPeriodThInit,compteur,"Sensor"));
	//augmentation des periodes du PSM
	//SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").setPowerCons(SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons()-2);

	SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").setMeasurementPeriod((long) this.addValue(getMeasurementPeriodPSMInit,compteur,"Sensor"));
	SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").setSendingPeriod((long) this.addValue(getSendingPeriodPSMInit,compteur,"Sensor"));
	 
	 long getMeasurementPeriodTh=SmartBuildingListener.getClientConnection().getThermometer("Thermometer").getMeasurementPeriod();
	 long getSendingPeriodTh=SmartBuildingListener.getClientConnection().getThermometer("Thermometer").getSendingPeriod();
	 long getSendingPeriodPSM=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getSendingPeriod();
	 long getMeasurementPeriodPSM=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getMeasurementPeriod();

	//envoi des modification au thermometre	 
	SmartBuildingListener.getClientConnection().sendToEntity("measure"+getMeasurementPeriodTh, SmartBuildingListener.getChatClientDeviceSession(),"Thermometer");
	SmartBuildingListener.getClientConnection().sendToEntity("sending"+getSendingPeriodTh, SmartBuildingListener.getChatClientDeviceSession(),"Thermometer");
	//envoi des modification au PSM
	//SmartBuildingListener.getClientConnection().sendToPowerSmartMeter("cons"+SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons(), SmartBuildingListener.getChatClientDeviceSession());

	SmartBuildingListener.getClientConnection().sendToEntity("measure"+getMeasurementPeriodPSM, SmartBuildingListener.getChatClientDeviceSession(),"PowerSmartMeter");
	SmartBuildingListener.getClientConnection().sendToEntity("sending"+getSendingPeriodPSM, SmartBuildingListener.getChatClientDeviceSession(),"PowerSmartMeter");
    //System.err.println("aaa"+getMeasurementPeriodTh+"bbbb");
	}
	public void reducePeriod(){
		//System.err.println("reducePeriod()");

		
		//rétabilr les periodes du thermometre	
		SmartBuildingListener.getClientConnection().getThermometer("Thermometer").setMeasurementPeriod(getMeasurementPeriodThInit);
		SmartBuildingListener.getClientConnection().getThermometer("Thermometer").setSendingPeriod(getSendingPeriodThInit);
		//rétablir les periodes du PSM
		SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").setMeasurementPeriod(getMeasurementPeriodPSMInit);
		SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").setSendingPeriod(getSendingPeriodPSMInit);

		/*long getMeasurementPeriodTh=SmartBuildingListener.getClientConnection().getThermometer("Thermometer").getMeasurementPeriod();
		 long getSendingPeriodTh=SmartBuildingListener.getClientConnection().getThermometer("Thermometer").getSendingPeriod();
		 long getSendingPeriodPSM=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getSendingPeriod();
		 long getMeasurementPeriodPSM=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getMeasurementPeriod();*/

		
		//envoi des modification au thermometre	 
		SmartBuildingListener.getClientConnection().sendToEntity("measure"+SmartBuildingListener.getClientConnection().getThermometer("Thermometer").getMeasurementPeriod(), SmartBuildingListener.getChatClientDeviceSession(),"Thermometer");
		SmartBuildingListener.getClientConnection().sendToEntity("sending"+SmartBuildingListener.getClientConnection().getThermometer("Thermometer").getSendingPeriod(), SmartBuildingListener.getChatClientDeviceSession(),"Thermometer");
		//envoi des modification au PSM
		//SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").setPowerCons(SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons()+6);

		//SmartBuildingListener.getClientConnection().sendToPowerSmartMeter("cons"+SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons(), SmartBuildingListener.getChatClientDeviceSession());

		SmartBuildingListener.getClientConnection().sendToEntity("measure"+SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getMeasurementPeriod(), SmartBuildingListener.getChatClientDeviceSession(),"PowerSmartMeter");
		SmartBuildingListener.getClientConnection().sendToEntity("sending"+SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getSendingPeriod(), SmartBuildingListener.getChatClientDeviceSession(),"PowerSmartMeter");

		}	
	
	public void changeCharacteristicsValues(){

		// augmenter la valeur de température des climatiseurs
		//SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").setPowerCons(SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons()-2);

		//SmartBuildingListener.getClientConnection().sendToPowerSmartMeter("cons"+SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons(), SmartBuildingListener.getChatClientDeviceSession());
		SmartBuildingListener.getClientConnection().sendToEntity("sending"+SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getSendingPeriod(), SmartBuildingListener.getChatClientDeviceSession(),"PowerSmartMeter");
		
		
		Vector<Component> acList=SmartBuildingListener.getClientConnection().findComponentsByName("AirConditioner");
		for(int i=0;i<acList.size();i++)
		{	if(acList.get(i).getName()!=bigConsumer)
			if(((AirConditioner) acList.get(i)).getTempValue()!=0.0)
			{((AirConditioner) acList.get(i)).setTempValue(this.addValue(((AirConditioner) acList.get(i)).getTempValue(),1,"AirCond"));
		SmartBuildingListener.getClientConnection().sendToEntity("userTempAirCondValue"+((AirConditioner) acList.get(i)).getTempValue(), SmartBuildingListener.getChatClientDeviceSession()
				, acList.get(i).getName());
		}
		}
		//System.out.println("Ac nouvelle valeur: "+SmartBuildingListener.getClientConnection().getAirConditioner().getTempValue());
		
		//diminuer la valeur d'intensité des lampes
		
		Vector<Component> lampList=SmartBuildingListener.getClientConnection().findComponentsByName("Lamp");
		for(int i=0;i<lampList.size();i++)
		{	if(lampList.get(i).getName()!=bigConsumer||oneTimeMajor!=2)	
		if(((Lamp) lampList.get(i)).getIntensity()!=0.0)
		{
			((Lamp) lampList.get(i)).setIntensity((this.reduceValue(((Lamp) lampList.get(i)).getIntensity(),1,"Lamp")));
		SmartBuildingListener.getClientConnection().sendToEntity("userLampIntenValue"+((Lamp) lampList.get(i)).getIntensity(),
				SmartBuildingListener.getChatClientDeviceSession(),((Lamp) lampList.get(i)).getName());
		}
		}
		
	}
	public void restoreCharacteristicsValues(){

		
		//rétablir la valeur d'intensité des lampes
		Vector<Component> lampList=SmartBuildingListener.getClientConnection().findComponentsByName("Lamp");
		for(int i=0;i<lampList.size();i++)
		{
			if(lampList.get(i).getName()!=bigConsumer||oneTimeMajor!=2)	
			{ 
				if(((Lamp) lampList.get(i)).getIntensity()!=(double)0.0)
				{
				try{
				//double inten=SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").getLampIntensity().get(lampList.get(i).getName());
			
				double inten=this.addValue(((Lamp) lampList.get(i)).getIntensity(),1,"Lamp");

				((Lamp) lampList.get(i)).setIntensity(inten);
		SmartBuildingListener.getClientConnection().sendToEntity("userLampIntenValue"+inten,
				SmartBuildingListener.getChatClientDeviceSession(),((Lamp) lampList.get(i)).getName());
			}
			catch(Exception e){}
			}
			}
		}

		// rétablir la valeur de température des climatiseurs
		Vector<Component> acList=SmartBuildingListener.getClientConnection().findComponentsByName("AirConditioner");
		for(int i=0;i<acList.size();i++)
		{	if(acList.get(i).getName()!=bigConsumer||oneTimeMajor!=2)		
			{
			if(((AirConditioner) acList.get(i)).getTempValue()!=(double)0.0)
			
			{
			try{
				//double temp=SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").getAcTemp().get(acList.get(i).getName());
			double temp=this.reduceValue(((AirConditioner) acList.get(i)).getTempValue(),1,"AirCond");
				((AirConditioner) acList.get(i)).setTempValue(temp);
		SmartBuildingListener.getClientConnection().sendToEntity("userTempAirCondValue"+temp, SmartBuildingListener.getChatClientDeviceSession()
				, acList.get(i).getName());
			}
			catch(Exception e){}
			}
		}		
		}
	}
	
	//adapt
		public void run(){
/////////////////

			System.out.println("Enter the value of comfort flexibility for the Lamp (lumen) : ");
			System.out.println("");
			Scanner sc = new Scanner(System.in);
			String i=sc.nextLine();
			double e=Double.parseDouble(i);
			componentConfList.put("Lamp", e);
			
			System.out.println("Enter the value of comfort flexibility for the air conditioner (degree) : ");
			System.out.println("");
			Scanner sc1 = new Scanner(System.in);
			String i1=sc1.nextLine();
			double ee=Double.parseDouble(i1);
			componentConfList.put("AirCond", ee);
			
			
			
			System.out.println("Enter the maximum value of comfort flexibility for the Lamp (lumen) : ");
			System.out.println("");
			Scanner sc11 = new Scanner(System.in);
			String i11=sc11.nextLine();
			double e11=Double.parseDouble(i11);
			componentMaxConfList.put("Lamp", e11);
			
			System.out.println("Enter the maximum value of comfort flexibility for the air conditioner (degree) : ");
			System.out.println("");
			Scanner sc111 = new Scanner(System.in);
			String i111=sc111.nextLine();
			double e111=Double.parseDouble(i111);
			componentMaxConfList.put("AirCond", e111);
			while(true){
				
				//////////////////
				this.manageStoppedSensors();
				this.manageStoppedEntities();
			System.out.println(bleu+"*********************************SmartBuildingManager********************************"+reset);
		    testMinor=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons()>minor_adpat_threshold;
		    testMajor=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons()>major_adpat_threshold;
		    normalPauseConsumption=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons()+consPause<=0.0;
			normalBigConsumption=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons()+bigCons<=0.0;
			twoNormalConsumption=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons()+bigCons+consPause<=0.0;

		    normalConsumption=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons()<=thrMinorRest;

		    //System.out.println("test: "+test);
		    if(!testMajor && !testMinor)
		    {System.out.println(bleu+" General energy consumption (PSM) ==> "+reset+vert+SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons());
		    
		    System.out.println(bleu+" Produced Energy  ==> "+reset+vert+f.getProd());
		    System.out.println(bleu+" Energy consumption ==> "+reset+vert+f.getCons()+reset+bleu+reset);
		    }
		    else
  {System.out.println(bleu+" General energy consumption (PSM) ==> "+reset+rouge+SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons());
		    
		    System.out.println(bleu+" Produced Energy  ==> "+reset+rouge+f.getProd());
		    System.out.println(bleu+" Energy consumption ==> "+reset+rouge+f.getCons()+reset+bleu+reset);
		    }
		    /** 
			 * if major adaptation threshold is achieved and the major adaptation was not executed
			 * 
			 */
    		//onTimeMajor==0 means that major adaptation is not executed

		    //begin adaptation algorithm
		    if(oneTimeMajor==-2)
	    		minor_Adaptation();

		    if(testMajor && oneTimeMajor==0)
		    	major_Adaptation();
		    else
		    	
		    	if(testMinor)
		    		minor_Adaptation();
		    //end adaptation algorithm

		    	else
				    //begin values restoration

		    		//onTimeMajor==1 means that only devices were paused
		    		//onTimeMajor==2 means that devices were paused and the functioning of the biggest consumer is reduced
		  
		    		if(twoNormalConsumption  && oneTimeMajor==2)
						  this.majorAdaptRestoreCharacteristicsValues2(); 
		    		else
		    if(normalPauseConsumption && oneTimeMajor==1)
				  this.majorAdaptRestoreCharacteristicsValues1();
			  else
				  if(normalBigConsumption  && oneTimeMajor==2)
			  this.majorAdaptRestoreCharacteristicsValues3(); 
			 else
			  if(normalConsumption && augmentation){
				  System.out.println("*"+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+"*");
				  System.out.println("*"+"+                   Periods restoration               +"+"*");
				  System.out.println("*"+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+"*");
				 this.reducePeriod(); 
				 augmentation=false; 
				 compteur=1;

			  }
			  else
				 
				  if(normalConsumption && confort){
					  System.out.println("*"+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+"*");
					  System.out.println("*"+"+       Entities' characteristics restoration    +"+"*");
					  System.out.println("*"+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+"*");
					  
			  this.restoreCharacteristicsValues();
			  
			  this.reducePeriod();

			  confort=false;
			  compteur=1;

			    //end values restoration

				  }			

			
		      try {
		    	 System.out.println("********************************************************************************");

			
		    	 
		    	 Thread.sleep((long) ((SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getMeasurementPeriod())*1.3));
		                    

				} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
					}
		      
		     
		      
		      
		      
  
			}
			
		}
		
	    /* switch on the paused devices 
	     * @author emna
	     */
		private void majorAdaptRestoreCharacteristicsValues1() {
			oneTimeMajor=0;
			System.out.println("major adaptation values restoration1");		
			if(SmartBuildingListener.getClientConnection().getDW("Dishwasher")!=null)
			{	
				if(SmartBuildingListener.getClientConnection().getDW("Dishwasher").getState().equals("pause"))
			SmartBuildingListener.getClientConnection().sendToEntity("userdwstatevalueon",
					SmartBuildingListener.getChatClientDeviceSession(),"Dishwasher");
			}
		}
		
		/* switch on the paused devices and restore normal functioning of the biggest consumer 
	     * @author emna
	     */
		private void majorAdaptRestoreCharacteristicsValues2() {
			oneTimeMajor=0;
			System.out.println("major adaptation values restoration2");		
			if(SmartBuildingListener.getClientConnection().getDW("Dishwasher")!=null)
			{ if(SmartBuildingListener.getClientConnection().getDW("Dishwasher").getState().equals("pause"))
			SmartBuildingListener.getClientConnection().sendToEntity("userdwstatevalueon",
					SmartBuildingListener.getChatClientDeviceSession(),"Dishwasher");
			}
			Component b=SmartBuildingListener.getClientConnection().getBigConsumer();
			
			if(b!=null)
			{bigConsumer="";	
				if(b.getName().contains("Lamp"))
			{ 				if(((Lamp)b).getIntensity()!=(double)0.0)
			{
					double inten=SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").getLampIntensity().get(b.getName());
					((Lamp) b).setIntensity(inten);
			SmartBuildingListener.getClientConnection().sendToEntity("userLampIntenValue"+inten,
					SmartBuildingListener.getChatClientDeviceSession(),b.getName());
					
				
				}
			}
				
					else
			
			{ if(((AirConditioner)b).getTempValue()!=(double)0.0)
						{double temp=SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").getAcTemp().get(b.getName()); 
						((AirConditioner) b).setTempValue(temp);
					SmartBuildingListener.getClientConnection().sendToEntity("userTempAirCondValue"+temp, SmartBuildingListener.getChatClientDeviceSession()
							, b.getName());
			
			
			}	
		}
			}
		
		}
		
		
		
		
		
		
		
		
		
		
		
		/* restore normal functioning of the biggest consumer 
	     * @author emna
	     */
		private void majorAdaptRestoreCharacteristicsValues3() {
			oneTimeMajor=0;
			System.out.println("major adaptation values restoration3");		
			Component b=SmartBuildingListener.getClientConnection().getBigConsumer();
			
			if(b!=null)
			{bigConsumer="";	
				if(b.getName().contains("Lamp"))
			{ 				if(((Lamp)b).getIntensity()!=(double)0.0)
			{
					double inten=SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").getLampIntensity().get(b.getName());
					((Lamp) b).setIntensity(inten);
			SmartBuildingListener.getClientConnection().sendToEntity("userLampIntenValue"+inten,
					SmartBuildingListener.getChatClientDeviceSession(),b.getName());
					
				
				}
			}
				
					else
			
			{ if(((AirConditioner)b).getTempValue()!=(double)0.0)
						{double temp=SmartBuildingListener.getClientConnection().getUserAgentPreferences("UserAgent").getAcTemp().get(b.getName()); // à voiiiiir
						((AirConditioner) b).setTempValue(temp);
					SmartBuildingListener.getClientConnection().sendToEntity("userTempAirCondValue"+temp, SmartBuildingListener.getChatClientDeviceSession()
							, b.getName());
			
			
			}	
		}
			}
		
		}
		
		
		
		
		
		
		
		
		
		
		/* calculate the possible pause period for devices 
	     * @author emna
	     */
		private int calculateSleepTime(String max, String RT)
		{				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
			Date dateMaxEndHour = null;
			try {
				dateMaxEndHour = simpleDateFormat.parse(max);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		String[] tabRemainTime = RT.split(":");
		
			Calendar TC = Calendar.getInstance();		    	
	    	 Calendar calendarMEH = Calendar.getInstance();
		    	calendarMEH.setTime(dateMaxEndHour);
	       
		    	TC.add(Calendar.HOUR_OF_DAY,Integer.parseInt(tabRemainTime[0]));
		    	TC.add(Calendar.MINUTE,Integer.parseInt(tabRemainTime[1]));
		    	TC.add(Calendar.SECOND,Integer.parseInt(tabRemainTime[2]));
		
		    	
		    	int hour2= TC.get(Calendar.HOUR_OF_DAY);
		        int minute2 = TC.get(Calendar.MINUTE);
		        int second2 = TC.get(Calendar.SECOND);

				    	
		

		    	
		    	calendarMEH.add(Calendar.HOUR_OF_DAY ,-hour2);
		    	calendarMEH.add(Calendar.MINUTE,-minute2);
		    	calendarMEH.add(Calendar.SECOND,-second2);


		    	
		    	int hour= calendarMEH.get(Calendar.HOUR);
		        int minute = calendarMEH.get(Calendar.MINUTE);
		        int second = calendarMEH.get(Calendar.SECOND);

			
	        
	        
			System.out.println("Dishwasher paused for :"+String.valueOf(hour)+" hour "+ " and "+String.valueOf(minute)+" minute and "+String.valueOf(second)+ " second ");
			
			int sleepTime=second*1000;
			sleepTime+=(minute*60)*1000;
			sleepTime+=((hour*60)*60)*1000;
return sleepTime;			
			
			
		}
		
		
		
		/* major adaptation : pause devices +  [reduce the functioning of the biggest consumer]
	     * @author emna
	     */
		private void major_Adaptation() {
			oneTimeMajor=-2;

			System.out.println(jaune+"Major adaptation selected"+reset);
			if(SmartBuildingListener.getClientConnection().getDW("Dishwasher")!=null)
			{ if(!SmartBuildingListener.getClientConnection().getDW("Dishwasher").getMax_end_hour().equals("0"))
			{ 
			int pauseTime=calculateSleepTime(SmartBuildingListener.getClientConnection().getDW("Dishwasher").getMax_end_hour(),
					SmartBuildingListener.getClientConnection().getDW("Dishwasher").getRemaining_Time());
String plugName=SmartBuildingListener.getClientConnection().getDevicePlugList().get("Dishwasher");
try{consPause=SmartBuildingListener.getClientConnection().getPlugConsListValues().get(plugName);}
catch(Exception e){}
if(pauseTime!=0)
{	oneTimeMajor=1;
	SmartBuildingListener.getClientConnection().getDW("Dishwasher").setState("pause");
SmartBuildingListener.getClientConnection().sendToEntity("userdwstatevaluepause"+pauseTime,SmartBuildingListener.getChatClientDeviceSession(),"Dishwasher");
}

				
			}//end max diff 0	
			}
			Component b=SmartBuildingListener.getClientConnection().getBigConsumer();
			if(b!=null)
			{	bigCons=SmartBuildingListener.getClientConnection().getPlugConsListValues().get(SmartBuildingListener.getClientConnection().getPlugBigConsumer());			
				bigConsumer=b.getName();
				oneTimeMajor=2;


				if(b.getName().contains("Lamp"))
			{if(((Lamp)b).getIntensity()!=(double)0.0)
			{
				((Lamp) b).setIntensity((this.reduceValue(((Lamp) b).getIntensity(),100,"Lamp")));
				SmartBuildingListener.getClientConnection().sendToEntity("userLampIntenValue"+((Lamp) b).getIntensity(),
						SmartBuildingListener.getChatClientDeviceSession(),((Lamp) b).getName());
				}
			}	
				
					else
			
			{ if(((AirConditioner)b).getTempValue()!=(double)0.0)
			{
				((AirConditioner) b).setTempValue(this.addValue(((AirConditioner) b).getTempValue(),100,"AirCond"));
				SmartBuildingListener.getClientConnection().sendToEntity("userTempAirCondValue"+((AirConditioner) b).getTempValue(), SmartBuildingListener.getChatClientDeviceSession()
						, b.getName());
			
			}
			}	
		}
		}

		/* minor adaptation :reduce sending and measurement period of sensors + [reduce functioning of lamps and air conditioners] 
	     * @author emna
	     */
		private void minor_Adaptation() {
			 
			 // si (c>p) on augmente la periode d'envoi et de mesure
			boolean t1=(testMinor && compteur<3);
			//System.out.println("test periodes : "+t1);
			boolean t2=(testMinor && compteur==3);
			//System.out.println("test confort : "+t2);
			//System.err.println("compteur: "+compteur);


			if(testMinor && compteur<3){
				System.out.println(jaune+"Minor adaptation selected"+reset);

				System.out.println("*"+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+"*");
				System.out.println("*"+"+         Adaptation cycle n°"+compteur+" <phase1 : increasing periods>        +"+"*");
				System.out.println("*"+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+"*");
				//System.err.println("");
				this.increasePeriod(); 
		        compteur=compteur+1;
		     // ce test permet de destinger entre (p<c normale) et (p<c apres subir un traitement de modification sur les periode d'envoi et de mesure ^^
		        augmentation=true;

		     //**********  
				}
			  else
				  if(testMinor && compteur==3){
						System.out.println("minor adaptation selected");

					  System.out.println("*"+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+"*");
					  System.out.println("*"+"+  Adaptation cycle <phase2 : changing entities' characteristics > +"+"*");
					  System.out.println("*"+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+"*");
						Double c=SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getPowerCons();
					  this.changeCharacteristicsValues();

					  confort=true;
					  augmentation=false;
				      compteur=compteur+1;
	  
				  }			
		}

		/** 
		 * manage the sensor' deconnection (if the sensor did not send any value 3 times => send a msg "are you alive" to the sensor => if he did not respond "ImAlive"=>deconnect it)
		 * @author emna
		 */
		private void manageStoppedSensors() {
			 ///////////////////// PSM deconnection///////////////////////////

			
			
			//if the PSM is connected
			if(SmartBuildingListener.getClientConnection().getPowerSmartMeter("PowerSmartMeter").getConnState()=="Alive")
		      { // System.out.println("PSM receiv : "+((ResponsePowerCons) SmartBuildingListener.getClientConnection().getMsgList().get("ResponsePowerCons")).isReceiv());
		      if (!(((ResponsePowerCons) SmartBuildingListener.getClientConnection().getMsgList().get("ResponsePowerCons")).isReceiv()))
				{ countDeadPSM++;
		    	  }
		      else
		    	  countDeadPSM=0;
		      
		      
		      if(countDeadPSM==2)
		      { 	((Alive) SmartBuildingListener.getClientConnection().getMsgList().get("ImAlivePSM")).setReceivPSMAlive(false);	
			SmartBuildingListener.getClientConnection().sendToEntity("areYouAlivePSM", 
					SmartBuildingListener.getChatClientDeviceSession(),"PowerSmartMeter");

		      }
		      if(countDeadPSM==3)
		      { if (!((Alive) SmartBuildingListener.getClientConnection().getMsgList().get("ImAlivePSM")).isReceivPSMAlive())
		      { SmartBuildingListener.getClientConnection().deconnect("PowerSmartMeter", "PowerSmartMeter");
	    	  //System.out.println("PSM is dead");
		      }
		      else
		      {		
		    	  countDeadPSM=0;
		      }
		      }
		      //System.out.println("countDeadPSM "+countDeadPSM);
				((ResponsePowerCons) SmartBuildingListener.getClientConnection().getMsgList().get("ResponsePowerCons")).setReceiv(false);
				

		      }
			
			
			  /////////////////////Thermometer deconnection///////////////////////////
			

			//if the Th is connected
			if(SmartBuildingListener.getClientConnection().getThermometer("Thermometer").getConnState()=="Alive")
		      {  //System.out.println("Th receiv : "+((ResponseTempThermometer) SmartBuildingListener.getClientConnection().getMsgList().get("ResponseTempThermometer")).isReceiv());
		      if (!((ResponseTempThermometer) SmartBuildingListener.getClientConnection().getMsgList().get("ResponseTempThermometer")).isReceiv())
				{ countDeadTh++;
		    	  }
		      else
		    	  countDeadTh=0;
		      
		      
		      if(countDeadTh==2)
		      { 		((Alive) SmartBuildingListener.getClientConnection().getMsgList().get("ImAliveTh")).setReceivThAlive(false);
			SmartBuildingListener.getClientConnection().sendToEntity("areYouAliveTh", 
					SmartBuildingListener.getChatClientDeviceSession(),"Thermometer");

		      }
		      if(countDeadTh==3)
		      { if (!((Alive) SmartBuildingListener.getClientConnection().getMsgList().get("ImAliveTh")).isReceivThAlive())
		      { SmartBuildingListener.getClientConnection().deconnect("Thermometer","Thermometer");
	    	  //System.out.println("Th is dead");
		      }
		      else
		      {
		    	  countDeadTh=0;
		      }
		      }
		      //System.out.println("countDeadTh "+countDeadTh);
				((ResponseTempThermometer) SmartBuildingListener.getClientConnection().getMsgList().get("ResponseTempThermometer")).setReceiv(false);
		      }
		     
		
		
		
		
		  /////////////////////PhotovoltaicPanel deconnection///////////////////////////
		

		//if the PhotovoltaicPanel is connected
		if(SmartBuildingListener.getClientConnection().getPhotovoltaicPanel("PhotovoltaicPanel").getConnState()=="Alive")
	      {  //System.out.println("PhotovoltaicPanel receiv : "+((ResponsepowerProdPhotovoltaicPanel) SmartBuildingListener.getClientConnection().getMsgList().get(
	    		 // "ResponsepowerProdPhotovoltaicPanel")).isReceiv());
	      if (!((ResponsepowerProdPhotovoltaicPanel) SmartBuildingListener.getClientConnection().getMsgList().get("ResponsepowerProdPhotovoltaicPanel")).isReceiv())
			{ countDeadPhPanel++;
	    	  }
	      else
	    	  countDeadPhPanel=0;
	      
	      
	      if(countDeadPhPanel==2)
	      { ((Alive) SmartBuildingListener.getClientConnection().getMsgList().get("ImAlivePanel")).setReceivPhPanelAlive(false);
		SmartBuildingListener.getClientConnection().sendToEntity("areYouAlivePhotovoltaicPanel", 
				SmartBuildingListener.getChatClientDeviceSession(),"PhotovoltaicPanel");

	      }
	      if(countDeadPhPanel==3)
	      { if (!((Alive) SmartBuildingListener.getClientConnection().getMsgList().get("ImAlivePanel")).isReceivPhPanelAlive())
	      { SmartBuildingListener.getClientConnection().deconnect("PhotovoltaicPanel","PhotovoltaicPanel");
    	  //System.out.println("PhotovoltaicPanel is dead");
	      }
	      else
	      {	      countDeadPhPanel=0;
	      }
	      }
	      //System.out.println("countDeadPhPanel "+countDeadPhPanel);
			((ResponsepowerProdPhotovoltaicPanel) SmartBuildingListener.getClientConnection().getMsgList().get("ResponsepowerProdPhotovoltaicPanel")).setReceiv(false);
	      }
	    
		
		
		
		 /////////////////////presenceSensor deconnection///////////////////////////
		Vector<PresenceSensor> pList = null;
		try{
		pList=SmartBuildingListener.getClientConnection().getComponentList().get("PresenceSensor");
		}
		catch (Exception e){}
for(int i=0;i<pList.size();i++)
{String name=pList.get(i).getName();
		//if the presenceSensor is connected
		if(pList.get(i).getConnState()=="Alive")
	      {  //System.out.println("PresenceSensor"+name+"  receiv : "+
		//Message.getSensorsEnvList().get(name));// false en suite
	      if (!(Message.getSensorsEnvList().get(name)))
			{ countDeadPS++;
	    	  }
	      else
	    	  countDeadPS=0;
	      
	      
	      if(countDeadPS==2)
	      {	((Alive) SmartBuildingListener.getClientConnection().getMsgList().get("ImAlivePS")).setReceivPS(name,false);
		SmartBuildingListener.getClientConnection().sendToEntity("areYouAlivePS", 
		SmartBuildingListener.getChatClientDeviceSession(),name);
			
	      }
	      if(countDeadPS==3)
	      { if (!((Alive) SmartBuildingListener.getClientConnection().getMsgList().get("ImAlivePS")).isReceivPS(name))
	      { SmartBuildingListener.getClientConnection().deconnect("PresenceSensor",name);
    	  //System.out.println("PresenceSensor"+name+" is dead");
	      }
	      else
	      {
	    	  countDeadPS=0;
	      }
	      }
	      //System.out.println("countDeadPS "+name+countDeadPS);
	      
	      Message.getSensorsEnvList().put(name,false);
	      }
	
}
	      

			
			 /////////////////////LuminositySensor deconnection///////////////////////////
			Vector<LuminositySensor> lList = null;
			try{
			lList=SmartBuildingListener.getClientConnection().getComponentList().get("LuminositySensor");
			}
			catch (Exception e){}
	for(int j=0;j<lList.size();j++)
	{String namel=lList.get(j).getName();
			//if the presenceSensor is connected
			if(lList.get(j).getConnState()=="Alive")
		      {  //System.out.println("LuminositySensor "+namel+"  receiv : "+
			//Message.getSensorsEnvList().get(namel));// false en suite
		      if (!(Message.getSensorsEnvList().get(namel)))
				{ countDeadLS++;
		    	  }
		      else
		    	  countDeadLS=0;
		      
		      
		      if(countDeadLS==2)
		      {		((Alive) SmartBuildingListener.getClientConnection().getMsgList().get("ImAliveLS")).setReceivLS(namel,false);
			SmartBuildingListener.getClientConnection().sendToEntity("areYouAliveLS", 
			SmartBuildingListener.getChatClientDeviceSession(),namel);
				
		      }
		      if(countDeadLS==3)
		      { if (!((Alive) SmartBuildingListener.getClientConnection().getMsgList().get("ImAliveLS")).isReceivLS(namel))
		      { SmartBuildingListener.getClientConnection().deconnect("LuminositySensor",namel);
	    	 // System.out.println("LuminositySensor"+namel+" is dead");
		      }
		      else
		      {
		    	  countDeadLS=0;
		      }
		      }
		      //System.out.println("countDeadLS "+namel+countDeadLS);
		      Message.getSensorsEnvList().put(namel,false);

		      }
		      
		     
}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		}
		
		
		
		
		

		/**
		 * verify that entities are running, if an entity is not running then deconnect it from the BM
		 * @author emna emna 
		 */

		private void manageStoppedEntities() {
			 /////////////////////Dishwasher deconnection///////////////////////////
		      
		      //if there is a message sent to the Dishwasher and there is no response from the Dishwasher
		      //the deconnect the Dishwasher
		 

			Vector<Component> dishwasherList=SmartBuildingListener.getClientConnection().findComponentsByName("Dishwasher");
			for(int i=0;i<dishwasherList.size();i++)
				{ Dishwasher currentDW=(Dishwasher)dishwasherList.get(i);
				  String client=dishwasherList.get(i).getName();
			
					if(currentDW.getConnState()=="Alive")
				    {
						if(Message.getComponentEnvList().get(client)!=null)
				    	{    	
					    	if(Message.getComponentEnvList().get(client)[0]&&
					    			! Message.getComponentEnvList().get(client)[1] )
					      {
					    	SmartBuildingListener.getClientConnection().deconnect("Dishwasher",client);
							//System.out.println(client+" is dead");
							String p=SmartBuildingListener.getClientConnection().getDevicePlugList().get(client);
							SmartPlug s=(SmartPlug) SmartBuildingListener.getClientConnection().findComponentByName("SmartPlug", p);
							if(s!=null)
							s.setState("off");
					      }
					      
					    	  //reset the values
							Message.getComponentEnvList().put(client,new Boolean[] {false,false});
		
				    	}
					   			
			    }
				}// DW deconn end
			
		     
		      /////////////////////Lamp deconnection///////////////////////////
		      
		      //if there is a message sent to the Lamp and there is no response from the Lamp
		      //the deconnect the Lamp
		 

			Vector<Component> lampList=SmartBuildingListener.getClientConnection().findComponentsByName("Lamp");
			for(int i=0;i<lampList.size();i++)
				{ Lamp currentLamp=(Lamp)lampList.get(i);
				  String client=lampList.get(i).getName();
			
					if(currentLamp.getConnState()=="Alive")
				    {
						if(Message.getComponentEnvList().get(client)!=null)
				    	{    	
					    	if(Message.getComponentEnvList().get(client)[0]&&
					    			! Message.getComponentEnvList().get(client)[1] )
					      {
					    	SmartBuildingListener.getClientConnection().deconnect("Lamp",client);
							//System.out.println(client+" is dead");
							
					      }
					      
					    	  //reset the values
							Message.getComponentEnvList().put(client,new Boolean[] {false,false});
		
				    	}
					   			
			    }
				}
		    
		      ///////////////////// AirConditioner deconnection///////////////////////////
			

		    //if there is a message sent to the Air conditioner and there is no response from it
		      //then deconnect the Air conditioner
			Vector<Component> acList=SmartBuildingListener.getClientConnection().findComponentsByName("AirConditioner");
			for(int i=0;i<acList.size();i++)
				{ AirConditioner currentAc=(AirConditioner)acList.get(i);
				  String client=acList.get(i).getName();
			
					if(currentAc.getConnState()=="Alive")
				    {
						if(Message.getComponentEnvList().get(client)!=null)
				    	{    	
					    	if(Message.getComponentEnvList().get(client)[0]&&
					    			! Message.getComponentEnvList().get(client)[1] )
					      {
					    	SmartBuildingListener.getClientConnection().deconnect("AirConditioner",client);
							//System.out.println(client+" is dead");
							
					      }
					      
					    	  //reset the values
							Message.getComponentEnvList().put(client,new Boolean[] {false,false});
		
				    	}
					   			
			    }
				}


	
		}
		
	}