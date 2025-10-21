import java.util.*;

/**
 * Class representing an Artist type of User.  Extends User class.
 * 
 * Methods here serve to add songs to the Catalog and list the Artist's songs from the Catalog.
 */
public class Artist extends User {
    /**
     * Constructor for Artist-type Users.
     *
     * @param email     email address for the artist
     * @param username  username for the artist
     * @param password  password for the artist
     * @param id        unique identifier for the artist
     */
    public Artist(String email, String username, String password, int id) {
        super(email, username, password, id); // Inherits from User class
    }

    /**
     * Adds a Song to a Catalog, always the global Catalog.
     * 
     * Validates that the title and artist name are non-null and the duration is positive.
     *
     * @param catalog An instance of the SearchService that manages the global Catalog
     * @param song    The song to be added to the catalog
     * @return true if song was successfully added; false otherwise.
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
     * Prints the indexed list of all Songs uploaded by this Artist.
     *
     * @param catalog An instance of the SearchService that manages the global catalog.
     * @return ArrayList<Song> An ArrayList of all Songs uploaded by this Artist.
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
     * Override for the toString() method; prints a description of the Artist
     *
     * @return String descriptor of an Artist object
     */
    @Override
    public String toString() {
        return "ID#" + super.getId() + " - '" + this.getUsername() + "' - " + this.getEmail() + " - ARTIST";
    }
    
    /**
     * Prints a specific output of data for the purposes of the Admin user type.
     * This is only called from the Admin's query functionality.
     * 
     * @return void
     */
    public void adminQuery() {
        System.out.println("=== " + this.getUsername() + "'s Data ===");
        System.out.println(toString());
    }
}
