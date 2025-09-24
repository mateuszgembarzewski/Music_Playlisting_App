
/**
 * This is the class which defines the user accounts in our application. 
 *
 * @authors Mateusz Gembarzewski, Michael Scandiffio, Hitiksh Doshi
 * @project Music Playlisting App
 * @group   HMM 
 * @version 1
 * 
 * 
 */
public abstract class User
{
    // instance variables - replace the example below with your own
    private String email;
    public String username;
    private String password; 
    
    /**
     * Constructor for objects of class user
     */
    public User(String email, String username, String password)
    {
        // initialise instance variables
        this.email = email;
        this.username = username;
        this.password = password; 
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password; 
    }
    
    public String getEmail() {
        return email; 
    }

}