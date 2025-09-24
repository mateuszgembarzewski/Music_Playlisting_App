
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
    private Song[] tracklist; // Collection of songs, empty by default, user-submitted.

    /**
     * Constructor for objects of class Playlist
     */
    public Playlist(String name, String creator, Song[] tracklist)
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
    
    public int getPlaylistLength() {
        int duration = 0;
        for (int i = 0; i < tracklist.length; i++) {
            duration += tracklist[i].getDurationInSecs();
        }
        return duration;
    }
}