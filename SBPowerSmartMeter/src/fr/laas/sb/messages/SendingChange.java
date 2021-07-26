package fr.laas.sb.messages;

import fr.laas.sb.entity.powerSmartMeter.PowerSmartMeterManager;

public class SendingChange extends Message {
	private int compteur=0;

	@Override
	public void handle(String value) {
		int sending  = Integer.parseInt(value);
///////////////chngement de l'energie consommée selon la variation de la periode
//	  if(sending>PowerSmartMeterManager.getPowerSmartMeter().getSendingPeriod()){
//		// System.out.println("aaaaa");
//		 double deux=5;
//				 //(sending-PowerSmartMeterManager.getPowerSmartMeter().getSendingPeriod())*2/3000;
//		compteur=(int) (compteur+deux);
//		// System.out.println(deux);
//		// System.out.println("compteur: "+compteur);
//
//		 double updatecons=PowerSmartMeterManager.getPowerSmartMeter().getPowerCons()-deux;
//		 //System.out.println(updatecons);
//
//		 PowerSmartMeterManager.getPowerSmartMeter().setPowerCons( updatecons);
//		// System.out.println("11111");
//
//	  }else
//		  if(sending<PowerSmartMeterManager.getPowerSmartMeter().getSendingPeriod()){
//			// System.out.println("bbbb");	  
//		PowerSmartMeterManager.getPowerSmartMeter().setPowerCons( PowerSmartMeterManager.getPowerSmartMeter().getPowerCons()+5);
//	// System.out.println("22222");
//
//		  }else 
//			  if(sending==PowerSmartMeterManager.getPowerSmartMeter().getSendingPeriod()&& compteur==10){
//				// System.out.println("ccccc");
//				  PowerSmartMeterManager.getPowerSmartMeter().setPowerCons( PowerSmartMeterManager.getPowerSmartMeter().getPowerCons()-2);
//				// System.out.println("33333");
//			  }
	  ///////////////
	
	 PowerSmartMeterManager.getPowerSmartMeter().setSendingPeriod(sending);

	  //System.out.println("-----> " + sending + " sending (int)" );
	
	}

}
