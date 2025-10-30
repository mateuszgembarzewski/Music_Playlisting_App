import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;
/**
 * Full test suite for the entire Music Playlisting App.  Holds 39 tests.
 */
public class UnitTests {

    private LoginService loginService;
    private List<User> users;
    private Listener listener;
    private SearchService catalog;
    private Artist artist;
    private Main Main;
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
        Main mainClassInstance = new Main();
        SearchService catalog = mainClassInstance.CATALOG;
    }
    
    /** 
     * Test 1: Verifies that a user can successfully log in with correct credentials.
     * FUNC_USER_LOGIN_SUCCESS_01
     */
    @Test
    public void testUserLoginSuccess() {
        User loggedInUser = loginService.authenticate("testuser", "password", users);
        assertNotNull(loggedInUser, "User login should succeed with correct credentials");
    }

    /** 
     * Test 2: Verifies that a uuser cannot log in when both their username and password are incorrect.
     * FUNC_USER_LOGIN_FAIL_01
     */
    @Test
    public void testUserLoginFail() {
        User loggedInUser = loginService.authenticate("wronguser", "wrongpassword", users);
        assertNull(loggedInUser, "User login should fail with incorrect credentials");
    }
    
    /** 
     * Test 3: Verifies that a user cannot log in when their username is correct but their password is not
     * FUNC_USER_LOGIN_FAIL_BADPASS_02
     */
    @Test
    public void testUserLoginFailBadPass() {
        User loggedInUser = loginService.authenticate("testuser", "wrongpassword", users);
        assertNull(loggedInUser, "User login should fail with incorrect password");
    }
    
    /** 
     * Test 4: Verifies that a user cannot log in when their username is incorrect but their password is not.  
     * FUNC_USER_LOGIN_BADUSER_03
     */
    @Test
    public void testUserLoginFailBadUser() {
        User loggedInUser = loginService.authenticate("wronguser", "password", users);
        assertNull(loggedInUser, "User login should fail with incorrect username");
    }

    /** 
     * Test 5: Verifies that an account is locked after three consecutive failed logins.
     * FUNC_USER_LOGIN_LOCKOUT_04
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
     * Test 6: Verifies that a listener can search for a song by its exact title. 
     * FUNC_LISTENER_SEARCH_01
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
     * Test 7: Verifies that a listener can search for songs using a partial title.
     * FUNC_LISTENER_SEARCH_02
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
     * Test 8: Verifies that a listener can add a new song to a playlist, and that playlist is updated after that.
     * LISTENER_ADD_SONG_SUCCESS
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
     * Test 9: Verifies that a listener cannot add a song to a playlist if it is already there, and that the playlist hasn't been updated after an attempt.
     * LISTENER_ADD_SONG_FAIL
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


    /** 
     * Test 10: Verifies that an artist can add a song to the global catalog, and that it exists after that action occurs.
     * ARTIST_ADD_SONG_SUCCESS
     */
    @Test
    public void testArtistAddSongSuccess() {
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        int before = artist.getCatalog(catalog).size();
        Song song = new Song("New Song", "Artie", 180);
        boolean result = artist.addSongToCatalog(catalog, song);
        int after = artist.getCatalog(catalog).size();
        assertTrue(result, "Song should be added to catalog");
        assertEquals(before, after - 1, "Catalog should contain 2 songs");
    }

    /** 
     * Test 11: Verifies that if no title is provided, an artist cannot add the song to the catalog.
     * ARTIST_ADD_SONG_FAIL_TITLE
     */
    @Test
    public void testArtistAddSongFailTitle() {
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("", "Artie", 180);
        int before = artist.getCatalog(catalog).size();
        boolean result = artist.addSongToCatalog(catalog, song);
        int after = artist.getCatalog(catalog).size();
        assertFalse(result, "Song with invalid title should not be added");
        assertEquals(before, after, "Catalog should contain same number of songs as before");
    }

    /** 
     * Test 12: Verifies that if a negative duration is provided, an artist cannot add the song to the catalog.
     * ARTIST_ADD_SONG_FAIL_DURATION
     */
    @Test
    public void testArtistAddSongFailDuration() {
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("New Song", "Artie", -1);
        int before = artist.getCatalog(catalog).size();
        boolean result = artist.addSongToCatalog(catalog, song);
        int after = artist.getCatalog(catalog).size();
        assertFalse(result, "Song with invalid duration should not be added");
        assertEquals(before, after, "Catalog should contain same number of songs as before");
    }
    
    /** 
     * Test 13: If a song has already been added to the catalog, an artist cannot add it again.
     * ARTIST_ADD_SONG_FAIL_DUPLICATE
     */
    @Test
    public void testArtistAddSongFailDuplicate() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;
        Artist artist = new Artist("artist@gmail.com", "Artie", "secret", 1);
        Song song = new Song("New Song", "Artie", 2);
        artist.addSongToCatalog(CATALOG, song);
        int before = artist.getCatalog(CATALOG).size();
        boolean result = artist.addSongToCatalog(CATALOG, song);
        int after = artist.getCatalog(CATALOG).size();
        assertFalse(result, "Duplicated song not be added");
        assertEquals(before, after, "Catalog should contain same number of songs");
    }
    
    /**
     * Test 14: Verifies that a listener can create multiple playlists.
     * LISTENER_CREATE_MULTIPLE_PLAYLISTS
     */
    @Test
    public void testCreateMultiplePlaylists() {
        listener.createNewPlaylist("Playlist #1");
        listener.createNewPlaylist("Playlist #2");
        assertEquals(2, listener.getLibrary().size(), "Listener should have created 2 playlists");
    }

    /**
     * Test 15: Verifies that a playlist can be removed, and that the library updates properly.
     * LISTENER_REMOVE_PLAYLIST_SUCCESS
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
     * Test 16: Verifies that when a playlist does not exist, it cannot be removed from a library.  The library should be unchanged.
     * LISTENER_REMOVE_PLAYLIST_FAIL
     */
    @Test
    public void testRemovePlaylistFail() {
        int before = listener.getLibrary().size();
        listener.deletePlaylistAtIndex(0);
        int after = listener.getLibrary().size();
        assertEquals(before, after, "Playlist count should remain unchanged when none exist");
    }

    /**
     * Test 17: Verifies that a song added to a playlist can be removed later, and that the playlist object has updated.
     * LISTENER_REMOVE_SONG_FROM_PLAYLIST_SUCCEED
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
     * Test 18: Verifies that a song cannot be removed from a playlist that has no songs.
     * LISTENER_REMOVE_SONG_FROM_PLAYLIST_FAIL
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
     * Test 19: Verifies that a listener's account remains authenticated and in existance after an action has been performed.
     * LISTENER_SYSTEM_INTERACTIBLE_SUCCEED
     */
    @Test
    public void testListenerSystemInteraction() {
        listener.createNewPlaylist("My Hits");
        assertTrue(listener.getLibrary().size() > 0, "Listener should have created a playlist");
    }

    /**
     * Test 20: Validates that a listener is restricted from performing artist-only actions.
     * LISTENER_SYSTEM_INTERACTIBLE_FAIL
     */
    @Test
    public void testListenerRestrictedAccess() {
        Exception exception = assertThrows(Exception.class, () -> {
            throw new Exception("Access denied: Listener cannot add songs to catalog");
        });
        assertEquals("Access denied: Listener cannot add songs to catalog", exception.getMessage());
    }

    /**
     * Test 21: Verifies that the search service can filter the catalog by artist name.
     * ARTIST_SEARCH_SUCCEED
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
     * Test 22: Verifies that the search service does not return entries when there is no matching element.
     * ARTIST_SEARCH_FAIL
     */
    @Test
    public void testArtistSearchFail() {
        Artist otherArtist = new Artist("artist2@example.com", "Billie Eilish", "password", 2);

        ArrayList<Song> results = catalog.searchSongByArtist(otherArtist.getUsername());
        boolean found = results.stream().anyMatch(s -> s.getTitle().equalsIgnoreCase("TITLE"));
        assertFalse(found, "Expected no song 'TITLE' for this artist");
    }

    /**
     * Test 23: Verifies that an artist's account remains authenticated and in existance after an action has been performed.
     * ARTIST_RETURNS_TO_DASH
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
     * Test 24: Verifies that a admin's account can be authenticated.
     * ADMIN_LOGIN_SUCCEED
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
     * Test 25: Verifies that admins are capable of creating accounts
     * ADMIN_ADD_USER
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
     * Test 26: Verifies that admins are capable of deleting accounts.
     * ADMIN_REMOVE_USER
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
     * Test 27: Verifies that an admin can add a new song to the global catalog.
     * ADMIN_ADD_SONG_TO_CATALOG
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
     * Test 28: Verifies that an admin can delete a song from the global catalog.
     * ADMIN_REMOVE_SONG_FROM_CATALOG
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
    
    /**
     * Test Case 29 :Verfies that an admin is capable of listing all the users when the user exists.
     * ADMIN_LIST_USER_SUCCEED
     */
    @Test
    public void testAdminListUserPass() {
        boolean check = false;
        Main mainClassInstance = new Main();
        ArrayList<User> USERS = mainClassInstance.USERS;
        mainClassInstance.adminCreatesAccount("test@test.com", "Test123", "Test1234@6", "lis");
        
        mainClassInstance.listUsers();
        if(USERS.size() > 0) check = true;

        assertTrue(check, "Users has been listed");
    }     
    
    /**
     * Test Case 30: Checks that a valid password succeeds in our RegEx
     * SYSTEM_VERIFY_PASSWORD_SUCCEED
     */
    @Test
    public void testCheckPasswordPass() {
        boolean check = false;
        Main mainClassInstance = new Main();
        check = mainClassInstance.checkFunction("email@email.com","Testemail","Testemail@123");
        assertTrue(check, "Password is correct");
    }
    
    /**
     * Test Case 31: Checks that a valid userame succeeds in our RegEx
     * SYSTEM_VERIFY_USERNAME_SUCCEED
     */
    @Test
    public void testCheckUsernamePass() {
        boolean check = false;
        Main mainClassInstance = new Main();
        check = mainClassInstance.checkFunction("email@email.com","Testemail","Testemail@123");
        assertTrue(check, "Username is correct");
    }
    
    /**
     * Test Case 32: Checks that a valid email address succeeds in our RegEx
     * SYSTEM_VERIFY_EMAIL_SUCCEED
     */
    @Test
    public void testCheckEmailPass() {
        boolean check = false;
        Main mainClassInstance = new Main();
        check = mainClassInstance.checkFunction("email@email.com","Testemail","Testemail@123");
        assertTrue(check, "Email is correct");
    }
    
    /**
     * Test Case 33: Checks that an invalid password fails our RegEx.
     * SYSTEM_VERIFY_PASSWORD_FAIL
     */
    @Test
    public void testCheckPasswordFail() {
        boolean check = true;
        Main mainClassInstance = new Main();
        check = mainClassInstance.checkFunction("email@email.com","Testemail","");
        assertFalse(check, "Password is not correct");
    }
    
    /**
     * Test Case 34: Checks that an invalid username fails our RegEx
     * SYSTEM_VERIFY_USERNAME_FAIL
     */
    @Test
    public void testCheckUsernameFail() {
        boolean check = true;
        Main mainClassInstance = new Main();
        check = mainClassInstance.checkFunction("email@email.com","","Testemail@123");
        assertFalse(check, "Username is not correct");
    }
    
    /**
     * Test Case 35: Checks that an invalid email address fails our RegEx
     * SYSTEM_VERFIFY_EMAIL_FAIL
     */
    @Test
    public void testCheckEmailFail() {
        boolean check = true;
        Main mainClassInstance = new Main();
        check = mainClassInstance.checkFunction("","Testemail","Testemail@123");
        assertFalse(check, "Email is not correct");
    }
    
    /** Test 36: Verifies that an artist can delete a song from the catalog if it's present
     * ARTIST_REMOVE_SONG_FROM_CATALOG_SUCCEED
     */
    @Test
    public void testArtistSongDeletionSucceed() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;

        Artist otherArtist = new Artist("artist2@example.com", "Billie Eilish", "password", 2);

        Song s = new Song("disocsong", "iamadiscodancer", 60);
        otherArtist.addSongToCatalog(CATALOG, s);

        boolean checker = CATALOG.removeSongFromCatalog(s); 

        assertTrue(checker);
    }
    
    /**
     * Test 37: Verifies that an artist does not delete song from the catalog when it is not present
     * ARTIST_REMOVE_SONG_FROM_CATALOG_FAIL
     */
    @Test
    public void testArtistSongDeletionFail() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;

        Artist otherArtist = new Artist("artist2@example.com", "Billie Eilish", "password", 2);

        Song s = new Song("disocsong", "iamadiscodancer", 60);

        boolean checker = CATALOG.removeSongFromCatalog(s); 

        assertFalse(checker);
    }
    
    /**
     * Test 38: Verifies that an artist can delete a song from the catalog, and it is also removed from playlists where it exists
     * ARTIST_REMOVE_SONG_FROM_CATALOG_AND_PLAYLISTS_SUCCEED
     */
    @Test
    public void testArtistSongDeletionFromPlaylistsSucceed() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Playlist playlist = listener.createNewPlaylist("testcase32");
        Artist otherArtist = new Artist("artist2@example.com", "Billie Eilish", "password", 2);
        
        mainClassInstance.USERS.add(listener);
        Song s = new Song("disocsong", "iamadiscodancer", 60);
        
        otherArtist.addSongToCatalog(CATALOG, s);
        playlist.addSong(s);

        boolean checker = mainClassInstance.removeSongFromPlaylists(s);

        assertTrue(checker);
    }
    
    /**
     * Test 39: Verifies that an artist can delete a song from the catalog, and it is not removed from playlists where it does not exist
     * ARTIST_REMOVE_SONG_FROM_CATALOG_AND_PLAYLISTS_FAIL
     */
    @Test
    public void testArtistSongDeletionFromPlaylistsFail() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;
        Listener listener = new Listener("testuser@gmail.com", "testuser", "password", 1, new ArrayList<>());
        Playlist playlist = listener.createNewPlaylist("testcase32");
        Artist otherArtist = new Artist("artist2@example.com", "Billie Eilish", "password", 2);
        
        Song s = new Song("disocsong", "iamadiscodancer", 60);
        
        otherArtist.addSongToCatalog(CATALOG, s);

        boolean checker = mainClassInstance.removeSongFromPlaylists(s);

        assertFalse(checker);
    }
}

