package controller;

import app.Application;
import app.SceneManager;
import app.SceneManager.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.exceptions.UserManagerException;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorLabel;

    private SceneManager sceneManager;
    private Application application;

    @FXML
    public void initialize() {
        this.sceneManager = SceneManager.getInstance();
        this.application = Application.getInstance();
    }

    public void login() {
        this.errorLabel.setText("");

        try {
            this.application.login(this.username.getText(), this.password.getText());

            this.sceneManager.setScene(Scene.CANVAS);
        } catch (UserManagerException e) {
            this.errorLabel.setText(e.getMessage());
        }
    }

    public void gotoSignUp() {
        this.sceneManager.setScene(Scene.SIGN_UP);
    }
}
