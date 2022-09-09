package app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SceneManager {
    public static enum Scene {
        SIGN_UP ("/view/SignUp.fxml"),
        LOGIN ("/view/Login.fxml"),
        CANVAS ("/view/Canvas.fxml"),
        PROFILE ("/view/Profile.fxml"),
        ABOUT ("/view/About.fxml");

        private String path;

        private Scene(String path) {
            this.path = path;
        }

        public String getPath() {
            return this.path;
        }
    }

    private static SceneManager instance = null;

    private Stage stage;
    private Stage currentModal;

    private SceneManager() {
        this.stage = null;
    }

    public static SceneManager getInstance() {
        if (SceneManager.instance == null) SceneManager.instance = new SceneManager();
        return SceneManager.instance;
    }

    public boolean setScene(Scene s) {
        javafx.scene.Scene scene = this.getScene(s);

        if (scene != null) {
            this.stage.setScene(scene);
            this.stage.centerOnScreen();
            this.stage.show();
            
            return true;
        } else return false;
    }

    public boolean openModal(Scene s) {
        Stage newWindow = new Stage();
        this.currentModal = newWindow;

        javafx.scene.Scene scene = this.getScene(s);
        
        if (scene != null) {
            newWindow.setScene(scene);
            newWindow.centerOnScreen();

            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(this.stage);

            newWindow.showAndWait();
        
            return true;
        } else {
            this.currentModal = null;
            return false;
        }
    }

    public void closeModal() {
        if (this.currentModal != null) {
            this.currentModal.close();
            this.currentModal = null;
        }
    }

    private javafx.scene.Scene getScene(Scene s) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s.getPath()));

        Pane root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            System.err.printf("Problem loading scene (%s)\n", s);
            System.err.println(e);
        }

        if (root != null) return new javafx.scene.Scene(root);
        else return null;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
