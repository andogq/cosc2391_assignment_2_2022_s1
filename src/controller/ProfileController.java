package controller;

import model.User;
import model.exceptions.UserManagerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import app.SceneManager;
import app.Application;

public class ProfileController {
    @FXML
    private ImageView profilePicture;
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private Label errorLabel;

    @FXML
    private Label premiumStatus;
    @FXML
    private Button upgradeButton;
    @FXML
    private Button downgradeButton;

    private String profilePicturePath;

    private SceneManager sceneManager;
    private Application application;

    @FXML
    public void initialize() {
        this.sceneManager = SceneManager.getInstance();
        this.application = Application.getInstance();

        User user = application.getUser();

        this.username.setText(user.getUsername());
        this.firstName.setText(user.getFirstName());
        this.lastName.setText(user.getLastName());

        this.profilePicturePath = user.getProfilePhoto();

        this.setPremium();

        try {
            this.profilePicture.setImage(new Image(new FileInputStream(new File(user.getProfilePhoto()))));
        } catch (FileNotFoundException e) {
            // Continue without profile picture
        }
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

                this.profilePicturePath = image.getAbsolutePath();
            } catch (FileNotFoundException e) {
                this.errorLabel.setText("Problem loading profile picture. Please try again.");
                this.profilePicturePath = null;
            }
        }
    }

    public void close() {
        this.sceneManager.closeModal();
    }

    public void save() {
        // Make sure that the fields are valid before closing
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();

        if (firstName.length() > 0 && lastName.length() > 0 && this.profilePicturePath != null && this.profilePicturePath.length() > 0) {
            // Save user data
            User user = this.application.getUser();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setProfilePhoto(this.profilePicturePath);

            try {
                this.application.updateUser(user);
            } catch (UserManagerException e) {
                this.errorLabel.setText(e.getMessage());
                return;
            }

            this.close();
        } else {
            this.errorLabel.setText("First name, last name and profile picture can't be empty");
        }

    }

    public void setPremium() {
        User user = this.application.getUser();

        String premiumStatus = "";
        boolean upgradeButton = false;

        if (user.getPremium()) {
            premiumStatus = "Premium";
            upgradeButton = false;
        } else {
            premiumStatus = "Not Premium";
            upgradeButton = true;
        }

        this.premiumStatus.setText(premiumStatus);
        this.upgradeButton.setDisable(!upgradeButton);
        this.downgradeButton.setDisable(upgradeButton);
    }

    public void upgrade() {
        this.application.getUser().subscribe();
        this.setPremium();
    }

    public void downgrade() {
        this.application.getUser().unsubscribe();
        this.setPremium();
    }
}
