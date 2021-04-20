package OSM.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import OSM.model.NodeConverter;
import OSM.model.NodeDTO;
import OSM.service.CRUDService;
import OSM.service.OsmParsingService;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/node")
@RequiredArgsConstructor
public class NodeController {

    private final CRUDService crudService;
    private final OsmParsingService osmParsingService;

    @GetMapping("/{id}")
    NodeDTO getNode(@PathVariable("id") Long id) {
        return NodeConverter.toDTO(crudService.read(id));
    }

    @PostMapping
    NodeDTO createNode(@RequestBody NodeDTO node) {
        return NodeConverter.toDTO(crudService.create(NodeConverter.toDb(node)));
    }

    @PutMapping("/{id}")
    NodeDTO updateNode(@PathVariable("id") Long id,
                       @RequestBody NodeDTO node) {
        return NodeConverter.toDTO(crudService.update(id, NodeConverter.toDb(node)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        crudService.delete(id);
    }

    @GetMapping("/search")
    public List<NodeDTO> search(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("radius") Double radius
    ) {
        return crudService.search(latitude, longitude, radius).stream()
                .map(NodeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/init")
    public void init(
            @RequestParam("osmPath") String osmPath
    ) throws IOException, JAXBException, XMLStreamException {
        try (InputStream is = new FileInputStream(osmPath)) {
            osmParsingService.parse(is);
        }
    }
}
