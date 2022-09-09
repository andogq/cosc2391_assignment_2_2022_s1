package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import app.Application;
import app.SceneManager;
import app.SceneManager.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.exceptions.UserExistsException;

public class SignUpController {
    @FXML
    private ImageView profilePicture;
    @FXML
    private Button profilePictureButton;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorLabel;

    private SceneManager sceneManager;
    private Application application;

    private String profilePicturePath;

    @FXML
    public void initialize() {
        this.sceneManager = SceneManager.getInstance();
        this.application = Application.getInstance();

        this.profilePicture.setVisible(false);
    }

    public void pickPicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select profile picture");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.jpeg", "*.png")
        );

        File image = fileChooser.showOpenDialog(this.sceneManager.getStage());
        if (image != null) {
            try {
                this.profilePicture.setImage(new Image(new FileInputStream(image)));

                this.profilePicture.setVisible(true);
                this.profilePictureButton.setVisible(false);

                this.profilePicturePath = image.getAbsolutePath();
            } catch (FileNotFoundException e) {
                this.errorLabel.setText("Problem loading profile picture. Please try again.");
            }
        }
    }

    public void gotoLogin() {
        this.sceneManager.setScene(Scene.LOGIN);
    }

    public void signUp() {
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        
        String error = null;
        if (firstName.length() == 0) error = "First name can't be empty";
        else if (lastName.length() == 0) error = "Last name can't be empty";
        else if (username.length() == 0) error = "Username can't be empty";
        else if (password.length() == 0) error = "Password can't be empty";
        else if (this.profilePicturePath == null || this.profilePicturePath.length() == 0) error = "Profile picture can't be empty";

        if (error == null) {
            try {
                this.application.signUp(this.firstName.getText(), this.lastName.getText(), this.username.getText(), this.password.getText(), this.profilePicturePath);
                
                this.sceneManager.setScene(Scene.CANVAS);
            } catch (UserExistsException e) {
                error = e.getMessage();
            }
        }

        if (error != null) this.errorLabel.setText(error);
    }
}
