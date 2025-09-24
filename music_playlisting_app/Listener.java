
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
    public Playlist[] library; 
    /**
     * Constructor for objects of class Admin
     */
    public Listener(String email, String username, String password, int listenerID, Playlist[] library)
    {
       super(email, username, password);
       this.listenerID = listenerID;
       this.library = library;
    }
    
    public int getListenerID() {
        return listenerID;
    }
}