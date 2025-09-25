import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;


public class Main { // Replace 'Main' with your chosen class name
     public static final ArrayList<User> USERS = new ArrayList<User>();
     private static ArrayList<Song> catalog = new ArrayList<Song>(); 
     public static void main(String[] args) {
             
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the HMM Music Playlisting Application!");
        
        System.out.println("Enter 1 to Create An Account or Press 2 to Login (Press 3 to skip) (Press 0 to exit) ");
        
        String input = scanner.nextLine();
        int value = Integer.parseInt(input); 
        System.out.println("You selected option: " + input);
        
        if (value == 1) {
            System.out.println("To create an account, please enter your desired username: ");
            String enteredUsername = scanner.nextLine();
            System.out.println("You selected the username: " + enteredUsername);
            System.out.println("Now please enter your desired password: ");
            String enteredPassword = scanner.nextLine();
            System.out.println("Now please enter your desired email: ");
            String enteredEmail = scanner.nextLine();
            System.out.println("Are you a listener or are you an artist? (A/L)");
            String enteredType = scanner.nextLine();
            
            if( enteredType.equals("A"))  {
                
                User newUser = new User(enteredUsername,enteredPassword,enteredEmail);
                
                USERS.add(artist1);
                
            } else if ( enteredType.equals("L")) {
                
                Listener listener1 = new Listener("listener@gmail.com","Matt","GoodPass1!",
                                                    1, new ArrayList<Playlist>());
            } else {
                
                System.out.println("Invalid entry");
            }
            
        } else if (value == 2) {
            
        } else {
            System.out.println("Neither value 1 or 2 was entered... skipping this step.");
        }
        
        System.out.println(USERS.get(0));
        
        Song song1 = new Song("SongName", "ArtistCreator", 300);
        catalog.add(song1);        
        //Song song2 = new Song("IllegalSong", "ArtistCreator", 601);
        
        System.out.println("Song1's duration in seconds is: " + song1.getDurationInSecs());
        
        //System.out.println("Testing to see if song2's length limit works: " + song1.getDurationInSecs());

        Admin Admin1 = new Admin("user@gmail.com","Bob","Qwerty123", 1); 
        Listener listener1 = new Listener("listener@gmail.com","Matt","GoodPass1!", 1, new ArrayList<Playlist>());
        
        listener1.createNewPlaylist("Test Playlist 1");
        listener1.createNewPlaylist("Test Playlist 2");
        listener1.listPlaylists();
        searchCatalog();        
        Playlist playlist = listener1.getPlaylist(0);
        playlist.addSong(song1);
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
     
     public static void searchCatalog() {
        System.out.println("==== CATALOG BEGIN ====");
        for (int i = 0; i < catalog.size(); i++) {
            System.out.println("[" + i + "] - " + catalog.get(i).toString());
        }
        System.out.println("==== CATALOG END ====");
     }
}