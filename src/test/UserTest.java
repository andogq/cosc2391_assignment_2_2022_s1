package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.User;

public class UserTest {
    public User user;

    @Before
    public void init() {
        this.user = new User("Test", "User", "test", "password", "/some/path");
    }

    @Test
    public void testSubscribe() {
        this.user.subscribe();
        
        assertTrue("User should be subscribed", this.user.getPremium());
    }

    @Test
    public void testUnsubscribe() {
        this.user.subscribe();

        this.user.unsubscribe();
        
        assertFalse("User should be unsubscribed", this.user.getPremium());
    }
}
