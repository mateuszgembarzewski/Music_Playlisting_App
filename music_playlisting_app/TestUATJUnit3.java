import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void testAdminSys_AccessAdminUI() {
        
        //Admin adminUser = new Admin("testAdmin@gmail.com","testAdmin","GoodPass1", 102);
        
        // The reflect.Method library allows us to test private methods in other classes.
        // The getDeclaredMethods() method is from the reflections.method library.
        // This method requires passing in the private method name as a string.
        // And it also requires passing in an array of class objects.
        
        // Creating an instance of the main class
        // I want to access it's private method adminUI
        // Main mainClassInstance = new Main(); 
        
        // // Creating an empty array which will hold class objects of the main class.
        // Class<?>[] paramTypes = new Class<?>[1]; 
        
        // paramTypes[0] = Object.class;

        // Class<?> targetClass = mainClassInstance.getClass();
        
        // Method privMeth = targetClass.getDeclaredMethod("adminUI", paramTypes);
        
        // privMeth.setAccessible(true);
        
        
        // try {
                
        // //Method method = clazz.getDeclaredMethod("adminUI", Scanner.class , Object.class);
        
        // } catch (Exception e) {
    
            // System.out.println("Creating the reflection private method failed. ");
            
        // }
        
        
        
        //Class<?> paramTypes = new Class<?>[] {Scanner.class, Object.class};
        
        //Method myMethod = Main.getDeclaredMethod("adminUI", Object.class);

        
        
        
        try {
            Admin adminUser = new Admin("testAdmin@gmail.com","testAdmin","GoodPass1", 102);
            
            Class<?> clazz = Main.class; 
            Method adminUI = clazz.getDeclaredMethod("adminUI", Object.class);
            adminUI.setAccessible(true);
            
            Main mainClassInstance = new Main();
            
                try {
                    assertEquals(adminUI.invoke(mainClassInstance, adminUser), 2);
                } catch (Exception e) {
                    System.out.println("Error of some sort");
                }
            
            
        } catch (NoSuchMethodException e) {
            // Handle the case where the method is not found
            e.printStackTrace();
        } catch (SecurityException e) {
            // Handle security restrictions if applicable
            e.printStackTrace();
        }
            

                
    }
    
}