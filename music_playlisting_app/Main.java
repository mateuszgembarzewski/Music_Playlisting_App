import java.util.ArrayList;
import java.util.Scanner;

public class Main { // Replace 'Main' with your chosen class name
     public static final ArrayList<User> USERS = new ArrayList<User>();
     private static ArrayList<Song> catalog = new ArrayList<Song>(); 
     public static void main(String[] args) {
             
        Scanner scanner = new Scanner(System.in);
        Listener listener0 = new Listener("testuser@gmail.com","testuser","password",1, new ArrayList<Playlist>());
        USERS.add(listener0);
        
        System.out.println("Welcome to the HMM Music Playlisting Application!");
        
        int value = 1;
        String enteredUsername = "";
        String enteredPassword = "";
        String enteredEmail = "";
        while(value != 0) {
            System.out.println("Enter 1 to Create An Account or Press 2 to Login (Press 3 to skip) (Press 0 to exit) ");
            String input = scanner.nextLine();
            value = Integer.parseInt(input); 
            System.out.println("You selected option: " + input);
            
            if (value == 1) {
                System.out.println("To create an account, please enter your desired username: ");
                enteredUsername = scanner.nextLine();
                System.out.println("You selected the username: " + enteredUsername);
                System.out.println("Now please enter your desired password: ");
                enteredPassword = scanner.nextLine();
                System.out.println("Now please enter your desired email: ");
                enteredEmail = scanner.nextLine();
                System.out.println("Are you a listener or are you an artist? (A/L)");
                String enteredType = scanner.nextLine();
                
                if( enteredType.equals("A"))  {
                    
                    Artist newArtist = new Artist(enteredUsername,enteredPassword,enteredEmail);
                    
                    USERS.add(newArtist);
                    
                } else if ( enteredType.equals("L")) {
                    
                    Listener listener1 = new Listener(enteredEmail,enteredUsername,enteredPassword,1, new ArrayList<Playlist>());
                                                        
                    USERS.add(listener1);
                                                                         
                } else {
                    
                    System.out.println("Invalid user type entry. Only L or A is acceptable.");
                }
                
            } else if (value == 2) {
                System.out.println("Enter Username: ");
                enteredUsername = scanner.nextLine();
                System.out.println("Enter Password: ");
                enteredPassword = scanner.nextLine();
                checkIfUsernameExists(enteredUsername, enteredPassword);
                
            } else {
                System.out.println("Neither value 1 or 2 was entered... skipping this step.");
            }
        }
        Listener listener1 = new Listener(enteredEmail,enteredUsername,enteredPassword,1, new ArrayList<Playlist>());
        System.out.println(USERS.get(0));
        
        //System.out.println(USERS.get(1));
        
        Song song1 = new Song("SongName", "ArtistCreator", 300);
        Song song2 = new Song("SongName2", "ArtistCreator", 400);
        Artist artist2 = new Artist("artist@gmail.com","Artie","Pastel4u?");
        artist2.addSong(catalog, "Bing Bong", 69);
        artist2.addSong(catalog, "Whoa", 30);
        artist2.addSong(catalog, "Whoa", 30);
        searchCatalog();
        //Song song2 = new Song("IllegalSong", "ArtistCreator", 601);
        
        System.out.println("Song1's duration in seconds is: " + song1.getDurationInSecs());
        
        //System.out.println("Testing to see if song2's length limit works: " + song1.getDurationInSecs());

        Admin Admin1 = new Admin("user@gmail.com","Bob","Qwerty123", 1); 
        
        listener1.createNewPlaylist("Test Playlist 1");
        listener1.createNewPlaylist("Test Playlist 2");
        listener1.listPlaylists();
        searchCatalog();        
        Playlist playlist = listener1.getPlaylist(0);
        playlist.addSong(song1);
        playlist.addSong(song1);
        playlist.addSong(song2);
        listener1.listPlaylists();
        playlist.getPlaylistLength();
        
        Artist artist1 = new Artist("artist@gmail.com","Artie","Pastel4u?");
     }
     
     private static boolean authenticate(String username, String password, User user) {         
         if ( user.getUsername().equals(username) && user.getPassword().equals(password) ) {
            return true;
         } else {
            return false;             
         }
     }
     
     public static boolean checkIfUsernameExists(String enteredUsername, String enteredPassword) {
        for (int i = 0; i < USERS.size(); i++) {
            if( USERS.get(i).getUsername().equals(enteredUsername)) {
                System.out.println("Username found.");
                return true;
            } else {
                System.out.println("Username not found. Try again.");
            }
        }
        return false;
     }
     
     public static void searchCatalog() {
        System.out.println("==== CATALOG BEGIN ====");
        for (int i = 0; i < catalog.size(); i++) {
            System.out.println("[" + i + "] - " + catalog.get(i).toString());
        }
        System.out.println("==== CATALOG END ====");
     }
}