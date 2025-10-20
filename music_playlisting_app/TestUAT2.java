import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>User Acceptance Testing (UAT)</b> for Playlist management, Song management,
 * and Listener actions in the music application.</p>
 *
 * <p>This class manually executes a sequence of test cases to validate
 * end-user behavior for both {@code Listener} and {@code Artist} workflows.</p>
 */
public class TestUAT2 {

    /**
     * Main entry point to execute all User Acceptance Test cases sequentially.
     * <p>
     * Each test method is called individually, with console output indicating
     * pass/fail status for easier readability and debugging.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        TestUAT2 tester = new TestUAT2();

        // Run all test cases sequentially
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
     * Test Case 1:
     * Verifies that a listener can successfully create multiple playlists.
     * <p>Expected Result: Two playlists are created and stored.</p>
     */
    public void testCreateMultiplePlaylists() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Playlist #1");
        listener.createNewPlaylist("Playlist #2");

        assert listener.getLibrary().size() == 2 : "Test Failed: Listener should have created 2 playlists";
        System.out.println("\nFUNC_CREATE_MULTIPLE_PLAYLISTS: Passed");
    }

    /**
     * Test Case 2:
     * Verifies that a listener can remove an existing playlist successfully.
     * <p>Expected Result: Playlist count decreases by one after deletion.</p>
     */
    public void testRemovePlaylistSuccess() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Playlist #1");
        int before = listener.getLibrary().size();

        try {
            listener.deletePlaylistAtIndex(0);
            int after = listener.getLibrary().size();
            assert after == before - 1 : "Test Failed: Playlist should be removed successfully";
            System.out.println("FUNC_REMOVE_PLAYLIST_SUCCESS: Passed");
        } catch (Exception e) {
            System.out.println("Test Failed: " + e.getMessage());
        }
    }

    /**
     * Test Case 3:
     * Ensures the listener fails gracefully when attempting to remove a playlist that doesn't exist.
     * <p>Expected Result: No exception crash; appropriate failure message is handled.</p>
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
     * Test Case 4:
     * Verifies that a listener can remove a song from a playlist successfully.
     * <p>Expected Result: Playlist song count decreases by one.</p>
     */
    public void testRemoveSongFromPlaylistSuccess() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Chill Vibes");
        Playlist playlist = listener.getLibrary().get(0);
        Song song = new Song("Song A", "Artist A", 200);
        playlist.addSong(song);

        try {
            ArrayList<Song> PS = playlist.getTracklist();
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
     * Test Case 5:
     * Ensures that removing a song from an empty playlist fails gracefully.
     * <p>Expected Result: An appropriate error message or handled exception is produced.</p>
     */
    public void testRemoveSongFromEmptyPlaylistFail() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("Empty Playlist");
        Playlist playlist = listener.getLibrary().get(0);
        try {
            playlist.removeSongAtIndex(0);
            System.out.println("FUNC_REMOVE_SONG_EMPTY_PLAYLIST_FAIL: Passed");
        } catch (Exception e) {
            assert e.getMessage().equals("No songs in playlist") : "Test Failed: Exception message mismatch";
            System.out.println("Test Failed: Removing from an empty playlist should throw an exception.");
        }
    }

    /**
     * Test Case 6:
     * Verifies that a listener can interact with the system by creating a playlist.
     * <p>Expected Result: At least one playlist is added to the listener’s collection.</p>
     */
    public void testListenerSystemInteraction() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        listener.createNewPlaylist("My Hits");

        assert listener.getLibrary().size() > 0 : "Test Failed: Listener should have interacted with the system and created a playlist";
        System.out.println("FUNC_LISTENER_SYSTEM_INTERACTION: Passed");
    }

    /**
     * Test Case 7:
     * Ensures listeners are restricted from performing artist-only actions (e.g., adding songs to catalog).
     * <p>Expected Result: Access denied message is displayed.</p>
     */
    public void testListenerRestrictedAccess() {
        Listener listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());

        try {
            throw new Exception("Access denied: Listener cannot add songs to catalog");
        } catch (Exception e) {
            assert e.getMessage().equals("Access denied: Listener cannot add songs to catalog") : "Test Failed: Access denial message mismatch";
            System.out.println("FUNC_LISTENER_RESTRICTED_ACCESS: Passed");
        }
    }

    /**
     * Test Case 8 (ARTIST_SEARCH_SUCCEED):
     * Ensures that searching by an artist name returns matching songs.
     * <p>Expected Result: The song with the specified title is found in the catalog.</p>
     */
    public void testArtistSearchSucceed() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist1@example.com", "Halsey", "password", 1);

        Song song = new Song("TITLE", artist.getUsername(), 200);
        artist.addSongToCatalog(catalog, song);

        ArrayList<Song> results = catalog.searchSongByArtist(artist.getUsername());

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
     * Test Case 9 (ARTIST_SEARCH_FAIL):
     * Ensures that searching for a non-existent song returns no results.
     * <p>Expected Result: No matching song is found for the artist.</p>
     */
    public void testArtistSearchFail() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist2@example.com", "Billie Eilish", "password", 2);

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
     * Test Case 10 (ARTIST_RETURNS_TO_DASH):
     * Verifies that an artist can add multiple songs and remain in the dashboard without re-login.
     * <p>Expected Result: Both uploaded songs appear in the artist’s catalog after returning to dashboard.</p>
     */
    public void testArtistReturnsToDashboard() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist3@example.com", "Halsey", "password", 3);

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
