package jdbc3.kladoov;

/*@author alekk*/
public class JDBC3Kladoov {

    public static void main(String[] args) {
        AccesoProfesor prof = new AccesoProfesor();

        prof.mostrarTablaProfesor();
        System.out.println("--------------------------------------------------");

        //comento porque tengo una Primary Key en nif que me saltara una exception al insertar el mismo nif
        //prof.insertarProfesor(4444, "ayendi", "programación", "profesor", 1200);
        prof.borrarProfesor(4444);
        System.out.println("--------------------------------------------------");

        System.out.println("--------------------------------------------------");
        prof.cambiarPuestoProfesor(1111, "cocinero");

        System.out.println("--------------------------------------------------");
        prof.buscadorProfesor("an");
        
        System.out.println("--------------------------------------------------");
        prof.impuestosProfesor();
        
        System.out.println("--------------------------------------------------");
        prof.totalSalariosProfesor();
        
        System.out.println("--------------------------------------------------");
        prof.desconectar();

    }

}
