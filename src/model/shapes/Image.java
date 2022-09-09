package model.shapes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.sidebar.DoubleValue;
import model.sidebar.SidebarValue;

public class Image implements Renderable {
    private javafx.scene.image.ImageView imageView;

    /* Renderable properties */
    private double x;
    private double y;
    private double[] dragStart;

    /* Shape specific properties */
    private String path;
    private double height;
    private double width;
    private double angle;

    private boolean headless;

    public Image(String path) {
        this(path, false);
    }

    public Image(String path, boolean headless) {
        this.imageView = new javafx.scene.image.ImageView();

        this.headless = headless;
        
        this.setPath(path);
        this.move(0, 0);
        this.setAngle(0);
    }

    /* Shape specific methods */
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;

        try {
            // Load image
            javafx.scene.image.Image image = new javafx.scene.image.Image(new FileInputStream(new File(path)));
            this.height = 1234;

            // Resize to fit image
            this.setHeight(image.getHeight());
            this.setWidth(image.getWidth());

            // Add image to view
            this.imageView.setImage(image);
        } catch (FileNotFoundException e) {
            if (!this.headless) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText("Image error");
                a.setContentText(String.format("File not found: {}", path));
                a.show();
            }
        }
    }
    
    public void setHeight(double height) {
        this.height = height;
        this.imageView.setFitHeight(height);
    }
    public void setWidth(double width) {
        this.width = width;
        this.imageView.setFitWidth(width);
    }
    public double getHeight() {
        return this.height;
    }
    public double getWidth() {
        return this.width;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.imageView.setRotate(angle);
    }

    public double getAngle() {
        return this.angle;
    }
    
    /* Renderable interface methods */
    @Override
    public String serialize() {
        return String.format("Image;%s;%.2f;%.2f;%.2f;%s", Renderable.super.serialize(), this.height, this.width, this.angle, this.path);
    }

    @Override
    public boolean inShape(double x, double y) {
        return (
            x >= this.x &&
            x <= this.x + this.width &&
            y >= this.y &&
            y <= this.y + this.height
        );
    }
    
    @Override
    public void move(double x, double y) {
        this.x = x;
        this.y = y;

        this.imageView.setX(x);
        this.imageView.setY(y);
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double[] getDragStart() {
        return this.dragStart;
    }

    @Override
    public void setDragStart(double[] position) {
        this.dragStart = position;
    }

    @Override
    public Node getNode() {
        return this.imageView;
    }

    @Override
    public ArrayList<SidebarValue> getSidebar() {
        ArrayList<SidebarValue> sidebar = new ArrayList<>();

        sidebar.add(new DoubleValue(
            "Height",
            this.height,
            height -> this.setHeight(height))
        );
        sidebar.add(new DoubleValue(
            "Width",
            this.width,
            width -> this.setWidth(width))
        );
        sidebar.add(new DoubleValue(
            "Rotation",
            this.angle,
            angle -> this.setAngle(angle))
        );
        
        return sidebar;
    }
}
