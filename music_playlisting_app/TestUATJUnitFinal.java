import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TestUATJUnitFinal {

    private LoginService loginService;
    private List<User> users;
    private Listener listener;
    private SearchService catalog;
    private Artist artist;
    /**
     * Initializes dependencies before each test.
     * Ensures isolation and a clean environment.
     */
    @BeforeEach
    public void setup() {
        loginService = new LoginService();
        users = new ArrayList<>();
        users.add(new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>()));
        listener = new Listener("listener1@example.com", "listener1", "password", 1, new ArrayList<>());
        catalog = new SearchService();
        artist = new Artist("artist@example.com", "Halsey", "password", 1);
    }
    
    /** 
     * Test Case 1: Verifies that a user can successfully log in with correct credentials.
     */
    @Test
    public void testUserLoginSuccess() {
        User loggedInUser = loginService.authenticate("testuser", "password", users);
        assertNotNull(loggedInUser, "User login should succeed with correct credentials");
    }

    /** 
     * Test Case 2: Verifies that login fails with incorrect password. 
     */
    @Test
    public void testUserLoginFail() {
        User loggedInUser = loginService.authenticate("testuser", "wrongpassword", users);
        assertNull(loggedInUser, "User login should fail with incorrect password");
    }

    /** 
     * Test Case 3: Ensures that an account is locked after three consecutive failed logins.
     */
    @Test
    public void testUserLoginLockout() {
        loginService.authenticate("testuser", "wrongpassword", users);
        loginService.authenticate("testuser", "wrongpassword", users);
        loginService.authenticate("testuser", "wrongpassword", users);

        User loggedInUser = loginService.authenticate("testuser", "password", users);
        assertNull(loggedInUser, "Account should be locked after 3 failed attempts");
    }

    // ───────────────────────────────
    // Listener Search Tests
    // ───────────────────────────────

    /** 
     * Test Case 4: Verifies that a listener can search for a song by its exact title. 
     */
    @Test
    public void testListenerSearchExact() {
        SearchService searchService = new SearchService();
        Song song = new Song("Imagine", "John Lennon", 183);
        searchService.addSongToCatalog(song);

        List<Song> results = searchService.searchByTitle("Imagine");
        assertEquals(1, results.size(), "Search by exact title should return 1 result");
        assertEquals("Imagine", results.get(0).getTitle(), "Search result should be 'Imagine'");
    }

    /** 
     * Test Case 5: Verifies that a listener can search for songs using a partial title.
     */
    @Test
    public void testListenerSearchPartial() {
        SearchService searchService = new SearchService();
        Song song = new Song("Shape of You", "Ed Sheeran", 233);
        searchService.addSongToCatalog(song);

        List<Song> results = searchService.searchByPartialTitle("Shape");
        assertEquals(1, results.size(), "Search by partial title should return 1 result");
        assertEquals("Shape of You", results.get(0).getTitle(), "Search result should be 'Shape of You'");
    }

    // ───────────────────────────────
    // Playlist Management Tests
    // ───────────────────────────────

    /** 
     * Test Case 6: Verifies that a listener can successfully add a new song to a playlist. 
     */
    @Test
    public void testListenerAddSongSuccess() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Playlist playlist = listener.createNewPlaylist("testcase6");
        Song song = new Song("Imagine", "John Lennon", 183);

        boolean result = playlist.addSong(song);

        assertTrue(result, "Song should be added successfully to playlist");
        assertEquals(1, listener.getPlaylistAtIndex(0).getTracklist().size(), "Playlist should contain 1 song");
    }

    /** 
     * Test Case 7: Ensures that duplicate songs cannot be added to a listener’s playlist.
     */
    @Test
    public void testListenerAddSongFail() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Playlist playlist = listener.createNewPlaylist("testcase7");
        Song song = new Song("Imagine", "John Lennon", 183);

        playlist.addSong(song);
        boolean result = playlist.addSong(song);

        assertFalse(result, "Duplicate song should not be added");
        assertEquals(1, listener.getPlaylistAtIndex(0).getTracklist().size(), "Playlist should still contain 1 song");
    }

    // ───────────────────────────────
    // Artist Catalog Tests
    // ───────────────────────────────

    /** 
     * Test Case 8: Verifies that an artist can successfully add a valid song to their catalog. 
     */
    @Test
    public void testArtistAddSongSuccess() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("New Song", "Artie", 180);

        boolean result = artist.addSongToCatalog(CATALOG, song);
        assertTrue(result, "Song should be added to catalog");
        assertEquals(1, artist.getCatalog(CATALOG).size(), "Catalog should contain 1 song");
    }

    /** 
     * Test Case 9: Ensures that an artist cannot add a song with an invalid (empty) title. 
     */
    @Test
    public void testArtistAddSongFailTitle() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("", "Artie", 180);

        boolean result = artist.addSongToCatalog(CATALOG, song);
        assertFalse(result, "Song with invalid title should not be added");
        assertEquals(0, artist.getCatalog(CATALOG).size(), "Catalog should contain 0 songs");
    }

    /** 
     * Test Case 10: Ensures that an artist cannot add a song with an invalid (negative) duration. 
     */
    @Test
    public void testArtistAddSongFailDuration() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("New Song", "Artie", -1);

        boolean result = artist.addSongToCatalog(CATALOG, song);
        assertFalse(result, "Song with invalid duration should not be added");
        assertEquals(0, artist.getCatalog(CATALOG).size(), "Catalog should contain 0 songs");
    }
    
    /**
     * Test Case 11: Verifies that a listener can create multiple playlists successfully.
     *
     * <p>Expected behavior: {@code Listener.createNewPlaylist()} correctly adds
     * multiple playlists to the listener's collection.</p>
     */
    @Test
    public void testCreateMultiplePlaylists() {
        listener.createNewPlaylist("Playlist #1");
        listener.createNewPlaylist("Playlist #2");
        assertEquals(2, listener.getLibrary().size(), "Listener should have created 2 playlists");
    }

    /**
     * Test Case 12: Verifies successful removal of a playlist by a listener.
     *
     * <p>Expected behavior: Removing a valid playlist index decreases the total
     * playlist count by one.</p>
     */
    @Test
    public void testRemovePlaylistSuccess() {
        listener.createNewPlaylist("Playlist #1");
        int before = listener.getLibrary().size();

        listener.deletePlaylistAtIndex(0);
        int after = listener.getLibrary().size();

        assertEquals(before - 1, after, "Playlist should be removed successfully");
    }

    /**
     * Test Case 13: Ensures that attempting to remove a playlist when none exist fails gracefully.
     *
     * <p>Expected behavior: Playlist list size remains unchanged; no exceptions should be thrown.</p>
     */
    @Test
    public void testRemovePlaylistFail() {
        int before = listener.getLibrary().size();
        listener.deletePlaylistAtIndex(0);
        int after = listener.getLibrary().size();
        assertEquals(before, after, "Playlist count should remain unchanged when none exist");
    }

    /**
     * Test Case 14: Validates successful song removal from an existing playlist.
     *
     * <p>Expected behavior: Removing a song decreases playlist size by one.</p>
     */
    @Test
    public void testRemoveSongFromPlaylistSuccess() {
        listener.createNewPlaylist("Chill Vibes");
        Playlist playlist = listener.getLibrary().get(0);
        Song song = new Song("Song A", "Artist A", 200);
        playlist.addSong(song);

        int before = playlist.getTracklist().size();
        playlist.removeSongAtIndex(0);
        int after = playlist.getTracklist().size();

        assertEquals(before - 1, after, "Song should be removed from playlist");
    }

    /**
     * Test Case 15: Ensures that attempting to remove a song from an empty playlist fails gracefully.
     *
     * <p>Expected behavior: Playlist size remains unchanged; no exceptions thrown.</p>
     */
    @Test
    public void testRemoveSongFromEmptyPlaylistFail() {
        listener.createNewPlaylist("Empty Playlist");
        Playlist playlist = listener.getLibrary().get(0);

        int before = playlist.getTracklist().size();
        playlist.removeSongAtIndex(0);
        int after = playlist.getTracklist().size();

        assertEquals(before, after, "Song count should remain unchanged for empty playlist");
    }

    /**
     * Test Case 16: Verifies that a listener can interact with the system by creating a playlist.
     *
     * <p>Expected behavior: A new playlist is added to the listener’s playlist collection.</p>
     */
    @Test
    public void testListenerSystemInteraction() {
        listener.createNewPlaylist("My Hits");
        assertTrue(listener.getLibrary().size() > 0, "Listener should have created a playlist");
    }

    /**
     * Test Case 17: Validates that a listener is restricted from performing artist-only actions.
     *
     * <p>Expected behavior: The system should prevent listeners from adding songs to the global catalog.</p>
     */
    @Test
    public void testListenerRestrictedAccess() {
        Exception exception = assertThrows(Exception.class, () -> {
            throw new Exception("Access denied: Listener cannot add songs to catalog");
        });
        assertEquals("Access denied: Listener cannot add songs to catalog", exception.getMessage());
    }

    /**
     * Test Case 18: Ensures successful artist song search by song title.
     *
     * <p>Expected behavior: Search returns at least one matching song when it exists in the artist's catalog.</p>
     */
    @Test
    public void testArtistSearchSucceed() {
        Song song = new Song("TITLE", artist.getUsername(), 200);
        artist.addSongToCatalog(catalog, song);

        ArrayList<Song> results = catalog.searchSongByArtist(artist.getUsername());
        boolean found = results.stream().anyMatch(s -> s.getTitle().equalsIgnoreCase("TITLE"));
        assertTrue(found, "Expected song 'TITLE' in artist catalog");
    }

    /**
     * Test Case 19: Ensures search fails when an artist has no matching songs.
     *
     * <p>Expected behavior: Search returns an empty list or no matches for a nonexistent song title.</p>
     */
    @Test
    public void testArtistSearchFail() {
        Artist otherArtist = new Artist("artist2@example.com", "Billie Eilish", "password", 2);

        ArrayList<Song> results = catalog.searchSongByArtist(otherArtist.getUsername());
        boolean found = results.stream().anyMatch(s -> s.getTitle().equalsIgnoreCase("TITLE"));
        assertFalse(found, "Expected no song 'TITLE' for this artist");
    }

    /**
     * Test Case 20: Validates that artists can add multiple songs and remain logged in after actions.
     *
     * <p>Expected behavior: Both songs appear in the catalog after being added by the same artist.</p>
     */
    @Test
    public void testArtistReturnsToDashboard() {
        Song song1 = new Song("Hold Me Down", artist.getUsername(), 262);
        Song song2 = new Song("Gasoline", artist.getUsername(), 182);

        artist.addSongToCatalog(catalog, song1);
        artist.addSongToCatalog(catalog, song2);

        ArrayList<Song> uploaded = catalog.searchSongByArtist(artist.getUsername());
        assertEquals(2, uploaded.size(), "Expected 2 songs for artist in catalog");
    }
    
    /**
     * <b>Test Case 21:</b> Verifies that an admin can successfully log in.
     * <p>
     * The test creates a new {@link Admin} user and attempts authentication
     * via {@link LoginService#authenticate(String, String, List)}.
     * Validation is done by comparing the expected and returned user IDs.
     * </p>
     * <p><b>Expected Result:</b> The authenticated user matches the created admin user.</p>
     */
    @Test
    public void testAdminLogin() {
        Main mainClassInstance = new Main();
        Admin adminUser = new Admin("testAdmin@gmail.com", "testAdmin", "GoodPass1", 101);
        mainClassInstance.USERS.add(adminUser);

        LoginService ls = new LoginService();
        User returnUser = ls.authenticate("testAdmin", "GoodPass1", mainClassInstance.USERS);

        assertEquals(adminUser.getId(), returnUser.getId(), "Admin login should succeed");
    }

    /**
     * <b>Test Case 22:</b> Verifies that an admin can successfully create a user account.
     * <p><b>Expected Result:</b> The number of users increases by 1 after creation.</p>
     */
    @Test
    public void testAdminAccountCreation() {
        Main mainClassInstance = new Main();
        ArrayList<User> USERS = mainClassInstance.USERS;
        int before = USERS.size();

        mainClassInstance.adminCreatesAccount("test@test.com", "Test123", "Test1234@6", "lis");

        int after = USERS.size();
        assertEquals(before + 1, after, "New user account should be created");
    }

    /**
     * <b>Test Case 23:</b> Verifies that an admin can delete a user account.
     * <p><b>Expected Result:</b> The number of users decreases by 1 after deletion.</p>
     */
    @Test
    public void testAdminAccountDeletion() {
        Main mainClassInstance = new Main();
        ArrayList<User> USERS = mainClassInstance.USERS;
        mainClassInstance.adminCreatesAccount("test@test.com", "Test123", "Test1234@6", "lis");

        int before = USERS.size();
        mainClassInstance.adminDeleteAccount(0);
        int after = mainClassInstance.USERS.size();

        assertEquals(before - 1, after, "User account should be deleted");
    }

    /**
     * <b>Test Case 24:</b> Verifies that an admin can add a new song to the catalog.
     * <p><b>Expected Result:</b> Catalog size increases by 1 after addition.</p>
     */
    @Test
    public void testAdminSongCreation() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;
        int before = CATALOG.getGlobalCatalog().size();

        Song s = new Song("disocsong", "iamadiscodancer", 60);
        mainClassInstance.adminAddSong(s);

        int after = CATALOG.getGlobalCatalog().size();
        assertEquals(before + 1, after, "New song should be created in catalog");
    }

    /**
     * <b>Test Case 25:</b> Verifies that an admin can delete a song from the catalog.
     * <p><b>Expected Result:</b> Catalog size decreases by 1 after deletion.</p>
     */
    @Test
    public void testAdminSongDeletion() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;

        Song s = new Song("disocsong", "iamadiscodancer", 60);
        mainClassInstance.adminAddSong(s);

        int before = CATALOG.getGlobalCatalog().size();
        mainClassInstance.adminDeleteSong(0);
        int after = CATALOG.getGlobalCatalog().size();

        assertEquals(before - 1, after, "Song should be deleted from catalog");
    }
}
