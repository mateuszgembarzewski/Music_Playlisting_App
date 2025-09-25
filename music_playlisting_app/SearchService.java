import java.util.*;

public class SearchService {
    private List<Song> songCatalog;

    // Constructor to initialize the song catalog
    public SearchService() {
        this.songCatalog = new ArrayList<>();
    }

    // Method to add a song to the catalog
    public boolean addSongToCatalog(Song song) {
        // Avoid duplicate songs in the catalog
        for (Song s : songCatalog) {
            if (s.equals(song)) {
                return false; // Song already exists
            }
        }
        songCatalog.add(song);
        return true; // Song added successfully
    }

    // Method to search songs by exact title
    public List<Song> searchByTitle(String title) {
        List<Song> result = new ArrayList<>();
        for (Song song : songCatalog) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                result.add(song);
            }
        }
        return result;
    }

    // Method to search songs by partial title (case-insensitive)
    public List<Song> searchByPartialTitle(String partialTitle) {
        List<Song> result = new ArrayList<>();
        for (Song song : songCatalog) {
            if (song.getTitle().toLowerCase().contains(partialTitle.toLowerCase())) {
                result.add(song);
            }
        }
        return result;
    }
}
