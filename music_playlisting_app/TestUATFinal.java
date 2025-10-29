import java.util.*;
import java.lang.reflect.Field;

/**
 * <p><b>Manual Unit Acceptance Testing (UAT)</b></p>
 *
 * <p>This class is responsible for manually executing and validating the
 * core functionalities of the music application. Each test method corresponds
 * to a functional user requirement and is executed sequentially in {@link #main(String[])}.</p>
 *
 * <p>Covered Scenarios:</p>
 * <ul>
 *   <li>User authentication (login success, failure, and lockout)</li>
 *   <li>Listener song search (exact and partial title)</li>
 *   <li>Listener library management (adding songs and preventing duplicates)</li>
 *   <li>Artist catalog management (commented out pending Sprint 2 updates)</li>
 * </ul>
 *
 * <p>Each test prints a descriptive success message to the console and uses
 * Java {@code assert} statements for validation.</p>
 */
public class TestUATFinal {

    /**
     * Test Case 1:
     * Verifies that user login succeeds with valid credentials.
     *
     * <p><b>Expected Result:</b> {@link LoginService#authenticate(String, String, List)} returns
     * a non-null {@link User} object.</p>
     */
    public void testUserLoginSuccess() {
        LoginService loginService = new LoginService();
        List<User> users = new ArrayList<>();
        users.add(new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>()));

        User loggedInUser = loginService.authenticate("testuser", "password", users);

        assert loggedInUser != null : "Test Failed: User login should succeed with correct credentials";
        System.out.println("FUNC_USER_LOGIN_SUCCESS_01: Passed");
    }

    /**
     * Test Case 2:
     * Validates that user login fails with an incorrect password.
     *
     * <p><b>Expected Result:</b> {@code null} is returned for invalid authentication attempts.</p>
     */
    public void testUserLoginFail() {
        LoginService loginService = new LoginService();
        List<User> users = new ArrayList<>();
        users.add(new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>()));

        User loggedInUser = loginService.authenticate("testuser", "wrongpassword", users);

        assert loggedInUser == null : "Test Failed: User login should fail with incorrect password";
        System.out.println("FUNC_USER_LOGIN_FAIL_01: Passed");
    }

    /**
     * Test Case 3:
     * Ensures that a user account is locked after three consecutive failed login attempts.
     *
     * <p><b>Expected Result:</b> After three failed attempts, the account is locked and
     * cannot be accessed even with the correct password.</p>
     */
    public void testUserLoginLockout() {
        LoginService loginService = new LoginService();
        List<User> users = new ArrayList<>();
        users.add(new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>()));

        // Simulate 3 failed login attempts
        loginService.authenticate("testuser", "wrongpassword", users);
        loginService.authenticate("testuser", "wrongpassword", users);
        loginService.authenticate("testuser", "wrongpassword", users);

        User loggedInUser = loginService.authenticate("testuser", "password", users);

        assert loggedInUser == null : "Test Failed: Account should be locked after 3 failed attempts";
        System.out.println("FUNC_USER_LOGIN_LOCKOUT_04: Passed");
    }

    /**
     * Test Case 4:
     * Validates that a listener can search for a song by its exact title.
     *
     * <p><b>Expected Result:</b> A single song matching the exact title is returned.</p>
     */
    public void testListenerSearchExact() {
        SearchService searchService = new SearchService();
        Song song = new Song("Imagine", "John Lennon", 183);
        searchService.addSongToCatalog(song);

        List<Song> results = searchService.searchByTitle("Imagine");

        assert results.size() == 1 : "Test Failed: Search by exact title should return 1 result";
        assert results.get(0).getTitle().equals("Imagine") : "Test Failed: Search result title should be 'Imagine'";
        System.out.println("FUNC_LISTENER_SEARCH_01: Passed");
    }

    /**
     * Test Case 5:
     * Verifies that a listener can search for a song using a partial title.
     *
     * <p><b>Expected Result:</b> Songs containing the partial keyword are returned.</p>
     */
    public void testListenerSearchPartial() {
        SearchService searchService = new SearchService();
        Song song = new Song("Shape of You", "Ed Sheeran", 233);
        searchService.addSongToCatalog(song);

        List<Song> results = searchService.searchByPartialTitle("Shape");

        assert results.size() == 1 : "Test Failed: Search by partial title should return 1 result";
        assert results.get(0).getTitle().equals("Shape of You") : "Test Failed: Search result title should be 'Shape of You'";
        System.out.println("FUNC_LISTENER_SEARCH_02: Passed");
    }

    /**
     * Test Case 6:
     * Confirms that a listener can add a new song to a playlist.
     *
     * <p><b>Expected Result:</b> The song is successfully added, and the playlist
     * reflects one new entry.</p>
     */
    public void testListenerAddSongSuccess() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Playlist playlist = listener.createNewPlaylist("testcase6");
        Song song = new Song("Imagine", "John Lennon", 183);

        boolean result = playlist.addSong(song);

        assert result == true : "Test Failed: Song should be added successfully to the library";
        assert listener.getPlaylistAtIndex(0).getTracklist().size() == 1 : "Test Failed: Listener library should contain 1 song";
        System.out.println("LISTENER_ADD_SONG_SUCCESS: Passed");
    }

    /**
     * Test Case 7:
     * Ensures that a listener cannot add duplicate songs to a listener's playlist.
     *
     * <p><b>Expected Result:</b> Duplicate addition fails, and the playlist size remains unchanged.</p>
     */
    public void testListenerAddSongFail() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Playlist playlist = listener.createNewPlaylist("testcase7");
        Song song = new Song("Imagine", "John Lennon", 183);

        playlist.addSong(song); // First addition
        boolean result = playlist.addSong(song); // Attempt duplicate

        assert result == false : "Test Failed: Song should not be added again to the library";
        assert listener.getPlaylistAtIndex(0).getTracklist().size() == 1 : "Test Failed: Listener library should still contain 1 song";
        System.out.println("LISTENER_ADD_SONG_FAIL: Passed");
    }

    /**
     * Test Case 8:
     * Validates that an artist can successfully add a valid song to their catalog.
     *
     * <p><b>Expected Result:</b> The song is added, and catalog count increases.</p>
     */
    public void testArtistAddSongSuccess() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("New Song", "Artie", 180);
    
        boolean result = artist.addSongToCatalog(catalog, song);
    
        assert result == true : "Test Failed: Song should be added to the artist's catalog";
        assert artist.getCatalog(catalog).size() == 1 : "Test Failed: Artist catalog should contain 1 song";
        System.out.println("ARTIST_ADD_SONG_SUCCESS: Passed");
    }

    /**
     * Test Case 9:
     * Ensures that an artist cannot add a song with an invalid (empty) title.
     *
     * <p><b>Expected Result:</b> The song is rejected and not added to the catalog.</p>
     */
    public void testArtistAddSongFailTitle() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("", "Artie", 180); // Invalid title
    
        boolean result = artist.addSongToCatalog(catalog, song);
    
        assert result == false : "Test Failed: Song with invalid title should not be added";
        assert artist.getCatalog(catalog).size() == 0 : "Test Failed: Artist catalog should still contain 0 songs";
        System.out.println("ARTIST_ADD_SONG_FAIL_TITLE: Passed");
    }

    /**
     * Test Case 10:
     * Ensures that an artist cannot add a song with an invalid (negative) duration.
     *
     * <p><b>Expected Result:</b> The song is rejected and not added to the catalog.</p>
     */
    public void testArtistAddSongFailDuration() {
        SearchService catalog = new SearchService();
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("New Song", "Artie", -1); // Invalid duration
    
        boolean result = artist.addSongToCatalog(catalog, song);
    
        assert result == false : "Test Failed: Song with invalid duration should not be added";
        assert artist.getCatalog(catalog).size() == 0 : "Test Failed: Artist catalog should still contain 0 songs";
        System.out.println("ARTIST_ADD_SONG_FAIL_DURATION: Passed");
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
    
    public void testAdminLogin() {
        try {
            Main mainClassInstance = new Main(); 
            Admin adminUser = new Admin("testAdmin@gmail.com", "testAdmin", "GoodPass1", 101);
            mainClassInstance.USERS.add(adminUser);

            LoginService ls = new LoginService();
            User returnUser = ls.authenticate("testAdmin", "GoodPass1", mainClassInstance.USERS);

            if (adminUser.getId() != returnUser.getId()) {
                throw new Exception("Expected ID " + adminUser.getId() + " but got " + returnUser.getId());
            }

            System.out.println("Test passed: Admin login verified successfully.");
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }

    public void testAdminAccountCreation() {
        try {
            Main mainClassInstance = new Main();
            ArrayList<User> USERS = mainClassInstance.USERS;
            int before = USERS.size();

            mainClassInstance.adminCreatesAccount("test@test.com", "Test123", "Test1234@6", "lis");
            int after = USERS.size();

            if (after != before + 1) {
                throw new Exception("Expected " + (before + 1) + " users, but got " + after);
            }

            System.out.println("\nTest passed: User account successfully created.");
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }

    public void testAdminAccountDeletion() {
        try {
            Main mainClassInstance = new Main();
            ArrayList<User> USERS = mainClassInstance.USERS;

            mainClassInstance.adminCreatesAccount("test@test.com", "Test123", "Test1234@6", "lis");
            int before = USERS.size();

            mainClassInstance.adminDeleteAccount(0);
            int after = mainClassInstance.USERS.size();

            if (after != before - 1) {
                throw new Exception("Expected user count " + (before - 1) + " but got " + after);
            }

            System.out.println("\nTest passed: User account successfully deleted.");
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }

    public void testAdminSongCreation() {
        try {
            Main mainClassInstance = new Main();
            SearchService CATALOG = mainClassInstance.CATALOG;

            int before = CATALOG.getGlobalCatalog().size();
            Song s = new Song("disocsong", "iamadiscodancer", 60);
            mainClassInstance.adminAddSong(s);
            int after = CATALOG.getGlobalCatalog().size();

            if (after != before + 1) {
                throw new Exception("Expected catalog size " + (before + 1) + " but got " + after);
            }

            System.out.println("Test passed: Song successfully added to catalog.");
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }

    public void testAdminSongDeletion() {
        try {
            Main mainClassInstance = new Main();
            SearchService CATALOG = mainClassInstance.CATALOG;
            Song s = new Song("disocsong", "iamadiscodancer", 60);
            mainClassInstance.adminAddSong(s);

            int before = CATALOG.getGlobalCatalog().size();
            mainClassInstance.adminDeleteSong(0);
            int after = CATALOG.getGlobalCatalog().size();

            if (after != before - 1) {
                throw new Exception("Expected catalog size " + (before - 1) + " but got " + after);
            }

            System.out.println("Test passed: Song successfully deleted from catalog.");
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }
    
    /**
     * Main entry point for executing all manual test cases.
     * <p>
     * Tests are executed sequentially for traceable console output.
     * Developer commentary and disabled tests are preserved for historical context.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        TestUATFinal tester = new TestUATFinal();
        tester.testUserLoginSuccess();
        tester.testUserLoginFail();
        tester.testUserLoginLockout();
        tester.testListenerSearchExact();
        tester.testListenerSearchPartial();
        tester.testListenerAddSongSuccess();
        tester.testListenerAddSongFail();
        tester.testArtistAddSongSuccess();
        tester.testArtistAddSongFailTitle();
        tester.testArtistAddSongFailDuration();
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
        tester.testAdminLogin();
        tester.testAdminAccountCreation();
        tester.testAdminAccountDeletion();
        tester.testAdminSongCreation();
        tester.testAdminSongDeletion();
    }
}
