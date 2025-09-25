/**
 * Abstract base class representing a user in the system.
 * A user has an email, username, password, and unique ID.
 * Subclasses (e.g., Artist, Listener) extend this class with additional functionality.
 */
public abstract class User {
    private String email;
    private String username;
    private String password;
    private int id;

    /**
     * Constructs a new User.
     *
     * @param email     the email address of the user
     * @param username  the username of the user
     * @param password  the password of the user
     * @param id        the unique ID of the user
     */
    public User(String email, String username, String password, int id) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    /**
     * @return the email of the user
     */
    public String getEmail() { return email; }

    /**
     * @return the username of the user
     */
    public String getUsername() { return username; }

    /**
     * @return the password of the user
     */
    public String getPassword() { return password; }

    /**
     * @return the unique ID of the user
     */
    public int getId() { return id; }

    /**
     * Updates the email of the user.
     *
     * @param email the new email address
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Updates the username of the user.
     *
     * @param username the new username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Updates the password of the user.
     *
     * @param password the new password
     */
    public void setPassword(String password) { this.password = password; }
}
