package clientetcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {

    static final String HOST = "localhost";
    static final int Puerto = 6000;

    public ClienteTCP() {
        try {
            Socket sCliente = new Socket(HOST, Puerto);
            DataInputStream flujoEntrada = new DataInputStream(sCliente.getInputStream());
            DataOutputStream flujoSalida = new DataOutputStream(sCliente.getOutputStream());

            //mensaje del servidor
            String msg = flujoEntrada.readUTF();
            System.out.println(msg);

            Scanner sc = new Scanner(System.in);
            int num;

            //envia numeros
            do {
                System.out.print("Ingresa un número o -1 para terminar: ");
                num = sc.nextInt();
                flujoSalida.writeInt(num);
            } while (num != -1);

            //lee el mensaje final del servidor
            String mensajeFinal = flujoEntrada.readUTF();
            System.out.println(mensajeFinal);

            sCliente.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] arg) {
        new ClienteTCP();
    }
}
