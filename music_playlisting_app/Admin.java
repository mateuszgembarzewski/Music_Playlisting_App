import java.util.Iterator;
import java.util.List;

/**
 * Class representing an Admin type of User.  Extends User class.
 * 
 * Most methods for the Admin are contained in Main due to their relation to the USERS list and global catalog.
 */
public class Admin extends User {
    /**
     * Constructor for Admin-type Users
     *
     * @param email    the email address of the admin
     * @param username the username of the admin
     * @param password the password of the admin
     * @param adminId  the unique ID of the admin
     */
    public Admin(String email, String username, String password, int adminId) {
        super(email, username, password, adminId);  // Inherits from User class
    }
    
    /**
     * Override for the toString() method; prints a description of the Admin
     *
     * @return String descriptor of an Admin object
     */
    @Override
    public String toString() {
        return "ID#" + super.getId() + " - '" + this.getUsername() + "' - " + this.getEmail() + " - ADMIN";
    }
    
    /**
     * Prints a specific output of data for the purposes of the Admin user type.
     * This is only called from the Admin's query functionality.
     * 
     * @return void
     */
    public void adminQuery() {
        System.out.println("=== " + this.getUsername() + "'s Data ===");
        System.out.println(toString());
    }
}
