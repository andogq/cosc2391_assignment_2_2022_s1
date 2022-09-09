package model.exceptions;

public class SubscriptionException extends Exception {
    public SubscriptionException(String message) {
        super(String.format("Subscription exception: %s", message));
    }
}
