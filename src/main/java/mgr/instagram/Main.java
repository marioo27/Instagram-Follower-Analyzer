package mgr.instagram;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

/**
 * Main class that displays a console menu
 * to analyze Instagram follower relationships.
 *
 * @author Mario Garcia
 * @version 1.0
 * @since 1.0
 */
public class Main {

    /**
     * Application entry point.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        String followingPath = "following.json";
        String followersPath = "followers_1.json";

        JsonManager manager = new JsonManager(followingPath, followersPath);

        try {
            manager.loadData();
        } catch (IOException e) {
            System.err.println("Error loading JSON files: " + e.getMessage());
            return;
        }

        Scanner sc = new Scanner(System.in);
        int option;

        do {
            System.out.println("===== INSTAGRAM MENU =====");
            System.out.println("1. See who follows me and I DON'T follow back");
            System.out.println("2. See mutual followers");
            System.out.println("3. See who I follow and DOESN'T follow me back");
            System.out.println("0. Exit");
            System.out.print("Choose an option -> ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid option. Enter a number -> ");
                sc.next();
            }
            option = sc.nextInt();

            switch (option) {
                case 1 -> {
                    Set<String> list = manager.getFollowersNoFollowBack();
                    System.out.println("=== They follow me and I DON'T follow back ===");
                    list.forEach(System.out::println);
                    System.out.println("Total: " + list.size());
                }
                case 2 -> {
                    Set<String> list = manager.getMutualFollowers();
                    System.out.println("=== Mutual followers ===");
                    list.forEach(System.out::println);
                    System.out.println("Total: " + list.size());
                }
                case 3 -> {
                    Set<String> list = manager.getFollowingNoFollowBack();
                    System.out.println("=== I follow them and they DON'T follow back ===");
                    list.forEach(System.out::println);
                    System.out.println("Total: " + list.size());
                }
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option.");
            }

            System.out.println();

        } while (option != 0);

        sc.close();
    }
}