import java.util.*;

/**
 * Represents a playlist containing a collection of songs.
 * <p>
 * A playlist has a name, a creator, and a list of tracks.
 * It provides methods for adding, removing, and listing songs,
 * as well as computing the total duration of the playlist.
 * </p>
 */
public class Playlist {

    /** The name of the playlist. */
    private String name;

    /** The creator (owner) of the playlist. */
    private String creator;

    /** The list of songs contained in the playlist. */
    private ArrayList<Song> tracklist;

    /**
     * Constructs a new {@code Playlist}.
     *
     * @param name      the name of the playlist (defaults to "Untitled" if {@code null})
     * @param creator   the creator of the playlist (defaults to "unknown" if {@code null})
     * @param tracklist the initial list of songs (defaults to an empty list if {@code null})
     */
    public Playlist(String name, String creator, ArrayList<Song> tracklist) {
        this.name = (name == null) ? "Untitled" : name;
        this.creator = (creator == null) ? "unknown" : creator;
        this.tracklist = (tracklist != null) ? tracklist : new ArrayList<>();
    }

    /**
     * Returns the name of the playlist.
     *
     * @return the playlist name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the creator (owner) of the playlist.
     *
     * @return the playlist creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Returns the list of songs in the playlist.
     *
     * @return the tracklist
     */
    public ArrayList<Song> getTracklist() {
        return tracklist;
    }

    /**
     * Adds a song to the playlist if it is not {@code null} and not already present.
     *
     * @param song the song to add
     * @return {@code true} if the song was added, {@code false} if it was {@code null} or already present
     */
    public boolean addSong(Song song) {
        if (song == null || tracklist.contains(song)) return false;
        tracklist.add(song);
        return true;
    }

    /**
     * Removes the song at the specified index.
     * <p>
     * Prints a message if the playlist is empty or the index is invalid.
     * </p>
     *
     * @param index the position of the song to remove
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
     * Removes a specific song from the playlist.
     *
     * @param song the song to remove
     * @return {@code true} if the song was successfully removed, {@code false} otherwise
     */
    public boolean removeSong(Song song) {
        return tracklist.remove(song);
    }

    /**
     * Returns the total duration of all songs in the playlist, formatted as {@code MM:SS}.
     *
     * @return the total duration as a formatted string
     */
    public String getTotalDurationFormatted() {
        int totalDuration = tracklist.stream().mapToInt(Song::getDuration).sum();
        int minutes = totalDuration / 60;
        int seconds = totalDuration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Prints all songs in the playlist to the console.
     * Each song is displayed with its index and details.
     */
    public void listSongs() {
        System.out.println("=== " + this.getName() + "'s Songs ===");
        for (int i = 0; i < tracklist.size(); i++) {
            System.out.println("[" + i + "] - " + tracklist.get(i).toString());
        }
    }

    /**
     * Returns a short description of the playlist, including its name, creator,
     * and the number of songs it contains.
     *
     * @return a formatted summary string for this playlist
     */
    @Override
    public String toString() {
        return "'" + name + "' created by " + creator + " - " + tracklist.size() + " songs";
    }
}
