import java.util.*;
import java.util.regex.*;

public class LoginService {
    
    public static boolean isValidUsername(String username) {
        String usernameRegex = "^[A-Za-z][A-Za-z0-9_]{5,30}$";
        Pattern pattern = Pattern.compile(usernameRegex);
        return username !=null && pattern.matcher(username).matches();
    }
    
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email !=null && pattern.matcher(email).matches();
    }
    
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return password !=null && pattern.matcher(password).matches();
    }
    
    /**
     * Map tracking the number of failed login attempts by username.
     * <p>The key is the username, and the value is the number of failed attempts.</p>
     */
    private final Map<String, Integer> failedAttempts = new HashMap<>();

    /**
     * Map tracking locked users and the timestamp of when they were locked.
     * <p>The key is the username, and the value is the time (in milliseconds)
     * when the account was locked.</p>
     */
    private final Map<String, Long> lockedUsers = new HashMap<>();

    /**
     * Attempts to authenticate a user by validating username and password.
     *
     * <p>If the account is locked due to too many failed attempts,
     * authentication will fail immediately. On successful authentication,
     * failed attempts are reset.</p>
     *
     * @param username the username provided by the user
     * @param password the password provided by the user
     * @param allUsers the list of all registered users
     * @return the authenticated {@link User} if credentials are valid;
     *         {@code null} otherwise
     */
    public User authenticate(String username, String password, List<User> allUsers) {
        if (isLocked(username)) {
            System.out.println("Account is locked due to too many failed login attempts.");
            return null;
        }

        User user = findUser(username, allUsers);
        if (user != null && user.getPassword().equals(password)) {
            resetFailedAttempts(username); // Successful login
            System.out.println("Login successful! Welcome " + username);
            return user;
        } else {
            handleFailedAttempt(username);
            return null;
        }
    }

    /**
     * Finds a user by username from the provided list.
     *
     * @param username the username to search for
     * @param allUsers the list of all registered users
     * @return the matching {@link User}, or {@code null} if not found
     */
    private User findUser(String username, List<User> allUsers) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Handles a failed login attempt for the specified username.
     *
     * <p>Increments the failed attempt count and locks the account
     * if the maximum number of attempts is reached.</p>
     *
     * @param username the username that failed to authenticate
     */
    private void handleFailedAttempt(String username) {
        failedAttempts.put(username, failedAttempts.getOrDefault(username, 0) + 1);
        int attempts = failedAttempts.get(username);

        if (attempts >= 3) {
            lockUser(username);
            System.out.println("Too many failed attempts. Your account has been locked.");
        } else {
            System.out.println("Invalid login. Try again.");
        }
    }

    /**
     * Locks a user account by recording the current system time.
     *
     * @param username the username to lock
     */
    private void lockUser(String username) {
        lockedUsers.put(username, System.currentTimeMillis());
    }

    /**
     * Checks whether a user account is currently locked.
     *
     * <p>Accounts remain locked for 10 minutes after the lock time.
     * If the lockout period has expired, the user is automatically unlocked.</p>
     *
     * @param username the username to check
     * @return {@code true} if the user is locked; {@code false} otherwise
     */
    private boolean isLocked(String username) {
        if (lockedUsers.containsKey(username)) {
            long lockTime = lockedUsers.get(username);
            if (System.currentTimeMillis() - lockTime < 600_000) { // 10 minutes lockout
                return true;
            } else {
                lockedUsers.remove(username); // Unlock after lockout period
            }
        }
        return false;
    }

    /**
     * Resets the failed login attempts for a user.
     *
     * @param username the username whose attempts should be reset
     */
    private void resetFailedAttempts(String username) {
        failedAttempts.put(username, 0);
    }
}
