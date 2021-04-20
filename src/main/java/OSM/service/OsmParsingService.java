package OSM.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.springframework.stereotype.Service;
import OSM.model.NodeConverter;
import OSM.model.generated.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class OsmParsingService {
    private static final String NODE = "node";

    private final CRUDService crudService;

    public void parse(InputStream inputStream) throws JAXBException, XMLStreamException, IOException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        try (InputStream osm = new BZip2CompressorInputStream(inputStream)) {
            reader = factory.createXMLStreamReader(osm);
            while (reader.hasNext()) {
                int event = reader.next();
                if (XMLStreamConstants.START_ELEMENT == event && NODE.equals(reader.getLocalName())) {
                    processNode(jaxbContext, reader);
                }
            }
        } finally {
            assert reader != null;
            reader.close();
        }
    }

    private void processNode(JAXBContext jaxbContext, XMLStreamReader reader) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Node node = (Node) unmarshaller.unmarshal(reader);
        crudService.create(NodeConverter.toDb(node));
    }

}
