package cinesax;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

/*@author alekk*/
public class CineSAXHandler extends DefaultHandler {

    public CineSAXHandler() {
    }

    public void startDocument() {
        System.out.println("-----LISTADO DEL CINE-----");
    }

    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (qName.equals("pelicula")) {
            System.out.print("La calificacion es " + atts.getValue(atts.getQName(0)));
        } else if (qName.equals("titulo")) {
            System.out.print("\nLa pelicula es ");
        } else if (qName.equals("sala")) {
            System.out.print("\nLa sala es ");
        } else if (qName.equals("precio")) {
            System.out.print("\nEl precio es ");
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        String car = new String(ch, start, length);
        car = car.replaceAll("\t", "");
        car = car.replaceAll("\n", "");
        System.out.print(car);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("pelicula")) {
            System.out.println("\n---------------------------------");
        }
    }

}
