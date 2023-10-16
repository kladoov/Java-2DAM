package pidenombre;

import java.util.Scanner;

/*author alexk*/
public class PideNombre {

    public static void main(String[] args) {

      /*Crea un programa PideNombre que pide un nombre de usuario por pantalla que 
      tenga 8 caracteres como máximo. Si es nombre es correcto, muestra un mensaje 
      de bienvenida y devuelve 0 (con System.exit). Si el nombre tiene más de 8 
      caracteres, muestra un mensaje de nombre no válido y devuelve -1.*/
      
        Scanner tc = new Scanner(System.in);
        System.out.println("*recuerda que si escribes > 8 caracteres salta el System.Exit();*");
        System.out.print("DIME TU NOMBRE: ");
        String name = tc.nextLine();
        System.out.println("TU NOMBRE ES " + name);

        if (name.length() > 8) {
            System.out.println("NOMBRE NO VALIDO, TIENE " + name.length() + " CARACTERES.");
            System.exit(-1);
        } else {
            System.out.println("-----BIENVENIDO-----");
            System.exit(0);
        }

    }
}
