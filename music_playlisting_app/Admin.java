
/**
 * Write a description of class Admin here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Admin extends User
{
    // instance variables - replace the example below with your own
    private int adminID;
    /**
     * Constructor for objects of class Admin
     */
    public Admin(String email, String username, String password, int adminID)
    {
       super(email, username, password);
       this.adminID = adminID;
    }
    
    public int getAdminID() {
        return adminID;
    }
    
}