import java.util.*;

/**
 * Represents a playlist containing a collection of songs.
 * A playlist has a name, a creator, and a list of tracks.
 */
public class Playlist {
    private String name;
    private String creator;
    private List<Song> tracklist;

    /**
     * Constructs a new playlist.
     *
     * @param name     the name of the playlist (defaults to "Untitled" if null)
     * @param creator  the creator of the playlist (defaults to "unknown" if null)
     * @param tracklist initial list of songs (defaults to an empty list if null)
     */
    public Playlist(String name, String creator, List<Song> tracklist) {
        this.name = (name == null) ? "Untitled" : name;
        this.creator = (creator == null) ? "unknown" : creator;
        this.tracklist = (tracklist != null) ? tracklist : new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    /**
     * Adds a song to the playlist if it is not null and not already present.
     *
     * @param song the song to add
     * @return true if the song was added, false if it was null or already present
     */
    public boolean addSong(Song song) {
        if (song == null || tracklist.contains(song)) return false;
        tracklist.add(song);
        return true;
    }
    
    public void removeSongAtIndex(int index) {
        tracklist.remove(index);
    }

    /**
     * Removes a song from the playlist.
     *
     * @param song the song to remove
     * @return true if the song was successfully removed, false otherwise
     */
    public boolean removeSong(Song song) {
        return tracklist.remove(song);
    }

    /**
     * Calculates and returns the total duration of all songs in the playlist,
     * formatted as MM:SS.
     *
     * @return a formatted string representing the total duration of the playlist
     */
    public String getTotalDurationFormatted() {
        int totalDuration = tracklist.stream().mapToInt(Song::getDuration).sum();
        int minutes = totalDuration / 60;
        int seconds = totalDuration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    public void listSongs() {
        int i = 0;
        System.out.println("=== " + this.getName() + "'s Songs ===");
        for (Song s : tracklist) {
            System.out.println("[" + i + "] - " + s.toString());
            i++;
        }
    }

    /**
     * Returns a string summary of the playlist including its name, creator,
     * and the number of songs it contains.
     *
     * @return a string description of the playlist
     */
    @Override
    public String toString() {
        return "'" + name + "' created by " + creator + " - " + tracklist.size() + " songs";
    }
}
