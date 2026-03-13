package mgr.instagram;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        // Rutas a los JSON (puedes cambiarlas o recibirlas por args)
        String followingPath = "following.json";
        String followersPath = "followers_1.json";

        ObjectMapper mapper = new ObjectMapper();

        try {
            Set<String> following = loadFollowing(mapper, followingPath);
            Set<String> followers = loadFollowers(mapper, followersPath);

            // 1) Me siguen y NO les sigo de vuelta
            Set<String> followersNoFollowBack = new HashSet<>(followers);
            followersNoFollowBack.removeAll(following);

            // 2) Seguimiento mutuo
            Set<String> mutual = new HashSet<>(followers);
            mutual.retainAll(following);

            // 3) Sigo y NO me siguen de vuelta
            Set<String> followingNoFollowBack = new HashSet<>(following);
            followingNoFollowBack.removeAll(followers);

            System.out.println("===== Me siguen y NO les sigo de vuelta =====");
            followersNoFollowBack.forEach(System.out::println);

            System.out.println("\n===== Seguimiento mutuo =====");
            mutual.forEach(System.out::println);

            System.out.println("\n===== Sigo y NO me siguen de vuelta =====");
            followingNoFollowBack.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error procesando ficheros: " + e.getMessage());
        }
    }

    /**
     * following.json:
     * {
     *   "relationships_following": [
     *     {
     *       "title": "usuario",
     *       "string_list_data": [ { "href": "...", "timestamp": ... } ]
     *     },
     *     ...
     *   ]
     * }
     */
    private static Set<String> loadFollowing(ObjectMapper mapper, String path) throws IOException {
        JsonNode root = mapper.readTree(new File(path));
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

    /**
     * followers_1.json:
     * [
     *   {
     *     "title": "",
     *     "string_list_data": [
     *       {
     *         "href": "https://www.instagram.com/usuario",
     *         "value": "usuario",
     *         "timestamp": 1234567890
     *       }
     *     ]
     *   },
     *   ...
     * ]
     */
    private static Set<String> loadFollowers(ObjectMapper mapper, String path) throws IOException {
        JsonNode array = mapper.readTree(new File(path));

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
