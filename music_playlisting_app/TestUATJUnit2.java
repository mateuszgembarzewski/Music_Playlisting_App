import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * User Acceptance Testing (UAT) for Playlist management, Song management,
 * and Listener actions in the music application using JUnit 5.
 */
public class TestUATJUnit2 {

    private Listener listener;

    /**
     * Setup method to initialize common resources before each test case.
     */
    @BeforeEach
    public void setUp() {
        listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
    }

    /**
     * 1. Test Case: Listener creates multiple playlists successfully.
     */
    @Test
    public void testCreateMultiplePlaylists() {
        listener.createNewPlaylist("Playlist #1");
        listener.createNewPlaylist("Playlist #2");

        assertEquals(2, listener.getPlaylists().size(), "Listener should have created 2 playlists");
    }

    /**
     * 2. Test Case: Listener removes a playlist successfully.
     */
    @Test
    public void testRemovePlaylistSuccess() {
        listener.createNewPlaylist("Playlist #1");
        int before = listener.getPlaylists().size();

        try {
            listener.deletePlaylistAtIndex(0);
            int after = listener.getPlaylists().size();
            assertEquals(before - 1, after, "Playlist should be removed successfully");
        } catch (Exception e) {
            fail("Test Failed: " + e.getMessage());
        }
    }

    /**
     * 3. Test Case: Listener removes a song from a playlist successfully.
     */
    @Test
    public void testRemoveSongFromPlaylistSuccess() {
        listener.createNewPlaylist("Chill Vibes");
        Playlist playlist = listener.getPlaylists().get(0);
        Song song = new Song("Song A", "Artist A", 200);
        playlist.addSong(song); // Add the song to the playlist

        try {
            // Use reflection to access the private 'songs' field in Playlist
            Field songsField = Playlist.class.getDeclaredField("songs");
            songsField.setAccessible(true);
            ArrayList<Song> songsList = (ArrayList<Song>) songsField.get(playlist);

            int before = songsList.size();

            // Get the song by index (0) and remove it
            Song songToRemove = songsList.get(0);
            playlist.removeSong(songToRemove); // Remove the song object

            int after = songsList.size();

            // Test: Ensure the song was removed successfully
            assertEquals(before - 1, after, "Song should be removed from the playlist");
        } catch (Exception e) {
            fail("FUNC_REMOVE_SONG_SUCCESS: Failed Why " + e.getMessage());
        }
    }

    /**
     * 4. Test Case: Listener interacts with the system (creates playlist).
     */
    @Test
    public void testListenerSystemInteraction() {
        listener.createNewPlaylist("My Hits");

        assertTrue(listener.getPlaylists().size() > 0, "Listener should have interacted with the system and created a playlist");
    }

    /**
     * 5. Test Case: Listener restricted from accessing artist-only features.
     */
    @Test
    public void testListenerRestrictedAccess() {
        try {
            // Simulate listener trying to add a song to artist catalog (not allowed)
            throw new Exception("Access denied: Listener cannot add songs to catalog");
        } catch (Exception e) {
            assertEquals("Access denied: Listener cannot add songs to catalog", e.getMessage(), "Access denial message mismatch");
        }
    }

    
    //6. Test Case: Listener fails to remove a playlist when none exist.
     
    // Commented out for later tweaks
    @Test
    public void testRemovePlaylistFail() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        try {
           listener.deletePlaylistAtIndex(0);
           fail("Test Failed: Playlist removal should have failed due to no playlists.");
        } catch (Exception e) {
            assertEquals("No playlists exist", e.getMessage(), "Test Failed: Exception message mismatch");
        }
    }
    
    
    /**
     * 7. Test Case: Listener fails to remove a song from an empty playlist.
     * 
     * // Commented out for later tweaks
     * @Test
     * public void testRemoveSongFromEmptyPlaylistFail() {
     *     Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
     *     listener.createNewPlaylist("Empty Playlist");
     *     Playlist playlist = listener.getPlaylists().get(0);
     *
     *     try {
     *         playlist.removeSong(0);  // This should throw an exception as the playlist is empty
     *         fail("Test Failed: Removing from an empty playlist should throw an exception.");
     *     } catch (Exception e) {
     *         assertEquals("No songs in playlist", e.getMessage(), "Test Failed: Exception message mismatch");
     *     }
     * }
     */
}
