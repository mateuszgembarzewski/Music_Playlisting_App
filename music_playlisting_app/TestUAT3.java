import java.util.*;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * The test class TestUAT3.
 *
 * Converted from JUnit to plain Java with UAT-style test execution
 * using try/catch exception handling for pass/fail detection.
 */
public class TestUAT3
{
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

    public static void main(String[] args) {
        TestUAT3 tester = new TestUAT3();

        tester.testAdminLogin();
        tester.testAdminAccountCreation();
        tester.testAdminAccountDeletion();
        tester.testAdminSongCreation();
        tester.testAdminSongDeletion();
    }
}
