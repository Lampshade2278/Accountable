package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class User implements AutoCloseable {

    // Attributes
    private byte[] passwordHash;

    // Constructors
    public User(String password) {
        setPassword(password);
    }

    // Getter
    public byte[] getPasswordHash() {
        return passwordHash.clone(); // Return a copy for security
    }

    // Setter
    public void setPassword(String password) {
        this.passwordHash = hashPassword(password);
    }

    private byte[] hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    // Validate password by rehashing and comparing
    public boolean validatePassword(String inputPassword) {
        byte[] inputHash = hashPassword(inputPassword);
        return Arrays.equals(this.passwordHash, inputHash);
    }

    // Clear the hashed password for security
    private void clearPasswordHash() {
        Arrays.fill(this.passwordHash, (byte) 0);
    }

    @Override
    public void close() {
        clearPasswordHash();
    }
}

