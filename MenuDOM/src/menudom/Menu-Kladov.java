package menudom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*@author alekk*/
public class Menu {

    Document doc;

    public Menu(File f) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);
            System.out.println("DOM CREADO LLAMADO " + f);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public int recorrerMenuYMostrar() {
        String[] datos = new String[4];
        Node n1;
        Node root = doc.getFirstChild();
        NodeList nl1 = root.getChildNodes();
        int plato = 0;
        System.out.println("*****MENU DESAYUNO*****");
        for (int i = 0; i < nl1.getLength(); i++) {
            n1 = nl1.item(i);
            if (n1.getNodeType() == Node.ELEMENT_NODE) {
                Node n2;
                int contador = 0;
                plato++;
                NodeList nl2 = n1.getChildNodes();

                for (int j = 0; j < nl2.getLength(); j++) {
                    n2 = nl2.item(j);
                    if (n2.getNodeType() == Node.ELEMENT_NODE) {
                        datos[contador] = n2.getTextContent();
                        contador++;
                    }
                }
                System.out.println("Plato nº" + plato + ": " + datos[0]
                        + "\nDescripcion: " + datos[1]
                        + "\nPrecio: " + datos[2]
                        + "\nCalorias: " + datos[3]);
                System.out.println("-------------------------------------------------------");
            }
        }
        return 1;
    }

    //borra el nodo que tiene ese nombre. Si no existe muestra un mensaje diciendo “El plato nombrePlato no existe”
    public int borrarPlato(String nombrePlato) {
        try {
            NodeList nl1 = doc.getElementsByTagName("nombre");
            Node n1;
            boolean encontrado = false;
            for (int i = 0; i < nl1.getLength(); i++) {
                n1 = nl1.item(i);
                if (n1.getTextContent().equalsIgnoreCase(nombrePlato)) {
                    n1.getParentNode().getParentNode().removeChild(n1.getParentNode());
                    encontrado = true;
                }
            }
            if (encontrado) {
                System.out.println("Plato " + nombrePlato + " eliminado.");
            } else {
                System.out.println("El plato " + nombrePlato + " no existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    //inserta un nodo <plato> al árbol DOM
    public int insertarPlato(String nombre, String precio, String descripcion, int calorias) {
        try {
            //CREA ELEMENTO NOMBRE
            Node nNombre = doc.createElement("nombre");
            Node nNombre_text = doc.createTextNode(nombre);
            nNombre.appendChild(nNombre_text);

            //CREA ELEMENTO PRECIO
            Node nPrecio = doc.createElement("precio");
            Node nPrecio_text = doc.createTextNode(precio);
            nPrecio.appendChild(nPrecio_text);

            //CREA ELEMENTO DESCRIPCION
            Node nDescripcion = doc.createElement("descripcion");
            Node nDescripcion_text = doc.createTextNode(descripcion);
            nDescripcion.appendChild(nDescripcion_text);

            //CREA ELEMENTO CALORIAS
            Node nCalorias = doc.createElement("calorias");
            Node nCalorias_text = doc.createTextNode(calorias + "");
            nCalorias.appendChild(nCalorias_text);

            //CREA NODO PLATO QUE AGREGA SUS 4 CORRESPOMDIENTES ELEMENTOS
            Node nPlato = doc.createElement("plato");
            nPlato.appendChild(nNombre);
            nPlato.appendChild(nPrecio);
            nPlato.appendChild(nDescripcion);
            nPlato.appendChild(nCalorias);
            //COJO EL PRIMER ELEMENTO DEL ARBOL Y AGREGO SU MENU
            Node raiz = doc.getFirstChild();
            raiz.appendChild(nPlato);
            System.out.println("PLATO INSERTADO EN EL MENU");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }

    public void crearArchivoMenuDOM(String nuevoArchivo) {
        try {
            Source src = new DOMSource(doc);
            StreamResult rst = new StreamResult(new File(nuevoArchivo));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(src, (javax.xml.transform.Result) rst);
            System.out.println("ARCHIVO NUEVO CREADO CON EL NOMBRE " + nuevoArchivo + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Crea un método muestra el Nombre y la descripción de los platos que continen “buscado”. Termina diciendo cuántos platos tienen ese “buscado” y devuelve el nº.
    public int buscarPlatoContiene(String buscado) {
        Node nNombre;
        NodeList nlNombre = doc.getElementsByTagName("nombre");
        Node nDescripcion;
        NodeList nlDescripcion = doc.getElementsByTagName("descripcion");
        
        int contador = 0;
        for (int i = 0; i < nlNombre.getLength(); i++) {
            nNombre = nlNombre.item(i);
            String sNombre = nNombre.getTextContent();
            
            nDescripcion = nlDescripcion.item(i);
            //CON QUE COINCIDA LA LETRA BUSCADA EN EL ARBOL DOM LA MUESTRA  
            if (sNombre.toLowerCase().contains(buscado.toLowerCase())) {
                System.out.println("HAS BUSCADO " + buscado + ".");
                System.out.println("EL RESULTADO DE LA BUSQUEDA ES " + nNombre.getTextContent() + " - " + nDescripcion.getTextContent());
                contador++;
            }
        }
        System.out.println("ESE BUSCADO CONTIENE " + contador + " PLATOS.");
        return 1;
    }

    //incrementa todos los precios un % del entero pasado (es decir, si pone 10, lo incrementa un 10%). Los precios nuevos deben estar en el arbol DOM y sustituir a los viejos.
    public void incrementarPrecios(int incremento) {
        Node nPrecio;
        NodeList nlPrecio = doc.getElementsByTagName("precio");
        
        for (int i = 0; i < nlPrecio.getLength(); i++) {
            nPrecio = nlPrecio.item(i);
            //CONVIERTO EL NODO PRECIO A UNA CADENA DE TEXTO Y DESPUES PARSEARLA
            String sPrecio = nPrecio.getTextContent();
            //REEMPLAZO EL CHAR $ POR UNA CADENA VACIA
            sPrecio = sPrecio.replace("$", "");
            double pPrecio = Double.parseDouble(sPrecio);
            //CALCULO EL INCREMENTO A NIVEL DEL PORCENTAJE
            double aumento = pPrecio * (incremento / 100.0);
            double nuevoPrecio = pPrecio + aumento;
            
            System.out.println("PRECIO VIEJO " + nPrecio.getTextContent() + ", PRECIO NUEVO " + nuevoPrecio + " HA INCREMENTADO $" + aumento + ",");
            nPrecio.setTextContent(nuevoPrecio+"");
        }
    
    }
    
    
    //que muestra los nombres y calorias y los platos que tienen menos calorias que las pasadas como argumento.
    public void platosMenosCalorias(int limiteCalorias) {
        Node nCalorias;
        NodeList nlCalorias = doc.getElementsByTagName("calorias");
        
        Node nNombre;
        NodeList nlNombre = doc.getElementsByTagName("nombre");
        
        boolean caloriasInferiores = false;
        System.out.println("EL LIMITE DE CALORIAS ES " + limiteCalorias);
        for (int i = 0; i < nlCalorias.getLength(); i++) {
            nCalorias = nlCalorias.item(i);
            nNombre = nlNombre.item(i);
            
            String sCalorias = nCalorias.getTextContent();
            int iCalorias = Integer.parseInt(sCalorias);
            if (limiteCalorias > iCalorias) {
                caloriasInferiores = true;
                System.out.println("EL PLATO " + nNombre.getTextContent() + " TIENE " + nCalorias.getTextContent());
            }
        }
        if (caloriasInferiores) {
        } else {System.out.println("NO HAY NINGUN PLATO QUE TENGA MENOS CALORIAS.");}
        
    }
    
    
    
    
    
    
}
