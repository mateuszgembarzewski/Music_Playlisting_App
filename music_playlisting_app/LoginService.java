import java.util.*;

public class LoginService {
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private Map<String, Integer> failedAttempts; // track failed attempts by username
    private Map<String, Long> lockedUsers; // track locked users and lockout time

    public LoginService() {
        failedAttempts = new HashMap<>();
        lockedUsers = new HashMap<>();
    }

    /**
     * Authenticate user with username and password.
     * @param username The username.
     * @param password The password.
     * @param allUsers List of all users to check against.
     * @return User if authentication is successful, null otherwise.
     */
    public User authenticate(String username, String password, List<User> allUsers) {
        if (isLocked(username)) {
            System.out.println("❌ Account is locked due to too many failed login attempts.");
            return null;
        }

        User user = findUser(username, allUsers);
        if (user != null && user.getPassword().equals(password)) {
            resetFailedAttempts(username); // successful login, reset failed attempts
            System.out.println("✅ Login successful! Welcome " + username);
            return user;
        } else {
            handleFailedAttempt(username);
            return null;
        }
    }

    /**
     * Find user by username.
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
     * Handle failed login attempts and lockout after too many failures.
     */
    private void handleFailedAttempt(String username) {
        failedAttempts.put(username, failedAttempts.getOrDefault(username, 0) + 1);
        int attempts = failedAttempts.get(username);
        
        if (attempts >= MAX_FAILED_ATTEMPTS) {
            lockUser(username);
            System.out.println("❌ Too many failed attempts. Your account has been locked.");
        } else {
            System.out.println("❌ Invalid login. Try again.");
        }
    }

    /**
     * Lock user account for 10 minutes.
     */
    private void lockUser(String username) {
        lockedUsers.put(username, System.currentTimeMillis());
    }

    /**
     * Check if the user is locked.
     */
    private boolean isLocked(String username) {
        if (lockedUsers.containsKey(username)) {
            long lockTime = lockedUsers.get(username);
            // Lockout duration: 10 minutes
            if (System.currentTimeMillis() - lockTime < 600000) {
                return true;
            } else {
                // Unlock the user after 10 minutes
                lockedUsers.remove(username);
            }
        }
        return false;
    }

    /**
     * Reset the failed attempts counter.
     */
    private void resetFailedAttempts(String username) {
        failedAttempts.put(username, 0);
    }
}
