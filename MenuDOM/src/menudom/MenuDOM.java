package menudom;

import java.io.File;

/*@author alekk*/
public class MenuDOM {

    public static void main(String[] args) {
        File f = new File("Menu.xml");
        Menu menu = new Menu(f);

        System.out.println("");
        menu.insertarPlato("Macarrones a la carbonara", "$10", "Macarrones fitnes de la abuela", 45);

        System.out.println("");
        menu.recorrerMenuYMostrar();

        System.out.println("");
        menu.borrarPlato("Macarrones a la carbonara");

        System.out.println("");
        menu.buscarPlatoContiene("g");

        System.out.println("");
        menu.incrementarPrecios(50);

        System.out.println("");
        menu.recorrerMenuYMostrar();
        
        System.out.println("");
        menu.platosMenosCalorias(700);

        System.out.println("");
        menu.crearArchivoMenuDOM("NuevoMenu");

    }

}
