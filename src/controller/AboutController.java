package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import app.SceneManager;
import app.Application;

public class AboutController {
    @FXML
    private Label version;

    private SceneManager sceneManager;
    private Application application;

    @FXML
    public void initialize() {
        this.sceneManager = SceneManager.getInstance();
        this.application = Application.getInstance();
        
        this.version.setText(application.getVersion());
    }

    public void close() {
        this.sceneManager.closeModal();
    }
}
