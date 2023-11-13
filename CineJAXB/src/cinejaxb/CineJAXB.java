package cinejaxb;

import java.io.File;

/*@author alekk*/
public class CineJAXB {

    public static void main(String[] args) {
        File f = new File("Cine.xml");
        UsoCine cine = new UsoCine(f);

        cine.mostrarPeliculas();
        System.out.println("--------------------------------------------------");

        //cine.peliculasPideCalificacion();
        System.out.println("--------------------------------------------------");

        cine.peliculaPideSala(4);
        System.out.println("--------------------------------------------------");
        
        cine.borrarPelicula(2);
        System.out.println("--------------------------------------------------");
        
        cine.insertarPelicula("Dos tanques", 5, 5, "tipo C");
        System.out.println("--------------------------------------------------");
        
        cine.mostrarPeliculas();
        System.out.println("--------------------------------------------------");
        
        cine.modificarPelicula();
        

    }

}
