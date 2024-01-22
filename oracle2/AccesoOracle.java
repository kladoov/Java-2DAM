package oracle2;

import java.sql.*;
import oracle.sql.STRUCT;

public class AccesoOracle {

    private Connection con;

    void abrirConexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "kladov", "root");
            System.out.println("CONEXION ESTABLECIDA CON EL SERVIDOR.");
            System.out.println("----------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cerrarConexion() {
        try {
            System.out.println("----------------------------------------------");
            System.out.println("CONEXION CERRADA CON EL SERVIDOR.");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //2 Insertar un estudiante en la tabla MISALUMNOS
    void insertarMisAlumnos(String id, String nombre, String telefono) {
        try {
            String sql = "INSERT INTO misAlumnos VALUES('" + id + "', PERSONA('" + nombre + "','" + telefono + "'))";
            Statement st = con.createStatement();
            int n = st.executeUpdate(sql);

            if (n == 0) {
                System.out.println("NO SE HA INSERTADO EL ALUMNO.");
            } else {
                System.out.println("ALUMNO INSERTADO CON ID " + id + ".");
            }

            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3 Borrar un alumno sabiendo su nombre. Suponemos que no hay dos alumnos con el mismo nombre
    void borrarMisAlumnos(String nombre) {
        try {
            String sql = "DELETE FROM misAlumnos m WHERE m.datos_personales.nombre='" + nombre + "'";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
            System.out.println("ALUMNO BORRADO CON NOMBRE " + nombre + ".");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //4 Buscar el teléfono de un alumno sabiendo el nombre
    void buscarTelefono(String nombre) {
        try {
            Statement st = con.createStatement();
            ResultSet resul = st.executeQuery("SELECT m.datos_personales.telefono FROM misAlumnos m WHERE m.datos_personales.nombre='" + nombre + "'");
            while (resul.next()) {
                System.out.println("EL TELEFONO DE " + nombre + " ES: " + resul.getString(1));
            }
            resul.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //5 Mostrar un listado con toda la información de todos los alumnos
    void mostrarmisAlumnos() {
        try {
            Statement st = con.createStatement();
            ResultSet resul = st.executeQuery("SELECT id_estudiante, m.datos_personales.nombre, m.datos_personales.telefono FROM misAlumnos m");
            System.out.println("DATOS DE LA TABLA misAlumnos:");
            while (resul.next()) {
                System.out.println("ID: " + resul.getString(1));
                System.out.println("NOMBRE: " + resul.getString(2));
                System.out.println("TELEFONO: " + resul.getString(3));
                System.out.println("------------------------------");
            }
            resul.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //5 Mostrar un listado con toda la información de todos los alumnos CON STRUCT
    void mostrarmisAlumnosSTRUCT() {
        try {
            Statement st = con.createStatement();
            ResultSet resul = st.executeQuery("SELECT * FROM misAlumnos");
            System.out.println("DATOS DE LA TABLA misAlumnos:");
            while (resul.next()) {
                String id = resul.getString(1);
                STRUCT dimensiones = (STRUCT) resul.getObject(2);
                Object[] atributos = dimensiones.getAttributes();
                String nombre = (String) atributos[0];
                String telefono = (String) atributos[1];
                System.out.println(id + " - " + nombre + " - " + telefono);
            }
            resul.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //6 Mostrar un listado con toda la información de todos la tabla admitidos
    void mostrarAdmitidos() {
        try {
            Statement st = con.createStatement();
            ResultSet resul = st.executeQuery("SELECT dia, a.matriculado.id_estudiante, a.matriculado.datos_personales.nombre, a.matriculado.datos_personales.telefono FROM admitidos a");
            System.out.println("DATOS DE LA TABLA admitidos:");
            while (resul.next()) {
                System.out.println("DIA: " + resul.getDate(1));
                System.out.println("ID: " + resul.getString(2));
                System.out.println("NOMBRE: " + resul.getString(3));
                System.out.println("TELEFONO: " + resul.getString(4));

                System.out.println("------------------------------");
            }
            resul.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //6 Mostrar un listado con toda la información de todos los admitidos CON STRUCT
    void mostrarAdmitidosSTRUCT() {
        try {
            Statement st = con.createStatement();
            ResultSet resul = st.executeQuery("SELECT * FROM admitidos");
            System.out.println("DATOS DE LA TABLA misAlumnos:");
            while (resul.next()) {
                Date dia = resul.getDate(1);

                //matriculados
                STRUCT matriculados = (STRUCT) resul.getObject(2);
                Object[] m = matriculados.getAttributes();
                String id = (String) m[0];

                //persona
                STRUCT persona = (STRUCT) m[1];
                Object[] p = persona.getAttributes();
                String nombre = (String) p[0];
                String telefono = (String) p[1];

                System.out.println(dia + " - " + id + " - " + nombre + " - " + telefono);
            }
            resul.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
