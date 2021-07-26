package fr.laas.sb.serverBM;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
 
/**
 * class for the simulation' XML file manipulation
 * @author imen
 */
public class FileRW {
     org.jdom.Document document;
     Element racine;
     String chemin="" ;
	 DecimalFormat df = new DecimalFormat ( ) ;
	 Element route;
	 Lock l = new ReentrantLock();
	/**
     * read the XMl file
     * @param cheminp path of the XML file
     */
	 public FileRW(String cheminp){
		 this.chemin=cheminp;
		 
	 }
	 
	 /**
	     * read the XMl file
	     * @param file path of the XML file
	     */ 
private synchronized  Element read(String file)
{
	df.setMaximumFractionDigits ( 3 ) ; //arrondi 3 chiffres apres la virgules
	df.setMinimumFractionDigits ( 3 ) ;
	df.setDecimalSeparatorAlwaysShown ( true ) ;
    // On crée une instance de SAXBuilder
        SAXBuilder sxb = new SAXBuilder();
        try {
        // On crée un nouveau document JDOM avec en argument le fichier XML
            document = sxb.build(new File(file));
        } catch (Exception e) {
                return null;
        }
        // On initialise un nouvel élément racine avec l'élément racine du
        // document.
        racine = document.getRootElement();
        return racine;
         
}//fin fc lire
 

/**
 * save the XMl file
 * @param file name of the new XML file
 */ 
private synchronized   void save(String file)
{try{
     
    XMLOutputter sortie=new XMLOutputter(Format.getPrettyFormat());
    sortie.output(document, new FileOutputStream(file));
    
    
}
catch(java.io.IOException e){};
} //fin fc enregistrer


/**
 * add a value to the tag "cons"
 * @param ad value to add
 */ 
public synchronized  void addCons(Double ad){
	
	l.lock();
	Double nb;
	try { 	 route=this.read(chemin);
try{
		  nb=Double.parseDouble(route.getChildText("cons"))+ad;
			
			 String st=df.format(nb);
				nb=Double.parseDouble(st.replace(',', '.'));
			 
			 
			 route.getChild("cons").setText(nb.toString());
			 this.save(chemin);
}
catch(Exception e){System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");};
		
		 } finally {
	    l.unlock();
	}
	

	
}

/**
 * add a value to the tag "prod"
 * @param ad value to add
 */
public synchronized  void addProd(Double ad){
	 route=this.read(chemin);
	 Double nb=Double.parseDouble(route.getChildText("prod"))+ad;

	 String st=df.format(nb);
		nb=Double.parseDouble(st.replace(',', '.'));	 
	 route.getChild("prod").setText(nb.toString());
	 this.save(chemin);

}




public synchronized  Double getCons(){
	 route=this.read(chemin);
	 return Double.parseDouble(route.getChildText("cons"));
}

public synchronized  Double getProd(){
	 route=this.read(chemin);
	 return Double.parseDouble(route.getChildText("prod"));
}

public synchronized  void setCons(Double ad) {
	route=this.read(chemin);
	 String st=df.format(ad);
		ad=Double.parseDouble(st.replace(',', '.'));
	 route.getChild("cons").setText(ad.toString());
	 this.save(chemin);
	
}

/**
 * create a new tag in the XML file
 * @param name name of the new tag
 * @param cons value of the new tag

 */
public synchronized  void create(String name, Double cons) {
	route=this.read(chemin);
	if(route.getChild(name)==null)
	{      Element e = new Element(name);
		route.addContent(e);
	}
	if(name !=null)
	route.getChild(name).setText(cons.toString());

	 this.save(chemin);
}


/**
 * initiate the XML file (0 in all tags)
 */
public synchronized void initiate() {
	route=this.read(chemin);
List<Element> ListeProcessus=route.getChildren();
Iterator<Element> itP= ListeProcessus.iterator();

//boucle pour la liste des processus
while(itP.hasNext())
    { Element baliseCour = itP.next();
if(baliseCour.getName()!="prod")
	baliseCour.setText("0.0");
this.save(chemin);


    }





}
}
