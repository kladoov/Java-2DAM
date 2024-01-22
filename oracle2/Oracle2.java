package oracle2;

/*@author alekk*/
public class Oracle2 {

    public static void main(String[] args) {
        AccesoOracle a = new AccesoOracle();
        a.abrirConexion();
        a.mostrarAdmitidosSTRUCT();
        //a.mostrarAdmitidos();
        //a.mostrarmisAlumnos();
        //System.out.println("-----STRUCT-----");
        //a.mostrarmisAlumnosSTRUCT();
        //a.insertarMisAlumnos("AK17", "Aleksandr", "603456783");
        //a.borrarMisAlumnos("04a");
        //a.buscarTelefono("Aleksandr");
        a.cerrarConexion();

    }

}
