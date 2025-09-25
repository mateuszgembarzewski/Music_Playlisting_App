import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Unit test suite for the music application using JUnit 5.
 *
 * <p>This class covers functional tests for:</p>
 * <ul>
 *   <li>User login and authentication flow</li>
 *   <li>Listener search functionality (exact and partial match)</li>
 *   <li>Library management (adding songs, preventing duplicates)</li>
 *   <li>Artist catalog management (valid and invalid song additions)</li>
 * </ul>
 *
 * Each test corresponds to a specific functional requirement to ensure
 * proper behavior of the core system components.
 */
public class TestUATJUnit {

    private LoginService loginService;
    private List<User> users;

    /**
     * Initializes test dependencies before each test case runs.
     * Sets up a {@link LoginService} instance and a test user.
     */
    @BeforeEach
    public void setup() {
        loginService = new LoginService();
        users = new ArrayList<>();
        users.add(new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>()));
    }

    /**
     * 1. Test Case: User login succeeds with correct credentials.
     */
    @Test
    public void testUserLoginSuccess() {
        User loggedInUser = loginService.authenticate("testuser", "password", users);
        assertNotNull(loggedInUser, "User login should succeed with correct credentials");
    }

    /**
     * 2. Test Case: User login fails with incorrect password.
     */
    @Test
    public void testUserLoginFail() {
        User loggedInUser = loginService.authenticate("testuser", "wrongpassword", users);
        assertNull(loggedInUser, "User login should fail with incorrect password");
    }

    /**
     * 3. Test Case: User account is locked after 3 failed login attempts.
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
     * 4. Test Case: Listener can search for a song by exact title.
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
     * 5. Test Case: Listener can search for a song by partial title.
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
     * 6. Test Case: Listener successfully adds a new song to their library.
     */
    @Test
    public void testListenerAddSongSuccess() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Song song = new Song("Imagine", "John Lennon", 183);

        boolean result = listener.addSongToLibrary(song);
        assertTrue(result, "Song should be added successfully to library");
        assertEquals(1, listener.getPersonalLibrary().size(), "Library should contain 1 song");
    }

    /**
     * 7. Test Case: Listener fails to add a duplicate song to their library.
     */
    @Test
    public void testListenerAddSongFail() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Song song = new Song("Imagine", "John Lennon", 183);

        listener.addSongToLibrary(song);
        boolean result = listener.addSongToLibrary(song);

        assertFalse(result, "Duplicate song should not be added");
        assertEquals(1, listener.getPersonalLibrary().size(), "Library should still contain 1 song");
    }

    /**
     * 8. Test Case: Artist successfully adds a valid song to their catalog.
     */
    @Test
    public void testArtistAddSongSuccess() {
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("New Song", "Artie", 180);

        boolean result = artist.addSongToCatalog(song);
        assertTrue(result, "Song should be added to catalog");
        assertEquals(1, artist.getCatalog().size(), "Catalog should contain 1 song");
    }

    /**
     * 9. Test Case: Artist fails to add a song with an invalid title.
     */
    @Test
    public void testArtistAddSongFailTitle() {
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("", "Artie", 180);

        boolean result = artist.addSongToCatalog(song);
        assertFalse(result, "Song with invalid title should not be added");
        assertEquals(0, artist.getCatalog().size(), "Catalog should contain 0 songs");
    }

    /**
     * 10. Test Case: Artist fails to add a song with an invalid duration.
     */
    @Test
    public void testArtistAddSongFailDuration() {
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("New Song", "Artie", -1);

        boolean result = artist.addSongToCatalog(song);
        assertFalse(result, "Song with invalid duration should not be added");
        assertEquals(0, artist.getCatalog().size(), "Catalog should contain 0 songs");
    }
}
