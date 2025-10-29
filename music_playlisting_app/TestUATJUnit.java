import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * <p><b>Unit Test Suite #1 — Core Application Functions (JUnit 5)</b></p>
 *
 * <p>This suite validates key functionalities across the core system components,
 * focusing on authentication, listener operations, and artist catalog management.</p>
 *
 * <p><b>Covered Functional Areas:</b></p>
 * <ul>
 *   <li>User authentication (success, failure, lockout)</li>
 *   <li>Listener search (exact and partial title)</li>
 *   <li>Playlist management (add and duplicate prevention)</li>
 *   <li>Artist catalog management (add, reject invalid songs)</li>
 * </ul>
 *
 * <p>Each test ensures functional correctness and integration among
 * {@link LoginService}, {@link Listener}, {@link SearchService}, and {@link Artist}.</p>
 **/
public class TestUATJUnit {

    /** Handles authentication and user verification logic. */
    private LoginService loginService;

    /** Stores mock users for authentication and test purposes. */
    private List<User> users;

    /**
     * Initializes dependencies before each test.
     * Ensures isolation and a clean environment.
     */
    @BeforeEach
    public void setup() {
        loginService = new LoginService();
        users = new ArrayList<>();
        users.add(new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>()));
    }

    // ───────────────────────────────
    // Authentication Tests
    // ───────────────────────────────

    /** Verifies that a user can successfully log in with correct credentials. */
    @Test
    public void testUserLoginSuccess() {
        User loggedInUser = loginService.authenticate("testuser", "password", users);
        assertNotNull(loggedInUser, "User login should succeed with correct credentials");
    }

    /** Verifies that login fails with incorrect password. */
    @Test
    public void testUserLoginFail() {
        User loggedInUser = loginService.authenticate("testuser", "wrongpassword", users);
        assertNull(loggedInUser, "User login should fail with incorrect password");
    }

    /** Ensures that an account is locked after three consecutive failed logins. */
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

    /** Verifies that a listener can search for a song by its exact title. */
    @Test
    public void testListenerSearchExact() {
        SearchService searchService = new SearchService();
        Song song = new Song("Imagine", "John Lennon", 183);
        searchService.addSongToCatalog(song);

        List<Song> results = searchService.searchByTitle("Imagine");
        assertEquals(1, results.size(), "Search by exact title should return 1 result");
        assertEquals("Imagine", results.get(0).getTitle(), "Search result should be 'Imagine'");
    }

    /** Verifies that a listener can search for songs using a partial title. */
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

    /** Verifies that a listener can successfully add a new song to a playlist. */
    @Test
    public void testListenerAddSongSuccess() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Playlist playlist = listener.createNewPlaylist("testcase6");
        Song song = new Song("Imagine", "John Lennon", 183);

        boolean result = playlist.addSong(song);

        assertTrue(result, "Song should be added successfully to playlist");
        assertEquals(1, listener.getPlaylistAtIndex(0).getTracklist().size(), "Playlist should contain 1 song");
    }

    /** Ensures that duplicate songs cannot be added to a listener’s playlist. */
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

    /** Verifies that an artist can successfully add a valid song to their catalog. */
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

    /** Ensures that an artist cannot add a song with an invalid (empty) title. */
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

    /** Ensures that an artist cannot add a song with an invalid (negative) duration. */
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
}
