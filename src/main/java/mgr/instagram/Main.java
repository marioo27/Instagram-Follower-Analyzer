package mgr.instagram;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        String followingPath = "following.json";
        String followersPath = "followers_1.json";

        JsonManager manager = new JsonManager(followingPath, followersPath);

        try {
            manager.loadData();
        } catch (IOException e) {
            System.err.println("Error cargando los ficheros JSON: " + e.getMessage());
            return;
        }

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("===== MENU INSTAGRAM =====");
            System.out.println("1. Ver quien me sigue y NO sigo de vuelta");
            System.out.println("2. Ver quien me sigue y yo tambien sigo (mutuos)");
            System.out.println("3. Ver a quien sigo y NO me sigue de vuelta");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            while (!sc.hasNextInt()) {
                System.out.print("Opcion no valida. Introduce un numero: ");
                sc.next();
            }
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> {
                    Set<String> list = manager.getFollowersNoFollowBack();
                    System.out.println("=== Me siguen y NO les sigo de vuelta ===");
                    list.forEach(System.out::println);
                    System.out.println("Total: " + list.size());
                }
                case 2 -> {
                    Set<String> list = manager.getMutualFollowers();
                    System.out.println("=== Seguimiento mutuo ===");
                    list.forEach(System.out::println);
                    System.out.println("Total: " + list.size());
                }
                case 3 -> {
                    Set<String> list = manager.getFollowingNoFollowBack();
                    System.out.println("=== Sigo y NO me siguen de vuelta ===");
                    list.forEach(System.out::println);
                    System.out.println("Total: " + list.size());
                }
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no valida.");
            }

            System.out.println();

        } while (opcion != 0);

        sc.close();
    }
}
