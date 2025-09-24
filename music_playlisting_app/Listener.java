import java.util.ArrayList;
/**
 * Defines the listener type of a User.  Extends User class.
 * listenerID (int) - ID number to identify the listener specifically.
 * library (Playlist[]) - Array of Playlist objects
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Listener extends User
{
    public int listenerID;
    public ArrayList<Playlist> library; 
    /**
     * Constructor for objects of class Admin
     */
    public Listener(String email, String username, String password, int listenerID, ArrayList<Playlist> library)
    {
       super(email, username, password);
       this.listenerID = listenerID;
       this.library = library;
    }
    
    public int getListenerID() {
        return listenerID;
    }
    
    public void createNewPlaylist(String playlistName) {
        library.add(new Playlist(playlistName, this.username, new ArrayList<Song>()));
    }
    
    public void listPlaylists() {
        System.out.println("==== " + this.username + "'s PLAYLISTS BEGIN ====");
        for (int i = 0; i < library.size(); i++) {
            System.out.println("[" + i + "] - " + library.get(i).toString());
        }
        System.out.println("==== " + this.username + "'s PLAYLISTS END ====");
    }
    
    public Playlist getPlaylist(int index) {
        return library.get(index);
    }
}