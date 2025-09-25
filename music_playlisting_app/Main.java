import java.util.*;

public class Main {
    public static final List<User> USERS = new ArrayList<>();
    private static final List<Song> CATALOG = new ArrayList<>();
    private static LoginService loginService = new LoginService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Preload a listener user for testing/demo
        Listener defaultListener = new Listener("testuser@gmail.com", "testuser", "password", 1);
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
                    // Account creation logic here
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
        Artist demoArtist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song1 = new Song("Bing Bong", "Artie", 69);
        demoArtist.addSongToCatalog(song1);
        CATALOG.add(song1); // Add to global catalog

        Song song2 = new Song("Whoa", "Artie", 30);
        demoArtist.addSongToCatalog(song2);
        CATALOG.add(song2); // Add to global catalog

        System.out.println("\nCatalog:");
        for (Song s : CATALOG) {
            System.out.println(" - " + s);
        }

        // If logged in as listener demo playlist operations
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
