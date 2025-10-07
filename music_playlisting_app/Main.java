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
        
        Artist defaultArtist = new Artist("artist@gmail.com", "artist", "artistpass", 2);
        USERS.add(defaultArtist);
        Song song1 = new Song("Somebody Else", "The 1975", 347);
        Song song2 = new Song("RAWFEAR", "Twenty One Pilots", 202);
        Song song3 = new Song("Hammer", "Lorde", 193);
        Song song4 = new Song("One More Time", "Daft Punk", 320);
        Song song5 = new Song("Club classics", "Charli xcx", 153);
        Song song6 = new Song("Hard Times", "Paramore", 182);
        defaultArtist.addSongToCatalog(CATALOG, song1);
        defaultArtist.addSongToCatalog(CATALOG, song2);
        defaultArtist.addSongToCatalog(CATALOG, song3);
        defaultArtist.addSongToCatalog(CATALOG, song4);
        defaultArtist.addSongToCatalog(CATALOG, song5);
        defaultArtist.addSongToCatalog(CATALOG, song6);
        //ArrayList<Song> artistListTest = defaultArtist.getCatalog(CATALOG);
        //System.out.println(artistListTest);
        // ^ test for my modifications for the SearchService/catalog object.
        // This version now works as I believe it should--we pass the global catalog to the 
        // artist object so it can modify it, and since it is static the modifications hold.

        System.out.println("Welcome to the HMM Music Playlisting Application!");

        boolean running = true;
        User loggedInUser = null;

        while (running) {
            System.out.println("\n1 = Create Account \n2 = Login \n0 = Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    loggedInUser = createAccount(scanner);
                    if (loggedInUser != null) {
                        System.out.println("You are now logged in as: " + loggedInUser.getUsername());
                        userToUI(scanner, loggedInUser);
                        //running = false; // Exit loop after successful account creation
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
                        //running = false;
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

        // // Demo: populate catalog and perform a few operations
        // Artist demoArtist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        // Song song1 = new Song("Bing Bong", "Artie", 69);
        // demoArtist.addSongToCatalog(song1);
        // Song song2 = new Song("Whoa", "Artie", 30);
        // demoArtist.addSongToCatalog(song2);

        // System.out.println("\nCatalog:");
        // for (Song s : CATALOG) {
            // System.out.println(" - " + s);
        // }

        // // If logged in as listener demo playlist ops
        // if (loggedInUser instanceof Listener) {
            // Listener listener = (Listener) loggedInUser;
            // Playlist p1 = listener.createNewPlaylist("Favorites");
            // boolean added = p1.addSong(new Song("Bing Bong", "Artie", 69));
            // System.out.println("Added Bing Bong to playlist: " + added);
            // System.out.println("Playlist length: " + p1.getTotalDurationFormatted());
        // }

        scanner.close();
        System.out.println("Closing application."); 
    }

    private static void userToUI(Scanner scanner, User user) {
        if (user instanceof Listener) {
            Listener listener = (Listener) user;
            listenerUI(scanner, listener);
        } else if (user instanceof Artist) {
            Artist artist = (Artist) user;
            artistUI(scanner, artist);
        } else if (user instanceof Admin) {
            Admin admin = (Admin) user;
            adminUI(scanner, admin);
        }
    }
    
    private static void listenerUI(Scanner scanner, Listener listener) {
        boolean running = true;
        while (running) {
            System.out.println("\n1 = Create a playlist \n2 = View all playlists \n3 = View a specific playlist \n4 = Search the catalog \n5 = Add a song to a playlist \n6 = Remove a song from a playlist \n7 = Delete a specific playlist \n8 = Delete all playlists \n0 = Exit");
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
                    viewPlaylist.listSongs();
                    break;
                
                case "4": 
                    System.out.print("Enter a search term: ");
                    String term = scanner.nextLine();
                    ArrayList<Song> results = CATALOG.searchByPartialTitle(term);
                    int tempSize = results.size();
                    for(int i = 0; i < tempSize; i++) {
                        System.out.println("[" + (i+1) + "]" + results.get(i));
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
                    listener.listPlaylists();
                    System.out.print("Enter a playlist index: ");
                    int removePlaylistIndex = Integer.parseInt(scanner.nextLine());
                    Playlist removePlaylist = listener.getPlaylistAtIndex(removePlaylistIndex);
                    removePlaylist.listSongs();
                    System.out.print("Enter a song index: ");
                    int removeSongIndex = Integer.parseInt(scanner.nextLine());
                    removePlaylist.removeSongAtIndex(removeSongIndex);
                    break;
                    
                case "7":
                    listener.listPlaylists();
                    System.out.print("Enter a playlist index: ");
                    int deletePlaylistIndex = Integer.parseInt(scanner.nextLine());
                    listener.deletePlaylistAtIndex(deletePlaylistIndex);
                    break;
                    
                case "8":
                    listener.clearPlaylists();
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
    
    private static void artistUI(Scanner scanner, Artist artist) {
        boolean running = true;
        while (running) {
            System.out.println("\n1 = Upload to global catalog \n2 = Get global catalog \n3 = Remove from global catalog \n0 = Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1": 
                    System.out.print("Type song name to upload to catalog:");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter duration in seconds to add to catalog:");
                    int time = scanner.nextInt();
                    Song s = new Song(title, artist.getUsername(), time);
                    artist.addSongToCatalog(CATALOG, s);
                    break;
                    
                case "2": 
                    ArrayList<Song> results = CATALOG.searchSongByArtist(artist.getUsername());
                    int tempSize = results.size();
                    for(int i = 0; i < tempSize; i++) {
                        System.out.println("[" + i + "]" + results.get(i));
                    }
                    break;
                    
                case "3":
                    ArrayList<Song> result = CATALOG.searchSongByArtist(artist.getUsername());
                    tempSize = result.size();
                    for(int i = 0; i < tempSize; i++) {
                        System.out.println("[" + i + "]" + result.get(i));
                    }
                    System.out.print("Enter the index of the song you wan to delete:");
                    int removeSongIndex = Integer.parseInt(scanner.nextLine());
                    Song tempResult = result.get(removeSongIndex);
                    boolean checker = CATALOG.removeSongToCatalog(tempResult);
                    if (checker == true) {
                        System.out.println(tempResult.getTitle() + " has been removed from the catalog");
                    }
                    break;                 
                
                case "0":
                    System.out.println("Bye Bye!!!!!!");
                    running = false;
                    break;
                    
                default:
                    System.out.print("Unknown option.");
                }
            }
    }
    
    private static void adminUI(Scanner scanner, Admin admin) {
        // Placeholder for admin interaction loop
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
