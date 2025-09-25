import java.util.Iterator;
import java.util.List;

/**
 * Represents an administrator user with global management capabilities.
 * Admins can manage songs in the global catalog and modify listener playlists.
 */
public class Admin extends User {
    private int adminId;

    /**
     * Constructs a new Admin.
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
     * @return the unique ID of the admin
     */
    public int getAdminId() { return adminId; }

    /**
     * Removes a song from the global catalog if present.
     *
     * @param catalog       the global catalog of songs
     * @param songToRemove  the song to remove
     * @return true if the song was successfully removed, false otherwise
     */
    public boolean deleteSongFromCatalog(List<Song> catalog, Song songToRemove) {
        return catalog.remove(songToRemove);
    }

    /**
     * Removes all occurrences of a given song from the provided playlists.
     *
     * @param playlists     the list of playlists to scan
     * @param songToRemove  the song to remove
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
     * Deletes all playlists belonging to a given listener.
     *
     * @param listener the listener whose playlists will be cleared
     */
    public void deleteAllPlaylistsForListener(Listener listener) {
        listener.clearPlaylists();
    }
}
