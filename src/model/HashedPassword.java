package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashedPassword {
    private String hash;

    public HashedPassword(String password) {
        try {
            // Get the md5 instsance
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Hash the password and convert to base64
            this.hash = Base64.getEncoder().encodeToString(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            System.err.println("MD5 algorithm not found");
            this.hash = "";
        }
    }

    public static HashedPassword fromHash(String hash) {
        HashedPassword hp = new HashedPassword("");

        hp.setHash(hash);

        return hp;
    }

    public String getHash() {
        return this.hash;
    }

    private void setHash(String hash) {
        this.hash = hash;
    }
}
