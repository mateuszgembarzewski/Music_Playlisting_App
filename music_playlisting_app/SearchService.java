import java.util.*;

/**
 * Service class that provides functionality to manage and search songs
 * within a local catalog.
 */
public class SearchService {
    private ArrayList<Song> songCatalog;

    /**
     * Constructs a new SearchService with an empty catalog.
     */
    public SearchService() {
        this.songCatalog = new ArrayList<Song>();
    }

    /**
     * Adds a song to the catalog if it does not already exist.
     * Duplicate songs (same title and creator) are not allowed.
     *
     * @param song the song to add
     * @return true if the song was successfully added, false if it already exists
     */
    public boolean addSongToCatalog(Song song) {
        // Avoid duplicate songs in the catalog
        for (Song s : songCatalog) {
            if (s.equals(song)) {
                return false; // Song already exists
            }
        }
        songCatalog.add(song);
        return true;
    }

    /**
     * Searches for songs by exact title (case-insensitive).
     *
     * @param title the title to search for
     * @return a list of songs matching the exact title
     */
    public ArrayList<Song> searchByTitle(String title) {
        ArrayList<Song> result = new ArrayList<>();
        for (Song song : songCatalog) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                result.add(song);
            }
        }
        return result;
    }

    /**
     * Searches for songs whose titles contain the given partial string,
     * case-insensitive.
     *
     * @param partialTitle the partial title to search for
     * @return a list of songs whose titles contain the partial string
     */
    public ArrayList<Song> searchByPartialTitle(String partialTitle) {
        ArrayList<Song> result = new ArrayList<>();
        for (Song song : songCatalog) {
            if (song.getTitle().toLowerCase().contains(partialTitle.toLowerCase())) {
                result.add(song);
            }
        }
        return result;
    }
    
    public boolean globalCatContains(Song song) {
        return songCatalog.contains(song);
    }
    
    public ArrayList<Song> getGlobalCatalog() {
        return songCatalog;
    }
    
    public void listSongs() {
        int i = 0;
        for (Song s : songCatalog) {
            System.out.println("[" + i + "] - " + s.toString());
            i++;
        }
    }
    
    public Song getSongAtIndex(int index) {
        return songCatalog.get(index);
    }
}
