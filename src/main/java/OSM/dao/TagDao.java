package OSM.dao;

import OSM.model.TagDb;

import java.sql.SQLException;
import java.util.List;

public interface TagDao {
    List<TagDb> getTags(long nodeId) throws SQLException;

    void insertTag(TagDb tag) throws SQLException;

    void insertPreparedTag(TagDb tag) throws SQLException;

    void batchInsertTags(List<TagDb> tags) throws SQLException;
}
