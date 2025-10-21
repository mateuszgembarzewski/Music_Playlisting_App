import java.util.ArrayList;
import java.util.Scanner;

/**
 * The entry point for the Music Playlisting Application
 * 
 * GLOBAL VARIABLES:
 * ArrayList<User> USERS - Stores the created, valid login credentials for all accounts
 * SearchService CATALOG - Manages the searchable global song catalog for the application
 * LoginService loginService - Responsible for authenticating credentials against the stored USER credentials.
 * 
 * The application can be started be executing the main() method.
 * 
 * @author Hitiksh Doshi
 * @author Mateusz Gembarzewski
 * @author Michael Scandiffio
 */
public class Main {
    // Stores created, valid login credentials for all accounts
    public static final ArrayList<User> USERS = new ArrayList<>();

    // Manages the global song catalog.
    private static SearchService CATALOG = new SearchService();

    // Necessary for authenticating credentials.
    private static LoginService loginService = new LoginService();

    /**
     * The actual method setup to begin the application's proper execution.
     *
     * main() initializes account and song data to assist in demoing and development.
     * It then feeds into a while loop that simulates an interactive text-based UI.
     * 
     * @param args command-line arguments (not used in this demo)
     * @return void
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Set up to take user input

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
        
        // Initialize songs to populate catalog
        Song song1 = new Song("Somebody Else", "artist", 347);
        Song song2 = new Song("RAWFEAR", "artist", 202);
        Song song3 = new Song("Hammer", "artist", 193);
        Song song4 = new Song("One More Time", "artist", 320);
        Song song5 = new Song("Club classics", "artist", 153);
        Song song6 = new Song("Hard Times", "artist", 182);
        
        // Populate the catalog
        defaultArtist.addSongToCatalog(CATALOG, song1);
        defaultArtist.addSongToCatalog(CATALOG, song2);
        defaultArtist.addSongToCatalog(CATALOG, song3);
        defaultArtist.addSongToCatalog(CATALOG, song4);
        defaultArtist.addSongToCatalog(CATALOG, song5);
        defaultArtist.addSongToCatalog(CATALOG, song6);

        System.out.println("Welcome to the HMM Music Playlisting Application!"); // welcome message

        boolean running = true; // While true, user is within the execution of our app
        User loggedInUser = null;
        while (running) {
            System.out.println(
                "\n1 = Create Account " + 
                "\n2 = Login " + 
                "\n0 = Close application"
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
                        userToUI(scanner, loggedInUser); // Passes the authenticated user to one of our UI loops
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
     * Takes in the Scanner and authenticated User and casts it to a specific user type.
     * Then passes context over to that user type's UI.
     *
     * @param scanner the scanner for user input
     * @param user    an authenticated User
     * @return void
     */
    private static void userToUI(Scanner scanner, User user) {
        // Determines the type of the user as we do not store any concrete marker for it
        if (user instanceof Listener) {
            listenerUI(scanner, (Listener) user);
        } else if (user instanceof Artist) {
            artistUI(scanner, (Artist) user);
        } else if (user instanceof Admin) {
            adminUI(scanner, (Admin) user);
        }
    }

    /**
     * Interactivity loop for Listener-type Users.
     * It is impossible to reach here without being an authenticated Listener-type user.
     * While loop functions similar to the initial log-in loop and will allow the user to perform actions until they exit.
     *
     * Allows for the creation, modification, and deletion of playlists.
     * Allows for searching the global catalog.
     * Allows for viewing lists of and the content of playlists.
     *
     * @param scanner  the scanner for user input
     * @param listener an authenticated Listener-type user
     * @return void
     */
    private static void listenerUI(Scanner scanner, Listener listener) {
        int sizePlaylist; // Value for Delete playlist functions
        boolean running = true; // Is only set to false on Choice 0; Log out
        // while in the context of this while loop, the user is a listener interacting w/ listener-role systems
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
                "\n0 = Log out"
            );
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Enter a playlist title: ");
                    String title = scanner.nextLine();
                    Playlist playlist = listener.createNewPlaylist(title);
                    
                    System.out.println("Playlist '" + title + "' created."); // Convey action to user
                    break;

                case "2":
                    listener.listLibrary(); // Prints library to user
                    break;

                case "3":
                    listener.listLibrary();
                    System.out.print("Enter a playlist index: ");
                    int viewPlaylistIndex = Integer.parseInt(scanner.nextLine());
                    Playlist viewPlaylist = listener.getPlaylistAtIndex(viewPlaylistIndex);
                    
                    // Only try to print data to the user if that data exists.
                    if (viewPlaylist.getTracklist().size() > 0) {
                        viewPlaylist.listSongs(); // Prints tracklist to user
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
                    listener.listLibrary();
                    // Don't allow the user to enter a value if they have no playlists.   
                    if (listener.getLibrary().size() > 0) {
                        System.out.print("Enter a playlist index: ");
                        int addPlaylistIndex = Integer.parseInt(scanner.nextLine());
                        // Validate user input 
                        if (addPlaylistIndex < listener.getLibrary().size() && addPlaylistIndex >= 0) {
                            Playlist addPlaylist = listener.getPlaylistAtIndex(addPlaylistIndex);
                            CATALOG.listSongs();
                            System.out.print("Enter a song index: ");
                            int addSongIndex = Integer.parseInt(scanner.nextLine());
                            // Validate user input 
                            if (addSongIndex < CATALOG.getGlobalCatalog().size() && addSongIndex >= 0) {
                                Song addSong = CATALOG.getSongAtIndex(addSongIndex);
                                addPlaylist.addSong(addSong);
                                System.out.println("'" + addSong.getTitle() + "' has been added to '" + addPlaylist.getName() + "'.");
                            } else {
                                System.out.println("Invalid index.");
                            }
                        } else {
                            System.out.println("Invalid index.");
                        }
                    }
                    break;

                case "6":
                    sizePlaylist = listener.getLibrary().size();
                    // Only allow user to select a playlist when at least one exists.
                    if (sizePlaylist > 0) {
                        listener.listLibrary();
                        System.out.print("Enter a playlist index: ");
                        int removePlaylistIndex = Integer.parseInt(scanner.nextLine());
                        Playlist removePlaylist = listener.getPlaylistAtIndex(removePlaylistIndex);
                        int sizeSongs = removePlaylist.getTracklist().size();
                        // Only allow a user to select a song when at least one exists.
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
                    sizePlaylist = listener.getLibrary().size();
                    if (sizePlaylist > 0) {
                        listener.listLibrary();
                        System.out.print("Enter a playlist index: ");
                        int deletePlaylistIndex = Integer.parseInt(scanner.nextLine());
                        listener.deletePlaylistAtIndex(deletePlaylistIndex);
                    } else {
                        System.out.print("There are no playlists to delete.");
                    }
                    break;

                case "8":
                    sizePlaylist = listener.getLibrary().size();
                    if (sizePlaylist > 0) {
                        listener.clearLibrary();
                    } else {
                        System.out.print("There are no playlists to delete.");
                    }
                    break;

                case "0":
                    System.out.println("Logging out...");
                    running = false;
                    break;

                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    /**
     * Interactivity loop for Artist-type Users.
     * 
     * Allows for the addition or removal of the authenticated Artist's songs from the global catalog.
     * Allows artist to view their own catalog that they have added..
     * 
     * @param scanner the scanner for user input
     * @param artist  an authenticated Artist-type user
     * @return void
     */
    private static void artistUI(Scanner scanner, Artist artist) {
        boolean running = true;
        while (running) {
            System.out.println(
                "\n1 = Upload to global catalog" + 
                "\n2 = Get global catalog " + 
                "\n3 = Remove from global catalog " + 
                "\n0 = Log out"
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
                    boolean checker = CATALOG.removeSongFromCatalog(tempResult); // Validates that a removal actually occurred.
                    if (checker) {
                        System.out.println(tempResult.getTitle() + " has been removed from the catalog.");
                    } // If nothing got removed, we print nothing to the user.
                    
                    boolean removePlaylistSuccess = removeSongFromPlaylists(tempResult); // Validates that the song actually existed on some playlist.
                    if (!removePlaylistSuccess){
                        System.out.println("Song did not exist in any playlists.");
                    } // If the song did not exist anywhere, nothing is printed to the user.
                    break;

                case "0":
                    System.out.println("Logging out...");
                    running = false;
                    break;

                default:
                    System.out.print("Unknown option.");
            }
        }
    }

    /**
     * Interactivity loop for Admin-type Users.
     * 
     * Allows for listing users and querying their account details
     * Allows for adding or removing songs from the global catalog for ANY artist name.
     * Allows for adding or removing Users from the application.
     *
     * @param scanner the scanner for user input
     * @param admin   an authenticated Admin-type user
     * @return void
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
                "\n0 = Logout"
            );
            
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    if (USERS.size() <= 0) {
                        System.out.println("No users."); // This should never be triggered.
                    } else {
                        listUsers();
                    }
                    break; 
                
                case "2":
                    if (USERS.size() <= 0) {
                        System.out.println("No users."); // This should never be triggered.
                    } else {
                        listUsers();
                    }
                    System.out.print("Enter a user index: ");
                    int userIndex = Integer.parseInt(scanner.nextLine());
                    // Validate user input
                    if (userIndex < USERS.size() && userIndex >= 0) {
                        queryUser(USERS.get(userIndex), scanner); 
                        // Passes User object and Scanner forward since we may need to ask the user to select a playlist as well.
                    } else {
                        System.out.println("Invalid index.");
                    }
                    break;
                    
                case "3":
                    // Entered value for "artistName" will correlate to the account the song is tied to-- so if you add a song by "artist" and
                    // log in with the 'artist' and 'artistpass' credentials and check the local artist catalog, you will see the song.
                    System.out.print("Enter a song title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter the name of the song's artist: ");
                    String artistName = scanner.nextLine().trim();
                    System.out.print("Enter duration in seconds to add to catalog: ");
                    int time = Integer.parseInt(scanner.nextLine());
                    Song s = new Song(title, artistName, time);
                    boolean addSuccess = CATALOG.addSongToCatalog(s);
                    // Print off information based on outcome.
                    // SearchService.addSongToCatalog() returns false if 
                    if (addSuccess) {
                        System.out.println("Added: " + s.toString());
                    } else {
                        System.out.println("Song already exists in catalog.");
                    }
                    break;
                    
                case "4":
                    if (CATALOG.getGlobalCatalog().size() <= 0) {
                        System.out.println("No songs exist on the catalog.");
                    } else {
                        CATALOG.listSongs();
                    }
                    System.out.print("Enter a song index: ");
                    int removeSongIndex = Integer.parseInt(scanner.nextLine());
                    // Validate user input
                    if (removeSongIndex < CATALOG.getGlobalCatalog().size() && removeSongIndex >= 0) {
                        Song removeSong = CATALOG.getSongAtIndex(removeSongIndex);
                        boolean removeCatalogSuccess = CATALOG.removeSongFromCatalog(removeSong);
                        boolean removePlaylistSuccess = removeSongFromPlaylists(removeSong);
                        // If song was successfully removed, print information about it
                        if (removeCatalogSuccess) {
                            System.out.println("Removed: " + removeSong.toString());
                        } else {
                            System.out.println("Song does not exist on the catalog.");
                        }
                        if (!removePlaylistSuccess) {
                            System.out.println("Song did not exist in any playlists.");
                        } 
                    } else {
                        System.out.println("Invalid index.");
                    }
                    break;
                    
                case "5": 
                    System.out.println("What kind of user would you like to create? \n Enter 'ADM' for Admin, 'LIS' for Listener, or 'ART' for Artist: ");
                    String entry = scanner.nextLine().trim();
                    
                    if (!entry.equalsIgnoreCase("ADM") && !entry.equalsIgnoreCase("LIS") && !entry.equalsIgnoreCase("ART")) {
                        System.out.println("Invalid entry please try again.");
                        break; 
                    }
                    
                    System.out.println("Please enter an email for the new user: ");
                    String enteredEmail = scanner.nextLine().trim();
                        
                    System.out.println("Please enter a username for the new user: ");
                    String enteredUser = scanner.nextLine().trim();
                        
                    System.out.println("Please enter a password for the new user: ");
                    String enteredPass = scanner.nextLine().trim();
                    
                    if (entry.equalsIgnoreCase("ADM")) {
                                          
                        for (User u : USERS) {
                            if (u.getEmail().equalsIgnoreCase(enteredEmail)) {
                                System.out.println("❌ Error: An account with this email already exists.");
                                break;
                            }
                            if (u.getUsername().equalsIgnoreCase(enteredUser)) {
                                System.out.println("❌ Error: Username already taken, please choose another.");
                                break;
                            }
                        }
                        
                        User newUser = null; 
                        
                        newUser = new Admin(enteredEmail, enteredUser, enteredPass, USERS.size() + 1);
                        USERS.add(newUser);
                        System.out.println("Admin account created successfully!");
                        System.out.print("This account has been created: " + USERS.get(USERS.size() - 1).toString());
                        
                            
                    } else if (entry.equalsIgnoreCase("LIS")) {
                                                               
                        for (User u : USERS) {
                            if (u.getEmail().equalsIgnoreCase(enteredEmail)) {
                                System.out.println("❌ Error: An account with this email already exists.");
                                break;
                            }
                            if (u.getUsername().equalsIgnoreCase(enteredUser)) {
                                System.out.println("❌ Error: Username already taken, please choose another.");
                                break;
                            }
                        }
                        
                        User newUser = null;
                        
                        newUser = new Listener(enteredEmail, enteredUser, enteredPass, USERS.size() + 1, new ArrayList<Playlist>());
                        USERS.add(newUser);
                        System.out.println("Listener account created successfully!");
                        System.out.print("This account has been created: " + USERS.get(USERS.size() - 1).toString());
                        
                        
                        
                    } else if (entry.equalsIgnoreCase("ART")) {
                                        
                        for (User u : USERS) {
                            if (u.getEmail().equalsIgnoreCase(enteredEmail)) {
                                System.out.println("❌ Error: An account with this email already exists.");
                                break;
                            }
                            if (u.getUsername().equalsIgnoreCase(enteredUser)) {
                                System.out.println("❌ Error: Username already taken, please choose another.");
                                break;
                            }
                        }
                        
                        User newUser = null;
                        
                        newUser = new Artist(enteredEmail, enteredUser, enteredPass, USERS.size() + 1);
                        USERS.add(newUser);
                        System.out.println("Artist account created successfully!");
                        System.out.print("This account has been created: " + USERS.get(USERS.size() - 1).toString());
                    
                    } else {
                        System.out.println("Invalid entry, please try again. ");
                    }
                    break;
                    
                case "6": 
                    listUsers();
                    System.out.print("Enter a user index: ");
                    int value = Integer.parseInt(scanner.nextLine());
                    System.out.println("The arraylist USERS.size() is : " + USERS.size());
                    if( value >= USERS.size() || value < 0 ) {
                        System.out.println("Invalid entry, value out of arraylist bounds."); 
                        break;
                    } else {
                    USERS.remove(value);
                    }
                    break; 
                    
                case "0":
                    System.out.println("Logging out...");
                    running = false;
                    break;

                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    /**
     * Called from the initial loop's Create Account option.
     *
     * Asks the user for their email, username, password, and type (Listener or Artist)
     * Creates their account details and stores them in USERS.
     *
     * @param scanner the scanner for user input
     * @return the authenticated User, or null when account creation fails.
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

        User newUser = null; // Initialize a value for the new User

        if ("A".equals(type)) {
            newUser = new Artist(email, username, password, USERS.size() + 1);
            USERS.add(newUser);
            System.out.println("Artist account created successfully!");
        } else if ("L".equals(type)) {
            newUser = new Listener(email, username, password, USERS.size() + 1, new ArrayList<Playlist>());
            USERS.add(newUser);
            System.out.println("Listener account created successfully!");
        } else {
            System.out.println("❌ Invalid type, account creation rejected.");
        }

        return newUser;
    }
    
    /**
     * 
     * 
     * 
     */
    
    private static void adminCreateUser() {
        
    }
    
    /**
     * Prints the contents of the USERS ArrayList to the user.
     * Called from Admin user Choice #1
     * 
     * @return void
     */
    private static void listUsers() {
        System.out.println("=== ALL USERS ===");
        for (int i = 0; i < USERS.size(); i++) {
            System.out.println("[" + i + "] - " + USERS.get(i).toString());
        }
    }
    
    /**
     * Determines the type of user and runs the corresponding adminQuery() function, 
     * which prints account details in a format specific to the Admin user role
     * Called from Admin user Choice #2
     * 
     * @param queryUser  the User we are querying data for from the Admin perspective
     * @param scanner Keep the Scanner object consistent so we can continue taking in user input without interuption.
     * @return void
     */
    private static void queryUser(User queryUser, Scanner scanner) {
        // Check the type of user
        if (queryUser instanceof Listener) {
            Listener queryListener = (Listener) queryUser; // Cast to new listener-type object
            queryListener.adminQuery(); // Run listener.adminQuery() to print general data
            
            // We need only proceed if this Listener has playlists
            if (queryListener.getLibrary().size() > 0) { 
                System.out.print("Enter a playlist index: ");
                int viewPlaylistIndex = Integer.parseInt(scanner.nextLine());
                // If-else validates the user input
                if (viewPlaylistIndex < queryListener.getLibrary().size() && viewPlaylistIndex >= 0) {
                    Playlist viewPlaylist = queryListener.getPlaylistAtIndex(viewPlaylistIndex);
                    // Only run Playlist.listSongs() if the tracklist actually has Songs to list.
                    if (viewPlaylist.getTracklist().size() > 0) {
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

    /**
     * Iterates through all listener's libraries' and playlists' and removes a specific song.
     * 
     * @param removeSong the song we are searching for and removing
     * @return boolean true if removeSong actually existed on any playlists; false otherwise
     */
    private static boolean removeSongFromPlaylists(Song removeSong) {
        boolean success = false;
        for (User u : USERS) {
            if (u instanceof Listener) {
                Listener l = (Listener) u;
                ArrayList<Playlist> library = l.getLibrary();
                for (Playlist p : library) {
                    ArrayList<Song> tracklist = p.getTracklist();
                    for (Song s : tracklist) {
                        if (tracklist.contains(removeSong)) {
                            success = true;
                        }
                    }
                    // Only try to remove the song if a match was found
                    // This avoids a ConcurrentModificationException that
                    // you reach when trying to modify the playlist during iteration
                    if (success) {
                        p.removeSong(removeSong);
                    }
                }
            }
        }
        return success;
    }
}
