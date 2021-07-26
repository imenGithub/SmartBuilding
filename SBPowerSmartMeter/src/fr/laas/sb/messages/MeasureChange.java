package fr.laas.sb.messages;

import fr.laas.sb.entity.powerSmartMeter.PowerSmartMeterManager;


public class MeasureChange extends Message {

	@Override
	public void handle(String value) {
		int measure  = Integer.parseInt(value);
////////////////chngement de l'energie consommée selon la variation de la periode
	/*  if(measure>PowerSmartMeterManager.getPowerSmartMeter().getMeasurementPeriod()){
		// System.out.println("aaaaa");
		 double deux=(measure-PowerSmartMeterManager.getPowerSmartMeter().getMeasurementPeriod())*2/3000;
		compteur=(int) (compteur+deux);
		// System.out.println(deux);
		// System.out.println("compteur: "+compteur);

		 double updatecons=PowerSmartMeterManager.getPowerSmartMeter().getPowerCons()-deux;
		 //System.out.println(updatecons);

		 PowerSmartMeterManager.getPowerSmartMeter().setPowerCons( updatecons);
		// System.out.println("11111");

	  }else
		  if(measure<PowerSmartMeterManager.getPowerSmartMeter().getMeasurementPeriod()){
			// System.out.println("bbbb");	  
		PowerSmartMeterManager.getPowerSmartMeter().setPowerCons( PowerSmartMeterManager.getPowerSmartMeter().getPowerCons()+6);
	// System.out.println("22222");

		  }else 
			  if(measure==PowerSmartMeterManager.getPowerSmartMeter().getMeasurementPeriod()&& compteur==10){
				// System.out.println("ccccc");
				  PowerSmartMeterManager.getPowerSmartMeter().setPowerCons( PowerSmartMeterManager.getPowerSmartMeter().getPowerCons()-2);
				// System.out.println("33333");
			  }*/
	  ///////////////
	 // System.out.println("suite");
	  PowerSmartMeterManager.getPowerSmartMeter().setMeasurementPeriod(measure);

	 // System.out.println("-----> " + measure + " measure (int)" );
	}

}
