package OSM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import OSM.model.TagDb;

@Repository
public interface TagRepository extends JpaRepository<TagDb, Long> {
}
