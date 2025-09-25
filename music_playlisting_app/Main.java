import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the HMM Music Playlisting Application.
 *
 * <p>This class provides a lightweight demo implementation of the
 * application. It keeps state in memory (no database), allows for
 * account creation and login, and demonstrates catalog and playlist
 * operations.</p>
 */
public class Main {

    /**
     * Global list of all registered users in the application.
     */
    public static final List<User> USERS = new ArrayList<>();

    /**
     * Global catalog of songs available in the application.
     */
    private static final List<Song> CATALOG = new ArrayList<>();

    /**
     * Service responsible for handling user authentication.
     */
    private static LoginService loginService = new LoginService(); // Login service instance

    /**
     * Main entry point of the application.
     *
     * <p>Handles user interaction, account creation, login, demo skipping,
     * and catalog/playlist demonstrations. Keeps the program running
     * until the user chooses to exit or a successful login occurs.</p>
     *
     * @param args command-line arguments (not used in this demo)
     */
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
                    loggedInUser = createAccount(scanner);
                    if (loggedInUser != null) {
                        System.out.println("You are now logged in as: " + loggedInUser.getUsername());
                        running = false; // Exit loop after successful account creation
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
        Artist demoArtist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song1 = new Song("Bing Bong", "Artie", 69);
        demoArtist.addSongToCatalog(song1);
        Song song2 = new Song("Whoa", "Artie", 30);
        demoArtist.addSongToCatalog(song2);

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

    /**
     * Handles account creation flow with duplicate email and username validation.
     *
     * <p>Prompts the user for account details and creates either a
     * {@link Listener} or {@link Artist} based on user input.
     * If validation fails, returns {@code null}.</p>
     *
     * @param scanner the {@link Scanner} instance for reading user input
     * @return the newly created {@link User}, or {@code null} if creation fails
     */
    private static User createAccount(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Type L for Listener or A for Artist: ");
        String type = scanner.nextLine().trim().toUpperCase();

        // 🔎 Check for duplicate email or username
        for (User u : USERS) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("❌ Error: An account with this email already exists.");
                return null;
            }
            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("❌ Error: Username already taken, please choose another.");
                return null;
            }
        }

        User newUser = null;

        if ("A".equals(type)) {
            newUser = new Artist(email, username, password, USERS.size() + 1);
            USERS.add(newUser);
            System.out.println("✅ Artist account created successfully!");
        } else if ("L".equals(type)) {
            newUser = new Listener(email, username, password, USERS.size() + 1, new ArrayList<>());
            USERS.add(newUser);
            System.out.println("✅ Listener account created successfully!");
        } else {
            System.out.println("❌ Invalid type, account creation rejected.");
        }

        return newUser;
    }
}
