import java.util.*;

/**
 * Provides a service and object representing the searchable catalog of songs on the system.
 * One SearchService instance should be created in main to manage the global catalog.
 */
public class SearchService {
    // Represents the global catalog as a collection of Songs
    private ArrayList<Song> songCatalog;

    /**
     * Constructor for a SearchService instance
     */
    public SearchService() {
        this.songCatalog = new ArrayList<Song>();
    }

    /**
     * Adds a Song object to the catalog provided it is not already present.
     * 
     * @param song Song we are adding to the catalog
     * @return boolean true if Song is added to catalog; false if catalog already contains Song
     */
    public boolean addSongToCatalog(Song song) {
        if (song == null) return false;
        // Refactored present code to use ArrayList.contains() method instead of a for-loop to find a match
        // This is cleaner--use this scheme if you need to be certain an object exists before doing an action on it.
        if (songCatalog.contains(song)) {
            return false;
        } else {
            songCatalog.add(song);
            return true;
        }
    }

    /**
     * Removes a specific song from the catalog wherever it may exist.
     * 
     * @param song Song we are removing from the catalog.
     * @return boolean true if song was present and removed; false otherwise.
     */
    public boolean removeSongFromCatalog(Song song) {
        // We only try to remove a song that we can prove exists
        // See comment in addSongToCatalog method above
        if (songCatalog.contains(song)) {
            songCatalog.remove(song);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Constructs an ArrayList of Songs where the 'title' matches the parameter (ignoring case)
     * Allows user to search the global catalog by a hard term.
     * 
     * @param title The string we are matching against the catalog's Song titles.
     * @return ArrayList<Song> containing search results.
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
     * Constructs an ArrayList of Songs where the 'title' contains the parameter.
     * Allows the user to search the global catalog by a soft term.
     * 
     * @param partialTitle The string we are checking for the presence of in the catalog's Song titles.
     * @return ArrayList<Song> containing search results.
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
     * Constructs an ArrayList of Songs where the 'creator' contains the parameter.
     * Allows the user to search the global catalog by an artist's name, softly.
     * 
     * @param partialArtist The string we are checking for the presence of in the catalog's Song's 'creator' attribute.
     * @return ArrayList<Song> containing search results.
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
     * Signifies if the catalog does or does not contain a specific Song.
     * 
     * @param song Song we are checking for.
     * @return boolean true if song is present; false otherwise.
     */
    public boolean globalCatContains(Song song) {
        return songCatalog.contains(song);
    }

    /**
     * Getter for the catalog's ArrayList of Songs.
     * 
     * @return ArrayList<Song> representing the entire catalog.
     */
    public ArrayList<Song> getGlobalCatalog() {
        return songCatalog;
    }

    /**
     * Prints the contents of the catalog to the user.
     * Songs are printed with an index as this function typically is to help the user choose a song.
     * 
     * @return void
     */
    public void listSongs() {
        int i = 0;
        for (Song s : songCatalog) {
            System.out.println("[" + i + "] - " + s.toString());
            i++;
        }
    }

    /**
     * Getter for a song at a specified index.
     * 
     * @param index the targetting index (Positive integer >=0; must be within range of the Catalog's size)
     * @return Song the song being targetted.
     */
    public Song getSongAtIndex(int index) {
        return songCatalog.get(index);
    }
}
