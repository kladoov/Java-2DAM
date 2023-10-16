package cinedom;

import java.io.File;

/*@author alekk*/
public class CineDOM {

    public static void main(String[] args) {

        File cine = new File("Cine.xml");
        SalaCine sala = new SalaCine(cine);
        
        sala.insertaPelicula("5", "AyendiLegendario", "8", 1000);
        
        System.out.println("");
        sala.muestraDOM();
        
        System.out.println("");
        sala.averagePrice();
        
        System.out.println("");
        sala.cambiarSala("Animales fantasticos", 8);
        
        System.out.println("");
        sala.borrarPelicula("AyendiLegendario");
        
        System.out.println("");
        sala.muestraDOM();
        
        System.out.println("");
        sala.crearNuevoArchivo("NuevoCine");
    }
}
