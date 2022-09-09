package model.exceptions;

public class PasswordIncorrectException extends UserManagerException {
    public PasswordIncorrectException(String username) {
        super(String.format("Password incorrect for user %s", username));
    }
}
