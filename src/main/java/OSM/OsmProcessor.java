package OSM;

import lombok.AllArgsConstructor;
import OSM.model.NodeDb;
import OSM.model.generated.Node;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.*;
import java.io.InputStream;

@AllArgsConstructor
public class OsmProcessor {
    private static final Logger LOG = LogManager.getLogger(OsmProcessor.class);
    private static final String NODE = "node";

    private final NodeService nodeService;

    public void process(InputStream inputStream) throws JAXBException, XMLStreamException {
        LOG.info("OSM processing start");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        try {
            reader = factory.createXMLStreamReader(inputStream);
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
        nodeService.flush();
        LOG.info("OSM processing finish");
    }

    private void processNode(JAXBContext jaxbContext, XMLStreamReader reader) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Node node = (Node) unmarshaller.unmarshal(reader);
        nodeService.putNodeBuffered(NodeDb.convert(node));
    }

}
