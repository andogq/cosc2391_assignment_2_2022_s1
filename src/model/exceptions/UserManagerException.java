package model.exceptions;

public class UserManagerException extends Exception {
    public UserManagerException(String message) {
        super(String.format("User manager exception: %s", message));
    }
}
