import java.util.*;

/**
 * Provides a centralized service for managing and searching songs
 * within a local or global music catalog.
 * <p>
 * This service supports adding, removing, and searching songs
 * by title or artist. It prevents duplicates based on a song’s
 * title and creator combination.
 * </p>
 *
 * <p><strong>Key features include:</strong></p>
 * <ul>
 *   <li>Adding and removing songs safely from the catalog</li>
 *   <li>Exact and partial title searches</li>
 *   <li>Artist-based search functionality</li>
 *   <li>Catalog listing and retrieval by index</li>
 * </ul>
 *
 * @see Song
 */
public class SearchService {

    /** The list of all songs currently available in the catalog. */
    private ArrayList<Song> songCatalog;

    /**
     * Constructs a new {@code SearchService} instance with an empty catalog.
     */
    public SearchService() {
        this.songCatalog = new ArrayList<Song>();
    }

    /**
     * Adds a song to the catalog if it does not already exist.
     * <p>
     * A duplicate song is defined as one that matches both title and creator.
     * </p>
     *
     * @param song the {@link Song} to add
     * @return {@code true} if the song was successfully added;
     *         {@code false} if it already exists or is {@code null}
     */
    public boolean addSongToCatalog(Song song) {
        if (song == null) return false;
        for (Song s : songCatalog) {
            if (s.equals(song)) {
                return false; // Song already exists
            }
        }
        songCatalog.add(song);
        return true;
    }

    /**
     * Removes a song from the catalog if it exists.
     *
     * @param song the {@link Song} to remove
     * @return {@code true} if the song was found and removed;
     *         {@code false} otherwise
     */
    public boolean removeSongToCatalog(Song song) {
        int find = 0;
        for (Song s : songCatalog) {
            if (s.equals(song)) {
                find = 1;
                break;
            } else {
                find = 0;
            }
        }
        if (find == 1) {
            songCatalog.remove(song);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Searches for songs that exactly match the given title.
     * The search is case-insensitive.
     *
     * @param title the exact song title to search for
     * @return a list of {@link Song} objects that match the exact title;
     *         an empty list if no matches are found
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
     * Searches for songs whose titles contain the given partial string.
     * The search is case-insensitive.
     *
     * @param partialTitle the partial title to search for
     * @return a list of {@link Song} objects whose titles contain
     *         the partial search string; an empty list if no matches are found
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

    /**
     * Searches for songs by the exact artist name.
     *
     * @param partialArtist the name of the artist to search for
     * @return a list of {@link Song} objects created by the specified artist;
     *         an empty list if no songs are found
     */
    public ArrayList<Song> searchSongByArtist(String partialArtist) {
        ArrayList<Song> result = new ArrayList<>();
        for (Song song : songCatalog) {
            if (song.getCreator().equals(partialArtist)) {
                result.add(song);
            }
        }
        return result;
    }

    /**
     * Checks if the catalog already contains the given song.
     *
     * @param song the {@link Song} to check for
     * @return {@code true} if the catalog contains the song; {@code false} otherwise
     */
    public boolean globalCatContains(Song song) {
        return songCatalog.contains(song);
    }

    /**
     * Returns the entire catalog of songs managed by this service.
     *
     * @return the global {@link ArrayList} of songs
     */
    public ArrayList<Song> getGlobalCatalog() {
        return songCatalog;
    }

    /**
     * Prints all songs in the catalog to the console with index numbers.
     * <p>
     * Useful for debugging and manual verification of catalog contents.
     * </p>
     */
    public void listSongs() {
        int i = 0;
        for (Song s : songCatalog) {
            System.out.println("[" + i + "] - " + s.toString());
            i++;
        }
    }

    /**
     * Retrieves a song from the catalog by its index position.
     *
     * @param index the zero-based index of the song in the catalog
     * @return the {@link Song} at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Song getSongAtIndex(int index) {
        return songCatalog.get(index);
    }
}
