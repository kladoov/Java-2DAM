
package cinesax;
import java.io.File;


/*@author alekk*/
public class CineSAX {

    public static void main(String[] args) {
  /*Usando SAX, haz haz un manejador SAX con el código necesario para mostrar 
    toda la información que hay en el archivo cine.xml
    Haz otro manejador SAX para que sólo se vean los títulos y las salas de las 
    peliculas que están catalogadas todos los públicos (TP)*/
  
    AccesoCineSAX sax = new AccesoCineSAX();
    File f = new File("Cine.xml");
    sax.parsearXMLconLibrosSAXhandler(f);
    
    System.out.println("");
    sax.parsearXMLconLibrosSAXhandlerDos(f);

        
        
        
        


    }
}
