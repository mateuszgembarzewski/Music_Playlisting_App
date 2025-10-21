/**
 * Abstract class representing a User type object.
 * This class is never used directly, only as an extension of Listener/Artist/Admin.
 * 
 * @author Hitiksh Doshi
 * @author Mateusz Gembarzewski
 * @author Michael Scandiffio
 */
public abstract class User {
    // Represents the email address for the user
    private String email;

    // Represents the username for the user
    private String username;

    // Represents the password for the user
    private String password;

    // Represents a unique identifier for the user
    private int id;

    /**
     * Constructor for User class
     *
     * @param email     email address for the user
     * @param username  username for the user
     * @param password  password for the user
     * @param id        unique identifier for the user
     */
    public User(String email, String username, String password, int id) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    /**
     * Getter for the user's 'email'
     *
     * @return String storing the user's email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Setter for the user's 'email'
     *
     * @param email new value for 'email'
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the user's 'username'
     *
     * @return String storing the user's username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Setter for the user's 'username'
     *
     * @param username new value for 'username'
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the user's 'password'
     *
     * @return String storing the user's password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Setter for the user's 'password
     *
     * @param password new value for 'password'
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the user's 'id'
     *
     * @return int storing the user's unique identifier
     */
    public int getId() {
        return id;
    }
}
