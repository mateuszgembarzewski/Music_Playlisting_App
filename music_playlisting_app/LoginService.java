import java.util.*;

public class LoginService {
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private Map<String, Integer> failedAttempts = new HashMap<>(); // track failed attempts by username
    private Map<String, Long> lockedUsers = new HashMap<>(); // track locked users and lockout time

    // Method to authenticate user
    public User authenticate(String username, String password, List<User> allUsers) {
        if (isLocked(username)) {
            System.out.println("❌ Account is locked due to too many failed login attempts.");
            return null;
        }

        User user = findUser(username, allUsers);
        if (user != null && user.getPassword().equals(password)) {
            resetFailedAttempts(username); // successful login
            System.out.println("✅ Login successful! Welcome " + username);
            return user;
        } else {
            handleFailedAttempt(username);
            return null;
        }
    }

    private User findUser(String username, List<User> allUsers) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

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

    private void lockUser(String username) {
        lockedUsers.put(username, System.currentTimeMillis());
    }

    private boolean isLocked(String username) {
        if (lockedUsers.containsKey(username)) {
            long lockTime = lockedUsers.get(username);
            if (System.currentTimeMillis() - lockTime < 600000) { // 10 minutes lockout
                return true;
            } else {
                lockedUsers.remove(username); // Unlock the user after 10 minutes
            }
        }
        return false;
    }

    private void resetFailedAttempts(String username) {
        failedAttempts.put(username, 0); // Reset the failed attempts after successful login
    }
}
