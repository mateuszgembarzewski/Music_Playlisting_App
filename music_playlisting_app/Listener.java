import java.util.*;

/**
 * Represents a listener user in the system.
 * A listener can manage playlists and maintain a personal library of songs.
 */
public class Listener extends User {
    private List<Playlist> playlists;
    private List<Song> personalLibrary; // Songs in the listener's personal library

    /**
     * Constructs a new Listener.
     *
     * @param email    the email address of the listener
     * @param username the username of the listener
     * @param password the password of the listener
     * @param id       the unique ID of the listener
     * @param library  the initial personal song library (can be empty but not null)
     */
    public Listener(String email, String username, String password, int id, List<Song> library) {
        super(email, username, password, id);
        this.personalLibrary = library;
        this.playlists = new ArrayList<>();
    }

    /**
     * Creates a new empty playlist with the given name.
     * The playlist is automatically attributed to the current listener as creator.
     *
     * @param playlistName the name of the new playlist
     * @return the newly created playlist
     */
    public Playlist createNewPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName, this.getUsername(), new ArrayList<>());
        playlists.add(newPlaylist);  // Add the new playlist to the listener's list of playlists
        return newPlaylist;
    }

    /**
     * Clears all playlists owned by the listener.
     */
    public void clearPlaylists() {
        playlists.clear();
    }

    /**
     * Adds a song to the listener's personal library if it is not null and not already present.
     *
     * @param song the song to add
     * @return true if the song was added, false if null or already in the library
     */
    public boolean addSongToLibrary(Song song) {
        if (song == null || personalLibrary.contains(song)) return false;
        personalLibrary.add(song);
        return true;
    }

    /**
     * @return the list of playlists owned by the listener
     */
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * @return the list of songs in the listener's personal library
     */
    public List<Song> getPersonalLibrary() {
        return personalLibrary;
    }
}
