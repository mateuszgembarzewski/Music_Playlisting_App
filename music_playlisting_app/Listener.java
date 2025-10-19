import java.util.*;

/**
 * Represents a listener user in the system.
 * <p>
 * A listener can manage playlists and maintain a personal library of songs.
 * </p>
 */
public class Listener extends User {
    /** The list of playlists owned by the listener. */
    private ArrayList<Playlist> playlists;

    /** The listener's personal library of songs. */
    private ArrayList<Song> personalLibrary;

    /**
     * Constructs a new {@code Listener}.
     *
     * @param email    the email address of the listener
     * @param username the username of the listener
     * @param password the password of the listener
     * @param id       the unique ID of the listener
     * @param library  the initial personal song library (can be empty but not {@code null})
     */
    public Listener(String email, String username, String password, int id, ArrayList<Song> library) {
        super(email, username, password, id);
        this.personalLibrary = library;
        this.playlists = new ArrayList<>();
    }

    /**
     * Creates a new empty playlist with the given name.
     * The playlist is automatically attributed to this listener as the creator.
     *
     * @param playlistName the name of the new playlist
     * @return the newly created {@link Playlist}
     */
    public Playlist createNewPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName, this.getUsername(), new ArrayList<>());
        playlists.add(newPlaylist);  // Add the new playlist to the listener's list of playlists
        return newPlaylist;
    }

    /**
     * Removes all playlists owned by the listener.
     * Prints a confirmation message upon completion.
     */
    public void clearPlaylists() {
        playlists.clear();
        System.out.println("All of " + this.getUsername() + "'s playlists have been removed.");
    }

    /**
     * Adds a song to the listener's personal library if it is not {@code null}
     * and not already present.
     *
     * @param song the {@link Song} to add
     * @return {@code true} if the song was added; {@code false} if the song was
     *         {@code null} or already exists in the library
     */
    public boolean addSongToLibrary(Song song) {
        if (song == null || personalLibrary.contains(song)) return false;
        personalLibrary.add(song);
        return true;
    }

    /**
     * Retrieves a playlist by its index.
     *
     * @param index the index of the desired playlist
     * @return the {@link Playlist} at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Playlist getPlaylistAtIndex(int index) {
        return playlists.get(index);
    }

    /**
     * Deletes a playlist from the listener’s list by index.
     * Prints a message indicating success or failure.
     *
     * @param index the index of the playlist to delete
     */
    public void deletePlaylistAtIndex(int index) {
        if (playlists.size() <= 0) {
            System.out.print("There are no playlists to delete");
            return;
        } else if (index < playlists.size() && index >= 0) {
            Playlist deleted = playlists.get(index);
            playlists.remove(index);
            System.out.println("The playlist '" + deleted.getName() + "' has been deleted from " + this.getUsername() + "'s library.");
        } else {
            System.out.println("Invalid Index Number");
        }
    }

    /**
     * Returns all playlists owned by the listener.
     *
     * @return an {@link ArrayList} of {@link Playlist} objects
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Prints all playlists owned by the listener with their corresponding indexes.
     */
    public void listPlaylists() {
        int i = 0;
        // Checks the amount of playlists before trying to print data on them.
        if (playlists.size() > 0) {
            System.out.println("=== " + this.getUsername() + "'s Playlists ===");
            for (Playlist p : playlists) {
                System.out.println("[" + i + "] - " + p.toString());
                i++;
            }
        } else {
            System.out.println(this.getUsername() + " does not have any playlists.");
        }
        
    }

    /**
     * Returns all songs in the listener’s personal library.
     *
     * @return an {@link ArrayList} of {@link Song} objects in the personal library
     */
    public ArrayList<Song> getPersonalLibrary() {
        return personalLibrary;
    }
    
    /**
     * Returns a short description of the Listener.
     *
     * @return a formatted summary string for this user.
     */
    @Override
    public String toString() {
        return "ID#" + super.getId() + " - '" + this.getUsername() + "' - " + this.getEmail() + " - LISTENER";
    }
    
    /**
     * Called on an Listener object when an Admin type user queries the account.
     */
    public void adminQuery() {
        System.out.println("=== " + this.getUsername() + "'s Data ===");
        System.out.println(toString());
        listPlaylists();
    }
}
