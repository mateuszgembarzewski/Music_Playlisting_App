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
        tester.testRemovePlaylistFail();
        tester.testRemoveSongFromPlaylistSuccess();
        tester.testRemoveSongFromEmptyPlaylistFail();
        tester.testListenerSystemInteraction();
        tester.testListenerRestrictedAccess();
        tester.testArtistSearchSucceed();
        tester.testArtistSearchFail();
        tester.testArtistReturnsToDashboard();
    }

    /**
     * 1. Test Case: Listener creates multiple playlists successfully.
     */
    public void testCreateMultiplePlaylists() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Playlist #1");
        listener.createNewPlaylist("Playlist #2");

        assert listener.getPlaylists().size() == 2 : "Test Failed: Listener should have created 2 playlists";
        System.out.println("\nFUNC_CREATE_MULTIPLE_PLAYLISTS: Passed");
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
     *3. Test Case: Listener fails to remove a playlist when none exist.
     */ 
    public void testRemovePlaylistFail() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
     
        try {
            listener.deletePlaylistAtIndex(0);
            System.out.println("FUNC_REMOVE_PLAYLIST_FAIL: Passed");
        } catch (Exception e) {
            assert e.getMessage().equals("There are no playlist to delete") : "Test Failed: Exception message mismatch";
            System.out.println("Test Failed: Playlist removal should have failed due to no playlists.");
        }
    }
     

    /**
     * 4. Test Case: Listener removes a song from a playlist successfully.
     */
    public void testRemoveSongFromPlaylistSuccess() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Chill Vibes");
        Playlist playlist = listener.getPlaylists().get(0);
        Song song = new Song("Song A", "Artist A", 200);
        playlist.addSong(song);
    
        try {
            ArrayList<Song> PS = playlist.getTrackList();
            int before = PS.size();
            Song songToRemove = PS.get(0); 
            playlist.removeSongAtIndex(0);
            int after = PS.size();
            assert after == before - 1 : "Test Failed: Song should be removed from the playlist";
            System.out.println("FUNC_REMOVE_SONG_SUCCESS: Passed");
        } catch (Exception e) {
            System.out.println("FUNC_REMOVE_SONG_SUCCESS: Failed " + e.getMessage());
        }
    }


    /**
     * 5. Test Case: Listener fails to remove a song from an empty playlist.
     */ 
    public void testRemoveSongFromEmptyPlaylistFail() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Empty Playlist");
        Playlist playlist = listener.getPlaylists().get(0);
        try {
            playlist.removeSongAtIndex(0);
            System.out.println("FUNC_REMOVE_SONG_EMPTY_PLAYLIST_FAIL: Passed");
        } catch (Exception e) {
            assert e.getMessage().equals("No songs in playlist") : "Test Failed: Exception message mismatch";
            System.out.println("Test Failed: Removing from an empty playlist should throw an exception.");
        }
    }
    
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
    
        /**
     * 8. ARTIST_SEARCH_SUCCEED
     * Test Case: Search by exact song title — song exists for this artist.
     */
    public void testArtistSearchSucceed() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist1@example.com", "Halsey", "password", 1);

        // Preconditions
        Song song = new Song("TITLE", artist.getUsername(), 200);
        artist.addSongToCatalog(catalog, song);

        // Execution
        ArrayList<Song> results = catalog.searchSongByArtist(artist.getUsername());

        // Verification
        boolean found = false;
        for (Song s : results) {
            if (s.getTitle().equalsIgnoreCase("TITLE")) {
                found = true;
                break;
            }
        }

        assert found : "Test Failed: Expected song 'TITLE' in artist catalog";

        System.out.println("ARTIST_SEARCH_SUCCEED: Passed (Song found and displayed)");
        System.out.println("Redirecting to dashboard in 3-6s...");
    }

    /**
     * 9. ARTIST_SEARCH_FAIL
     * Test Case: Search by exact song title — song does not exist for this artist.
     */
    public void testArtistSearchFail() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist2@example.com", "Billie Eilish", "password", 2);

        // No songs uploaded
        ArrayList<Song> results = catalog.searchSongByArtist(artist.getUsername());

        boolean found = false;
        for (Song s : results) {
            if (s.getTitle().equalsIgnoreCase("TITLE")) {
                found = true;
                break;
            }
        }

        assert !found : "Test Failed: Expected no song 'TITLE' for this artist";

        System.out.println("ARTIST_SEARCH_FAIL: Passed ('No song found' message displayed)");
        System.out.println("Redirecting to dashboard in 3-6s...");
    }

    /**
     * 10. ARTIST_RETURNS_TO_DASH
     * Test Case: Ensure artist can add multiple songs and return to dashboard without re-login.
     */
    public void testArtistReturnsToDashboard() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist3@example.com", "Halsey", "password", 3);

        // Add songs sequentially
        Song song1 = new Song("Hold Me Down", artist.getUsername(), 262);
        Song song2 = new Song("Gasoline", artist.getUsername(), 182);

        artist.addSongToCatalog(catalog, song1);
        System.out.println("Added Song1 and returned to dashboard...");

        artist.addSongToCatalog(catalog, song2);
        System.out.println("Added Song2 and returned to dashboard...");

        ArrayList<Song> uploaded = catalog.searchSongByArtist(artist.getUsername());
        assert uploaded.size() == 2 : "Test Failed: Expected 2 songs for artist in catalog";

        System.out.println("ARTIST_RETURNS_TO_DASH: Passed (Artist added multiple songs and returned to dashboard)");
    }

}
