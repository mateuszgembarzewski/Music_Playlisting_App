import java.util.*;

public class TestUAT {

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
        tester.testArtistAddSongSuccess();
        tester.testArtistAddSongFailTitle();
        tester.testArtistAddSongFailDuration();  
    }

    // 1. Test Case: User Login Success
    public void testUserLoginSuccess() {
        LoginService loginService = new LoginService();
        List<User> users = new ArrayList<>();
        users.add(new Listener("testuser@gmail.com", "testuser", "password", 1));

        User loggedInUser = loginService.authenticate("testuser", "password", users);
        
        assert loggedInUser != null : "Test Failed: User login should succeed with correct credentials";
        System.out.println("FUNC_USER_LOGIN_SUCCESS_01: Passed");
    }

    // 2. Test Case: User Login Fail with Incorrect Password
    public void testUserLoginFail() {
        LoginService loginService = new LoginService();
        List<User> users = new ArrayList<>();
        users.add(new Listener("testuser@gmail.com", "testuser", "password", 1));

        User loggedInUser = loginService.authenticate("testuser", "wrongpassword", users);
        
        assert loggedInUser == null : "Test Failed: User login should fail with incorrect password";
        System.out.println("FUNC_USER_LOGIN_FAIL_01: Passed");
    }

    // 3. Test Case: Account lockout after 3 failed login attempts
    public void testUserLoginLockout() {
        LoginService loginService = new LoginService();
        List<User> users = new ArrayList<>();
        users.add(new Listener("testuser@gmail.com", "testuser", "password", 1));

        // Simulate 3 failed login attempts
        loginService.authenticate("testuser", "wrongpassword", users);
        loginService.authenticate("testuser", "wrongpassword", users);
        loginService.authenticate("testuser", "wrongpassword", users);

        User loggedInUser = loginService.authenticate("testuser", "password", users);
        
        assert loggedInUser == null : "Test Failed: Account should be locked after 3 failed attempts";
        System.out.println("FUNC_USER_LOGIN_LOCKOUT_04: Passed");
    }

    // 4. Test Case: Listener Search by Exact Title
    public void testListenerSearchExact() {
        SearchService searchService = new SearchService();
        Song song = new Song("Imagine", "John Lennon", 183);
        searchService.addSongToCatalog(song);
        
        List<Song> results = searchService.searchByTitle("Imagine");
        
        assert results.size() == 1 : "Test Failed: Search by exact title should return 1 result";
        assert results.get(0).getTitle().equals("Imagine") : "Test Failed: Search result title should be 'Imagine'";
        System.out.println("FUNC_LISTENER_SEARCH_01: Passed");
    }

    // 5. Test Case: Listener Search by Partial Title
    public void testListenerSearchPartial() {
        SearchService searchService = new SearchService();
        Song song = new Song("Shape of You", "Ed Sheeran", 233);
        searchService.addSongToCatalog(song);
        
        List<Song> results = searchService.searchByPartialTitle("Shape");
        
        assert results.size() == 1 : "Test Failed: Search by partial title should return 1 result";
        assert results.get(0).getTitle().equals("Shape of You") : "Test Failed: Search result title should be 'Shape of You'";
        System.out.println("FUNC_LISTENER_SEARCH_02: Passed");
    }

    // 6. Test Case: Listener Adding Song to Library (Success)
    public void testListenerAddSongSuccess() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1);
        Song song = new Song("Imagine", "John Lennon", 183);
        
        boolean result = listener.addSongToLibrary(song);
        
        assert result == true : "Test Failed: Song should be added successfully to the library";
        assert listener.getPersonalLibrary().size() == 1 : "Test Failed: Listener library should contain 1 song";
        System.out.println("LISTENER_ADD_SONG_SUCCESS: Passed");
    }

    // 7. Test Case: Listener Adding Duplicate Song to Library (Fail)
    public void testListenerAddSongFail() {
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1);
        Song song = new Song("Imagine", "John Lennon", 183);
        
        listener.addSongToLibrary(song); // Add first instance
        
        boolean result = listener.addSongToLibrary(song); // Try adding the same song again
        
        assert result == false : "Test Failed: Song should not be added again to the library";
        assert listener.getPersonalLibrary().size() == 1 : "Test Failed: Listener library should still contain 1 song";
        System.out.println("LISTENER_ADD_SONG_FAIL: Passed");
    }

    // 8. Test Case: Artist Adding Song to Catalog (Success)
    public void testArtistAddSongSuccess() {
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("New Song", "Artie", 180);
        
        boolean result = artist.addSongToCatalog(song);
        
        assert result == true : "Test Failed: Song should be added to the artist's catalog";
        assert artist.getCatalog().size() == 1 : "Test Failed: Artist catalog should contain 1 song";
        System.out.println("ARTIST_ADD_SONG_SUCCESS: Passed");
    }

    // 9. Test Case: Artist Adding Song with Invalid Title (Fail)
    public void testArtistAddSongFailTitle() {
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("", "Artie", 180); // Invalid title
        
        boolean result = artist.addSongToCatalog(song);
        
        assert result == false : "Test Failed: Song with invalid title should not be added";
        assert artist.getCatalog().size() == 0 : "Test Failed: Artist catalog should still contain 0 songs";
        System.out.println("ARTIST_ADD_SONG_FAIL_TITLE: Passed");
    }

    // 10. Test Case: Artist Adding Song with Invalid Duration (Fail)
    public void testArtistAddSongFailDuration() {
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("New Song", "Artie", -1); // Invalid duration
        
        boolean result = artist.addSongToCatalog(song);
        
        assert result == false : "Test Failed: Song with invalid duration should not be added";
        assert artist.getCatalog().size() == 0 : "Test Failed: Artist catalog should still contain 0 songs";
        System.out.println("ARTIST_ADD_SONG_FAIL_DURATION: Passed");
    }
}
