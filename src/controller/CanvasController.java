package controller;

import model.Canvas;
import model.User;
import model.shapes.Circle;
import model.shapes.Rectangle;
import model.shapes.Renderable;
import model.shapes.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import app.SceneManager;
import app.SceneManager.Scene;
import app.Application;

public class CanvasController {
    @FXML
    private ImageView profilePicture;
    @FXML
    private Label userName;

    @FXML
    private Slider zoomLevel;
    @FXML
    private Label zoomLevelLabel;

    @FXML
    private Group scrollPane;
    @FXML
    private Pane pane;

    @FXML
    private VBox sidebar;

    @FXML
    private ColorPicker canvasColor;

    @FXML
    private TextField canvasHeight;
    @FXML
    private TextField canvasWidth;

    @FXML
    private MenuItem deleteMenu;
    @FXML
    private MenuItem openTemplateMenu;
    @FXML
    private MenuItem saveTemplateMenu;

    private SceneManager sceneManager;
    private Application application;

    @FXML
    public void initialize() {
        this.sceneManager = SceneManager.getInstance();
        this.application = Application.getInstance();

        this.loadUser();

        Canvas canvas = this.application.getCanvas();
        canvas.setPane(this.pane);

        canvas.setWidth(500);
        canvas.setHeight(500);

        this.canvasHeight.setText(String.format("%.2f", canvas.getHeight()));
        this.canvasWidth.setText(String.format("%.2f", canvas.getWidth()));

        this.deleteMenu.setDisable(true);

        this.updateTemplateMenu();
    }

    public void updateTemplateMenu() {
        User user = this.application.getUser();
        boolean disabled = user == null || !user.getPremium();

        this.saveTemplateMenu.setDisable(disabled);
        this.openTemplateMenu.setDisable(disabled);
    }
    
    public void zoomDrag() {
        double zoomLevel = this.zoomLevel.getValue();

        this.application.getCanvas().setZoomLevel(zoomLevel / 100);

        this.zoomLevelLabel.setText(String.format("Zoom level: %.0f%%", zoomLevel));
    }

    public void showProfile() {
        this.sceneManager.openModal(Scene.PROFILE);
        this.loadUser();
        this.updateTemplateMenu();
    }

    public void showAbout() {
        this.sceneManager.openModal(Scene.ABOUT);
    }

    private void loadUser() {
        User user = application.getUser();

        this.userName.setText(user.getName());
        try {
            this.profilePicture.setImage(new Image(new FileInputStream(new File(user.getProfilePhoto()))));
        } catch (FileNotFoundException e) {
            // Continue without profile picture
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Error loading profile photo");
            a.setContentText(e.getMessage());
            a.show();
        }
    }

    // Methods relating to canvas manipulation
    public void canvasClick(MouseEvent e) {
        Renderable node = this.application.getCanvas().selectShape(e.getX(), e.getY());

        if (node != null) {
            this.sidebar.getChildren().setAll(
                node.getSidebar().stream()
                    .map(value -> value.getBox())
                    .collect(Collectors.toList())
            );
            this.deleteMenu.setDisable(false);
        } else this.clearSidebar();
    }

    public void clearSidebar() {
        this.sidebar.getChildren().clear();
        this.deleteMenu.setDisable(true);
    }

    public void setCanvasColor() {
        this.application.getCanvas().setBackgroundColor(this.canvasColor.getValue());
    }

    public void clearCanvas() {
        this.application.getCanvas().clear();
        this.canvasColor.setValue(Color.WHITE); 
    }

    public void addText() {
        this.application.getCanvas().addShape(new Text());
    }

    public void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.jpeg", "*.png"),
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File image = fileChooser.showOpenDialog(this.sceneManager.getStage());
        if (image != null) {
            this.application.getCanvas().addShape(new model.shapes.Image(image.getAbsolutePath()));
        }
    }

    public void addRectangle() {
        this.application.getCanvas().addShape(new Rectangle());
    }

    public void addCircle() {
        this.application.getCanvas().addShape(new Circle());
    }

    public void setCanvasHeight() {
        try {
            this.application.getCanvas().setHeight(Double.parseDouble(this.canvasHeight.getText()));
        } catch (NumberFormatException e) {
            // Can ignore
        }
    }

    public void setCanvasWidth() {
        try {
            this.application.getCanvas().setWidth(Double.parseDouble(this.canvasWidth.getText()));
        } catch (NumberFormatException e) {
            // Can ignore
        }
    }

    public void save() {
        // Generate the snapshot
        WritableImage image = this.pane.snapshot(new SnapshotParameters(), null);

        // Get a save location
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select save location");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("All Images", "*.png")
        );
        File saveLocation = fileChooser.showSaveDialog(this.sceneManager.getStage());

        if (saveLocation != null) {
            // Write the image
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", saveLocation);
            } catch (IOException e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText("Error saving image");
                a.setContentText(e.getMessage());
                a.show();
            }
        }
    }

    public void deleteSelected() {
        this.application.getCanvas().deleteSelected();
        this.clearSidebar();
    }

    public void saveTemplate() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select save location");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Canvas Template", "*.canvas")
        );
        File saveLocation = fileChooser.showSaveDialog(this.sceneManager.getStage());

        if (saveLocation != null) {
            String serialised = this.application.getCanvas().serialize();

            try (FileWriter output = new FileWriter(saveLocation)) {
                output.write(serialised);
            } catch (IOException e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText("Error saving template");
                a.setContentText(e.getMessage());
                a.show();
            }
        }
    }

    public void openTemplate() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select template");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Canvas Template", "*.canvas")
        );
        File templateLocation = fileChooser.showOpenDialog(this.sceneManager.getStage());

        if (templateLocation != null) {
            Canvas canvas = this.application.getCanvas();
            canvas.clear();
            canvas.load(templateLocation);

            this.canvasColor.setValue(canvas.getBackgroundColor());
            this.canvasHeight.setText(String.format("%.2f", canvas.getHeight()));
            this.canvasWidth.setText(String.format("%.2f", canvas.getWidth()));
        }
    }
}
