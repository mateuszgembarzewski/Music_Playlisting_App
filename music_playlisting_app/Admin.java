import java.util.Iterator;
import java.util.List;

/**
 * Administrator user with global management capabilities.
 */
public class Admin extends User {
    private int adminId;

    public Admin(String email, String username, String password, int adminId) {
        super(email, username, password, adminId);
        this.adminId = adminId;
    }

    public int getAdminId() { return adminId; }

    /**
     * Delete a song from the global catalog.
     * @return true if removed, false if not found
     */
    public boolean deleteSongFromCatalog(List<Song> catalog, Song songToRemove) {
        return catalog.remove(songToRemove);
    }

    /**
     * Remove a song from all playlists provided.
     * @return number of removals performed
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
     * Delete all playlists for a given listener.
     */
    public void deleteAllPlaylistsForListener(Listener listener) {
        listener.clearPlaylists();
    }
}
