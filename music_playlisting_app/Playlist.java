import java.util.ArrayList;
/**
 * Playlist - a collection of Song objects representing a playlist of individual songs.
 * Playlists are created by listener-type users and consist of:
 *  - name (String): Name of playlist
 *  - creator (String): Username of playlist creator
 *  - tracklist (Song[]): Array of Song objects added by the user.
 *
 * @author (Michael Scandiffio)
 * @version (09-24-2025)
 */
public class Playlist
{
    private String name; // Custom, user-submitted name
    private String creator; // Automatically filled in with listener's username
    private ArrayList<Song> tracklist; // Collection of songs, empty by default, user-submitted.

    /**
     * Constructor for objects of class Playlist
     */
    public Playlist(String name, String creator, ArrayList<Song> tracklist)
    {
        this.name = name;
        this.creator = creator;
        this.tracklist = tracklist;
    }

    public String getPlaylistName() {
        return name;
    }
    
    public String getPlaylistCreator() {
        return creator;
    }
    
    public void getPlaylistLength() {
        int duration = 0;
        for (int i = 0; i < tracklist.size(); i++) {
            duration += tracklist.get(i).getDurationInSecs();
        }
        int minutes = duration / 60;
        int seconds = duration % 60;
        System.out.println(String.format("%02d:%02d", minutes, seconds));
    }
    
    public String toString() {
        return name + " created by  " + creator + " - " + tracklist.size() + " songs";
    }
    
    public void addSong(Song song) {
        tracklist.add(song);
    }
}