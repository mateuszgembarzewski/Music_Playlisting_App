import java.util.*;

public class Listener extends User {
    private List<Playlist> playlists;  // Playlist list for the listener
    private List<Song> personalLibrary; // Songs in the listener's personal library

    public Listener(String email, String username, String password, int id) {
        super(email, username, password, id);
        this.playlists = new ArrayList<>();
        this.personalLibrary = new ArrayList<>();
    }

    // Method to create a new playlist
    public Playlist createNewPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName, this.getUsername(), new ArrayList<>());
        playlists.add(newPlaylist);  // Add the new playlist to the listener's list of playlists
        return newPlaylist;
    }

    // Method to clear all playlists for the listener
    public void clearPlaylists() {
        playlists.clear();  // Removes all playlists from the list
    }

    // Method to add a song to the personal library
    public boolean addSongToLibrary(Song song) {
        if (song == null || personalLibrary.contains(song)) return false;  // Check for null and duplicates
        personalLibrary.add(song);  // Add song to the personal library
        return true;  // Return true if the song was added successfully
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public List<Song> getPersonalLibrary() {
        return personalLibrary;
    }
}
