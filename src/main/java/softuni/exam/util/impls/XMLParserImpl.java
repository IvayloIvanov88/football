package softuni.exam.util.impls;

import org.springframework.stereotype.Component;
import softuni.exam.util.XMLParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class XMLParserImpl implements XMLParser {


    @Override
    public <O> void marshalToXML(String path, O object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, new File(path));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <O> O unmarshalFromXML(String path, Class<O> object) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(object);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (O) unmarshaller.unmarshal(new FileReader(path));
    }
}
