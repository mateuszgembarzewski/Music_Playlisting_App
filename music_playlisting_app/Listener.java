import java.util.*;

/**
 * Class representing a Listener type of User.  Extends User class.
 * 
 * Listeners store an ArrayList of Playlists representing their library.
 * Methods here serve to modify that library's content.
 */
public class Listener extends User {
    // Represents the library as a collection of Playlists
    private ArrayList<Playlist> library;

    /**
     * Constructor for Listener-type Users.
     *
     * @param email    email address for the listener
     * @param username username for the listener
     * @param password password for the listener
     * @param id       unique identifier for listener
     * @param library  library (collection of Playlists) for the listener
     */
    public Listener(String email, String username, String password, int id, ArrayList<Playlist> library) {
        super(email, username, password, id); // Inherits from User class
        this.library = new ArrayList<Playlist>();
    }

    /**
     * Creates a new playlist and adds it to the Listener's library.
     *
     * @param playlistName the new playlist's name
     * @return Playlist object of the new playlist.  Any string is valid.
     */
    public Playlist createNewPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName, this.getUsername(), new ArrayList<Song>());
        library.add(newPlaylist);  // Add the new playlist to the listener's list of playlists
        return newPlaylist;
    }

    /**
     * Clears the Listener's library of all it's playlists.
     * 
     * @return void
     */
    public void clearLibrary() {
        library.clear();
        System.out.println("All of " + this.getUsername() + "'s playlists have been removed.");
    }

    /**
     * Getter for a specific playlist from the Listener's library based on an index.
     * Before this method is run the user is printed a list of their playlists with
     * the corresponding indexes provided.
     *
     * @param index an integer provided by the user representing their desired playlist.  The index is validated before being passed to this method.
     * @return Playlist object of the desired playlist
     */
    public Playlist getPlaylistAtIndex(int index) {
        return library.get(index);
    }

    /**
     * Deletes a specific playlist from the Listener's library based on an index.
     *
     * @param index an integer representing the desired playlist.  The index is validated inside the method.
     * @return void
     */
    public void deletePlaylistAtIndex(int index) {
        // Validates the user input
        if (library.size() <= 0) {
            System.out.print("There are no playlists to delete");
            return;
        } else if (index < library.size() && index >= 0) { // We directly use Java's count-from-0 indexes here, so we need to allow values 0 and above so long as they are within the range of the Library's indexes.
            Playlist deleted = library.get(index);
            library.remove(index);
            System.out.println("The playlist '" + deleted.getName() + "' has been deleted from " + this.getUsername() + "'s library.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    /**
     * Getter for the Listener's 'library', or collection of Playlists 
     * 
     * @return ArrayList<Playlist> representing the Listener's library
     */
    public ArrayList<Playlist> getLibrary() {
        return library;
    }

    /**
     * Prints an indexed list of all playlists within the Listener's library.
     * Prints a notice if no playlists exist within the library.
     * 
     * @return void
     */
    public void listLibrary() {
        int i = 0;
        // Checks the amount of playlists before trying to print data on them.
        if (library.size() > 0) {
            System.out.println("=== " + this.getUsername() + "'s Playlists ===");
            for (Playlist p : library) {
                System.out.println("[" + i + "] - " + p.toString());
                i++;
            }
        } else {
            System.out.println(this.getUsername() + " does not have any playlists.");
        }
        
    }
    
    /**
     * Override for the toString() method; prints a description of the Listener
     *
     * @return String descriptor of a Listener object
     */
    @Override
    public String toString() {
        return "ID#" + super.getId() + " - '" + this.getUsername() + "' - " + this.getEmail() + " - LISTENER";
    }
    
    /**
     * Prints a specific output of data for the purposes of the Admin user type.
     * This is only called from the Admin's query functionality.
     * 
     * @return void
     */
    public void adminQuery() {
        System.out.println("=== " + this.getUsername() + "'s Data ===");
        System.out.println(toString());
        listLibrary();
    }
}
