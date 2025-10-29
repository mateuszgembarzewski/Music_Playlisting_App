import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * <p>This test suite validates administrative features of the Music Application,
 * focusing on account and catalog management.</p>
 *
 * <p>Covered Functional Areas:</p>
 * <ul>
 *   <li>Admin login authentication</li>
 *   <li>User account creation and deletion by admin</li>
 *   <li>Song catalog creation and deletion by admin</li>
 * </ul>
 *
 * <p>Each test interacts with the {@link Main}, {@link LoginService},
 * and {@link SearchService} classes to verify proper integration.</p>
 **/
public class TestUATJUnit3 {

    /**
     * <b>Test Case 1:</b> Verifies that an admin can successfully log in.
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
     * <b>Test Case 2:</b> Verifies that an admin can successfully create a user account.
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
     * <b>Test Case 3:</b> Verifies that an admin can delete a user account.
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
     * <b>Test Case 4:</b> Verifies that an admin can add a new song to the catalog.
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
     * <b>Test Case 5:</b> Verifies that an admin can delete a song from the catalog.
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
