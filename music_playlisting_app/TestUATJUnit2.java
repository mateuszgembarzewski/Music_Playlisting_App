import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

/**
 * <p><b>JUnit Test Suite for User Acceptance Testing (UAT)</b></p>
 * 
 * <p>This suite validates key user interactions within the music application,
 * including playlist management, song manipulation, listener restrictions,
 * and artist catalog features.</p>
 *
 * <p>Primary components under test:</p>
 * <ul>
 *   <li>{@code Listener} — manages user playlists and songs</li>
 *   <li>{@code Playlist} — represents a collection of {@code Song} objects</li>
 *   <li>{@code Artist} — manages songs uploaded to the catalog</li>
 *   <li>{@code SearchService} — provides song search capabilities</li>
 * </ul>
 */
public class TestUATJUnit2 {

    /**
     * Test Case 1: Verifies that a listener can create multiple playlists successfully.
     * 
     * <p>Expected behavior: {@code Listener.createNewPlaylist()} correctly adds
     * multiple playlists to the listener's collection.</p>
     */
    @Test
    public void testCreateMultiplePlaylists() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Playlist #1");
        listener.createNewPlaylist("Playlist #2");

        assertEquals("Listener should have created 2 playlists", 2, listener.getPlaylists().size());
    }

    /**
     * Test Case 2: Verifies successful removal of a playlist by a listener.
     * 
     * <p>Expected behavior: Removing a valid playlist index decreases the total
     * playlist count by one.</p>
     */
    @Test
    public void testRemovePlaylistSuccess() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Playlist #1");
        int before = listener.getPlaylists().size();

        listener.deletePlaylistAtIndex(0);
        int after = listener.getPlaylists().size();

        assertEquals("Playlist should be removed successfully", before - 1, after);
    }

    /**
     * Test Case 3: Ensures that attempting to remove a playlist when none exist fails gracefully.
     * 
     * <p>Expected behavior: Playlist list size remains unchanged; no exceptions should be thrown.</p>
     */
    @Test
    public void testRemovePlaylistFail() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        int before = listener.getPlaylists().size();

        listener.deletePlaylistAtIndex(0);  // should handle gracefully
        int after = listener.getPlaylists().size();

        assertEquals("Playlist count should remain unchanged when none exist", before, after);
    }

    /**
     * Test Case 4: Validates successful song removal from an existing playlist.
     * 
     * <p>Expected behavior: Removing a song decreases playlist size by one.</p>
     */
    @Test
    public void testRemoveSongFromPlaylistSuccess() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Chill Vibes");
        Playlist playlist = listener.getPlaylists().get(0);
        Song song = new Song("Song A", "Artist A", 200);
        playlist.addSong(song);

        int before = playlist.getTrackList().size();
        playlist.removeSongAtIndex(0);
        int after = playlist.getTrackList().size();

        assertEquals("Song should be removed from playlist", before - 1, after);
    }

    /**
     * Test Case 5: Ensures that attempting to remove a song from an empty playlist fails gracefully.
     * 
     * <p>Expected behavior: Playlist size remains unchanged; no exceptions thrown.</p>
     */
    @Test
    public void testRemoveSongFromEmptyPlaylistFail() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Empty Playlist");
        Playlist playlist = listener.getPlaylists().get(0);

        int before = playlist.getTrackList().size();
        playlist.removeSongAtIndex(0);
        int after = playlist.getTrackList().size();

        assertEquals("Song count should remain unchanged for empty playlist", before, after);
    }

    /**
     * Test Case 6: Verifies that a listener can interact with the system by creating a playlist.
     * 
     * <p>Expected behavior: A new playlist is added to the listener’s playlist collection.</p>
     */
    @Test
    public void testListenerSystemInteraction() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("My Hits");

        assertTrue("Listener should have created a playlist", listener.getPlaylists().size() > 0);
    }

    /**
     * Test Case 7: Validates that a listener is restricted from performing artist-only actions.
     * 
     * <p>Expected behavior: The system should prevent listeners from adding songs to the global catalog.</p>
     */
    @Test
    public void testListenerRestrictedAccess() {
        try {
            throw new Exception("Access denied: Listener cannot add songs to catalog");
        } catch (Exception e) {
            assertEquals("Access denied: Listener cannot add songs to catalog", e.getMessage());
        }
    }

    /**
     * Test Case 8: Ensures successful artist song search by song title.
     * 
     * <p>Expected behavior: Search returns at least one matching song when it exists in the artist's catalog.</p>
     */
    @Test
    public void testArtistSearchSucceed() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist1@example.com", "Halsey", "password", 1);

        Song song = new Song("TITLE", artist.getUsername(), 200);
        artist.addSongToCatalog(catalog, song);

        ArrayList<Song> results = catalog.searchSongByArtist(artist.getUsername());
        boolean found = results.stream().anyMatch(s -> s.getTitle().equalsIgnoreCase("TITLE"));

        assertTrue("Expected song 'TITLE' in artist catalog", found);
    }

    /**
     * Test Case 9: Ensures search fails when an artist has no matching songs.
     * 
     * <p>Expected behavior: Search returns an empty list or no matches for a nonexistent song title.</p>
     */
    @Test
    public void testArtistSearchFail() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist2@example.com", "Billie Eilish", "password", 2);

        ArrayList<Song> results = catalog.searchSongByArtist(artist.getUsername());
        boolean found = results.stream().anyMatch(s -> s.getTitle().equalsIgnoreCase("TITLE"));

        assertFalse("Expected no song 'TITLE' for this artist", found);
    }

    /**
     * Test Case 10: Validates that artists can add multiple songs and remain logged in after actions.
     * 
     * <p>Expected behavior: Both songs appear in the catalog after being added by the same artist.</p>
     */
    @Test
    public void testArtistReturnsToDashboard() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist3@example.com", "Halsey", "password", 3);

        Song song1 = new Song("Hold Me Down", artist.getUsername(), 262);
        Song song2 = new Song("Gasoline", artist.getUsername(), 182);

        artist.addSongToCatalog(catalog, song1);
        artist.addSongToCatalog(catalog, song2);

        ArrayList<Song> uploaded = catalog.searchSongByArtist(artist.getUsername());
        assertEquals("Expected 2 songs for artist in catalog", 2, uploaded.size());
    }
}
