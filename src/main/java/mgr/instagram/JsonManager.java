package mgr.instagram;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JsonManager {

    private final ObjectMapper mapper;
    private final String followingPath;
    private final String followersPath;

    private Set<String> following;
    private Set<String> followers;

    public JsonManager(String followingPath, String followersPath) {
        this.mapper = new ObjectMapper();
        this.followingPath = followingPath;
        this.followersPath = followersPath;
    }

    public void loadData() throws IOException {
        this.following = loadFollowing();
        this.followers = loadFollowers();
    }

    // Me siguen y NO les sigo de vuelta
    public Set<String> getFollowersNoFollowBack() {
        checkLoaded();
        Set<String> result = new HashSet<>(followers);
        result.removeAll(following);
        return result;
    }

    // Seguimiento mutuo
    public Set<String> getMutualFollowers() {
        checkLoaded();
        Set<String> result = new HashSet<>(followers);
        result.retainAll(following);
        return result;
    }

    // Sigo y NO me siguen de vuelta
    public Set<String> getFollowingNoFollowBack() {
        checkLoaded();
        Set<String> result = new HashSet<>(following);
        result.removeAll(followers);
        return result;
    }

    private void checkLoaded() {
        if (following == null || followers == null) {
            throw new IllegalStateException("Los datos no están cargados. Llama primero a loadData().");
        }
    }

    private Set<String> loadFollowing() throws IOException {
        JsonNode root = mapper.readTree(new File(followingPath));
        JsonNode relationships = root.get("relationships_following");

        Set<String> result = new HashSet<>();

        if (relationships != null && relationships.isArray()) {
            for (JsonNode node : relationships) {
                JsonNode titleNode = node.get("title");
                if (titleNode != null && !titleNode.isNull()) {
                    String username = titleNode.asText();
                    if (username != null && !username.isBlank()) {
                        result.add(username);
                    }
                }
            }
        }
        return result;
    }

    private Set<String> loadFollowers() throws IOException {
        JsonNode array = mapper.readTree(new File(followersPath));

        Set<String> result = new HashSet<>();

        if (array != null && array.isArray()) {
            for (JsonNode node : array) {
                JsonNode stringList = node.get("string_list_data");
                if (stringList != null && stringList.isArray() && stringList.size() > 0) {
                    JsonNode first = stringList.get(0);
                    JsonNode valueNode = first.get("value");
                    if (valueNode != null && !valueNode.isNull()) {
                        String username = valueNode.asText();
                        if (username != null && !username.isBlank()) {
                            result.add(username);
                        }
                    }
                }
            }
        }
        return result;
    }
}
