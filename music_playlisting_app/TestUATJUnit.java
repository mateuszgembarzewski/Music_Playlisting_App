import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * <p><b>Unit Test Suite for the Music Application (JUnit 5)</b></p>
 *
 * <p>This test suite validates critical functionalities across the core system components,
 * focusing on user authentication, listener operations, and song search capabilities.</p>
 *
 * <p>Covered Functional Areas:</p>
 * <ul>
 *   <li>User login and authentication flow</li>
 *   <li>Listener search functionality (exact and partial title matches)</li>
 *   <li>Listener’s personal library management (adding and preventing duplicates)</li>
 *   <li>Artist catalog management (commented-out tests pending Sprint 2 updates)</li>
 * </ul>
 *
 * <p>Each test corresponds to a defined functional requirement to ensure
 * correct integration and behavior within the application.</p>
 */
public class TestUATJUnit {

    /** Handles authentication and user verification logic. */
    private LoginService loginService;

    /** Stores mock users for authentication and test purposes. */
    private List<User> users;

    /**
     * Initializes common dependencies before each test case executes.
     * <p>
     * This setup ensures a fresh {@link LoginService} instance and
     * test user for every test to maintain isolation and consistency.
     * </p>
     */
    @BeforeEach
    public void setup() {
        loginService = new LoginService();
        users = new ArrayList<>();
        users.add(new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>()));
    }

    /**
     * Test Case 1:
     * Verifies that a user can successfully log in with correct credentials.
     * <p>Expected Result: {@link LoginService#authenticate(String, String, List)} returns a valid {@link User} object.</p>
     */
    @Test
    public void testUserLoginSuccess() {
        User loggedInUser = loginService.authenticate("testuser", "password", users);
        assertNotNull(loggedInUser, "User login should succeed with correct credentials");
    }

    /**
     * Test Case 2:
     * Verifies that user login fails with an incorrect password.
     * <p>Expected Result: Authentication returns {@code null} for invalid credentials.</p>
     */
    @Test
    public void testUserLoginFail() {
        User loggedInUser = loginService.authenticate("testuser", "wrongpassword", users);
        assertNull(loggedInUser, "User login should fail with incorrect password");
    }

    /**
     * Test Case 3:
     * Ensures that a user account is locked after three consecutive failed login attempts.
     * <p>Expected Result: User cannot log in even with correct credentials after lockout.</p>
     */
    @Test
    public void testUserLoginLockout() {
        loginService.authenticate("testuser", "wrongpassword", users);
        loginService.authenticate("testuser", "wrongpassword", users);
        loginService.authenticate("testuser", "wrongpassword", users);

        User loggedInUser = loginService.authenticate("testuser", "password", users);
        assertNull(loggedInUser, "Account should be locked after 3 failed attempts");
    }

    /**
     * Test Case 4:
     * Verifies that a listener can search for a song by its exact title.
     * <p>Expected Result: The search returns exactly one matching song with the correct title.</p>
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
     * Test Case 5:
     * Verifies that a listener can search for songs using a partial title.
     * <p>Expected Result: The search returns songs containing the partial keyword in their title.</p>
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

    /**
     * Test Case 6:
     * Verifies that a listener can successfully add a new song to a playlist.
     * <p>Expected Result: The song is added and reflected in the listener’s library list.</p>
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
     * Test Case 7:
     * Ensures that duplicate songs cannot be added to a listener’s playlist.
     * <p>Expected Result: The second addition fails and library count remains unchanged.</p>
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

    /*
     * ------------------------------------------------------------------------
     * Sprint 2 Updates (Commented Tests)
     * ------------------------------------------------------------------------
     * These artist-related test cases are temporarily disabled
     * pending updates to the {@link Artist} class and related catalog methods.
     */

    // /**
    //  * Test Case 8:
    //  * Verifies that an artist can successfully add a valid song to their catalog.
    //  * Expected Result: Song is added and catalog count increases.
    //  */
    // @Test
    // public void testArtistAddSongSuccess() {
    //     Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
    //     Song song = new Song("New Song", "Artie", 180);
    //
    //     boolean result = artist.addSongToCatalog(song);
    //     assertTrue(result, "Song should be added to catalog");
    //     assertEquals(1, artist.getCatalog().size(), "Catalog should contain 1 song");
    // }

    // /**
    //  * Test Case 9:
    //  * Ensures that an artist cannot add a song with an invalid (empty) title.
    //  * Expected Result: Song is rejected and not added to catalog.
    //  */
    // @Test
    // public void testArtistAddSongFailTitle() {
    //     Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
    //     Song song = new Song("", "Artie", 180);
    //
    //     boolean result = artist.addSongToCatalog(song);
    //     assertFalse(result, "Song with invalid title should not be added");
    //     assertEquals(0, artist.getCatalog().size(), "Catalog should contain 0 songs");
    // }

    // /**
    //  * Test Case 10:
    //  * Ensures that an artist cannot add a song with an invalid (negative) duration.
    //  * Expected Result: Song is rejected and not added to catalog.
    //  */
    // @Test
    // public void testArtistAddSongFailDuration() {
    //     Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
    //     Song song = new Song("New Song", "Artie", -1);
    //
    //     boolean result = artist.addSongToCatalog(song);
    //     assertFalse(result, "Song with invalid duration should not be added");
    //     assertEquals(0, artist.getCatalog().size(), "Catalog should contain 0 songs");
    // }
}
