package model.exceptions;

public class AlreadySubscribedException extends SubscriptionException {
    public AlreadySubscribedException() {
        super("User already has subscription");
    }
}
