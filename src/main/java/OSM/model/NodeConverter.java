package OSM.model;

import org.springframework.stereotype.Component;
import OSM.model.generated.Node;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NodeConverter {
    public static NodeDb toDb(NodeDTO nodeDto) {
        List<TagDb> tags = nodeDto.getTags().entrySet().stream()
                .map(t -> TagDb.builder()
                        .key(t.getKey())
                        .value(t.getValue())
                        .build()).collect(Collectors.toList());
        return NodeDb.builder()
                .latitude(nodeDto.getLatitude())
                .longitude(nodeDto.getLongitude())
                .name(nodeDto.getName())
                .tags(tags)
                .build();
    }

    public static NodeDb toDb(Node node) {
        List<TagDb> tags = node.getTag().stream()
                .map(t -> TagDb.builder()
                        .key(t.getK())
                        .value(t.getV())
                        .build()).collect(Collectors.toList());
        return NodeDb.builder()
                .latitude(node.getLat())
                .longitude(node.getLon())
                .name(node.getUser())
                .tags(tags)
                .build();
    }

    public static NodeDTO toDTO(NodeDb nodeDb) {
        Map<String, String> tags = nodeDb.getTags().stream().collect(Collectors.toMap(TagDb::getKey, TagDb::getValue));
        return new NodeDTO(nodeDb.getId(), nodeDb.getName(), nodeDb.getLongitude(), nodeDb.getLatitude(), tags);
    }
}
