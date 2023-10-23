/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.ws.interfaces;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author matejsp
 */
public class JAXBUtility {

    public static String XmlToString(Object obj) throws JAXBException {
        JAXBContext jaxbctx = JAXBContext.newInstance(obj.getClass());
        Marshaller marshallerout = jaxbctx.createMarshaller();
        marshallerout.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshallerout.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshallerout.marshal(obj, sw);
        return sw.toString();
    }

    public static Object StringToXml(String input, Class X) throws JAXBException {
        JAXBContext jaxbctx = JAXBContext.newInstance(X);
        Unmarshaller unmarshaller = jaxbctx.createUnmarshaller();
        return unmarshaller.unmarshal(new StringReader(input));
    }
}
