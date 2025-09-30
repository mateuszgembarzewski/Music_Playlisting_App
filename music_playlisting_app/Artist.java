import java.util.*;

/**
 * Represents an artist user in the system.
 * An artist can add songs to the global catalog.
 */
public class Artist extends User {
    //private List<Song> catalog;  // Artists aren't supposed to maintain a personal catalog.
    // I know our documentation is vague/slightly inconsistent on this--but I think this approach makes the most sense.  -Michael

    /**
     * Constructs a new Artist.
     *
     * @param email     the email address of the artist
     * @param username  the username of the artist
     * @param password  the password of the artist
     * @param id        the unique ID of the artist
     */
    public Artist(String email, String username, String password, int id) {
        super(email, username, password, id);
        //this.catalog = new ArrayList<>(); // See comment @ line 8+9
    }

    /**
     * Adds a song to the artist's catalog after validating it.
     * Validation rules:
     * - The song must not be null.
     * - Title must not be null or empty.
     * - Creator must not be null or empty.
     * - Duration must be positive.
     * - The catalog must not already contain the song.
     *
     * @param the SearchService object from Main (so that the whole catalog remains searchable)
     * ^ see comment @ line 8+9
     * @param song the song to add
     * @return true if the song was successfully added, false otherwise
     */
    public boolean addSongToCatalog(SearchService catalog, Song song) {
        if (song == null) return false;
    
        // Validation: title and artist name cannot be empty, duration must be positive
        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) return false;
        if (song.getCreator() == null || song.getCreator().trim().isEmpty()) return false;
        if (song.getDuration() <= 0) return false;
    
        if (catalog.globalCatContains(song)) return false; // Prevent duplicates
        catalog.addSongToCatalog(song);
        return true;
    }

    /**
     * @return the list of songs in the artist's catalog
     */
    public ArrayList<Song> getCatalog(SearchService catalog) {
        ArrayList<Song> localCatalog = new ArrayList<Song>();
        ArrayList<Song> globalCatalog = catalog.getGlobalCatalog();
        for (Song s : globalCatalog) {
            if (s.getCreator().equals(this.getUsername())) {
                localCatalog.add(s);
                System.out.println(s);
            }
        }
        return localCatalog;
    }
}
