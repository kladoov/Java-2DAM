package cinedom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;//clase File
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*@author alekk*/
public class SalaCine {

    private Document doc;

    public SalaCine(File f) {
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

    public void muestraDOM() {
        String[] datos = new String[4];
        Node n1;
        Node root = doc.getFirstChild(); //devuelve el primer nodo 'cine'
        NodeList nl1 = root.getChildNodes(); //saco los nodos hijos del root 'pelicula' y los almacena en un nodelist
        for (int i = 0; i < nl1.getLength(); i++) { 
            n1 = nl1.item(i);//node toma el valor de los hijos de raíz
            if (n1.getNodeType() == Node.ELEMENT_NODE) { //miramos nodos de tipo Element
                Node n2;
                int contador = 1;
                datos[0] = n1.getAttributes().item(0).getNodeValue(); //devuelve el atributo del nodo 'pelicula'
                NodeList nl2 = n1.getChildNodes(); //se crea otro nodeList para almacenar los nodos hijos

                for (int j = 0; j < nl2.getLength(); j++) { 
                    n2 = nl2.item(j);
                    if (n2.getNodeType() == Node.ELEMENT_NODE) {
                        datos[contador] = n2.getTextContent();
                        contador++;
                    }
                }
                System.out.println(datos[0] + " - " + datos[1] + " - " + datos[2] + " - " + datos[3]);
            }
        }
    }

    
    
    
    
    //inserta una nuevo película en el árbol. Devuelve 0 si funciona, -1 si hay algún error    
    public int insertaPelicula(String calificacion, String titulo, String sala, double precio) {
        try {
            //CREA TITULO
            Node ntitulo = doc.createElement("titulo");//crea etiquetas <Titulo>...</Titulo>
            Node ntitulo_text = doc.createTextNode(titulo);//crea el nodo texto para el Titulo
            ntitulo.appendChild(ntitulo_text);//añade el titulo a las etiquetas=><Titulo>titulo</Titulo>

            //CREA SALA
            Node nsala = doc.createElement("sala");
            Node nsala_text = doc.createTextNode(sala);
            nsala.appendChild(nsala_text);

            //CREA PRECIO
            Node nprecio = doc.createElement("precio");
            Node nprecio_text = doc.createTextNode(precio + "");
            nprecio.appendChild(nprecio_text);

            //CREA LIBRO, con atributo y nodos titulo, sala, precio 
            Node nPelicula = doc.createElement("pelicula");
            ((Element) nPelicula).setAttribute("calificacion", calificacion);
            nPelicula.appendChild(ntitulo);
            nPelicula.appendChild(nsala);
            nPelicula.appendChild(nprecio);
            //APPEND LIBRO TO THE ROOT

            Node raiz = doc.getFirstChild();
            raiz.appendChild(nPelicula);
            System.out.println("Pelicula insertado en DOM.");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }

    }

    
    
    
    //calcula el precio medio de todas las películas, lo muestra por consola y lo devuelve.
    public double averagePrice() {
        String[] datos = new String[4];
        Node nodo ;
        Node root = doc.getFirstChild();
        NodeList nodelist = root.getChildNodes();
        double precioMedio = 0, suma = 0;
        for (int i = 0; i < nodelist.getLength(); i++) {
            nodo = nodelist.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Node ntemp ;
                int contador = 1;
                datos[0] = nodo.getAttributes().item(0).getNodeValue();
                NodeList nl2 = nodo.getChildNodes();

                for (int j = 0; j < nl2.getLength(); j++) {
                    ntemp = nl2.item(j);
                    if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                        datos[contador] = ntemp.getTextContent();
                        contador++;
                    }
                }
                precioMedio = Double.parseDouble(datos[3]);
                suma = suma + precioMedio;
            }
        }
        precioMedio = suma / datos[3].length();
        System.out.println("El precio medio de todas las peliclas es " + precioMedio);
        return precioMedio;
    }
    
    
    
    

    //cambia la sala de la película con ese título. Primero comprueba si la sala no está ya asignada, y si lo está nos avisa de que ya está asignada, y no hace el cambio.
    public void cambiarSala(String titulo, int nuevaSala) {
        boolean asignada = false;
        Node sala;
        NodeList lsala = doc.getElementsByTagName("sala");
        for (int i = 0; i < lsala.getLength();i++) {
            sala = lsala.item(i);
            if(Integer.parseInt(sala.getTextContent()) == nuevaSala) {
                asignada = true;
            }
        }
        if (asignada == true) {
            System.out.println("La sala " + nuevaSala + " ya esta asiganada.");
        } else {
            NodeList ltitulo = doc.getElementsByTagName("titulo");
            Node ntitulo;
            boolean peliExiste = false;
            for (int j = 0; j < ltitulo.getLength(); j++) {
                ntitulo = ltitulo.item(j);
                if(ntitulo.getTextContent().equalsIgnoreCase(titulo)) {
                    peliExiste = true;
                    //primero cuenta el intro y despues se mete dentro de sala
                    ntitulo.getNextSibling().getNextSibling().setTextContent(nuevaSala+""); 
                } 
            }
            if (peliExiste) {
                System.out.println("Se ha cambiado la sala.");
            } else {
                System.out.println("No se ha cambiado la sala.");
            }
        }
    }
    
    
    

    //elimina una película sabiendo el nombre (elimina todo el nodo) 
    public void borrarPelicula(String titulo) {
        System.out.println("Buscando la pelicula " + titulo + " para borrarlo");
        try {
            NodeList nl1 = doc.getElementsByTagName("titulo");
            Node n1;
            for (int i = 0; i < nl1.getLength(); i++) {
                n1 = nl1.item(i);
                if (n1.getTextContent().equalsIgnoreCase(titulo)) {
                    n1.getParentNode().getParentNode().removeChild(n1.getParentNode());
                    System.out.println("NODO <pelicula> " + titulo + " eliminada.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    //crea un nuevo archivo con el nombre que le pasemos que contiene la nueva información del DOM
    public void crearNuevoArchivo(String nuevoArchivo) {
        try {
            Source src = new DOMSource(doc); // Definimos el origen
            StreamResult rst = new StreamResult(new File(nuevoArchivo)); // Definimos el resultado
            // Declaramos el Transformer que tiene el método .transform() que necesitamos.
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            // Opción para indentar el archivo
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(src, (javax.xml.transform.Result) rst);
            System.out.println("Archivo creado del DOM con éxito llamado " + nuevoArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
