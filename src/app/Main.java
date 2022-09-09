package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void init() throws Exception {
	}

    @Override
    public void start(Stage primaryStage) {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setStage(primaryStage);
        
        sceneManager.setScene(SceneManager.Scene.LOGIN);
    }

	@Override
	public void stop() throws Exception {
	}
    public static void main(String[] args) {
		launch(args);
    }
}
