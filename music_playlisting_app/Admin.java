import java.util.Iterator;
import java.util.List;

/**
 * Represents an administrator user with elevated privileges in the system.
 * <p>
 * An {@code Admin} can perform global management operations, including:
 * </p>
 * <ul>
 *   <li>Managing songs in the global catalog</li>
 *   <li>Removing songs from all user playlists</li>
 *   <li>Deleting all playlists belonging to a specific listener</li>
 * </ul>
 *
 * @see User
 * @see Listener
 * @see Playlist
 * @see Song
 */
public class Admin extends User {

    /** The unique identifier assigned to this administrator. */
    private int adminId;

    /**
     * Constructs a new {@code Admin}.
     *
     * @param email    the email address of the admin
     * @param username the username of the admin
     * @param password the password of the admin
     * @param adminId  the unique ID of the admin
     */
    public Admin(String email, String username, String password, int adminId) {
        super(email, username, password, adminId);
        this.adminId = adminId;
    }

    /**
     * Returns the unique ID of this administrator.
     *
     * @return the administrator’s unique ID
     */
    public int getAdminId() {
        return adminId;
    }

    /**
     * Removes a specific song from the global catalog if it exists.
     *
     * @param catalog      the global catalog of songs
     * @param songToRemove the {@link Song} to remove from the catalog
     * @return {@code true} if the song was successfully removed;
     *         {@code false} if the song was not found
     */
    public boolean deleteSongFromCatalog(List<Song> catalog, Song songToRemove) {
        return catalog.remove(songToRemove);
    }

    /**
     * Removes all occurrences of a given song from every playlist in the system.
     *
     * @param playlists     the list of {@link Playlist} objects to scan
     * @param songToRemove  the {@link Song} to remove from all playlists
     * @return the total number of removals performed across all playlists
     */
    public int removeSongFromAllPlaylists(List<Playlist> playlists, Song songToRemove) {
        int removedCount = 0;
        for (Playlist p : playlists) {
            while (p.removeSong(songToRemove)) {
                removedCount++;
            }
        }
        return removedCount;
    }

    /**
     * Deletes all playlists belonging to a specified listener.
     *
     * @param listener the {@link Listener} whose playlists will be cleared
     */
    public void deleteAllPlaylistsForListener(Listener listener) {
        listener.clearPlaylists();
    }
}
