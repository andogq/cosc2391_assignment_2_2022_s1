package model.exceptions;

public class NotSubscribedException extends SubscriptionException {
    public NotSubscribedException() {
        super("User is not currently subscribed");
    }
}
