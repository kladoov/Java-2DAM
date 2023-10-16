package lanzapidenombre;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/*@author alexk*/
public class LanzaPideNombre {

    public static void main(String[] args) {

        /*Crea otro programa que lance al anterior, le pase un nombre, visualice la 
        consola de salida en caso de éxito, la consola en caso de error y que visualice 
        el valor de salida de PideNombre.*/
        
        Process p;
        File directorio = new File(".\\PideNombre\\src\\pidenombre");
        ProcessBuilder pb = new ProcessBuilder("java", "PideNombre.java");
        try {
            pb.directory(directorio);
            System.out.println("El directorio de trabajo es: " + directorio);
            p = pb.start();
            
            //Write in the other project
            OutputStream os = p.getOutputStream();
            os.write("Aleksand\n".getBytes());
            os.flush();

            //return true
            InputStream is = p.getInputStream();
            int c, e;
            while ((c = is.read()) != -1) {
                System.out.print((char) c);
            }
            is.close();

            //return error
            InputStream er = p.getErrorStream();
            while ((e = er.read()) != -1) {
                System.out.print((char) e);
            }
            is.close();
            
            //return the exit at other project
            int salida = p.waitFor();
            if (salida == 0) {
                System.out.println("LA SALIDA ES " + salida);
            } else {
                System.out.println("LA SALIDA ES " + salida);
            }

        } catch (Exception e) {
            e.getMessage();

        }
    }
}
