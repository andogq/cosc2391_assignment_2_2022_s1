package model.exceptions;

public class UserExistsException extends UserManagerException {
    public UserExistsException(String username) {
        super(String.format("User already exists with username %s", username));
    }
}
