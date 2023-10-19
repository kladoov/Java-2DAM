package cinesax;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/*@author alekk*/
public class AccesoCineSAX {

    SAXParser parser;

    public int parsearXMLconLibrosSAXhandler(File f) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
            CineSAXHandler cs = new CineSAXHandler();
            parser.parse(f, cs);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int parsearXMLconLibrosSAXhandlerDos(File f) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
            CineSAXHandlerDos cs = new CineSAXHandlerDos();
            parser.parse(f, cs);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
