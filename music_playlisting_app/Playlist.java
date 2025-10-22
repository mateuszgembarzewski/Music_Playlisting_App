import java.util.*;

/**
 * Represents a collection of Songs with an attached 'name' and 'creator' (corresponds to some existing Listener's username)
 */
public class Playlist {
    // Represents the name of the playlist
    private String name;

    // Represents the creator of the playlist
    private String creator;

    // Represents the tracklisting of the playlist itself
    private ArrayList<Song> tracklist;

    /**
     * Constructor for Song objects
     *
     * @param name      name of the playlist
     * @param creator   Listener who created and owns the playlist
     * @param tracklist the tracklisting of the playlist; contains the Song content of the object.
     */
    public Playlist(String name, String creator, ArrayList<Song> tracklist) {
        this.name = name;
        this.creator = creator;
        this.tracklist = tracklist;
    }

    /**
     * Getter for the Playlist's 'name'
     * 
     * @return String storing the Playlist's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the Playlist's 'creator'
     * 
     * @return String storing the Playlist's creator 
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Getter for the Playlist's 'tracklist'
     *
     * @return ArrayList<Song> storing the Song content of the Playlist object.
     */
    public ArrayList<Song> getTracklist() {
        return tracklist;
    }

    /**
     * Adds a song to this Playlist provided it is not already present.
     *
     * @param song Song we are adding to the Playlist
     * @return boolean true if Song is added to Playlist; false if Playlist already contains Song
     */
    public boolean addSong(Song song) {
        if (song == null || tracklist.contains(song)) return false;
        tracklist.add(song);
        return true;
    }

    /**
     * Removes a song from this Playlist at a specified index.
     *
     * @param index the targetting index (Positive integer >=0; function rejects invalid values.
     * @return void
     */
    public void removeSongAtIndex(int index) {
        if (tracklist.isEmpty()) {
            System.out.print("There are no songs to delete.\n");
            return;
        } else if (index >= 0 && index < tracklist.size()) {
            tracklist.remove(index);
            return;
        } else {
            System.out.println("Invalid Index Number");
            return;
        }
    }

    /**
     * Removes a specific song from this Playlist wherever it may exist.
     * 
     * @param song Song we are removing from this Playlist.
     * @return boolean true if song was present and removed; false otherwise.
     */
    public boolean removeSong(Song song) {
        return tracklist.remove(song);
    }

    /**
     * Calculates the total duration of the Playlist
     * Then converts the duration integer value into a formatted string with colon separated values.
     *
     * @return String formatted time string
     */
    public String getTotalDurationFormatted() {
        int totalDuration = 0;
        for (Song s : tracklist) {
            totalDuration += s.getDuration();
        }
        int minutes = totalDuration / 60;
        int seconds = totalDuration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Prints the contents of the 'tracklist' to the user.
     * Songs are printed with an index as this function can be used to help users choose a song
     * 
     * @return void
     */
    public void listSongs() {
        System.out.println("=== " + this.getName() + "'s Songs ===");
        for (int i = 0; i < tracklist.size(); i++) {
            System.out.println("[" + i + "] - " + tracklist.get(i).toString());
        }
    }

    /**
     * Override of the toString() method; prints a description of the Playlist
     *
     * @return String descriptor of a Playlist object
     */
    @Override
    public String toString() {
        return "'" + name + "' created by " + creator + " - " + tracklist.size() + " songs - " + getTotalDurationFormatted();
    }
}
