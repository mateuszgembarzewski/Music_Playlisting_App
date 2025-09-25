import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Lightweight demo main for the app. Keeps state in memory (no DB).
 */
public class Main {
    public static final List<User> USERS = new ArrayList<>();
    private static final List<Song> CATALOG = new ArrayList<>();
    private static LoginService loginService = new LoginService(); // Login service instance

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Preload a listener user for testing/demo
        Listener defaultListener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        USERS.add(defaultListener);

        System.out.println("Welcome to the HMM Music Playlisting Application!");

        boolean running = true;
        User loggedInUser = null;

        while (running) {
            System.out.println("\n1 = Create Account | 2 = Login | 3 = Skip to Demo | 0 = Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine().trim();
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine().trim();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine().trim();
                    System.out.print("Type L for Listener or A for Artist: ");
                    String type = scanner.nextLine().trim().toUpperCase();

                    if ("A".equals(type)) {
                        Artist a = new Artist(email, username, password);
                        USERS.add(a);
                        loggedInUser = a;
                        System.out.println("Artist account created.");
                    } else if ("L".equals(type)) {
                        Listener l = new Listener(email, username, password, USERS.size() + 1, new ArrayList<>());
                        USERS.add(l);
                        loggedInUser = l;
                        System.out.println("Listener account created.");
                    } else {
                        System.out.println("Invalid type, rejected.");
                    }
                    break;

                case "2":
                    System.out.print("Username: ");
                    String u = scanner.nextLine().trim();
                    System.out.print("Password: ");
                    String p = scanner.nextLine().trim();
                    loggedInUser = loginService.authenticate(u, p, USERS);
                    if (loggedInUser != null) {
                        running = false;
                    }
                    break;

                case "3":
                    System.out.println("Skipping to demo...");
                    running = false;
                    break;

                case "0":
                    System.out.println("Exiting...");
                    running = false;
                    break;

                default:
                    System.out.println("Unknown option.");
            }
        }

        // Demo: populate catalog and perform a few operations
        Artist demoArtist = new Artist("artist@gmail.com", "Artie", "secret");
        demoArtist.addSong(CATALOG, "Bing Bong", 69);
        demoArtist.addSong(CATALOG, "Whoa", 30);

        System.out.println("\nCatalog:");
        for (Song s : CATALOG) {
            System.out.println(" - " + s);
        }

        // If logged in as listener demo playlist ops
        if (loggedInUser instanceof Listener) {
            Listener listener = (Listener) loggedInUser;
            Playlist p1 = listener.createNewPlaylist("Favorites");
            boolean added = p1.addSong(new Song("Bing Bong", "Artie", 69));
            System.out.println("Added Bing Bong to playlist: " + added);
            System.out.println("Playlist length: " + p1.getTotalDurationFormatted());
        }

        scanner.close();
    }
}
