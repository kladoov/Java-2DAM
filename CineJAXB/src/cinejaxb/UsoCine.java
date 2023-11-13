package cinejaxb;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;
import java.io.*;
import java.util.List;
import javax.xml.bind.JAXBException;
import generated.Cine.Pelicula;
import generated.*;
import java.util.Scanner;

/*@author alekk*/
public class UsoCine {

    private JAXBContext contexto;
    private Unmarshaller u;
    private Cine cine;
    private List<Pelicula> lPelicula;

    public UsoCine(File f) {
        try {
            contexto = JAXBContext.newInstance(Cine.class);
            u = contexto.createUnmarshaller();
            cine = (Cine) u.unmarshal(f);
            lPelicula = cine.getPelicula();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Scanner tc = new Scanner(System.in);

    //Visualizar toda la inforamación del archivo xml, incluidos los atributos.
    public void mostrarPeliculas() {
        try {
            System.out.println("Numero de peliculas:" + lPelicula.size() + "\n");
            Pelicula peli;
            for (int i = 0; i < lPelicula.size(); i++) {
                peli = lPelicula.get(i);
                System.out.println("- Calificación " + peli.getCalificacion() + " con titulo " + peli.getTitulo() + ", sala " + peli.getSala() + " y su precio es " + peli.getPrecio());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Ver la películas con una calificación que se pide por pantalla.
    public void peliculasPideCalificacion() {
        System.out.print("Dime la calificación de la pelicula: ");
        String cali = tc.nextLine();
        boolean encontrado = false;
        Pelicula peli;
        try {

            for (int i = 0; i < lPelicula.size(); i++) {
                peli = lPelicula.get(i);
                if (peli.getCalificacion().equalsIgnoreCase(cali)) {
                    encontrado = true;
                    System.out.println("Calificación Encontrada.");
                    System.out.println("- Calificación " + peli.getCalificacion() + " con titulo " + peli.getTitulo() + ", sala " + peli.getSala() + " y su precio es " + peli.getPrecio());
                }
            }
            if (!encontrado) {
                System.out.println("No existe la calificación " + cali);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //Buscar la sala donde está una película que pasaremos como argumento
    public void peliculaPideSala(int sala) {
        boolean encontrado = false;
        Pelicula peli;
        try {

            for (int i = 0; i < lPelicula.size(); i++) {
                peli = lPelicula.get(i);
                if (peli.getSala() == sala) {
                    encontrado = true;
                    System.out.println("Sala Encontrada.");
                    System.out.println("- Calificación " + peli.getCalificacion() + " con titulo " + peli.getTitulo() + ", sala " + peli.getSala() + " y su precio es " + peli.getPrecio());
                }
            }
            if (!encontrado) {
                System.out.println("No existe la sala " + sala);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //Borrar la pelicula que está en una sala que preguntamos por pantalla
    public void borrarPelicula(int sala) {
        boolean encontrado = false;
        Pelicula peli;
        try {
            for (int i = 0; i < lPelicula.size(); i++) {
                peli = lPelicula.get(i);
                if (peli.getSala() == sala) {
                    lPelicula.remove(lPelicula.get(i));
                    encontrado = true;
                    System.out.println("Se ha borrado la pelicula a través de la sala " + sala);
                }
            }
            if (!encontrado) {
                System.out.println("No se ha encontrado la sala " + sala + " para borrarla.");
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //Insertar una película nueva.
    public void insertarPelicula(String titulo, int sala, float precio, String cali) {
        try {
            Pelicula peli = new Pelicula();
            peli.setTitulo(titulo);
            peli.setSala(sala);
            peli.setPrecio(precio);
            peli.setCalificacion(cali);
            lPelicula.add(peli);
            System.out.println("Se ha insertado " + titulo + " - " + sala + " - " + precio + " - " + cali);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Modificar una película. Este método nos pregunta por consola si sabemos el nombre o la sala 
    donde está la película, según la respuesta, nos mostrará toda la información de la película y 
    nos dirá que introduzcamos la nueva información que queremos que aparezca (si hemos dicho que 
    conocemos el nombre, sólo nos pedirá la calificación y la sala, si hemos dicho que conocemos 
    la sala, sólo nos pedirá la calificación y el nombre)*/
    public void modificarPelicula() {
        System.out.println("¿Conoces el nombre de la película o la sala?");
        String respuesta = tc.nextLine();
        Pelicula peli;
        boolean encontrado = false;
        String newCalificacion;

        for (int i = 0; i < lPelicula.size(); i++) {
            peli = lPelicula.get(i);
            if (peli.getTitulo().equalsIgnoreCase(respuesta)) {
                encontrado = true;
                System.out.println("Se ha buscado por título.");
                System.out.println("- Calificación " + peli.getCalificacion() + " con titulo " + peli.getTitulo() + ", sala " + peli.getSala() + " y su precio es " + peli.getPrecio());

                System.out.print("Dime la nueva calificación ");
                newCalificacion = tc.nextLine();
                System.out.print("Dime la nueva sala ");
                int newSala = tc.nextInt();
                peli.setCalificacion(newCalificacion);
                peli.setSala(newSala);
                System.out.println("");
                System.out.println("Cambio exitoso.");
                System.out.println("- Calificación " + peli.getCalificacion() + " con titulo " + peli.getTitulo() + ", sala " + peli.getSala() + " y su precio es " + peli.getPrecio());

            } else {
                try {
                    int sala = Integer.parseInt(respuesta);
                    if (peli.getSala() == sala) {
                        encontrado = true;
                        System.out.println("Se ha buscado por sala.");
                        System.out.println("- Calificación " + peli.getCalificacion() + " con titulo " + peli.getTitulo() + ", sala " + peli.getSala() + " y su precio es " + peli.getPrecio());

                        System.out.print("Dime la nueva calificación ");
                        newCalificacion = tc.nextLine();
                        System.out.print("Dime el nuevo nombre de la pelicula ");
                        String newTitulo = tc.nextLine();
                        peli.setCalificacion(newCalificacion);
                        peli.setTitulo(newTitulo);
                        System.out.println("- Calificación " + peli.getCalificacion() + " con titulo " + peli.getTitulo() + ", sala " + peli.getSala() + " y su precio es " + peli.getPrecio());

                    }
                } catch (NumberFormatException e) {
                    e.getMessage();
                }
            }
        }
        if (!encontrado) {
            System.out.println("No es un titulo ni una sala " + respuesta);
        }
    }
    
}