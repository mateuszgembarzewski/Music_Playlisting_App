import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Playlist - a collection of Song objects
 */
public class Playlist {
    private String name;
    private String creator; // username of the creator
    private List<Song> tracklist;

    public Playlist(String name, String creator, ArrayList<Song> tracklist) {
        this.name = (name == null) ? "Untitled" : name;
        this.creator = (creator == null) ? "unknown" : creator;
        this.tracklist = (tracklist != null) ? tracklist : new ArrayList<>();
    }

    public String getPlaylistName() { return name; }
    public String getPlaylistCreator() { return creator; }
    public List<Song> getTracklist() { return tracklist; }

    /** Returns total duration in seconds */
    public int getTotalDurationInSeconds() {
        int duration = 0;
        for (Song s : tracklist) {
            duration += s.getDurationInSecs();
        }
        return duration;
    }

    /** Returns formatted mm:ss of playlist length */
    public String getTotalDurationFormatted() {
        int duration = getTotalDurationInSeconds();
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /** Adds a song if not duplicate (title + creator). Returns true if added. */
    public boolean addSong(Song song) {
        if (song == null) return false;
        for (Song s : tracklist) {
            if (s.equals(song)) return false; // duplicate
        }
        tracklist.add(song);
        return true;
    }

    /** Remove a song if present. Returns true if removed. */
    public boolean removeSong(Song song) {
        Iterator<Song> it = tracklist.iterator();
        while (it.hasNext()) {
            Song s = it.next();
            if (s.equals(song)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name + " created by " + creator + " - " + tracklist.size() + " songs";
    }
}
