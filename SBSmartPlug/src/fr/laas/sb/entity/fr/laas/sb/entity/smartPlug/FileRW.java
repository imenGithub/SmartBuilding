package fr.laas.sb.entity.fr.laas.sb.entity.smartPlug;
import java.io.File;
import java.io.FileOutputStream;
 
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
 
 
public class FileRW {
    static org.jdom.Document document;
    static Element racine;
    static String chemin="../../SimFile.xml" ;
    /**
     * Fonction qui lit le fichier xml
     * @param fichier nom du fichier xml en entrée
     * @return le nom de la balise racine du fichier xml en entrée
     */
public static Element read(String file)
{
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
 * fonction qui enregistre un fichier xml
 * @param fichier nom du fichier xml à enregistré
 */
static void save(String file)
{try{
     
    XMLOutputter sortie=new XMLOutputter(Format.getPrettyFormat());
    sortie.output(document, new FileOutputStream(file));
    
    
}
catch(java.io.IOException e){};
} //fin fc enregistrer



public synchronized static void addCons(Double ad){
	 Element route=FileRW.read(chemin);
	 Double nb=Double.parseDouble(route.getChildText("cons"))+ad;
	 route.getChild("cons").setText(nb.toString());
	 FileRW.save(chemin);
}

public synchronized static void addProd(Double ad){
	 Element route=FileRW.read(chemin);
	 route.getChild("prod").setText(ad.toString());
	 FileRW.save(chemin);

}




public synchronized static Double getCons(){
	 Element route=FileRW.read(chemin);
	 return Double.parseDouble(route.getChildText("cons"));
}

public synchronized static Double getProd(){
	 Element route=FileRW.read(chemin);
	 return Double.parseDouble(route.getChildText("prod"));
}

public synchronized static Double getContentBalise(String baliseName){
	 Element route=FileRW.read(chemin);
	 return Double.parseDouble(route.getChild(baliseName).getText());
}

}
