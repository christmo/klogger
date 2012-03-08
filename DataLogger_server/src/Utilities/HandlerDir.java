/*
 * Permite gestionar la dirección actual
 * que representa una coordenada GPS.
 */
package Utilities;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Cumar C.
 */
public class HandlerDir extends DefaultHandler {

    String tempVal;
    String Direccion = "";

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {

        if (qName.equalsIgnoreCase("road")) {
            Direccion += "Calle " + tempVal;
            tempVal = "";
        } else if (qName.equalsIgnoreCase("county")) {
            Direccion += ", " + tempVal;
            tempVal = "";
        } else if (qName.equalsIgnoreCase("country")) {
            Direccion += ", " + tempVal;
            tempVal = "";
        } else if (qName.equalsIgnoreCase("park")) {
            Direccion += ", " + tempVal;
            tempVal = "";
        } else if (qName.equalsIgnoreCase("place_of_worship")) {
            Direccion += ", " + tempVal;
            tempVal = "";
        } else if (qName.equalsIgnoreCase("city")) {
            Direccion += ", " + tempVal;
            tempVal = "";
        } else if (qName.equalsIgnoreCase("village")) {
            Direccion += ", " + tempVal;
            tempVal = "";
        }

    }

    public String getDireccion() {
        return Direccion;
    }
}
