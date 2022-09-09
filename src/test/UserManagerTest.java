package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.User;
import model.UserManager;

public class UserManagerTest {
    private UserManager userManager;

    @Before
    public void init() throws model.exceptions.UserExistsException {
        this.userManager = new UserManager(true);
        
        this.userManager.addUser(new User("Test", "User", "test", "password", "/some/path"));
    }

    @Test
    public void testAddUser() {
        User user = new User("Test", "User", "test2", "password", "/some/path");

        try {    
            this.userManager.addUser(user);
        } catch (model.exceptions.UserExistsException e) {
            fail("UserExistsException thrown");
        }

        assertEquals("Two user is added", this.userManager.getUserCount(), 2);
    }

    @Test (expected = model.exceptions.UserExistsException.class)
    public void testAddUserDuplicateUsername() throws model.exceptions.UserExistsException {
        User user2 = new User("Test2", "User", "test", "password", "/some/path");

        this.userManager.addUser(user2);
    }

    @Test
    public void testLogin() {
        User user = null;

        try {
            user = this.userManager.login("test", "password");
        } catch (model.exceptions.UserManagerException e) {
            fail("UserManagerException thrown");
        }

        assertNotEquals("User shouldn't be null", user, null);
    }

    @Test (expected = model.exceptions.UserNotFoundException.class)
    public void testLoginNoUser() throws model.exceptions.UserNotFoundException {
        try {
            this.userManager.login("test2", "password");
        } catch (model.exceptions.PasswordIncorrectException e) {
            fail("PasswordIncorrectException thrown");
        }
    }

    @Test (expected = model.exceptions.PasswordIncorrectException.class)
    public void testLoginIncorrectPassword() throws model.exceptions.PasswordIncorrectException {
        try {
            this.userManager.login("test", "PASSWORD");
        } catch (model.exceptions.UserNotFoundException e) {
            fail("PasswordIncorrectException thrown");
        }
    }

    @Test
    public void testUpdateUser() {
        User newUser = new User("Test2", "User2", "test", "password2", "/some/path2");

        try {
            this.userManager.updateUser(newUser);
        } catch (model.exceptions.UserNotFoundException e) {
            fail("UserNotFoundException thrown");
        }

        assertEquals("User should be replaced", this.userManager.getUserCount(), 1);
    }

    @Test (expected = model.exceptions.UserNotFoundException.class)
    public void testUpdateUserNoUser() throws model.exceptions.UserNotFoundException {
        User newUser = new User("Test2", "User2", "test2", "password2", "/some/path2");

        this.userManager.updateUser(newUser);
    }
}
