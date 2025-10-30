import java.util.*;
import java.util.regex.*;
/**
 * Provides a service and object serving to validate user login credentials and provide
 * authentication functions.
*/
public class LoginService {
    
    // Represents failed login attempts, hashing keeps records of each failed attempt distinct.
    private final Map<String, Integer> failedAttempts = new HashMap<>();

    // Represents currently locked out users, hashing keeps sessions of lockout unique and distinctly stored.
    private final Map<String, Long> lockedUsers = new HashMap<>();

    /**
     * Authenticates a user provided they've entered a valid username and password.
     * Otherwise returns null and the login fails.
     * 
     * This is the function that allows a User access to the application.
     *
     * @param username  username for the user
     * @param password  password for the user
     * @param allUsers  The arrayList of all currently registrered users.
     *         
     * @return User an authenticated user (can only occur if the user's real credentials were entered
     */
    public User authenticate(String username, String password, List<User> allUsers) {
        if (isLocked(username)) {
            System.out.println("This account is locked out.  Try again later.");
            return null; // Do not attempt authentication at all.
        }

        // If user does not exist, we do not proceed and return null
        // If the user does exist and their valid password was entered, we authenticate the user.
        User user = findUser(username, allUsers);
        if (user != null && user.getPassword().equals(password)) {
            resetFailedAttempts(username); // On success, remove any failed attempts
            System.out.println("Login successful! Welcome " + username);
            return user;
        } else {
            handleFailedAttempt(username); // On failure, add a failed attempt
            return null;
        }
    }

    /**
     *  Getter for a specific registred User based on their username
     *  
     *  @param username  username for the user
     *  @param allUsers  The arrayList of all currently registered users.
     *  
     *  @return User object of the desired User
     */
    private User findUser(String username, List<User> allUsers) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user; // This user's username matches the username we are searching for
            }
        }
        return null; // No user was found to have this username, null is returned.
    }

    /**
     * Called when the user enters an incorrect password.
     * Appends a new failed login attempt to the list, or increments an existing one.
     * 
     * @param username  username for the user
     * @return void
     */
    private void handleFailedAttempt(String username) {
        failedAttempts.put(username, failedAttempts.getOrDefault(username, 0) + 1);
        int attempts = failedAttempts.get(username);

        // As soon as the attempts reach or exceed 3, lock the user.
        // Even if the attempts are greater than 3, the user should still be locked.
        if (attempts >= 3) { 
            lockUser(username);
            System.out.println("Too many failed attempts. Your account has been locked.");
        } else {
            System.out.println("Invalid login. Try again.");
        }
    }

    /**
     * Called when the user's failed login attempts reach 3
     * 
     * @param username  username for the user
     * @return void 
     */
    private void lockUser(String username) {
        lockedUsers.put(username, System.currentTimeMillis());  // Adds a distinct entry to the lockedUsers list
    }

    /**
     * Checks if a username currently exists on the list of locked users, and also if they should be unlocked.
     * 
     * @param username  username for the user
     * @return boolean true if the user exists on the list of locked users; false otherwise
     */
    private boolean isLocked(String username) {
        if (lockedUsers.containsKey(username)) {
            long lockTime = lockedUsers.get(username);
            // Checks if the lockout is still in place
            // If it is, return true.  Otherwise end the lockout.
            if (System.currentTimeMillis() - lockTime < 600_000) { 
                return true;
            } else {
                lockedUsers.remove(username); // Unlock after lockout period
            }
        }
        return false;
    }

    /**
     * Sets the count of failed login attempts for a specific username back to 0
     *
     * @param username  username for the user
     * @return void
     */
    private void resetFailedAttempts(String username) {
        failedAttempts.put(username, 0);
    }
    
    /**
     * Validates the username against a regular expression
     * Requires a username to begin with a letter, and then allow digits and underscores.
     * Username must be a minimum length of 6 characters, and maximum of 31.
     * 
     * @param username  username to be validated
     * @return boolean true if the username is validated by the RegEx, false otherwise;
     */
    public static boolean isValidUsername(String username) {
        String usernameRegex = "^[A-Za-z][A-Za-z0-9_]{5,30}$";
        Pattern pattern = Pattern.compile(usernameRegex);
        
        if (username == null) {
            return false;
        }
        return pattern.matcher(username).matches();
    }
    
    /**
     * Validates the email against a regular expression
     * Requires an email to contain a valid name, an @, domain name, and top level domain.
     * 
     * @param email  email to be validated
     * @return boolean true if the email is validated by the RegEx, false otherwise;
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }
    
    /**
     * Validates the password against a regular expression
     * Requires a password to use mixed-case characters, digits, and atleast one of a set of special characters.
     * Password must be a minimum length of 8 and a maximum length of 20.  
     * 
     * @param password  password to be validated
     * @return boolean true if the password is validated by the RegEx, false otherwise;
     */
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        
        if (password == null) {
            return false;
        } 
        return pattern.matcher(password).matches();
    }
        
}
