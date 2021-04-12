package OSM;

import java.util.HashMap;
import java.util.Map;

public class OsmResultContainer {
    private final Map<String, Integer> users = new HashMap<>();
    private final Map<String, Integer> tags = new HashMap<>();

    public void userEditsCount(String user) {
        users.compute(user, (k, v) -> (v == null) ? 1 : v + 1);
    }

    public void incrementTagCount(String tag) {
        tags.compute(tag, (k, v) -> (v == null) ? 1 : v + 1);
    }

    public Map<String, Integer> getUsers() {
        return users;
    }

    public Map<String, Integer> getTags() {
        return tags;
    }
}
