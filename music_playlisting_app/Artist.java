import java.util.*;

/**
 * Represents an artist user in the system.
 * <p>
 * Artists can add songs to the global catalog (via {@link SearchService}) 
 * instead of maintaining their own personal catalog.
 * </p>
 */
public class Artist extends User {

    // private List<Song> catalog;  
    // Artists aren't supposed to maintain a personal catalog.
    // This is intentional to ensure songs remain globally accessible.  -Michael

    /**
     * Constructs a new {@code Artist} object.
     *
     * @param email     the email address of the artist
     * @param username  the username of the artist
     * @param password  the password of the artist
     * @param id        the unique ID of the artist
     */
    public Artist(String email, String username, String password, int id) {
        super(email, username, password, id);
        // this.catalog = new ArrayList<>(); // See comment above
    }

    /**
     * Adds a validated song to the global catalog using the provided {@link SearchService}.
     * <p>
     * Validation rules:
     * <ul>
     *   <li>The song must not be {@code null}.</li>
     *   <li>The title must not be {@code null} or empty.</li>
     *   <li>The creator name must not be {@code null} or empty.</li>
     *   <li>The duration must be greater than zero.</li>
     *   <li>The song must not already exist in the global catalog.</li>
     * </ul>
     * </p>
     *
     * @param catalog the {@link SearchService} instance managing the global catalog
     * @param song    the {@link Song} to add
     * @return {@code true} if the song was successfully added; {@code false} otherwise
     */
    public boolean addSongToCatalog(SearchService catalog, Song song) {
        if (song == null) return false;
    
        // Validation: title and artist name cannot be empty, duration must be positive
        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) return false;
        if (song.getCreator() == null || song.getCreator().trim().isEmpty()) return false;
        if (song.getDuration() <= 0) return false;
    
        // Prevent adding duplicate songs
        if (catalog.globalCatContains(song)) return false;
        
        catalog.addSongToCatalog(song);
        return true;
    }

    /**
     * Retrieves all songs in the global catalog created by this artist.
     * <p>
     * This method filters the global catalog to collect only songs whose
     * creator name matches this artist's username.
     * </p>
     *
     * @param catalog the {@link SearchService} containing the global catalog
     * @return an {@link ArrayList} of {@link Song} objects created by this artist
     */
    public ArrayList<Song> getCatalog(SearchService catalog) {
        ArrayList<Song> localCatalog = new ArrayList<>();
        ArrayList<Song> globalCatalog = catalog.getGlobalCatalog();
        boolean artistHasSongs = false;
        // Runs to first check that the artist has any songs at all on the catalog.
        // If the artist has NO songs, the boolean remains false and nothing happens.
        for (Song s : globalCatalog) {
            if (s.getCreator().equals(this.getUsername())) {
                artistHasSongs = true;
                break;
            }
        }
        // We only try printing data on an Artist's songs if they have any.
        if (artistHasSongs) {
            System.out.println("=== " + this.getUsername() + "'s CATALOG ===");
            for (Song s : globalCatalog) {
                if (s.getCreator().equals(this.getUsername())) {
                    localCatalog.add(s);
                    System.out.println(s);
                }
            }
        } else {
            System.out.println(this.getUsername() + " does not have any songs.");
        }
        
        return localCatalog;
    }
    
    /**
     * Returns a short description of the Artist.
     *
     * @return a formatted summary string for this user.
     */
    @Override
    public String toString() {
        return "ID#" + super.getId() + " - '" + this.getUsername() + "' - " + this.getEmail() + " - ARTIST";
    }
    
    /**
     * Called on an Artist object when an Admin type user queries the account.
     */
    public void adminQuery() {
        System.out.println("=== " + this.getUsername() + "'s Data ===");
        System.out.println(toString());
    }
}
