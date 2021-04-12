package OSM;

import OSM.dao.NodeDao;
import OSM.dao.NodeDaoImpl;
import OSM.dao.TagDao;
import OSM.dao.TagDaoImpl;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Wrong argument count");
        }
        LOG.info("File decompressing start");
        try (InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream(args[0]))) {
            LOG.info("File decompressing finish");
            DbUtils.init();
            NodeDao nodeDao = new NodeDaoImpl();
            TagDao tagDao = new TagDaoImpl();
            NodeService nodeService = new NodeService(nodeDao, tagDao);
            OsmProcessor osmProcessor = new OsmProcessor(nodeService);
            osmProcessor.process(inputStream);
        } catch (FileNotFoundException e) {
            LOG.error("File not found", e);
        } catch (IOException e) {
            LOG.error("File read error", e);
        } catch (JAXBException | XMLStreamException e) {
            LOG.error("File processing error", e);
        } catch (SQLException e) {
            LOG.error("Failed to initialize database", e);
        } finally {
            DbUtils.closeConnection();
        }
    }
}