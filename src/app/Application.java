package app;

import model.Canvas;
import model.UserManager;
import model.exceptions.UserExistsException;
import model.exceptions.UserManagerException;
import model.exceptions.UserNotFoundException;
import model.User;

public class Application {
    private final String version = "0.0.1";

    private UserManager userManager;
    private User user;
    private Canvas canvas;

    private static Application instance = null;

    private Application() {
        this.userManager = new UserManager();
        this.canvas = new Canvas(500, 500);
    }
    
    public static Application getInstance() {
        if (Application.instance == null) Application.instance = new Application();
        return Application.instance;
    }
    
    public void login(String username, String password) throws UserManagerException {
        this.user = this.userManager.login(username, password);
    }

    public void signUp(String firstName, String lastName, String username, String password, String profilePhoto) throws UserExistsException {
        User newUser = new User(firstName, lastName, username, password, profilePhoto);

        this.userManager.addUser(newUser);
        
        this.user = newUser;
    }

    public void updateUser(User user) throws UserNotFoundException {
        this.userManager.updateUser(user);
        this.user = user;
    }

    public void logout() {
        this.user = null;
    }

    public User getUser() {
        return this.user;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public String getVersion() {
        return this.version;
    }
}
