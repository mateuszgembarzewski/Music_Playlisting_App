
/**
 * Write a description of class Artist here.
 *
 * @author Mateusz Gembarzewski
 * @version (a version number or a date)
 */
public class Artist extends User
{
    // instance variables - replace the example below with your own
    //private int artistID;
    /**
     * Constructor for objects of class Admin
     */
    public Artist(String username, String password, String email)//, int artistID)
    {
       super(email, username, password);
       //this.artistID = artistID;
    }
    
    public String toString() {
        return "Username: " + getUsername() + "Password: " + getPassword() + "Email: " + getEmail();
    }
}