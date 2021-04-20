package OSM.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Builder
public class NodeDTO {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    @NotNull
    private Map<String, String> tags;
}
