import java.util.*;

public class Playlist {
    private String name;
    private String creator;
    private List<Song> tracklist;

    public Playlist(String name, String creator, List<Song> tracklist) {
        this.name = (name == null) ? "Untitled" : name;
        this.creator = (creator == null) ? "unknown" : creator;
        this.tracklist = (tracklist != null) ? tracklist : new ArrayList<>();
    }

    public boolean addSong(Song song) {
        if (song == null || tracklist.contains(song)) return false;
        tracklist.add(song);
        return true;
    }

    public boolean removeSong(Song song) {
        return tracklist.remove(song);
    }

    public String getTotalDurationFormatted() {
        int totalDuration = tracklist.stream().mapToInt(Song::getDuration).sum();
        int minutes = totalDuration / 60;
        int seconds = totalDuration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return name + " created by " + creator + " - " + tracklist.size() + " songs";
    }
}
