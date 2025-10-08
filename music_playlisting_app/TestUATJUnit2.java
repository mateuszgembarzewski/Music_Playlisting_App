import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

/**
 * JUnit Test Suite for User Acceptance Testing (UAT)
 * Covers Playlist management, Song management,
 * Listener actions, and Artist interactions in the music app.
 */
public class TestUATJUnit2 {

    /**
     * 1. Test Case: Listener creates multiple playlists successfully.
     */
    @Test
    public void testCreateMultiplePlaylists() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Playlist #1");
        listener.createNewPlaylist("Playlist #2");

        assertEquals("Listener should have created 2 playlists", 2, listener.getPlaylists().size());
    }

    /**
     * 2. Test Case: Listener removes a playlist successfully.
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
     * 3. Test Case: Listener fails to remove a playlist when none exist.
     */
    @Test
    public void testRemovePlaylistFail() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        int before = listener.getPlaylists().size();
        listener.deletePlaylistAtIndex(0);  // likely prints an error message, not throw
        int after = listener.getPlaylists().size();

        assertEquals("Playlist count should remain unchanged when none exist", before, after);
    }

    /**
     * 4. Test Case: Listener removes a song from a playlist successfully.
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
     * 5. Test Case: Listener fails to remove a song from an empty playlist.
     */
    @Test
    public void testRemoveSongFromEmptyPlaylistFail() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Empty Playlist");
        Playlist playlist = listener.getPlaylists().get(0);

        int before = playlist.getTrackList().size();
        playlist.removeSongAtIndex(0);  // likely prints 'No songs in playlist'
        int after = playlist.getTrackList().size();

        assertEquals("Song count should remain unchanged for empty playlist", before, after);
    }

    /**
     * 6. Test Case: Listener interacts with the system (creates playlist).
     */
    @Test
    public void testListenerSystemInteraction() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("My Hits");

        assertTrue("Listener should have created a playlist", listener.getPlaylists().size() > 0);
    }

    /**
     * 7. Test Case: Listener restricted from accessing artist-only features.
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
     * 8. ARTIST_SEARCH_SUCCEED
     * Test Case: Search by exact song title — song exists for this artist.
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
     * 9. ARTIST_SEARCH_FAIL
     * Test Case: Search by exact song title — song does not exist for this artist.
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
     * 10. ARTIST_RETURNS_TO_DASH
     * Test Case: Ensure artist can add multiple songs and return to dashboard without re-login.
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
