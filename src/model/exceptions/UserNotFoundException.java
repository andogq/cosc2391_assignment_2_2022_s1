package model.exceptions;

public class UserNotFoundException extends UserManagerException {
    public UserNotFoundException(String username) {
        super(String.format("User not found with username %s", username));
    }
}