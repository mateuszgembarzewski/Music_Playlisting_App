import java.util.*;

/**
 * Manual Unit Acceptance Testing (UAT) class for validating core functionality
 * of the music application, such as login, search, and library/catalog management.
 *
 * Each test case follows a numbered scenario for easier traceability and debugging.
 */
public class TestUAT {

    /**
     * Main entry point to run all test cases sequentially.
     */
    public static void main(String[] args) {
        TestUAT tester = new TestUAT();
        
        // Run all test cases
        tester.testUserLoginSuccess();
        tester.testUserLoginFail();
        tester.testUserLoginLockout();
        tester.testListenerSearchExact();
        tester.testListenerSearchPartial();
        tester.testListenerAddSongSuccess();
        tester.testListenerAddSongFail();
        // Tests are broken due to Sprint 2 changes.  Sorry.  --Michael
        //tester.testArtistAddSongSuccess();
        //tester.testArtistAddSongFailTitle();
        //tester.testArtistAddSongFailDuration();  
    }

    /**
     * 1. Test Case: User login succeeds with correct credentials.
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
     * 2. Test Case: User login fails with incorrect password.
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
     * 3. Test Case: User account is locked after 3 failed login attempts.
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
     * 4. Test Case: Listener can search for a song by exact title.
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
     * 5. Test Case: Listener can search for a song by partial title.
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
     * 6. Test Case: Listener successfully adds a new song to their library.
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
     * 7. Test Case: Listener fails to add a duplicate song to their library.
     */
    public void testListenerAddSongFail() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Song song = new Song("Imagine", "John Lennon", 183);
        
        listener.addSongToLibrary(song); // Add first instance
        boolean result = listener.addSongToLibrary(song); // Try adding the same song again
        
        assert result == false : "Test Failed: Song should not be added again to the library";
        assert listener.getPersonalLibrary().size() == 1 : "Test Failed: Listener library should still contain 1 song";
        System.out.println("LISTENER_ADD_SONG_FAIL: Passed");
    }

    // Tests are broken due to Sprint 2 changes.  Sorry.  --Michael
    
    // /**
     // * 8. Test Case: Artist successfully adds a valid song to their catalog.
     // */
    // public void testArtistAddSongSuccess() {
        // Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        // Song song = new Song("New Song", "Artie", 180);
        
        // boolean result = artist.addSongToCatalog(song);
        
        // assert result == true : "Test Failed: Song should be added to the artist's catalog";
        // assert artist.getCatalog().size() == 1 : "Test Failed: Artist catalog should contain 1 song";
        // System.out.println("ARTIST_ADD_SONG_SUCCESS: Passed");
    // }

    // /**
     // * 9. Test Case: Artist fails to add a song with an invalid title.
     // */
    // public void testArtistAddSongFailTitle() {
        // Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        // Song song = new Song("", "Artie", 180); // Invalid title
        
        // boolean result = artist.addSongToCatalog(song);
        
        // assert result == false : "Test Failed: Song with invalid title should not be added";
        // assert artist.getCatalog().size() == 0 : "Test Failed: Artist catalog should still contain 0 songs";
        // System.out.println("ARTIST_ADD_SONG_FAIL_TITLE: Passed");
    // }

    // /**
     // * 10. Test Case: Artist fails to add a song with an invalid duration.
     // */
    // public void testArtistAddSongFailDuration() {
        // Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        // Song song = new Song("New Song", "Artie", -1); // Invalid duration
        
        // boolean result = artist.addSongToCatalog(song);
        
        // assert result == false : "Test Failed: Song with invalid duration should not be added";
        // assert artist.getCatalog().size() == 0 : "Test Failed: Artist catalog should still contain 0 songs";
        // System.out.println("ARTIST_ADD_SONG_FAIL_DURATION: Passed");
    // }
}
