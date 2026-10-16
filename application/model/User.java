package model;

public class User {
    private String username;
    private String password;
    private boolean blocked;
    private int failedAttempts;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.blocked = false;
        this.failedAttempts = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    public void resetFailedAttempts() {
        this.failedAttempts = 0;
    }
}