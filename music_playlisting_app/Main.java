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
    private static SearchService CATALOG = new SearchService();

    /**
     * Service responsible for handling user authentication.
     */
    private static LoginService loginService = new LoginService();

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

        // Preload demo users and songs
        Listener defaultListener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        USERS.add(defaultListener);

        Artist defaultArtist = new Artist("artist@gmail.com", "artist", "artistpass", 2);
        USERS.add(defaultArtist);
        
        Admin admin = new Admin("admin@gmail.com", "admin", "adminpass", 3);
        USERS.add(admin);
        
        // Users with no data
        Listener noNameListener = new Listener("noname1@gmail.com", "noname", "password", 1, new ArrayList<>());
        USERS.add(noNameListener);

        Artist noNameArtist = new Artist("noname2@gmail.com", "nothing", "password", 2);
        USERS.add(noNameArtist);

        Song song1 = new Song("Somebody Else", "artist", 347);
        Song song2 = new Song("RAWFEAR", "artist", 202);
        Song song3 = new Song("Hammer", "artist", 193);
        Song song4 = new Song("One More Time", "artist", 320);
        Song song5 = new Song("Club classics", "artist", 153);
        Song song6 = new Song("Hard Times", "artist", 182);

        defaultArtist.addSongToCatalog(CATALOG, song1);
        defaultArtist.addSongToCatalog(CATALOG, song2);
        defaultArtist.addSongToCatalog(CATALOG, song3);
        defaultArtist.addSongToCatalog(CATALOG, song4);
        defaultArtist.addSongToCatalog(CATALOG, song5);
        defaultArtist.addSongToCatalog(CATALOG, song6);

        System.out.println("Welcome to the HMM Music Playlisting Application!");

        boolean running = true;
        User loggedInUser = null;

        while (running) {
            System.out.println(
                "\n1 = Create Account " + 
                "\n2 = Login " + 
                "\n0 = Exit"
            );
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    loggedInUser = createAccount(scanner);
                    if (loggedInUser != null) {
                        System.out.println("You are now logged in as: " + loggedInUser.getUsername());
                        userToUI(scanner, loggedInUser);
                    }
                    break;

                case "2":
                    System.out.print("Username: ");
                    String u = scanner.nextLine().trim();
                    System.out.print("Password: ");
                    String p = scanner.nextLine().trim();
                    loggedInUser = loginService.authenticate(u, p, USERS);
                    if (loggedInUser != null) {
                        userToUI(scanner, loggedInUser);
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

        scanner.close();
        System.out.println("Closing application.");
    }

    /**
     * Routes the logged-in {@link User} to the correct user interface (UI)
     * based on their account type.
     *
     * @param scanner the scanner for user input
     * @param user    the logged-in user
     */
    private static void userToUI(Scanner scanner, User user) {
        if (user instanceof Listener) {
            listenerUI(scanner, (Listener) user);
        } else if (user instanceof Artist) {
            artistUI(scanner, (Artist) user);
        } else if (user instanceof Admin) {
            adminUI(scanner, (Admin) user);
        }
    }

    /**
     * Provides an interactive menu for a {@link Listener} user,
     * enabling playlist creation, modification, and catalog searching.
     *
     * @param scanner  the scanner for reading user input
     * @param listener the logged-in listener user
     */
    private static void listenerUI(Scanner scanner, Listener listener) {
        int sizePlaylist;
        boolean running = true;
        while (running) {
            System.out.println(
                "\n1 = Create a playlist " + 
                "\n2 = View all playlists " + 
                "\n3 = View a specific playlist " +
                "\n4 = Search the catalog " + 
                "\n5 = Add a song to a playlist " + 
                "\n6 = Remove a song from a playlist " +
                "\n7 = Delete a specific playlist " + 
                "\n8 = Delete all playlists " + 
                "\n0 = Exit"
            );
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter a playlist title: ");
                    String title = scanner.nextLine();
                    Playlist playlist = listener.createNewPlaylist(title);
                    System.out.println("Playlist '" + title + "' created.");
                    break;

                case "2":
                    listener.listPlaylists();
                    break;

                case "3":
                    listener.listPlaylists();
                    System.out.print("Enter a playlist index: ");
                    int viewPlaylistIndex = Integer.parseInt(scanner.nextLine());
                    Playlist viewPlaylist = listener.getPlaylistAtIndex(viewPlaylistIndex);
                    if (viewPlaylist.getTrackList().size() > 0) {
                        viewPlaylist.listSongs();
                    } else {
                        System.out.println(viewPlaylist.getName() + " is empty.");
                    }
                    break;

                case "4":
                    System.out.print("Enter a search term: ");
                    String term = scanner.nextLine();
                    ArrayList<Song> results = CATALOG.searchByPartialTitle(term);
                    for (int i = 0; i < results.size(); i++) {
                        System.out.println("[" + (i + 1) + "] " + results.get(i));
                    }
                    break;

                case "5":
                    listener.listPlaylists();
                    System.out.print("Enter a playlist index: ");
                    int addPlaylistIndex = Integer.parseInt(scanner.nextLine());
                    Playlist addPlaylist = listener.getPlaylistAtIndex(addPlaylistIndex);
                    CATALOG.listSongs();
                    System.out.print("Enter a song index: ");
                    int addSongIndex = Integer.parseInt(scanner.nextLine());
                    Song addSong = CATALOG.getSongAtIndex(addSongIndex);
                    addPlaylist.addSong(addSong);
                    System.out.println("'" + addSong.getTitle() + "' has been added to '" + addPlaylist.getName() + "'.");
                    break;

                case "6":
                    sizePlaylist = listener.getPlaylists().size();
                    if (sizePlaylist > 0) {
                        listener.listPlaylists();
                        System.out.print("Enter a playlist index: ");
                        int removePlaylistIndex = Integer.parseInt(scanner.nextLine());
                        Playlist removePlaylist = listener.getPlaylistAtIndex(removePlaylistIndex);
                        int sizeSongs = removePlaylist.getTrackList().size();
                        if (sizeSongs > 0) {
                            removePlaylist.listSongs();
                            System.out.print("Enter a song index: ");
                            int removeSongIndex = Integer.parseInt(scanner.nextLine());
                            removePlaylist.removeSongAtIndex(removeSongIndex);
                        } else {
                            System.out.print("There are no songs.");
                        }
                    } else {
                        System.out.print("There are no playlists.");
                    }
                    break;

                case "7":
                    sizePlaylist = listener.getPlaylists().size();
                    if (sizePlaylist > 0) {
                        listener.listPlaylists();
                        System.out.print("Enter a playlist index: ");
                        int deletePlaylistIndex = Integer.parseInt(scanner.nextLine());
                        listener.deletePlaylistAtIndex(deletePlaylistIndex);
                    } else {
                        System.out.print("There are no playlists to delete.");
                    }
                    break;

                case "8":
                    sizePlaylist = listener.getPlaylists().size();
                    if (sizePlaylist > 0) {
                        listener.clearPlaylists();
                    } else {
                        System.out.print("There are no playlists to delete.");
                    }
                    break;

                case "0":
                    System.out.println("Exiting...");
                    running = false;
                    break;

                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    /**
     * Provides an interactive menu for an {@link Artist} user,
     * enabling song uploads, catalog viewing, and removal.
     *
     * @param scanner the scanner for reading user input
     * @param artist  the logged-in artist user
     */
    private static void artistUI(Scanner scanner, Artist artist) {
        boolean running = true;
        while (running) {
            System.out.println(
                "\n1 = Upload to global catalog" + 
                "\n2 = Get global catalog " + 
                "\n3 = Remove from global catalog " + 
                "\n0 = Exit"
            );
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Type song name to upload to catalog: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter duration in seconds to add to catalog: ");
                    int time = Integer.parseInt(scanner.nextLine());
                    Song s = new Song(title, artist.getUsername(), time);
                    artist.addSongToCatalog(CATALOG, s);
                    break;

                case "2":
                    ArrayList<Song> results = CATALOG.searchSongByArtist(artist.getUsername());
                    for (int i = 0; i < results.size(); i++) {
                        System.out.println("[" + i + "] " + results.get(i));
                    }
                    break;

                case "3":
                    ArrayList<Song> result = CATALOG.searchSongByArtist(artist.getUsername());
                    for (int i = 0; i < result.size(); i++) {
                        System.out.println("[" + i + "] " + result.get(i));
                    }
                    System.out.print("Enter the index of the song you want to delete: ");
                    int removeSongIndex = Integer.parseInt(scanner.nextLine());
                    Song tempResult = result.get(removeSongIndex);
                    boolean checker = CATALOG.removeSongToCatalog(tempResult);
                    if (checker) {
                        System.out.println(tempResult.getTitle() + " has been removed from the catalog.");
                    }
                    break;

                case "0":
                    System.out.println("Bye Bye!");
                    running = false;
                    break;

                default:
                    System.out.print("Unknown option.");
            }
        }
    }

    /**
     * Provides an interactive menu for an {@link Admin} user.
     * <p>Currently a placeholder for future admin-specific operations.</p>
     *
     * @param scanner the scanner for reading user input
     * @param admin   the logged-in admin user
     */
    private static void adminUI(Scanner scanner, Admin admin) {
        boolean running = true;
        while (running) {
            System.out.println(
                "\n1 = List all users" +
                "\n2 = Query a user" + 
                "\n3 = Upload to global catalog" + 
                "\n4 = Remove from global catalog" + 
                "\n5 = Add new user" + 
                "\n6 = Delete a user" + 
                "\n0 = Exit"
            );
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (USERS.size() <= 0) {
                        System.out.println("No users.");
                    } else {
                        listUsers();
                    }
                    break; 
                
                case "2":
                    if (USERS.size() <= 0) {
                        System.out.println("No users.");
                    } else {
                        listUsers();
                    }
                    System.out.print("Enter a user index: ");
                    int userIndex = Integer.parseInt(scanner.nextLine());
                    // Validate user input
                    if (USERS.size() <= 0) {
                        System.out.println("No users.");
                    } else if (userIndex < USERS.size() && userIndex >= 0) {
                        queryUser(USERS.get(userIndex), scanner); 
                        // Passes User object and Scanner forward since we may need to ask the user to select a playlist as well.
                    } else {
                        System.out.println("Invalid index.");
                    }
                    break;
                    
                case "0":
                    System.out.println("Exiting...");
                    running = false;
                    break;

                default:
                    System.out.println("Unknown option.");
            }
        }
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

        // Check for duplicate email or username
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
    
    private static void listUsers() {
        System.out.println("=== ALL USERS ===");
        for (int i = 0; i < USERS.size(); i++) {
            System.out.println("[" + i + "] - " + USERS.get(i).toString());
        }
    }
    
    /**
     * Called from Admin user Choice #2
     * 
     * @param User queryUser -- the User we are querying data for from the Admin perspective
     * @param Scanner scanner -- Keep the Scanner object consistent so we can continue taking in user input without interuption.
     */
    private static void queryUser(User queryUser, Scanner scanner) {
        // Check the type of user
        if (queryUser instanceof Listener) {
            Listener queryListener = (Listener) queryUser; // Cast to new listener-type object
            queryListener.adminQuery(); // Run listener.adminQuery() to print general data
            
            // We need only proceed if this Listener has playlists
            if (queryListener.getPlaylists().size() > 0) { 
                System.out.print("Enter a playlist index: ");
                int viewPlaylistIndex = Integer.parseInt(scanner.nextLine());
                // If-else validates the user input
                if (viewPlaylistIndex < queryListener.getPlaylists().size() && viewPlaylistIndex >= 0) {
                    Playlist viewPlaylist = queryListener.getPlaylistAtIndex(viewPlaylistIndex);
                    // Only run Playlist.listSongs() if the tracklist actually has Songs to list.
                    if (viewPlaylist.getTrackList().size() > 0) {
                        viewPlaylist.listSongs();
                    } else {
                        System.out.println(viewPlaylist.getName() + " is empty.");
                    }
                } else {
                    System.out.println("Invalid index.");
                }
            }
        } else if (queryUser instanceof Artist) {
            Artist queryArtist = (Artist) queryUser;
            queryArtist.adminQuery();
            // Printing the Catalog must be done from Main as the Catalog must be passed forward to the Artist class.
            ArrayList artistCatalog = queryArtist.getCatalog(CATALOG);
        } else if (queryUser instanceof Admin) {
            Admin queryAdmin = (Admin) queryUser;
            queryAdmin.adminQuery();
        }
    }

}
