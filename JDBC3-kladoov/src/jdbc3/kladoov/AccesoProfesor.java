package jdbc3.kladoov;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.sql.*;

/*@author alekk*/
public class AccesoProfesor {

    private Connection con;
    private Statement s;
    private ResultSet rs;

    public Scanner sc = new Scanner(System.in);

    //Conectarse a la bdd “kladoov”
    public AccesoProfesor() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/kladoov", "root", "");
            s = con.createStatement();
            System.out.println("-----Conexión abierta-----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Desconectarse de "kladoov"
    public void desconectar() {
        try {
            con.close();
            System.out.println("-----Conexión cerrada-----");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Mostrar toda la información en la table “profesor”
    public void mostrarTablaProfesor() {
        try {
            System.out.println("Mostrando consulta de la tabla profesor: ");
            rs = s.executeQuery("SELECT * FROM profesor");
            while (rs.next()) {
                //%d para int | %s para varchar | %n para saltos de linea
                System.out.printf("%d, %s, %s, %s, %d %n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Insertar un nuevo profesor (la información estará en los argumentos)
    public void insertarProfesor(int nif, String nombre, String asignatura, String puesto, int salario) {
        try {
            System.out.println("Insertando profesor con nif " + nif + ".");
            String sql = String.format("INSERT INTO profesor VALUES('%d','%s','%s','%s', %d)", nif, nombre, asignatura, puesto, salario);
            int n = s.executeUpdate(sql);
            System.out.println("Se ha insertado " + n + " registro");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Borrar un profesor (sabiendo el nif)
    public void borrarProfesor(int nif) {
        try {
            String sql = "DELETE FROM profesor WHERE profesor.nif='" + nif + "'";
            int n = s.executeUpdate(sql);
            if (n == 0) {
                System.out.println("No existe el nif - " + nif + " para borrarlo.");
            } else {
                System.out.println("Se ha borrado el nif - " + nif + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error en el metodo borrarProfesor");
        }
    }

    //Cambiar el puesto de un profesor sabiendo el nif y el nuevo puesto
    public void cambiarPuestoProfesor(int nif, String puesto) {
        try {
            String sql = String.format("UPDATE profesor SET puesto='%s' WHERE profesor.nif='%d'", puesto, nif);
            s.executeUpdate(sql);
            System.out.println("Se ha modificado el puesto " + puesto + " del nif " + nif + ".");
        } catch (SQLException e) {
            System.out.println("Error en el metodo cambiarPuestoProfesor.");
        }
    }

    //Mostrar toda la información de un profesor sabiendo el nombre o parte del nombre. 
    //Si hay varios profesores con ese nombre, debe verse la información de todos.
    public void buscadorProfesor(String nombre) {
        try {
            System.out.println("Estas buscando por el nombre '" + nombre + "'.");
            String sql = "SELECT * FROM profesor WHERE nombre LIKE '%" + nombre + "%'";
            rs = s.executeQuery(sql);
            while (rs.next()) {
                System.out.printf("%d, %s, %s, %s, %d %n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Calcula los impuestos que tenemos que pagar mensualmente por cada profesor, 
    //sabiendo que son un 21% del salario. Genera un archivo “impuestos.csv”, donde 
    //tendremos el nif, nombre y la cantidad de impuestos a pagar de cada uno.
    public void impuestosProfesor() {
        try {
            System.out.println("-Impuestos mensuales por cada profesor-");
            rs = s.executeQuery("SELECT nif, nombre, salario FROM profesor");

            FileWriter fw = new FileWriter("impuestos.csv");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);

            while (rs.next()) {
                int nif = rs.getInt(1);
                String nombre = rs.getString(2);
                int salario = rs.getInt(3);

                double impuestos = salario * 0.21;
                int resultado = (int) impuestos;
                System.out.println(nif + "," + nombre + "," + resultado);
                salida.println(nif + "," + nombre + "," + resultado);
            }

            salida.close();
            bw.close();
            fw.close();
            System.out.println("Archivo impuestos.csv creado.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    //Calcula la cantidad total de salarios que pagamos a todos los profesores (sin tener el cuenta los impuestos).
    public void totalSalariosProfesor() {
        try {
            System.out.print("El total que le pagamos a los profesores sin tener en cuenta los impuestos es: ");
            rs = s.executeQuery("SELECT SUM(salario) FROM profesor");
            while (rs.next()) {
                System.out.printf("%d %n", rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
