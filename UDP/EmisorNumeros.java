package emisornumeros;

import java.net.*;
import java.io.*;
import java.util.Random;

public class EmisorNumeros {

    public static void main(String args[]) {
        try {
            DatagramSocket sSocket = new DatagramSocket();
            InetAddress maquina = InetAddress.getByName("localhost");
            int Puerto = 1500;
            Random rnd = new Random();
            DatagramPacket mensaje;

            for (int i = 0; i < 20; i++) {
                int numEnviar = rnd.nextInt(1, 100);
                String info = String.valueOf(numEnviar);
                byte[] cadena = info.getBytes();
                mensaje = new DatagramPacket(cadena, cadena.length, maquina, Puerto);

                sSocket.send(mensaje);
                System.out.println("Numero enviado: " + info + ".");
            }
            
            String numFinal = "0";
            byte[] cadenaFinal = numFinal.getBytes();
            mensaje = new DatagramPacket(cadenaFinal, cadenaFinal.length, maquina, Puerto);
            sSocket.send(mensaje);
            System.out.println("Fin de la transmision, se ha enviado el numero " + numFinal );
            
            sSocket.close();

        } catch (UnknownHostException e) {
            System.err.println("Desconocido: " + e.getMessage());
        } catch (SocketException e) {
            System.err.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("E/S: " + e.getMessage());
        }
    }

}
