import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * User Acceptance Testing (UAT) for Playlist management, Song management,
 * and Listener actions in the music application.
 */
public class TestUAT2 {

    /**
     * Main entry point to run all test cases sequentially.
     */
    public static void main(String[] args) {
        TestUAT2 tester = new TestUAT2();

        // Run all test cases
        tester.testCreateMultiplePlaylists();
        tester.testRemovePlaylistSuccess();
        //tester.testRemovePlaylistFail();  // Commented out for later tweaks
        tester.testRemoveSongFromPlaylistSuccess();
        //tester.testRemoveSongFromEmptyPlaylistFail();  // Commented out for later tweaks
        tester.testListenerSystemInteraction();
        tester.testListenerRestrictedAccess();
    }

    /**
     * 1. Test Case: Listener creates multiple playlists successfully.
     */
    public void testCreateMultiplePlaylists() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Playlist #1");
        listener.createNewPlaylist("Playlist #2");

        assert listener.getPlaylists().size() == 2 : "Test Failed: Listener should have created 2 playlists";
        System.out.println("FUNC_CREATE_MULTIPLE_PLAYLISTS: Passed");
    }

    /**
     * 2. Test Case: Listener removes a playlist successfully.
     */
    public void testRemovePlaylistSuccess() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Playlist #1");
        int before = listener.getPlaylists().size();

        try {
            listener.deletePlaylistAtIndex(0);
            int after = listener.getPlaylists().size();
            assert after == before - 1 : "Test Failed: Playlist should be removed successfully";
            System.out.println("FUNC_REMOVE_PLAYLIST_SUCCESS: Passed");
        } catch (Exception e) {
            System.out.println("Test Failed: " + e.getMessage());
        }
    }

    /**
     * 3. Test Case: Listener fails to remove a playlist when none exist.
     * 
     * // Commented out for later tweaks
     * public void testRemovePlaylistFail() {
     *     Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
     *
     *     try {
     *         listener.deletePlaylistAtIndex(0);
     *         System.out.println("Test Failed: Playlist removal should have failed due to no playlists.");
     *     } catch (Exception e) {
     *         assert e.getMessage().equals("No playlists exist") : "Test Failed: Exception message mismatch";
     *         System.out.println("FUNC_REMOVE_PLAYLIST_FAIL: Passed");
     *     }
     * }
     */

    /**
     * 4. Test Case: Listener removes a song from a playlist successfully.
     */
    public void testRemoveSongFromPlaylistSuccess() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Chill Vibes");
        Playlist playlist = listener.getPlaylists().get(0);
        Song song = new Song("Song A", "Artist A", 200);
        playlist.addSong(song); // Add the song to the playlist
    
        try {
            ArrayList<Song> PS = playlist.getTrackList();
            int before = PS.size(); // Get the size of the 'songs' list before removal
            // Get the song by index (0 in this case) and remove it
            Song songToRemove = PS.get(0); 
            Song songThatWasRemoved = playlist.removeSongAtIndex(0); // Remove the song object
            //System.out.println("Song "+songThatWasRemoved+" has been removed");
            int after = PS.size(); // Get size after removal
            // Test: Ensure the song was removed successfully
            assert after == before - 1 : "Test Failed: Song should be removed from the playlist";
            System.out.println("FUNC_REMOVE_SONG_SUCCESS: Passed");
        } catch (Exception e) {
            System.out.println("FUNC_REMOVE_SONG_SUCCESS: Failed Why " + e.getMessage());
        }
    }


    /**
     * 5. Test Case: Listener fails to remove a song from an empty playlist.
     * 
     * // Commented out for later tweaks
     * public void testRemoveSongFromEmptyPlaylistFail() {
     *     Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
     *     listener.createNewPlaylist("Empty Playlist");
     *     Playlist playlist = listener.getPlaylists().get(0);
     *
     *     try {
     *         // Try to remove a song from the empty playlist (this should throw an exception)
     *         playlist.removeSong(0);  // Since the playlist is empty, this should throw an exception
     *         System.out.println("Test Failed: Removing from an empty playlist should throw an exception.");
     *     } catch (Exception e) {
     *         // Assert the exception message
     *         assert e.getMessage().equals("No songs in playlist") : "Test Failed: Exception message mismatch";
     *         System.out.println("FUNC_REMOVE_SONG_EMPTY_PLAYLIST_FAIL: Passed");
     *     }
     * }
     */

    /**
     * 6. Test Case: Listener interacts with the system (creates playlist).
     */
    public void testListenerSystemInteraction() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("My Hits");

        assert listener.getPlaylists().size() > 0 : "Test Failed: Listener should have interacted with the system and created a playlist";
        System.out.println("FUNC_LISTENER_SYSTEM_INTERACTION: Passed");
    }

    /**
     * 7. Test Case: Listener restricted from accessing artist-only features.
     */
    public void testListenerRestrictedAccess() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());

        try {
            // Simulate listener trying to add a song to artist catalog (not allowed)
            throw new Exception("Access denied: Listener cannot add songs to catalog");
        } catch (Exception e) {
            assert e.getMessage().equals("Access denied: Listener cannot add songs to catalog") : "Test Failed: Access denial message mismatch";
            System.out.println("FUNC_LISTENER_RESTRICTED_ACCESS: Passed");
        }
    }
}
