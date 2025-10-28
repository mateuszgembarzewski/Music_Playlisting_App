import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.Assert.*;

// This package allows me to test the private methods in the main class. 
// I could also just change the methods I wish to test to public.
// Sources: 
// https://medium.com/@AlexanderObregon/how-to-test-private-methods-in-java-ec1872e81911
//
import java.lang.reflect.Method;
// Need a scanner to pass in as a parameters into the getDeclaredMethod() method. 
import java.util.Scanner;


/**
 * The test class TestUATJUnit3.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TestUATJUnit3
{
    /**
     * Default constructor for test class TestUATJUnit3
     */
    public TestUATJUnit3()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    /*
     * Mateusz Gembarzewski
     * Software Testing
     * Oct 26th 2025
     * 
     * This test needs to access the USERS arraylist in the main method().
     * To access this arraylist, I created an instance of the main class. 
     * 
     * At which point I was able to test if the admin can login to the system
     * by creating a new admin account and then calling the authenticate() method
     * in the LoginService class to attempt to authenticate the new admin user.  
     * 
     * I compared the IDs of the passed in and returned users from the authenticate()
     * in order to verify if the login was successful. 
     * 
     * Test Successfully passed. 
     */
    
    @Test
    public void testAdminLogin() {
        
        Main mainClassInstance = new Main(); 
        
        //Giving this admin object an arbitrary userID of 101 
        Admin adminUser = new Admin("testAdmin@gmail.com","testAdmin","GoodPass1", 101);
        
        mainClassInstance.USERS.add(adminUser);
        
        LoginService ls = new LoginService();
           
        // parameters: user, pass, users arraylist
        User returnUser = ls.authenticate("testAdmin" , "GoodPass1" , mainClassInstance.USERS);
        
        assertEquals(adminUser.getId(), returnUser.getId());
        
    }
    
    @Test
    public void testAdminAccountCreation() {
        Main mainClassInstance = new Main();
        ArrayList<User> USERS = mainClassInstance.USERS;
        int before = USERS.size();
        
        mainClassInstance.adminCreatesAccount("test@test.com","Test123","Test1234@6","lis");
        
        int after = USERS.size();
        assertEquals("New User Account has been created", before + 1, after);
    }

    
    @Test
    public void testAdminAccountDeletion() {
        Main mainClassInstance = new Main();
        ArrayList<User> USERS = mainClassInstance.USERS;
        mainClassInstance.adminCreatesAccount("test@test.com","Test123","Test1234@6","lis");
        int before = USERS.size();
        mainClassInstance.adminDeleteAccount(0);
        
        int after = mainClassInstance.USERS.size();
        assertEquals("User Account has been deleted", before, after + 1);
    }
    
    @Test
    public void testAdminSongCreation() {
        Main mainClassInstance = new Main();
        SearchService CATALOG = mainClassInstance.CATALOG;
        int before  = CATALOG.getGlobalCatalog().size();
        
        Song s = new Song("disocsong", "iamadiscodancer", 60);
        mainClassInstance.adminAddSong(s);
        
        int after = CATALOG.getGlobalCatalog().size();
        assertEquals("New Song has been created", before + 1, after);
    }
}
