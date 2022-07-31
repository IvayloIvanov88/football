package softuni.exam.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XMLParser {

    <O> void marshalToXML(String path, O object) throws JAXBException;

    <O> O unmarshalFromXML(String path, Class<O> object) throws JAXBException, FileNotFoundException;
}