package receptornumeros;

import java.net.*;
import java.io.*;

public class ReceptorNumeros {

    public static void main(String args[]) {

        try {
            DatagramSocket sSocket = new DatagramSocket(1500);
            byte[] cadena = new byte[1000];
            DatagramPacket mensaje = new DatagramPacket(cadena, cadena.length);

            int[] par = new int[20];
            int[] impar = new int[20];
            int iPar = 0;
            int iImpar = 0;

            String datos;
            System.out.println("Esperando numeros...");
            do {
                sSocket.receive(mensaje);
                datos = new String(mensaje.getData(), 0, mensaje.getLength());
                int numRecibido = Integer.parseInt(datos);

                //si recibe el numero 0, el bucle while da por finalizado
                if (numRecibido == 0) {
                    break;
                }

                //si es par, se añade al array e incrementa su posicion en 1 para seguir metiendo numeros chulos
                if (numRecibido % 2 == 0) {
                    par[iPar++] = numRecibido;
                } else {
                    impar[iImpar++] = numRecibido;
                }

                System.out.println("Numero recibido: " + numRecibido);
            } while (true);

            //muestra los numeros pares
            System.out.println("Numeros pares recibidos: ");
            for (int i = 0; i < iPar; i++) {
                System.out.print(par[i] + " ");
            }
            
            //muestra los numeros impares
            System.out.println("\nNumeros impares recibidos: ");
            for (int i = 0; i < iImpar; i++) {
                System.out.print(impar[i] + " ");
            }
            
            //good bye
            System.out.println("\nCerrando receptor, número 0 recibido.");
            sSocket.close();

        } catch (SocketException e) {
            System.err.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("E/S: " + e.getMessage());
        }
    }
}
