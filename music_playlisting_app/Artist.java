import java.util.*;

/**
 * Represents an artist user in the system.
 * An artist can maintain a personal catalog of songs they have created.
 */
public class Artist extends User {
    private List<Song> catalog;

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
        this.catalog = new ArrayList<>();
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
     * @param song the song to add
     * @return true if the song was successfully added, false otherwise
     */
    public boolean addSongToCatalog(Song song) {
        if (song == null) return false;
    
        // Validation: title and artist name cannot be empty, duration must be positive
        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) return false;
        if (song.getCreator() == null || song.getCreator().trim().isEmpty()) return false;
        if (song.getDuration() <= 0) return false;
    
        if (catalog.contains(song)) return false; // Prevent duplicates
        catalog.add(song);
        return true;
    }

    /**
     * @return the list of songs in the artist's catalog
     */
    public List<Song> getCatalog() {
        return catalog;
    }
}
