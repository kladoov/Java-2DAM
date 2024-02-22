package servidortcp;

import java.io.*;
import java.net.*;

public class ServidorTCP extends Thread {

    Socket skCliente;
    static final int Puerto = 6000;
    private int total = 0;

    public ServidorTCP(Socket sCliente) {
        skCliente = sCliente;
    }

    public static void main(String[] arg) {
        try {
            // Client connect
            ServerSocket skServidor = new ServerSocket(Puerto);
            System.out.println("Escuchando en el puerto " + Puerto + ".");

            while (true) {
                Socket skCliente = skServidor.accept();
                System.out.println("Cliente conectado.");
                new ServidorTCP(skCliente).start();
            }
        } catch (IOException e) {
            System.out.println("Error al crear el socket del servidor: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            DataInputStream flujoEntrada = new DataInputStream(skCliente.getInputStream());
            DataOutputStream flujoSalida = new DataOutputStream(skCliente.getOutputStream());

            flujoSalida.writeUTF("Se ha conectado el cliente de forma correcta.");

            int num;
            do {
                num = flujoEntrada.readInt();
                if (num != -1) {
                    total = total + num;
                    System.out.println("Ha llegado el numero " + num + ".");
                }
            } while (num != -1);

            flujoSalida.writeUTF("Fin, ha llegado el -1. La suma total es: " + total);
            System.out.println("Fin, ha llegado el -1. La suma total es: " + total);
            
            skCliente.close();
            System.out.println("Cliente desconectado.");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
