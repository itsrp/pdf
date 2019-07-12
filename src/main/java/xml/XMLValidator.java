package xml;

import com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class XMLValidator {

    public static boolean validateAgainstXSD(String xml, StreamSource xsd) {
        try {
            SchemaFactory factory = XMLSchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsd);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream inputStream = XMLValidator.class.getClassLoader().getResourceAsStream("order.xsd");
        XMLValidator.validateAgainstXSD("order1.xml", new StreamSource(inputStream));
    }
}
