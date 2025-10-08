/**
 * Abstract base class representing a user in the system.
 * <p>
 * A user has an email, username, password, and unique ID.
 * Subclasses such as {@code Artist} and {@code Listener} extend this class
 * to provide additional role-specific functionality.
 * </p>
 */
public abstract class User {

    /** The email address of the user. */
    private String email;

    /** The username of the user. */
    private String username;

    /** The password of the user. */
    private String password;

    /** The unique identifier assigned to the user. */
    private int id;

    /**
     * Constructs a new {@code User} instance.
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
     * Returns the email address of the user.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the username of the user.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the unique ID of the user.
     *
     * @return the user's unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the user's email address.
     *
     * @param email the new email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Updates the user's username.
     *
     * @param username the new username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Updates the user's password.
     *
     * @param password the new password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
