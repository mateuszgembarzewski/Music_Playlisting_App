import java.util.*;

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
public class TestUAT {

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
        TestUAT tester = new TestUAT();

        // Run all active test cases
        tester.testUserLoginSuccess();
        tester.testUserLoginFail();
        tester.testUserLoginLockout();
        tester.testListenerSearchExact();
        tester.testListenerSearchPartial();
        tester.testListenerAddSongSuccess();
        tester.testListenerAddSongFail();

        // Tests disabled pending Sprint 2 updates
        // tester.testArtistAddSongSuccess();
        // tester.testArtistAddSongFailTitle();
        // tester.testArtistAddSongFailDuration();
    }

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
     * Confirms that a listener can add a new song to their personal library.
     *
     * <p><b>Expected Result:</b> The song is successfully added, and the library
     * reflects one new entry.</p>
     */
    public void testListenerAddSongSuccess() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Song song = new Song("Imagine", "John Lennon", 183);

        boolean result = listener.addSongToLibrary(song);

        assert result == true : "Test Failed: Song should be added successfully to the library";
        assert listener.getPersonalLibrary().size() == 1 : "Test Failed: Listener library should contain 1 song";
        System.out.println("LISTENER_ADD_SONG_SUCCESS: Passed");
    }

    /**
     * Test Case 7:
     * Ensures that a listener cannot add duplicate songs to their personal library.
     *
     * <p><b>Expected Result:</b> Duplicate addition fails, and the library size remains unchanged.</p>
     */
    public void testListenerAddSongFail() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Song song = new Song("Imagine", "John Lennon", 183);

        listener.addSongToLibrary(song); // First addition
        boolean result = listener.addSongToLibrary(song); // Attempt duplicate

        assert result == false : "Test Failed: Song should not be added again to the library";
        assert listener.getPersonalLibrary().size() == 1 : "Test Failed: Listener library should still contain 1 song";
        System.out.println("LISTENER_ADD_SONG_FAIL: Passed");
    }

    /*
     * ------------------------------------------------------------------------
     * Disabled Artist Tests (Pending Sprint 2 Changes)
     * ------------------------------------------------------------------------
     */

    // /**
    //  * Test Case 8:
    //  * Validates that an artist can successfully add a valid song to their catalog.
    //  *
    //  * <p><b>Expected Result:</b> The song is added, and catalog count increases.</p>
    //  */
    // public void testArtistAddSongSuccess() {
    //     Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
    //     Song song = new Song("New Song", "Artie", 180);
    //
    //     boolean result = artist.addSongToCatalog(song);
    //
    //     assert result == true : "Test Failed: Song should be added to the artist's catalog";
    //     assert artist.getCatalog().size() == 1 : "Test Failed: Artist catalog should contain 1 song";
    //     System.out.println("ARTIST_ADD_SONG_SUCCESS: Passed");
    // }

    // /**
    //  * Test Case 9:
    //  * Ensures that an artist cannot add a song with an invalid (empty) title.
    //  *
    //  * <p><b>Expected Result:</b> The song is rejected and not added to the catalog.</p>
    //  */
    // public void testArtistAddSongFailTitle() {
    //     Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
    //     Song song = new Song("", "Artie", 180); // Invalid title
    //
    //     boolean result = artist.addSongToCatalog(song);
    //
    //     assert result == false : "Test Failed: Song with invalid title should not be added";
    //     assert artist.getCatalog().size() == 0 : "Test Failed: Artist catalog should still contain 0 songs";
    //     System.out.println("ARTIST_ADD_SONG_FAIL_TITLE: Passed");
    // }

    // /**
    //  * Test Case 10:
    //  * Ensures that an artist cannot add a song with an invalid (negative) duration.
    //  *
    //  * <p><b>Expected Result:</b> The song is rejected and not added to the catalog.</p>
    //  */
    // public void testArtistAddSongFailDuration() {
    //     Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
    //     Song song = new Song("New Song", "Artie", -1); // Invalid duration
    //
    //     boolean result = artist.addSongToCatalog(song);
    //
    //     assert result == false : "Test Failed: Song with invalid duration should not be added";
    //     assert artist.getCatalog().size() == 0 : "Test Failed: Artist catalog should still contain 0 songs";
    //     System.out.println("ARTIST_ADD_SONG_FAIL_DURATION: Passed");
    // }
}
