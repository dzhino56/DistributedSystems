package OSM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag_db")
public class TagDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "node_id")
    private NodeDb node;
}
